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
public class GreetingServlet extends HttpServlet {
  private static final String message = "Welcome this is your first time here.";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    HttpSession session = req.getSession();
    if (session.getAttribute("message") == null) {
      session.setAttribute("message", message);
    }
    String city = req.getParameter("city");
    if (session.getAttribute(city) == null) {
      session.setAttribute(city, "visited");
    } else {
      session.setAttribute("message", "You have already visited us.");
    }
    PrintWriter writer = resp.getWriter();
    writer.println("<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Welcome traveler</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>" + session.getAttribute("message") + "</h1>\n" +
            "<form action=\"GreetingServlet\" method=\"POST\">\n" +
            "    <input type=\"radio\" name=\"city\" value=\"Visit Paris\"/>Paris\n" +
            "    <input type=\"radio\" name=\"city\" value=\"Visit London\"/>London\n" +
            "    <input type=\"radio\" name=\"city\" value=\"Visit Sofia\"/>Sofia\n" +
            "<input type=\"submit\" value=\"Visit\" />" +
            "</form>\n" +
            "</body>\n" +
            "</html>");
    session.setAttribute("message", null);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
