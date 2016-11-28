package servlets;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.mortbay.jetty.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class PageServletTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private HttpServletRequest req = context.mock(HttpServletRequest.class);
  private HttpServletResponse resp = context.mock(HttpServletResponse.class);

  @Test
  public void happyPath() throws Exception {
    StringWriter out = new StringWriter();
    PrintWriter writer = new PrintWriter(out);
    PageServlet servlet = new PageServlet();

    context.checking(new Expectations(){{
      oneOf(req).getHeader(HttpHeaders.REFERER);
      will(returnValue(null));
      oneOf(resp).getWriter();
      will(returnValue(writer));
    }});

    servlet.doGet(req,resp);

    assertThat(out.toString(),containsString("Clicked from index"));
  }

  @Test
  public void fromIndexClickingOnAPage() throws Exception {
    StringWriter out = new StringWriter();
    PrintWriter writer = new PrintWriter(out);
    PageServlet servlet = new PageServlet();

    context.checking(new Expectations(){{
      oneOf(req).getHeader(HttpHeaders.REFERER);
      will(returnValue("http://localhost:8080/"));
      oneOf(resp).getWriter();
      will(returnValue(writer));
    }});

    servlet.doGet(req,resp);

    assertThat(out.toString(),containsString("Clicked from http://localhost:8080/"));
  }

  @Test
  public void fromPage1ClickingOnAPage() throws Exception {
    StringWriter out = new StringWriter();
    PrintWriter writer = new PrintWriter(out);
    PageServlet servlet = new PageServlet();

    context.checking(new Expectations(){{
      oneOf(req).getHeader(HttpHeaders.REFERER);
      will(returnValue("http://localhost:8080/page1"));
      oneOf(resp).getWriter();
      will(returnValue(writer));
    }});

    servlet.doGet(req,resp);

    assertThat(out.toString(),containsString("Clicked from http://localhost:8080/page1"));
  }
}