import adapter.ClickedLinks;
import adapter.HtmlTemplate;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import servlets.ClickCounterServlet;
import servlets.ResourceServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class JettyMain {
  public static void main(String[] args) {
    final ClickedLinks clickableLinks = new ClickedLinks(new LinkedHashMap<String, Integer>() {{
      put("link1", 0);
      put("link2", 0);
      put("link3", 0);
    }});

    File indexHtml = new File("src/main/resources/servlets/index.html");
    String indexString = null;
    try {
      indexString = FileUtils.readFileToString(indexHtml, "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
    final HtmlTemplate indexTemplate = new HtmlTemplate(indexString);

    Server server = new Server(8080);
    ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
    servletContext.setContextPath("/");
    servletContext.addEventListener(new ServletContextListener() {
      @Override
      public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.addServlet("ClickCounterServlet", new ClickCounterServlet(indexTemplate, clickableLinks)).addMapping("/");
        servletContext.addServlet("ResourceServlet", new ResourceServlet()).addMapping("/assets/*");
      }

      @Override
      public void contextDestroyed(ServletContextEvent servletContextEvent) {
      }
    });
    server.setHandler(servletContext);
    try {
      server.start();
      server.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
