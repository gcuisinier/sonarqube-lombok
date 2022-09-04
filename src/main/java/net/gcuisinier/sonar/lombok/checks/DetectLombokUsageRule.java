package net.gcuisinier.sonar.lombok.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Rule(key = "Lombok-CoreUsage")
public class DetectLombokUsageRule extends IssuableSubscriptionVisitor {


    public static final List<String> CLASS_ANNOTATIONS = Arrays.asList("lombok.ToString",
            "lombok.EqualsAndHashCode",
            "lombok.RequiredArgsConstructor",
            "lombok.AllArgsConstructor",
            "lombok.Data",
            "lombok.Builder",
            "lombok.ToString",
            "lombok.Value",
            "lombok.extern.java.Log",
            "lombok.extern.slf4j.Slf4j");

    public static final List<String> METHOD_ANNOTATIONS = Arrays.asList("lombok.Getter",
            "lombok.Setter",
            "lombok.ToString.Exclude",
            "lombok.ToString.Include",
            "lombok.EqualsAndHashCode.Exclude",
            "lombok.EqualsAndHashCode.Include",
            "lombok.SneakyThrows",
            "lombok.ToString");

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
    }

    private void checkClass(ClassTree classTree) {

        Symbol.TypeSymbol classSymbol = classTree.symbol();

        // Check annotations at class level
        boolean isClassAnnotatedWithLombok = CLASS_ANNOTATIONS.stream().anyMatch(classSymbol.metadata()::isAnnotatedWith);

        boolean areMethodsOrVariablesAnnotated = classSymbol.memberSymbols().stream()
                .filter(m -> m.isMethodSymbol() || m.isVariableSymbol())
                .anyMatch(symbol -> {
                    return METHOD_ANNOTATIONS.stream().anyMatch(symbol.metadata()::isAnnotatedWith);
                });

        if (isClassAnnotatedWithLombok || areMethodsOrVariablesAnnotated)
            reportIssue(classTree.simpleName(), "This class is using Lombok annotations");

    }
}
