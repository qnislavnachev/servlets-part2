package counter;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class CounterTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    private final Counter counter = new Counter();
    private final FakeHttpSession httpSession = new FakeHttpSession(new LinkedHashMap<>());
    private final HttpServletRequest request = context.mock(HttpServletRequest.class);
    private final HttpServletResponse response = context.mock(HttpServletResponse.class);
    private final StringWriter writer = new StringWriter();
    private final PrintWriter printWriter = new PrintWriter(writer);

    @Test
    public void happyPath() throws Exception {

        context.checking(new Expectations(){{
            oneOf(request).getSession();
            will(returnValue(httpSession));

            oneOf(request).getParameter("linkName");
            will(returnValue("first"));

            oneOf(response).getWriter();
            will(returnValue(printWriter));
        }});

        counter.doGet(request, response);

        assertThat( writer.toString(),containsString("Link1</a>&nbsp1") );
        assertThat( writer.toString(),containsString("Link2") );
        assertThat( writer.toString(),containsString("Link3") );

    }

    @Test
    public void oneLinkClickedTwice() throws Exception {

        context.checking(new Expectations(){{
            exactly(2).of(request).getSession();
            will(returnValue(httpSession));

            exactly(2).of(request).getParameter("linkName");
            will(returnValue("third"));

            exactly(2).of(response).getWriter();
            will(returnValue(printWriter));
        }});

        counter.doGet(request, response);
        counter.doGet(request, response);

        assertThat( writer.toString(),containsString("Link1") );
        assertThat( writer.toString(),containsString("Link2") );
        assertThat( writer.toString(),containsString("Link3</a>&nbsp2") );

    }

    @Test
    public void differentLinksClicked() throws Exception {

        context.checking(new Expectations() {{
            exactly(2).of(request).getSession();
            will(returnValue(httpSession));

            oneOf(request).getParameter("linkName");
            will(returnValue("first"));

            oneOf(request).getParameter("linkName");
            will(returnValue("second"));

            exactly(2).of(response).getWriter();
            will(returnValue(printWriter));
        }});

        counter.doGet(request, response);
        counter.doGet(request, response);

        assertThat( writer.toString(),containsString("Link1</a>&nbsp1") );
        assertThat( writer.toString(),containsString("Link2</a>&nbsp1") );
        assertThat( writer.toString(),containsString("Link3") );
    }
}
