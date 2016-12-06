package chain;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class ChainletTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void happyPath() throws Exception {
        Chainlet chainlet = new Chainlet();
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        context.checking(new Expectations() {{
            oneOf(request).getHeader("referer");
            will(returnValue("home"));

            oneOf(request).getRequestURI();
            will(returnValue("page1"));

            oneOf(response).getWriter();
            will(returnValue(printWriter));
        }});

        chainlet.doGet(request, response);

        assertThat(writer.toString(), containsString("I came from home"));
        assertThat(writer.toString(), containsString("Go to page 2"));
        assertThat(writer.toString(), containsString("Go to page 3"));
    }

    @Test
    public void oneLinkClicked() throws Exception {
        Chainlet chainlet = new Chainlet();
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        context.checking(new Expectations() {{
            atLeast(1).of(request).getHeader("referer");
            will(returnValue("http://localhost:8080/page1"));

            atLeast(1).of(request).getRequestURI();
            will(returnValue("page2"));

            exactly(2).of(response).getWriter();
            will(returnValue(printWriter));
        }});

        chainlet.doGet(request, response);
        chainlet.doGet(request, response);

        assertThat(writer.toString(), containsString("I came from http://localhost:8080/page1"));
        assertThat(writer.toString(), containsString("Go to page 1"));
        assertThat(writer.toString(), containsString("Go to page 3"));
    }
}
