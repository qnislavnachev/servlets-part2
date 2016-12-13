package counterservlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

public class CountingServlet extends HttpServlet {
    private Template template = new Template();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        PrintWriter writer = resp.getWriter();

        String param = req.getParameter("link");
        int count = (int) getAttribute(param, session);
        session.setAttribute(param, count + 1);

        template.add("link1", getAttribute("1", session).toString());
        template.add("link2", getAttribute("2", session).toString());
        template.add("link3", getAttribute("3", session).toString());

        render(CountingServlet.class.getResourceAsStream("mainPage.html"), writer);
    }

    private void render(InputStream resourceAsStream, PrintWriter writer) throws IOException {
        String page = readStreamToString(resourceAsStream);
        writer.write(template.evaluate(page));
        writer.flush();
    }

    private String readStreamToString(InputStream resourceAsStream) throws IOException {
        String page = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
        String line;
        while ((line = reader.readLine()) != null) {
            page += line;
        }
        return page;
    }

    private Object getAttribute(String name, HttpSession session) {
        if (session.getAttribute(name) == null) {
            session.setAttribute(name, 0);
        }
        return session.getAttribute(name);
    }
}