package net.gcuisinier.sonar.lombok.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Collections;
import java.util.List;

import static net.gcuisinier.sonar.lombok.CheckUtils.findAnnotation;

/**
 * This rule detects when @Builder annotation is used on a class that extends another class.
 * By default, @Builder doesn't include fields from the parent class, which can lead to incomplete objects.
 * 
 * @author gcuisinier (github.com/gcuisinier/)
 */
@Rule(key = "Lombok-BuilderWithInheritance")
public class BuilderWithInheritanceRule extends IssuableSubscriptionVisitor {

    private static final String BUILDER_ANNOTATION = "lombok.Builder";
    private static final String SUPER_BUILDER_ANNOTATION = "lombok.experimental.SuperBuilder";

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
        
        // Skip if the class doesn't extend another class
        if (classTree.superClass() == null) {
            return;
        }
        
        // Check if the class has @Builder annotation
        if (classSymbol.metadata().isAnnotatedWith(BUILDER_ANNOTATION)) {
            // Check if the class also has @SuperBuilder annotation
            if (!classSymbol.metadata().isAnnotatedWith(SUPER_BUILDER_ANNOTATION)) {
                findAnnotation(classTree.modifiers(), BUILDER_ANNOTATION)
                        .ifPresent(annotation ->
                                reportIssue(
                                        annotation,
                                        "Avoid using @Builder on a class that extends another one."
                                )
                        );
            }
        }
    }
}