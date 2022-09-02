package net.gcuisinier.sonar.lombok.utils;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

  @Test
  void spaces() {
    assertThat(StringUtils.spaces(5))
      .hasSize(5)
      .containsOnlyWhitespaces();
  }

  @Test
  void isNotEmpty() {
    assertThat(StringUtils.isNotEmpty(null)).isFalse();
    assertThat(StringUtils.isNotEmpty("")).isFalse();
    assertThat(StringUtils.isNotEmpty(" ")).isTrue();
    assertThat(StringUtils.isNotEmpty("bob")).isTrue();
    assertThat(StringUtils.isNotEmpty("   bob   ")).isTrue();
  }

}
