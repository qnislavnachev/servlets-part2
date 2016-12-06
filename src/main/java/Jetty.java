
import chain.Chainlet;
import counter.Counter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public final class Jetty {
    private final Server server;

    public Jetty(int port) {
        this.server = new Server(port);
    }

    public void start() {
        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContext.setContextPath("/");

        servletContext.addEventListener(new ServletContextListener() {

            public void contextInitialized(ServletContextEvent servletContextEvent) {
                ServletContext servletContext = servletContextEvent.getServletContext();


                servletContext.addServlet("chainletServlet", new Chainlet()).addMapping("/page1", "/page2", "/page3");
                servletContext.addServlet("counterServlet", new Counter()).addMapping("/counter");
            }

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
