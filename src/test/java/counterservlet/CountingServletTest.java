package counterservlet;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class CountingServletTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void happyPath() throws Exception {
        CountingServlet countingServlet = new CountingServlet();
        FakeHttpSession session = new FakeHttpSession();
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        context.checking(new Expectations() {{

            oneOf(request).getSession();
            will(returnValue(session));

            oneOf(request).getParameter("link");
            will(returnValue("1"));

            oneOf(response).getWriter();
            will(returnValue(writer));
        }});

        countingServlet.doGet(request, response);

        assertThat(session.getAttribute("1"), is(equalTo(1)));
        assertThat(session.getAttribute("2"), is(equalTo(0)));
        assertThat(session.getAttribute("3"), is(equalTo(0)));
    }

    @Test
    public void differentLinksClicked() throws Exception {
        CountingServlet countingServlet = new CountingServlet();
        FakeHttpSession session = new FakeHttpSession();
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        context.checking(new Expectations() {{

            exactly(2).of(request).getSession();
            will(returnValue(session));

            exactly(2).of(request).getParameter("link");
            will(onConsecutiveCalls(returnValue("1"), returnValue("3")));

            exactly(2).of(response).getWriter();
            will(returnValue(writer));
        }});

        countingServlet.doGet(request, response);
        countingServlet.doGet(request, response);

        assertThat(session.getAttribute("1"), is(equalTo(1)));
        assertThat(session.getAttribute("2"), is(equalTo(0)));
        assertThat(session.getAttribute("3"), is(equalTo(1)));
    }

    @Test
    public void moreThenOneClickPerLink() throws Exception {
        CountingServlet countingServlet = new CountingServlet();
        FakeHttpSession session = new FakeHttpSession();
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        context.checking(new Expectations() {{

            atLeast(2).of(request).getSession();
            will(returnValue(session));

            atLeast(2).of(request).getParameter("link");
            will(returnValue("2"));

            atLeast(2).of(response).getWriter();
            will(returnValue(writer));
        }});

        countingServlet.doGet(request, response);
        countingServlet.doGet(request, response);
        countingServlet.doGet(request, response);

        assertThat(session.getAttribute("1"), is(equalTo(0)));
        assertThat(session.getAttribute("2"), is(equalTo(3)));
        assertThat(session.getAttribute("3"), is(equalTo(0)));
    }

    @Test
    public void renderPage() throws Exception {
        CountingServlet countingServlet = new CountingServlet();
        FakeHttpSession session = new FakeHttpSession();
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        session.setAttribute("1", 5);
        session.setAttribute("2", 1);
        session.setAttribute("3", 2);

        context.checking(new Expectations() {{

            oneOf(request).getSession();
            will(returnValue(session));

            oneOf(request).getParameter("link");
            will(returnValue(null));

            oneOf(response).getWriter();
            will(returnValue(writer));
        }});

        countingServlet.doGet(request, response);

        assertThat(out.toString().contains("I am link 1 - Clicked: 5"), is(equalTo(true)));
        assertThat(out.toString().contains("I am link 2 - Clicked: 1"), is(equalTo(true)));
        assertThat(out.toString().contains("I am link 3 - Clicked: 2"), is(equalTo(true)));
    }
}