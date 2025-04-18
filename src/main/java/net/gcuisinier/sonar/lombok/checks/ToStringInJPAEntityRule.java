package net.gcuisinier.sonar.lombok.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.*;
import java.util.stream.Collectors;

import static net.gcuisinier.sonar.lombok.CheckUtils.*;

/**
 * @author gcuisinier (github.com/gcuisinier/)
 */
@Rule(key = "Lombok-ToStringJPA")
public class ToStringInJPAEntityRule extends IssuableSubscriptionVisitor {
    private static final Map<String, Boolean> JPA_RELATION_DEFAULTS;
    static {
        Map<String, Boolean> relationDefaults = new HashMap<>();
        // JEE annotations
        relationDefaults.put("javax.persistence.OneToOne", false);
        relationDefaults.put("javax.persistence.OneToMany", true);
        relationDefaults.put("javax.persistence.ManyToOne", false);
        relationDefaults.put("javax.persistence.ManyToMany", true);
        // Jakarta EE annotations
        relationDefaults.put("jakarta.persistence.OneToOne", false);
        relationDefaults.put("jakarta.persistence.OneToMany", true);
        relationDefaults.put("jakarta.persistence.ManyToOne", false);
        relationDefaults.put("jakarta.persistence.ManyToMany", true);
        JPA_RELATION_DEFAULTS = Collections.unmodifiableMap(relationDefaults);
    }

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {

        ClassTree cut = (ClassTree) tree;

        checkClass((ClassTree) cut);

        super.visitNode(tree);

    }

    private void checkClass(ClassTree classTree) {

        Symbol.TypeSymbol classSymbol = classTree.symbol();
        if (!isAnEntity(classSymbol) || !(isAnnotatedWithToString(classSymbol) || isAnnotatedWithData(classSymbol) ) ) {
            return;
        }

       getLazyJPAFieldsNonExcluded(classTree).forEach(field ->
                reportIssue(
                        field.simpleName(),
                        String.format("Field '%s' is a LAZY JPA relation and should be excluded from Lombok's @ToString",
                                field.simpleName().name())
                )
        );
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
        Symbol symbol = variableTree.symbol();
        SymbolMetadata metadata = symbol.metadata();

        for (Map.Entry<String, Boolean> relation : JPA_RELATION_DEFAULTS.entrySet()) {
            String annotation = relation.getKey();
            boolean defaultLazy = relation.getValue();

            if (metadata.isAnnotatedWith(annotation)) {
                return isAnnotationLazy(metadata, annotation, defaultLazy);
            }
        }
        return false;


    }

    private Boolean isAnnotationLazy(SymbolMetadata symbolMetadata, String annotationName, boolean defaultLazyValue) {
        return symbolMetadata.valuesForAnnotation(annotationName)
                .stream().filter(annotationValue -> annotationValue.name().equals("fetch"))
                .map(annotationValue -> annotationValue.value())
                .map(Symbol.class::cast)
                .map(symbol -> symbol.name().equals("LAZY"))
                .findFirst().orElse(defaultLazyValue);
    }


}
