/*
 * The MIT License
 * Copyright © 2022 @gcuisinier
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
