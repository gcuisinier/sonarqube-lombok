package net.gcuisinier.sonar.lombok.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata;
import org.sonar.plugins.java.api.tree.AnnotationTree;
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

        if (!isAnEntity(classSymbol)) {
            return;
        }

        boolean hasEqualsAndHashCode = isAnnotatedWithEqualsAndHashcode(classSymbol);
        boolean hasData = isAnnotatedWithData(classSymbol);

        if (!(hasEqualsAndHashCode || hasData)) {
            return;
        }

        Optional<SymbolMetadata.AnnotationValue> annotationParam = getOnlyExplicitlyIncludedValueAnnotation(classSymbol);

        if (!annotationParam.map(p -> Boolean.TRUE.equals(p.value())).orElse(false)) {
            // Try to find the annotation to report on
            Optional<AnnotationTree> annotation = Optional.empty();

            if (hasEqualsAndHashCode) {
                annotation = findAnnotation(classTree.modifiers(), "lombok.EqualsAndHashCode");
            }

            if (!annotation.isPresent() && hasData) {
                annotation = findAnnotation(classTree.modifiers(), "lombok.Data");
            }

            if (annotation.isPresent()) {
                reportIssue(annotation.get(), "Lombok @EqualsAndHashCode should not be used on JPA @Entity");
            } else {
                // Fallback to reporting on class name if annotation not found
                reportIssue(classTree.simpleName(), "Lombok @EqualsAndHashCode should not be used on JPA @Entity");
            }
        }
    }


}
