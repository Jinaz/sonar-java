import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SessionAttributes("foo") // Noncompliant [[sc=1;ec=19]] {{Add a call to "setComplete()" on the SessionStatus object in a "@RequestMapping" method that handles "POST".}}
public class Foo {

  @RequestMapping("/foo")
  public String foo(String foo) {

    return "foo" + foo;
  }

  @RequestMapping(value = "/end", method = RequestMethod.POST)
  public void end(SessionStatus status) {
    // no call to setComplete
  }

  @RequestMapping(value = "/end", method = RequestMethod.GET) // should be POST
  public void end(SessionStatus status) {
    status.setComplete();
  }

  @GetMapping
  public void end(SessionStatus status) {
    status.setComplete();
  }
}

@Controller
@SessionAttributes("x")
public class Bar {
  @RequestMapping(value = "/end", method = RequestMethod.POST)
  public void end(SessionStatus status) {
    status.setComplete();
  }
}

@Controller
@SessionAttributes("x")
public class Baw {
  @PostMapping
  public void end(SessionStatus status) {
    status.setComplete();
  }
}

@Controller
@SessionAttributes("x")
public class Baz {
  @RequestMapping(method = POST)
  public void end(SessionStatus status) {
    status.setComplete();
  }
}

@SessionAttributes("foo")
public class Boo { // not a controller
  @RequestMapping("/foo")
  public String foo(String foo) {

    return "foo" + foo;
  }
}

public abstract class AbstractController {
  @PostMapping
  public void end(SessionStatus status) {
    status.setComplete();
  }
}

@Controller
@SessionAttributes("foo")
public class ResultControllerHosting extends AbstractController {

}
