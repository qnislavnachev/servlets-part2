package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class CounterServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    String uri = req.getRequestURI();
    if (!uri.equals("/")) {
      clickLink(uri, session);
    }
    PrintWriter out = resp.getWriter();
    out.println("<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Links</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>Click Links below as hard as you can</h1>\n" +
            "<a href=\"link1\">Link1 clicked " + render("/link1", session) + "</a>\n" +
            "<a href=\"link2\">Link2 clicked " + render("/link2", session) + "</a>\n" +
            "<a href=\"link3\">Link3 clicked " + render("/link3", session) + "</a>\n" +
            "</body>\n" +
            "</html>");
  }

  private Integer render(String link, HttpSession session) {
    Integer currentCount = (Integer) session.getAttribute(link);
    if (currentCount == null) {
      session.setAttribute(link, 0);
      return 0;
    } else {
      return currentCount;
    }
  }

  private void clickLink(String uri, HttpSession session) {
    Integer count = (Integer) session.getAttribute(uri);
    if (count == null) {
      count = 0;
    }
    session.setAttribute(uri, ++count);
  }
}
