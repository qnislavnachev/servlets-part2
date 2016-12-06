package servlets;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class CounterServletTest {

  private CounterServlet servlet = new CounterServlet();

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private HttpServletRequest req = context.mock(HttpServletRequest.class);
  private HttpServletResponse resp = context.mock(HttpServletResponse.class);
  private final StringWriter out = new StringWriter();
  private final FakeHttpSession session = new FakeHttpSession();
  private final PrintWriter writer = new PrintWriter(out);

  @Test
  public void happyPath() throws Exception {
    context.checking(new Expectations() {{
      oneOf(req).getSession();
      will(returnValue(session));
      oneOf(req).getRequestURI();
      will(returnValue("/"));
      oneOf(resp).getWriter();
      will(returnValue(writer));
    }});

    servlet.doGet(req, resp);

    assertThat(out.toString(), containsString("Link1 clicked 0"));
    assertThat(out.toString(), containsString("Link2 clicked 0"));
    assertThat(out.toString(), containsString("Link3 clicked 0"));
  }

  @Test
  public void clickingLink1() throws Exception {
    context.checking(new Expectations() {{
      atLeast(2).of(req).getSession();
      will(returnValue(session));
      atLeast(2).of(req).getRequestURI();
      will(returnValue("/link1"));
      atLeast(2).of(resp).getWriter();
      will(returnValue(writer));
    }});

    servlet.doGet(req, resp);
    servlet.doGet(req, resp);

    assertThat(out.toString(), containsString("Link1 clicked 1"));
    assertThat(out.toString(), containsString("Link2 clicked 0"));
    assertThat(out.toString(), containsString("Link3 clicked 0"));
  }

  @Test
  public void clickingAnotherLink() throws Exception {
    context.checking(new Expectations() {{
      atLeast(2).of(req).getSession();
      will(returnValue(session));
      atLeast(2).of(req).getRequestURI();
      will(returnValue("/link2"));
      atLeast(2).of(resp).getWriter();
      will(returnValue(writer));
    }});

    servlet.doGet(req, resp);
    servlet.doGet(req, resp);


    assertThat(out.toString(), containsString("Link1 clicked 0"));
    assertThat(out.toString(), containsString("Link2 clicked 1"));
    assertThat(out.toString(), containsString("Link3 clicked 0"));
  }

}

