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

import static net.gcuisinier.sonar.lombok.CheckUtils.*;

/**
 * Rule to check that JPA Entity classes are not using @AllArgsConstructor without a default constructor.
 * JPA requires a default no-args constructor for entity classes.
 * 
 * @author gcuisinier (github.com/gcuisinier/)
 */
@Rule(key = "Lombok-JPAWithAllArgsConstructor")
public class AllArgsConstructorInJPAEntityRule extends IssuableSubscriptionVisitor {

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

        // Check if the class is a JPA entity
        if (!isAnEntity(classSymbol)) {
            return;
        }

        // Check if the class has @AllArgsConstructor annotation
        if (!isAnnotatedWithAllArgsConstructor(classSymbol)) {
            return;
        }

        // Check if the class has a default constructor (either explicitly or via @NoArgsConstructor)
        boolean hasNoArgsConstructor = isAnnotatedWithNoArgsConstructor(classSymbol);
        boolean hasExplicitDefaultConstructor = hasExplicitDefaultConstructor(classTree);
        boolean hasDefaultConstructor = hasNoArgsConstructor || hasExplicitDefaultConstructor;

        if (!hasDefaultConstructor) {
            Optional<AnnotationTree> allArgsAnnotation = findAnnotation(classTree.modifiers(), "lombok.AllArgsConstructor");
            if (allArgsAnnotation.isPresent()) {
                reportIssue(allArgsAnnotation.get(), 
                    "@AllArgsConstructor should not be used alone on JPA Entity");
            } else {
                // Fallback to reporting on class name if annotation not found
                reportIssue(classTree.simpleName(), 
                    "@AllArgsConstructor should not be used alone on JPA Entity");
            }
        }
    }

    /**
     * Check if the class has an explicit default constructor
     */
    private boolean hasExplicitDefaultConstructor(ClassTree classTree) {
        return classTree.members().stream()
                .filter(member -> member.is(Tree.Kind.CONSTRUCTOR))
                .anyMatch(constructor -> ((org.sonar.plugins.java.api.tree.MethodTree) constructor).parameters().isEmpty());
    }
}
