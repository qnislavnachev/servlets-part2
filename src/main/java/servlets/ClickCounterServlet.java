package servlets;

import core.Link;
import core.Template;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class ClickCounterServlet extends HttpServlet {
  private Template template;
  private Link links;

  public ClickCounterServlet(Template template, Link links) {
    this.template = template;
    this.links = links;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String uri = req.getRequestURI();
    if (uri.contains("link")) {
      String link = uri.substring(1, uri.length());
      links.click(link);
    }

    PrintWriter writer = resp.getWriter();
    writer.print(getHtml(template));
    writer.flush();
  }

  private String getHtml(Template template) {
    for (String currentLink : links.getClicks().keySet()) {
      template.put(currentLink, links.getClicksFor(currentLink).toString());
    }
    return template.evaluate();
  }
}
