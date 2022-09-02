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


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Duplicates org.sonar.java.checks.verifier.FilesUtils to locate test jars within the custom-rules plugin
 */
public class FilesUtils {

  private FilesUtils() {
  }

  /**
   * Default location of the jars/zips to be taken into account when performing the analysis.
   */
  private static final String DEFAULT_TEST_JARS_DIRECTORY = "target/test-jars";


  public static List<File> getClassPath(String jarsDirectory) {
    List<File> classpath = new LinkedList<>();
    Path testJars = Paths.get(jarsDirectory);
    if (testJars.toFile().exists()) {
      classpath = getFilesRecursively(testJars, "jar", "zip");
    } else if (!DEFAULT_TEST_JARS_DIRECTORY.equals(jarsDirectory)) {
      throw new AssertionError("The directory to be used to extend class path does not exists ("
        + testJars.toAbsolutePath()
        + ").");
    }
    classpath.add(new File("target/test-classes"));
    return classpath;
  }

  private static List<File> getFilesRecursively(Path root, String... extensions) {
    final List<File> files = new ArrayList<>();

    FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) {
        for (String extension : extensions) {
          if (filePath.toString().endsWith("."
            + extension)) {
            files.add(filePath.toFile());
            break;
          }
        }
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
      }
    };

    try {
      Files.walkFileTree(root, visitor);
    } catch (IOException e) {
      // we already ignore errors in the visitor
    }

    return files;
  }

}
