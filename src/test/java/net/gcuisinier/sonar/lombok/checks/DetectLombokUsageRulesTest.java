package net.gcuisinier.sonar.lombok.checks;

import net.gcuisinier.sonar.lombok.utils.FilesUtils;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class DetectLombokUsageRulesTest {

    @Test
    void detected() {

        DetectLombokUsageRule rule = new DetectLombokUsageRule();

        CheckVerifier.newVerifier()
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .onFile("src/test/files/DetectLombokUsageRule.java")
                .withCheck(rule)
                .verifyIssues();
    }

}