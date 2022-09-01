package net.gcuisinier.sonar.lombok;

import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonarsource.api.sonarlint.SonarLintSide;

import java.util.List;


@SonarLintSide
public class LombokFileCheckRegistrar implements CheckRegistrar {


    public static List<Class<? extends JavaCheck>> checkClasses() {
        return RulesList.getJavaChecks();
    }

    public static List<Class<? extends JavaCheck>> testCheckClasses() {
        return RulesList.getJavaTestChecks();
    }

    @Override
    public void register(RegistrarContext registrarContext) {
        // Call to registerClassesForRepository to associate the classes with the correct repository key
        registrarContext.registerClassesForRepository(LombokRulesDefinition.REPOSITORY_KEY, checkClasses(), testCheckClasses());
    }
}
