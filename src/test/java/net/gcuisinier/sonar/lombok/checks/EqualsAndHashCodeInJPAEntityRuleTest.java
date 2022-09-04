package net.gcuisinier.sonar.lombok.checks;

import net.gcuisinier.sonar.lombok.utils.FilesUtils;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class EqualsAndHashCodeInJPAEntityRuleTest {

    @Test
    void detected() {

        EqualsAndHashCodeInJPAEntityRule rule = new EqualsAndHashCodeInJPAEntityRule();

        CheckVerifier.newVerifier()
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .onFile("src/test/files/EqualsAndHashCodeInJPAEntityRule.java")
                .withCheck(rule)

                .verifyIssues();
    }

}