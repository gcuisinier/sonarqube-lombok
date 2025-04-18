package net.gcuisinier.sonar.lombok.checks;

import net.gcuisinier.sonar.lombok.utils.FilesUtils;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class AllArgsConstructorInJPAEntityRuleTest {

    @Test
    void detected() {
        AllArgsConstructorInJPAEntityRule rule = new AllArgsConstructorInJPAEntityRule();

        CheckVerifier.newVerifier()
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .onFile("src/test/files/AllArgsConstructorInJPAEntityRule.java")
                .withCheck(rule)
                .verifyIssues();
    }
}