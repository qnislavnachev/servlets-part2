package servlets;

import adapter.ClickedLinks;
import adapter.HtmlTemplate;
import core.Link;
import core.Template;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

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
    String link = req.getRequestURL().toString();
    evaluateClick(link);
    PrintWriter writer = resp.getWriter();
    render(template, writer);
  }

  private void render(Template template, PrintWriter writer) {
    writer.print(template.evaluate());
    writer.flush();
  }

  private void evaluateClick(String clickedLink) {
    if (clickedLink.contains("link")) {
      clickedLink = clickedLink.split("/")[3];
      links.click(clickedLink);
    }
    Map<String, Integer> clickedLinks = links.getClicks();
    for (String link : clickedLinks.keySet()) {
      template.put(link, links.getClicksFor(link).toString());
    }
  }
}
