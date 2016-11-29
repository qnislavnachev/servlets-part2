package servlets;

import core.Template;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class CounterServlet extends HttpServlet {
  private Template template;
  private HashMap<String, Integer> links;

  public CounterServlet(Template template, HashMap<String, Integer> links) {
    this.template = template;
    this.links = links;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String uri = req.getRequestURI();

    String link = uri.substring(1, uri.length());

    if (links.containsKey(link)) {
      Integer count = links.get(link);
      links.put(link, count + 1);
    }

    for (Entry<String, Integer> entry : links.entrySet()) {
      template.put(entry.getKey(), entry.getValue().toString());
    }

    PrintWriter writer = resp.getWriter();
    writer.println(template.evaluate());
  }
}
