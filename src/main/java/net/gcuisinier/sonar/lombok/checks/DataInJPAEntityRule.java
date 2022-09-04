package net.gcuisinier.sonar.lombok.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Collections;
import java.util.List;

import static net.gcuisinier.sonar.lombok.CheckUtils.isAnEntity;
import static net.gcuisinier.sonar.lombok.CheckUtils.isAnnotatedWithData;

/**
 * @author gcuisinier (github.com/gcuisinier/)
 */
@Rule(key = "Lombok-JPAWithData")
public class DataInJPAEntityRule extends IssuableSubscriptionVisitor {


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
        if (!isAnnotatedWithData(classSymbol)) {
            return;
        }

        reportIssue(classTree.simpleName(), "Using Lombok @Data annotation within JPA @Entity should be avoided");


    }


}
