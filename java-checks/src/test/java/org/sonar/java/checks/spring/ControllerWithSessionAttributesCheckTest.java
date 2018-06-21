package org.sonar.java.checks.spring;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ControllerWithSessionAttributesCheckTest {
  @Test
  public void test() {
    JavaCheckVerifier.verify("src/test/files/checks/spring/ControllerWithSessionAttributesCheck.java", new ControllerWithSessionAttributesCheck());
    JavaCheckVerifier.verifyNoIssueWithoutSemantic("src/test/files/checks/spring/ControllerWithSessionAttributesCheck.java", new ControllerWithSessionAttributesCheck());
  }
}
