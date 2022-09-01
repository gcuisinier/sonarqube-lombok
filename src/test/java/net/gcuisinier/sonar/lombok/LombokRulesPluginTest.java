package net.gcuisinier.sonar.lombok;

import org.junit.jupiter.api.Test;
import org.sonar.api.*;
import org.sonar.api.utils.Version;

import static org.assertj.core.api.Assertions.assertThat;

class LombokRulesPluginTest {

  @Test
  void testName() {
    Plugin.Context context = new Plugin.Context(new MockedSonarRuntime());

    new LombokRulesPlugin().define(context);

    assertThat(context.getExtensions()).hasSize(0);
  }
  public static class MockedSonarRuntime implements SonarRuntime {

    @Override
    public Version getApiVersion() {
      return Version.create(8, 9);
    }

    @Override
    public SonarProduct getProduct() {
      return SonarProduct.SONARQUBE;
    }

    @Override
    public SonarQubeSide getSonarQubeSide() {
      return SonarQubeSide.SCANNER;
    }

    @Override
    public SonarEdition getEdition() {
      return SonarEdition.COMMUNITY;
    }
  }

}
