package greetingpages;

import greetingpages.servlets.*;
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
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        handler.addEventListener(new ServletContextListener() {

            @Override
            public void contextInitialized(ServletContextEvent sce) {
                ServletContext servletContext = sce.getServletContext();
                servletContext.addServlet("default", new DefaultServlet());
                servletContext.addServlet("greeting", new GreetingServlet()).addMapping("/result");
                servletContext.addServlet("home", new HomePageServlet()).addMapping("/");
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {

            }
        });

        server.setHandler(handler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}