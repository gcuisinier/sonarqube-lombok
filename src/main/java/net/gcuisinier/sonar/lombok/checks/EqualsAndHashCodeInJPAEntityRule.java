package net.gcuisinier.sonar.lombok.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static net.gcuisinier.sonar.lombok.CheckUtils.*;

/**
 * @author gcuisinier (github.com/gcuisinier/)
 */
@Rule(key = "Lombok-JPAWithEqualsAndHashCode")
public class EqualsAndHashCodeInJPAEntityRule extends IssuableSubscriptionVisitor {


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

        if (!isAnEntity(classSymbol)) {
            return;
        }

        if (!isAnnotatedWithEqualsAndHashcode(classSymbol)) {
            return;
        }

        Optional<SymbolMetadata.AnnotationValue> annotationParam = getOnlyExplicitlyIncludedValueAnnotation(classSymbol);

        if (annotationParam.isPresent()) {
            if (annotationParam.get().value().equals(true))
                return;
            else
                reportIssue(classTree.simpleName(), "Lombok @EqualsAndHashCode annotation used incorrectly with JPA @Entity");

        } else {
            reportIssue(classTree.simpleName(), "Lombok @EqualsAndHashCode annotation used incorrectly with JPA @Entity");
        }


    }


}
