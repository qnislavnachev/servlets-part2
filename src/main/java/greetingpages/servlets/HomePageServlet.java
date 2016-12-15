package greetingpages.servlets;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HomePageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String page = Resources.toString(HomePageServlet.class.getResource("index.html"), Charsets.UTF_8);
        writer.write(page);
        writer.flush();
    }
}