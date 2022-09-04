package net.gcuisinier.sonar.lombok.checks;

import net.gcuisinier.sonar.lombok.utils.FilesUtils;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class ToStringInJPAEntityRuleTest {

    @Test
    void detected() {

        ToStringInJPAEntityRule rule = new ToStringInJPAEntityRule();

        CheckVerifier.newVerifier()
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .onFile("src/test/files/ToStringInJPAEntityRule.java")
                .withCheck(rule)

                .verifyIssues();
    }

}