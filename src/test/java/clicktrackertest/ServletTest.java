package clicktrackertest;

import clicktracker.servlet.ClickTrackerServlet;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>
 */
public class ServletTest {
    private JUnit4Mockery context = new JUnit4Mockery();
    private HttpServletRequest request = context.mock(HttpServletRequest.class);
    private HttpServletResponse response = context.mock(HttpServletResponse.class);
    private StringWriter writer = new StringWriter();
    private PrintWriter printWriter = new PrintWriter(writer);
    private ClickTrackerServlet clickTracker = new ClickTrackerServlet();

    @Test
    public void happyPath() throws Exception {

        context.checking(new Expectations() {{
            oneOf(request).getParameter("link");
            will(returnValue(("link1")));

            oneOf(response).setContentType("text/html");

            oneOf(response).getWriter();
            will(returnValue(printWriter));
        }});
        clickTracker.doGet(request, response);
        String actual = writer.toString();
        assertThat(actual, containsString("Clicks: 1"));
    }

    @Test
    public void multipleClickedLinks() throws Exception {

        context.checking(new Expectations() {{
            oneOf(request).getParameter("link");
            will(returnValue("link3"));

            oneOf(response).setContentType("text/html");

            oneOf(response).getWriter();
            will(returnValue(printWriter));



            oneOf(request).getParameter("link");
            will(returnValue("link3"));

            oneOf(response).setContentType("text/html");

            oneOf(response).getWriter();
            will(returnValue(printWriter));
        }});
        clickTracker.doGet(request, response);
        clickTracker.doGet(request, response);
        String actual = writer.toString();
        assertThat(actual, containsString("Clicks: 2"));
    }

}
