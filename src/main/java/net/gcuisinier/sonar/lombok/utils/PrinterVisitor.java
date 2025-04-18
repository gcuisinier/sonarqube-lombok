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


import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.Tree;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class PrinterVisitor extends BaseTreeVisitor {
    private static final int INDENT_SPACES = 2;

    private final StringBuilder sb;
    private int indentLevel;

    public PrinterVisitor() {
        sb = new StringBuilder();
        indentLevel = 0;
    }

    public static void print(Tree tree, Consumer<String> output) {
        PrinterVisitor pv = new PrinterVisitor();
        pv.scan(tree);
        output.accept(pv.sb.toString());
    }

    private StringBuilder indent() {
        return sb.append(StringUtils.spaces(INDENT_SPACES * indentLevel));
    }

    @Override
    protected void scan(List<? extends Tree> trees) {
        if (!trees.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
            sb.append(" : [\n");
            super.scan(trees);
            indent().append("]\n");
        }
    }

    @Override
    protected void scan(@Nullable Tree tree) {
        if (tree != null) {
            Class<?>[] interfaces = tree.getClass().getInterfaces();
            if (interfaces.length > 0) {
                indent().append(interfaces[0].getSimpleName()).append("\n");
            }
        }
        indentLevel++;
        super.scan(tree);
        indentLevel--;
    }
}
