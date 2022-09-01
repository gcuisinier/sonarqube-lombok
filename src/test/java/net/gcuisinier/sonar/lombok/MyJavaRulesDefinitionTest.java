package net.gcuisinier.sonar.lombok;

import org.junit.jupiter.api.Test;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.debt.DebtRemediationFunction.Type;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinition.Param;
import org.sonar.api.server.rule.RulesDefinition.Repository;
import org.sonar.api.server.rule.RulesDefinition.Rule;

import static org.assertj.core.api.Assertions.assertThat;

class LombokRulesDefinitionTest {

  @Test
  void test() {
    LombokRulesDefinition rulesDefinition = new LombokRulesDefinition(new LombokRulesPluginTest.MockedSonarRuntime());
    RulesDefinition.Context context = new RulesDefinition.Context();
    rulesDefinition.define(context);
    RulesDefinition.Repository repository = context.repository(LombokRulesDefinition.REPOSITORY_KEY);

    assertThat(repository.name()).isEqualTo(LombokRulesDefinition.REPOSITORY_NAME);
    assertThat(repository.language()).isEqualTo("java");
    assertThat(repository.rules()).hasSize(RulesList.getChecks().size());
    assertThat(repository.rules().stream().filter(Rule::template)).isEmpty();

    assertRuleProperties(repository);
    //assertParameterProperties(repository);
    assertAllRuleParametersHaveDescription(repository);
  }



  private static void assertRuleProperties(Repository repository) {
    Rule rule = repository.rule("Lombok-Synchronized");
    assertThat(rule).isNotNull();
    assertThat(rule.name()).isEqualTo("Avoid Lombok @Synchronized");
    assertThat(rule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
    assertThat(rule.type()).isEqualTo(RuleType.CODE_SMELL);
  }

  private static void assertAllRuleParametersHaveDescription(Repository repository) {
    for (Rule rule : repository.rules()) {
      for (Param param : rule.params()) {
        assertThat(param.description()).as("description for " + param.key()).isNotEmpty();
      }
    }
  }

}
