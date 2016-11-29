package servlets;

import core.Link;
import core.Template;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class ClickCounterServletTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private Template template = context.mock(Template.class);
  private Link links = context.mock(Link.class);
  private HttpServletRequest req = context.mock(HttpServletRequest.class);
  private HttpServletResponse resp = context.mock(HttpServletResponse.class);

  @Test
  public void happyPath() throws Exception {
    final PrintWriter writer=new PrintWriter(new StringWriter());
    ClickCounterServlet clickCounterServlet = new ClickCounterServlet(template, links);
    context.checking(new Expectations() {{
      oneOf(req).getRequestURI();
      will(returnValue(""));
      oneOf(resp).getWriter();
      will(returnValue(writer));


    }});
    clickCounterServlet.doGet(req, resp);
    assertThat(writer.toString(),is("test"));
  }
}