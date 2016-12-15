package greetingpages.servlets;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class GreetingPagesTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void loadHomePage() throws Exception {
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        final HttpServletResponse response = context.mock(HttpServletResponse.class);
        HomePageServlet homePageServlet = new HomePageServlet();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        final PrintWriter writer = new PrintWriter(out);

        context.checking(new Expectations() {{
            oneOf(response).getWriter();
            will(returnValue(writer));
        }});

        homePageServlet.doGet(request, response);

        assertThat(out.toString(), containsString("<title>Home Page</title>"));
    }

    @Test
    public void goToPageOnce() throws Exception {
        final HttpServletRequest request = context.mock(HttpServletRequest.class);
        final HttpServletResponse response = context.mock(HttpServletResponse.class);
        final FakeSession session = new FakeSession();
        GreetingServlet greetingServlet = new GreetingServlet();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        final PrintWriter writer = new PrintWriter(out);

        context.checking(new Expectations() {{
            oneOf(request).getSession();
            will(returnValue(session));

            oneOf(request).getParameter("pages");
            will(returnValue("page1"));

            oneOf(response).getWriter();
            will(returnValue(writer));
        }});

        greetingServlet.doGet(request, response);

        assertThat(out.toString(), containsString("<h1>Welcome to Page 1</h1>"));
    }

    @Test
    public void goToPageTwice() throws Exception {
        final HttpServletRequest request = context.mock(HttpServletRequest.class);
        final HttpServletResponse response = context.mock(HttpServletResponse.class);
        final FakeSession session = new FakeSession();
        GreetingServlet greetingServlet = new GreetingServlet();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        final PrintWriter writer = new PrintWriter(out);

        context.checking(new Expectations() {{
            exactly(2).of(request).getSession();
            will(returnValue(session));

            exactly(2).of(request).getParameter("pages");
            will(returnValue("page1"));

            exactly(2).of(response).getWriter();
            will(returnValue(writer));
        }});

        greetingServlet.doGet(request, response);
        greetingServlet.doGet(request, response);

        assertThat(out.toString(), containsString("<h1>You already were here</h1>"));
    }

    @Test
    public void goToDifferentPages() throws Exception {
        final HttpServletRequest request = context.mock(HttpServletRequest.class);
        final HttpServletResponse response = context.mock(HttpServletResponse.class);
        final FakeSession session = new FakeSession();
        GreetingServlet greetingServlet = new GreetingServlet();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        final PrintWriter writer = new PrintWriter(out);

        context.checking(new Expectations() {{
            exactly(2).of(request).getSession();
            will(returnValue(session));

            exactly(2).of(request).getParameter("pages");
            will(onConsecutiveCalls(returnValue("page1"), returnValue("page3")));

            exactly(2).of(response).getWriter();
            will(returnValue(writer));
        }});

        greetingServlet.doGet(request, response);
        greetingServlet.doGet(request, response);

        assertThat(out.toString(), containsString("<h1>Welcome to Page 3</h1>"));

    }

    @Test
    public void goToDifferentPagesToFirstOne() throws Exception {
        final HttpServletRequest request = context.mock(HttpServletRequest.class);
        final HttpServletResponse response = context.mock(HttpServletResponse.class);
        final FakeSession session = new FakeSession();
        GreetingServlet greetingServlet = new GreetingServlet();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        final PrintWriter writer = new PrintWriter(out);

        context.checking(new Expectations() {{
            exactly(3).of(request).getSession();
            will(returnValue(session));

            exactly(3).of(request).getParameter("pages");
            will(onConsecutiveCalls(returnValue("page1"), returnValue("page3"), returnValue("page1")));

            exactly(3).of(response).getWriter();
            will(returnValue(writer));
        }});

        greetingServlet.doGet(request, response);
        greetingServlet.doGet(request, response);
        greetingServlet.doGet(request, response);

        assertThat(out.toString(), containsString("<h1>You already were here</h1>"));
    }
}