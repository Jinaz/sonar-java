import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("hello")  // Noncompliant; this doesn't get cleaned up
public class Foo {

  @RequestMapping("/greet")
  public String greet(String greetee) {

    return "Hello " + greetee;
  }
}

@Controller
@SessionAttributes("hello")
public class Bar {

  @RequestMapping("/greet")
  public String greet(String greetee) {

    return "Hello " + greetee;
  }

  @RequestMapping("/goodbye")
  public String goodbye(SessionStatus status) {
    //...
    status.setComplete();
  }

}