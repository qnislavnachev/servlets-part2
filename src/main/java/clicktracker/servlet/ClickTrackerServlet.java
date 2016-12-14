package clicktracker.servlet;

import clicktracker.HtmlTemplate;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>
 */
public class ClickTrackerServlet extends HttpServlet {
    private Map<String, String> links = new LinkedHashMap<>();
    private int counter;

    /**
     * Servlet with overrode doGet() method that gets a parameter and checks if it's in the hash map 'links'
     * If it's not it sets the counter to 1, if it is it increases it by 1.
     * The content type being send to the client is set to text/html.
     * Implementation of HtmlTemplate to evaluate the html file and the content of the hash map 'links'
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String link = request.getParameter("link");
        if (link != null) {
            if (!links.containsKey(link)) {
                counter = 1;
                links.put(link, String.valueOf(counter));
            } else {
                counter = Integer.valueOf(links.get(link)) + 1;
                links.put(link, String.valueOf(counter));
            }
        }
        response.setContentType("text/html");
        PrintWriter printer = response.getWriter();
        URL url = ClickTrackerServlet.class.getClassLoader().getResource("links.html");
        String path = url.getPath();
        String html = Files.toString(new File(path), Charsets.UTF_8);
        HtmlTemplate htmlTemplate = new HtmlTemplate(html, links);
        printer.write(htmlTemplate.evaluate());
    }
}
