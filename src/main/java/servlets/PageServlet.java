package servlets;

import org.mortbay.jetty.HttpHeaders;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class PageServlet extends HttpServlet {


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String fromPage = req.getHeader(HttpHeaders.REFERER);
    if (fromPage == null) {
      fromPage = "index";
    }
    PrintWriter writer = resp.getWriter();
    writer.println("<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Pages</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>Pages</h1>\n" +
            "<h1>Clicked from " + fromPage + "</h1>\n" +
            "<a href=\"page1\">To Page1</a>\n" +
            "<a href=\"page2\">To Page2</a>\n" +
            "<a href=\"page3\">To Page3</a>\n" +
            "</body>\n" +
            "</html>");
  }
}
