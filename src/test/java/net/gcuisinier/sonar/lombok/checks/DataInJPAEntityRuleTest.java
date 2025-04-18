package net.gcuisinier.sonar.lombok.checks;

import net.gcuisinier.sonar.lombok.utils.FilesUtils;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class DataInJPAEntityRuleTest {

    @Test
    void detected() {

        DataInJPAEntityRule rule = new DataInJPAEntityRule();

        CheckVerifier.newVerifier()
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .onFile("src/test/files/DataInJPAEntityRule.java")
                .withCheck(rule)

                .verifyIssues();
    }

    @Test
    void detectedJakarta() {

        DataInJPAEntityRule rule = new DataInJPAEntityRule();

        CheckVerifier.newVerifier()
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .onFile("src/test/files/DataInJPAEntityRuleJakarta.java")
                .withCheck(rule)

                .verifyIssues();
    }

}
