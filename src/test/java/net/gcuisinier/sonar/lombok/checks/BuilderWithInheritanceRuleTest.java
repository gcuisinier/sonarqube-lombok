package net.gcuisinier.sonar.lombok.checks;

import net.gcuisinier.sonar.lombok.utils.FilesUtils;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class BuilderWithInheritanceRuleTest {

    @Test
    void detected() {
        BuilderWithInheritanceRule rule = new BuilderWithInheritanceRule();

        CheckVerifier.newVerifier()
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .onFile("src/test/files/BuilderWithInheritanceRule.java")
                .withCheck(rule)
                .verifyIssues();
    }
}