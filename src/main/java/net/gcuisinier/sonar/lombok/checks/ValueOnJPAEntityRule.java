package net.gcuisinier.sonar.lombok.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static net.gcuisinier.sonar.lombok.CheckUtils.findAnnotation;
import static net.gcuisinier.sonar.lombok.CheckUtils.isAnEntity;

@Rule(key = "Lombok-ValueOnJPAEntity")
public class ValueOnJPAEntityRule extends IssuableSubscriptionVisitor {

    private static final String VALUE_ANNOTATION = "lombok.Value";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        checkClass(classTree);
        super.visitNode(tree);
    }

    private void checkClass(ClassTree classTree) {
        Symbol.TypeSymbol classSymbol = classTree.symbol();

        if (!isAnEntity(classSymbol)) {
            return;
        }

        if (classSymbol.metadata().isAnnotatedWith(VALUE_ANNOTATION)) {
            Optional<AnnotationTree> valueAnnotation = findAnnotation(classTree.modifiers(), VALUE_ANNOTATION);
            if (valueAnnotation.isPresent()) {
                reportIssue(
                        valueAnnotation.get(),
                        "@Value is not compatible with JPA @Entity. JPA requires mutable fields and a no-args constructor."
                );
            } else {
                // Fallback to reporting on class name if annotation not found
                reportIssue(
                        classTree.simpleName(),
                        "@Value is not compatible with JPA @Entity. JPA requires mutable fields and a no-args constructor."
                );
            }
        }
    }
}
