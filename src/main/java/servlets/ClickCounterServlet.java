package servlets;

import adapter.ClickedLinks;
import adapter.HtmlTemplate;

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
  private HtmlTemplate template;
  private ClickedLinks links;

  public ClickCounterServlet(HtmlTemplate template, ClickedLinks links) {
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

  private void render(HtmlTemplate template, PrintWriter writer) {
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
