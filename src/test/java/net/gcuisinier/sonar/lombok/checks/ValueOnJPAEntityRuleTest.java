package net.gcuisinier.sonar.lombok.checks;

import net.gcuisinier.sonar.lombok.utils.FilesUtils;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class ValueOnJPAEntityRuleTest {

    @Test
    void detected() {
        ValueOnJPAEntityRule rule = new ValueOnJPAEntityRule();

        CheckVerifier.newVerifier()
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .onFile("src/test/files/ValueOnJPAEntityRule.java")
                .withCheck(rule)
                .verifyIssues();
    }
}