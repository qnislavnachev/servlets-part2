package servlets;

import core.Template;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
  public class CounterServletTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    private Template template = context.mock(Template.class);
    private HttpServletRequest req = context.mock(HttpServletRequest.class);
    private HttpServletResponse resp = context.mock(HttpServletResponse.class);

    @Test
    public void happyPath() throws Exception {
      StringWriter out = new StringWriter();
      final PrintWriter writer = new PrintWriter(out);
      CounterServlet servlet = new CounterServlet(template, new HashMap<String, Integer>());

      context.checking(new Expectations() {{
        oneOf(req).getRequestURI();
        will(returnValue("sdfdsf"));

        oneOf(resp).getWriter();
        will(returnValue(writer));

        oneOf(template).evaluate();
        will(returnValue("test"));
      }});
      servlet.doGet(req, resp);

      assertThat(out.toString(), is("test\n"));
    }

    @Test
    public void clickLink() throws Exception {
      StringWriter out = new StringWriter();
      final PrintWriter writer = new PrintWriter(out);
      CounterServlet servlet = new CounterServlet(template, new HashMap<String, Integer>() {{
        put("link1", 0);
        put("link2", 0);
      }});

      context.checking(new Expectations() {{
        oneOf(req).getRequestURI();
        will(returnValue("/link1"));

        oneOf(resp).getWriter();
        will(returnValue(writer));

        oneOf(template).put("link1", "1");
        oneOf(template).put("link2", "0");

        oneOf(template).evaluate();
      }});

      servlet.doGet(req, resp);
    }
}
