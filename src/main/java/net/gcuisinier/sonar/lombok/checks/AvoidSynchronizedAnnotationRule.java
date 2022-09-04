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
package net.gcuisinier.sonar.lombok.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;

/**
 * @author gcuisinier (github.com/gcuisinier/)
 */
@Rule(key = "Lombok-Synchronized")
public class AvoidSynchronizedAnnotationRule extends BaseTreeVisitor implements JavaFileScanner {

    public static final String SYNCHRONIZED_ANNOTATION = "lombok.Synchronized";
    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitMethod(MethodTree tree) {
        List<AnnotationTree> annotations = tree.modifiers().annotations();

        for (AnnotationTree annotationTree : annotations) {
            TypeTree annotationType = annotationTree.annotationType();
            if (annotationType.is(Tree.Kind.IDENTIFIER)) {
                IdentifierTree identifier = (IdentifierTree) annotationType;
                if (SYNCHRONIZED_ANNOTATION.equals(annotationType.symbolType().fullyQualifiedName())) {
                    context.reportIssue(this, identifier, String.format("Avoid using annotation @Synchronized from Lombok"));
                }
            }
        }

        super.visitMethod(tree);
    }
}
