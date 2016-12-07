package servlets;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class GreetingServletTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private HttpServletRequest req = context.mock(HttpServletRequest.class);
  private HttpServletResponse resp = context.mock(HttpServletResponse.class);
  private FakeSession session = new FakeSession();

  @Test
  public void visitForTheFirstTime() throws Exception {
    StringWriter out = new StringWriter();
    final PrintWriter writer = new PrintWriter(out);
    GreetingServlet servlet = new GreetingServlet();

    context.checking(new Expectations() {{
      atLeast(2).of(req).getSession();
      will(returnValue(session));
      atLeast(2).of(req).getParameter("city");
      will(returnValue(null));
      atLeast(2).of(resp).getWriter();
      will(returnValue(writer));
    }});

    servlet.doGet(req, resp);
    servlet.doPost(req, resp);

    assertThat(out.toString(), containsString("Welcome this is your first time here."));
  }

  @Test
  public void cityWasAlreadyVisited() throws Exception {
    StringWriter out = new StringWriter();
    final PrintWriter writer = new PrintWriter(out);
    GreetingServlet servlet = new GreetingServlet();

    context.checking(new Expectations() {{
      atLeast(2).of(req).getSession();
      will(returnValue(session));
      atLeast(2).of(req).getParameter("city");
      will(returnValue("visited"));
      atLeast(2).of(resp).getWriter();
      will(returnValue(writer));
    }});

    servlet.doGet(req, resp);
    servlet.doPost(req, resp);

    assertThat(out.toString(), containsString("You have already visited us."));
  }
}