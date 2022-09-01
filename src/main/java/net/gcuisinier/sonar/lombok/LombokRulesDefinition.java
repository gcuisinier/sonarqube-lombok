package net.gcuisinier.sonar.lombok;

import org.sonar.api.SonarRuntime;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Declare rule metadata in server repository of rules.
 * That allows to list the rules in the page "Rules".
 */
public class LombokRulesDefinition implements RulesDefinition {

    public static final String REPOSITORY_KEY = "lombok-java";
    public static final String REPOSITORY_NAME = "Lombok Repository";
    // don't change that because the path is hard coded in CheckVerifier
    private static final String RESOURCE_BASE_PATH = "net/gcuisinier/sonar/lombok/rules/";
    // Add the rule keys of the rules which need to be considered as template-rules
    private static final Set<String> RULE_TEMPLATES_KEY = Collections.emptySet();

    private final SonarRuntime runtime;

    public LombokRulesDefinition(SonarRuntime runtime) {
        this.runtime = runtime;
    }

    private static void setTemplates(NewRepository repository) {
        RULE_TEMPLATES_KEY.stream()
                .map(repository::rule)
                .filter(Objects::nonNull)
                .forEach(rule -> rule.setTemplate(true));
    }

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(REPOSITORY_KEY, "java").setName(REPOSITORY_NAME);

        RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_BASE_PATH, runtime);

        ruleMetadataLoader.addRulesByAnnotatedClass(repository, new ArrayList<>(RulesList.getChecks()));

        setTemplates(repository);

        repository.done();
    }
}
