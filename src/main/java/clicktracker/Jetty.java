package clicktracker;

import clicktracker.servlet.ClickTrackerServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>
 */
public class Jetty {
    private Server server;

    /**
     * Constructor to access the server via port.
     *
     * @param server
     */
    public Jetty(Server server) {
        this.server = server;
    }

    /**
     * Servlet handler which adds servlet listener where the servlet is added with mapping '/links'.
     * It also sets a wrapper for the servlet handler.
     * And starts the server in a try block.
     * In case the server doesn't start, the stacktrace is printed.
     */
    public void start() {
        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContext.setContextPath("/");
        servletContext.addEventListener(new ServletContextListener() {
            @Override
            public void contextInitialized(ServletContextEvent servletContextEvent) {
                ServletContext servletContext = servletContextEvent.getServletContext();
                servletContext.addServlet("clickTracker", new ClickTrackerServlet()).addMapping("/links");
            }

            @Override
            public void contextDestroyed(ServletContextEvent servletContextEvent) {
            }
        });

        server.setHandler(servletContext);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
