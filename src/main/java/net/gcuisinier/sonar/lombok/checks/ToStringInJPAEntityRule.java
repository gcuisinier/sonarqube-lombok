package net.gcuisinier.sonar.lombok.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static net.gcuisinier.sonar.lombok.CheckUtils.isAnEntity;
import static net.gcuisinier.sonar.lombok.CheckUtils.isAnnotatedWithToString;

/**
 * @author gcuisinier (github.com/gcuisinier/)
 */
@Rule(key = "Lombok-ToStringJPA")
public class ToStringInJPAEntityRule extends IssuableSubscriptionVisitor {
    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.COMPILATION_UNIT);
    }

    @Override
    public void visitNode(Tree tree) {

        CompilationUnitTree cut = (CompilationUnitTree) tree;

        cut.types().stream()
                .filter(typeTree -> typeTree.is(Tree.Kind.CLASS))
                .forEach(typeTree -> checkClass((ClassTree) typeTree));

        super.visitNode(tree);

    }

    private void checkClass(ClassTree classTree) {

        Symbol.TypeSymbol classSymbol = classTree.symbol();
        if (!isAnEntity(classSymbol) || !isAnnotatedWithToString(classSymbol)) {
            return;
        }

        Set<VariableTree> lazyJPAField = getLazyJPAFieldsNonExcluded(classTree);
        lazyJPAField.forEach(s -> reportIssue(s.type(), "JPA lazy relations should be excluded from Lombok toString"));

    }

    private Set<VariableTree> getLazyJPAFieldsNonExcluded(ClassTree classTree) {
        Set<VariableTree> getLazyJPAFieldsNonExcluded = classTree.members().stream()
                .filter(tree -> tree.is(Tree.Kind.VARIABLE))
                .map(VariableTree.class::cast)
                .filter(this::isLazyRelation)
                .filter(this::notExcluded)
                .collect(Collectors.toSet());
        return getLazyJPAFieldsNonExcluded;
    }

    private boolean notExcluded(VariableTree variableTree) {
        boolean hasExcludeAnnotation = variableTree.symbol().metadata().isAnnotatedWith("lombok.ToString$Exclude");
        return !hasExcludeAnnotation;
    }


    private boolean isLazyRelation(Tree tree) {
        VariableTree variableTree = (VariableTree) tree;
        if (variableTree.symbol().metadata().isAnnotatedWith("javax.persistence.OneToMany")) {
            return checkAnnotationLazyValue(variableTree, "javax.persistence.OneToMany", true);
        } else if (variableTree.symbol().metadata().isAnnotatedWith("javax.persistence.OneToOne")) {
            return checkAnnotationLazyValue(variableTree, "javax.persistence.OneToOne", false);
        } else if (variableTree.symbol().metadata().isAnnotatedWith("javax.persistence.ManyToOne")) {
            return checkAnnotationLazyValue(variableTree, "javax.persistence.ManyToOne", false);
        } else if (variableTree.symbol().metadata().isAnnotatedWith("javax.persistence.ManyToMany")) {
            return checkAnnotationLazyValue(variableTree, "javax.persistence.ManyToMany", true);
        }
        return false;
    }

    private Boolean checkAnnotationLazyValue(VariableTree variableTree, String annotationName, boolean defaultLazyValue) {
        return variableTree.symbol().metadata().valuesForAnnotation(annotationName)
                .stream().filter(annotationValue -> annotationValue.name().equals("fetch"))
                .map(annotationValue -> annotationValue.value())
                .map(Symbol.class::cast)
                .map(symbol -> symbol.name().equals("LAZY"))
                .findFirst().orElse(defaultLazyValue);
    }


}
