package net.gcuisinier.sonar.lombok.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Collections;
import java.util.List;

import static net.gcuisinier.sonar.lombok.CheckUtils.*;

/**
 * Rule that checks for the use of Lombok's @Data annotation on JPA entities.
 * @author gcuisinier (github.com/gcuisinier/)
 */
@Rule(key = "Lombok-JPAWithData")
public class DataInJPAEntityRule extends IssuableSubscriptionVisitor {

    private static final String DATA_ANNOTATION = "lombok.Data";


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
        if (!isAnnotatedWithData(classSymbol)) {
            return;
        }

        findAnnotation(classTree.modifiers(), DATA_ANNOTATION)
                .ifPresent(annotation ->
                        reportIssue(annotation, 
                            "Using Lombok @Data annotation within JPA @Entity should be avoided.")
                );
    }


}
