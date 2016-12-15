package greetingpages.servlets;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import greetingpages.Template;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

public class GreetingServlet extends HttpServlet {

    private Template template = new Template() {{
        add("page1", "Welcome to Page 1");
        add("page2", "Welcome to Page 2");
        add("page3", "Welcome to Page 3");
    }};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        PrintWriter writer = resp.getWriter();

        String pageName = req.getParameter("pages");
        int value = incrementAttributeValue(pageName, session);

        String message = generateMessage(pageName, value);
        String resources = Resources.toString(GreetingServlet.class.getResource("message.html"), Charsets.UTF_8);

        renderPage(resources, message, writer);
    }

    private int incrementAttributeValue(String pageName, HttpSession session) {
        if (session.getAttribute(pageName) == null) {
            session.setAttribute(pageName, 0);
        }
        Integer count = (Integer) session.getAttribute(pageName) + 1;
        session.setAttribute(pageName, count);
        return (int) session.getAttribute(pageName);
    }

    private String generateMessage(String param, int value) throws ServletException, IOException {
        if (value < 2) {
            return template.getValue(param);
        }
        return "You already were here";
    }

    private void renderPage(String resources, String message, PrintWriter writer) throws IOException {
        resources = resources.replace("message", message);
        writer.write(resources);
        writer.flush();
    }
}