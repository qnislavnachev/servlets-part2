package clicktracker;

import org.eclipse.jetty.server.Server;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>
 */
public class Demo {
    public static void main(String[] args) {
        Jetty jetty = new Jetty(new Server(8080));
        jetty.start();
    }
}
