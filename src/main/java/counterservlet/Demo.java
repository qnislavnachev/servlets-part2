package counterservlet;

import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws IOException {
        Jetty jetty = new Jetty(8080);
        jetty.start();
    }
}