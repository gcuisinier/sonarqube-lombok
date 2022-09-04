package net.gcuisinier.sonar.lombok;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata;

import java.util.List;
import java.util.Optional;

public class CheckUtils {

    public static boolean isAnEntity(Symbol.TypeSymbol classSymbol) {
        return classSymbol.metadata().isAnnotatedWith("javax.persistence.Entity");
    }

    public static boolean isAnnotatedWithEqualsAndHashcode(Symbol.TypeSymbol classSymbol) {
        return classSymbol.metadata().isAnnotatedWith("lombok.EqualsAndHashCode");
    }

    public static boolean isAnnotatedWithToString(Symbol.TypeSymbol classSymbol) {
        return classSymbol.metadata().isAnnotatedWith("lombok.ToString");
    }

    public static boolean isAnnotatedWithNoArgsConstructor(Symbol.TypeSymbol classSymbol) {
        return classSymbol.metadata().isAnnotatedWith("lombok.NoArgsConstructor");
    }

    public static boolean isAnnotatedWithData(Symbol.TypeSymbol classSymbol) {
        return classSymbol.metadata().isAnnotatedWith("lombok.Data");
    }

    public static Optional<SymbolMetadata.AnnotationValue> getOnlyExplicitlyIncludedValueAnnotation(Symbol.TypeSymbol classSymbol) {
        List<SymbolMetadata.AnnotationValue> annotationValues = classSymbol.metadata().valuesForAnnotation("lombok.EqualsAndHashCode");
        if (annotationValues == null) return Optional.empty();
        Optional<SymbolMetadata.AnnotationValue> annotation = annotationValues.stream().filter(m -> "onlyExplicitlyIncluded".equals(m.name())).findFirst();
        return annotation;
    }


}
