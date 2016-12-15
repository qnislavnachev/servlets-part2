package pageloader;

import org.eclipse.jetty.http.HttpHeaders;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PageLoaderTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void loadHomePage() throws Exception {
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);
        PageLoaderServlet pageLoaderServlet = new PageLoaderServlet();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        context.checking(new Expectations() {{
            oneOf(request).getRequestURI();
            will(returnValue("/"));

            oneOf(request).getHeader(HttpHeaders.REFERER);
            will(returnValue(null));

            oneOf(response).getWriter();
            will(returnValue(writer));
        }});

        pageLoaderServlet.doGet(request, response);
        assertThat(out.toString().contains("You are at Home Page"), is(true));
    }

    @Test
    public void loadFirstPageFromHome() throws Exception {
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);
        PageLoaderServlet pageLoaderServlet = new PageLoaderServlet();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        context.checking(new Expectations() {{
            exactly(2).of(request).getRequestURI();
            will(onConsecutiveCalls(returnValue("/"), returnValue("/page1")));

            exactly(2).of(request).getHeader(HttpHeaders.REFERER);
            will(onConsecutiveCalls(returnValue(null), returnValue("http://localhost:8080/")));

            exactly(2).of(response).getWriter();
            will(returnValue(writer));
        }});

        pageLoaderServlet.doGet(request, response);
        pageLoaderServlet.doGet(request, response);

        assertThat(out.toString().contains("You are at First Page, clicked from http://localhost:8080/"), is(true));
    }

    @Test
    public void loadThirdPageFromFirst() throws Exception {
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);
        PageLoaderServlet pageLoaderServlet = new PageLoaderServlet();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        context.checking(new Expectations() {{
            exactly(2).of(request).getRequestURI();
            will(onConsecutiveCalls(returnValue("/page1"), returnValue("/page3")));

            exactly(2).of(request).getHeader(HttpHeaders.REFERER);
            will(onConsecutiveCalls(returnValue(null), returnValue("http://localhost:8080/page1")));

            exactly(2).of(response).getWriter();
            will(returnValue(writer));
        }});

        pageLoaderServlet.doGet(request, response);
        pageLoaderServlet.doGet(request, response);

        assertThat(out.toString().contains("You are at Third Page, clicked from http://localhost:8080/page1"), is(true));
    }

    @Test
    public void loadSecondPageFromItSelf() throws Exception {
        HttpServletRequest request = context.mock(HttpServletRequest.class);
        HttpServletResponse response = context.mock(HttpServletResponse.class);
        PageLoaderServlet pageLoaderServlet = new PageLoaderServlet();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        context.checking(new Expectations() {{
            exactly(2).of(request).getRequestURI();
            will(returnValue("/page2"));

            exactly(2).of(request).getHeader(HttpHeaders.REFERER);
            will(onConsecutiveCalls(returnValue(null), returnValue("http://localhost:8080/page2")));

            exactly(2).of(response).getWriter();
            will(returnValue(writer));
        }});

        pageLoaderServlet.doGet(request, response);
        pageLoaderServlet.doGet(request, response);

        assertThat(out.toString().contains("You are at Second Page, clicked from http://localhost:8080/page2"), is(true));
    }
}