package net.gcuisinier.sonar.lombok.checks;

import net.gcuisinier.sonar.lombok.utils.FilesUtils;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

import java.io.File;
import java.util.Collections;

class AvoidSynchronizedAnnotationRuleTest {

    @Test
    void detected() {

        AvoidSynchronizedAnnotationRule rule = new AvoidSynchronizedAnnotationRule();

        CheckVerifier.newVerifier()
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .onFile("src/test/files/AvoidAnnotationRule.java")
                .withCheck(rule)
                .verifyIssues();
    }
}
