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
package org.sonar.java.checks.spring;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.sonar.java.AnalyzerMessage;
import org.sonar.java.checks.verifier.MultipleFilesJavaCheckVerifier;

public class SpringBeansShouldBeAccessibleCheckTest {

  @Test
  public void testComponentScan() throws IOException {
    MultipleFilesJavaCheckVerifier verifier = new MultipleFilesJavaCheckVerifier();
    List<File> filesToScan = Arrays.asList(
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/ComponentScan/A.java"),
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/ComponentScan/B.java"),
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/ComponentScan/C.java"),
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/ComponentScan/DefaultPackage.java"),
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/ComponentScan/FalsePositive.java"),
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/ComponentScan/Y1.java"),
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/ComponentScan/Y2.java"),
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/ComponentScan/Z2.java"),
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/ComponentScan/ComponentScan.java"));

    Set<AnalyzerMessage> analysisResult = verifier.scanFiles(new SpringBeansShouldBeAccessibleCheck(), verifier, filesToScan);
    verifier.checkIssues(analysisResult, false);
  }

  @Test
  public void testSpringBootApplication() throws IOException {
    MultipleFilesJavaCheckVerifier verifier = new MultipleFilesJavaCheckVerifier();
    List<File> filesToScan = Arrays.asList(
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/SpringBootApplication/Ko.java"),
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/SpringBootApplication/SpringBoot.java"),
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/SpringBootApplication/AnotherSpringBoot.java"),
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/SpringBootApplication/AnotherOk.java"),
      new File("src/test/files/checks/spring/SpringBeansShouldBeAccessibleCheck/SpringBootApplication/Ok.java"));

    Set<AnalyzerMessage> analysisResult = verifier.scanFiles(new SpringBeansShouldBeAccessibleCheck(), verifier, filesToScan);
    verifier.checkIssues(analysisResult, false);
  }

}
