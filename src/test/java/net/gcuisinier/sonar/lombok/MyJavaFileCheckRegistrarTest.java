package net.gcuisinier.sonar.lombok;

import org.junit.jupiter.api.Test;
import org.sonar.plugins.java.api.CheckRegistrar;

import static org.assertj.core.api.Assertions.assertThat;

class MyJavaFileCheckRegistrarTest {

    @Test
    void checkNumberRules() {
        CheckRegistrar.RegistrarContext context = new CheckRegistrar.RegistrarContext();

        LombokFileCheckRegistrar registrar = new LombokFileCheckRegistrar();
        registrar.register(context);

        assertThat(context.checkClasses()).hasSize(1);
        assertThat(context.testCheckClasses()).hasSize(0);
    }

}
