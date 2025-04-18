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
package net.gcuisinier.sonar.lombok;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.TestCheckRegistrarContext;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.api.rule.RuleKey;


import static org.assertj.core.api.Assertions.assertThat;

class MyJavaFileCheckRegistrarTest {

    @Test
    void checkNumberRules() {
        TestCheckRegistrarContext context = new TestCheckRegistrarContext();

        LombokFileCheckRegistrar registrar = new LombokFileCheckRegistrar();
        registrar.register(context);

        assertThat(context.mainRuleKeys).extracting(RuleKey::toString).containsExactly(
                "lombok:Lombok-Synchronized",
                "lombok:Lombok-ToStringJPA",
                "lombok:Lombok-JPAWithEqualsAndHashCode",
                "lombok:Lombok-JPAWithData",
                "lombok:Lombok-BuilderWithInheritance",
                "lombok:Lombok-JPAWithAllArgsConstructor",
                "lombok:Lombok-ValueOnJPAEntity");

        assertThat(context.mainCheckClasses).extracting(Class::getSimpleName).containsExactly(
                "AvoidSynchronizedAnnotationRule",
                "ToStringInJPAEntityRule",
                "EqualsAndHashCodeInJPAEntityRule",
                "DataInJPAEntityRule",
                "BuilderWithInheritanceRule",
                "AllArgsConstructorInJPAEntityRule",
                "ValueOnJPAEntityRule");

    }

}
