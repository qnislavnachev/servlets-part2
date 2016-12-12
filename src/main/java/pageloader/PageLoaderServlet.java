package pageloader;

import org.eclipse.jetty.http.HttpHeaders;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

public class PageLoaderServlet extends HttpServlet {

    private Template template = new Template(){{
        add("/", "Home Page");
        add("/page1?", "First Page");
        add("/page2?", "Second Page");
        add("/page3?", "Third Page");
    }};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String header = req.getHeader(HttpHeaders.REFERER);
        PrintWriter writer = resp.getWriter();

        String message = generateMessage(uri, header);
        renderPage(Jetty.class.getResourceAsStream("index.html"), template.evaluate(message), writer);
    }

    private void renderPage(InputStream stream, String message, PrintWriter writer) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String page = "";
        while ((line = reader.readLine()) != null) {
            page += line;
        }
        page = page.replace("message", message);
        writer.write(page);
        writer.flush();
    }

    private String generateMessage(String uri, String header) {
        if (header == null) {
            return "You are at ${" + uri + "}";
        }
        return "You are at ${" + uri + "}, clicked from " + header;
    }
}