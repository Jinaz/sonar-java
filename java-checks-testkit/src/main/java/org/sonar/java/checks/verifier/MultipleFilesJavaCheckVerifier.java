/*
 * SonarQube Java
 * Copyright (C) 2012-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.java.checks.verifier;

import com.google.common.annotations.Beta;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.sonar.java.AnalyzerMessage;
import org.sonar.java.ast.JavaAstScanner;
import org.sonar.java.ast.parser.JavaParser;
import org.sonar.java.ast.visitors.SubscriptionVisitor;
import org.sonar.java.model.JavaVersionImpl;
import org.sonar.java.model.VisitorsBridgeForTests;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.plugins.java.api.tree.SyntaxTrivia;
import org.sonar.plugins.java.api.tree.Tree;

@Beta
public class MultipleFilesJavaCheckVerifier extends CheckVerifier {

  public Set<AnalyzerMessage> scanFiles(JavaCheck check, MultipleFilesJavaCheckVerifier localVerifier, List<File> filesToScan) throws IOException {
    Path jarPath = Paths.get(JavaCheckVerifier.DEFAULT_TEST_JARS_DIRECTORY);
    List<File> classPath = JavaCheckVerifier.getFilesRecursively(jarPath, new String[] {"jar", "zip"});
    VisitorsBridgeForTests vb = new VisitorsBridgeForTests(
      Arrays.asList(check, new ExpectedIssueCollector(localVerifier)),
      classPath,
      null);
    vb.setJavaVersion(new JavaVersionImpl());

    JavaAstScanner astScanner = new JavaAstScanner(JavaParser.createParser(), null);
    astScanner.setVisitorBridge(vb);
    astScanner.scan(filesToScan);

    VisitorsBridgeForTests.TestJavaFileScannerContext testJavaFileScannerContext = vb.lastCreatedTestContext();
    return testJavaFileScannerContext.getIssues();
  }

  @Override
  public String getExpectedIssueTrigger() {
    return "// " + ISSUE_MARKER;
  }

  private static class ExpectedIssueCollector extends SubscriptionVisitor {

    private final MultipleFilesJavaCheckVerifier verifier;

    ExpectedIssueCollector(MultipleFilesJavaCheckVerifier verifier) {
      this.verifier = verifier;
    }

    @Override
    public List<Tree.Kind> nodesToVisit() {
      return Collections.singletonList(Tree.Kind.TRIVIA);
    }

    @Override
    public void visitTrivia(SyntaxTrivia syntaxTrivia) {
      verifier.collectExpectedIssues(syntaxTrivia.comment(), syntaxTrivia.startLine());
    }
  }
}
