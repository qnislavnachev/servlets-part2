package counterservlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Jetty {
    private Server server;

    public Jetty(int port) {
        server = new Server(port);
    }

    public void start() {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");

        contextHandler.addEventListener(new ServletContextListener() {

            @Override
            public void contextInitialized(ServletContextEvent sce) {
                ServletContext servletContext = sce.getServletContext();
                servletContext.addServlet("counter", new CountingServlet()).addMapping("/test");
                servletContext.addServlet("default", new DefaultServlet());
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {

            }
        });

        server.setHandler(contextHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
