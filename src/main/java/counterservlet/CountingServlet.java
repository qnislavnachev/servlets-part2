package counterservlet;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CountingServlet extends HttpServlet {

    private Map<String, String> placeHolders = new LinkedHashMap<String, String>() {{
        put("link1", "1");
        put("link2", "2");
        put("link3", "3");
    }};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        PrintWriter writer = resp.getWriter();
        String param = req.getParameter("link");

        /**
         * update attribute with incremented value
         */
        session.setAttribute(param, (int) getAttribute(param, session) + 1);

        render("src/main/resources/mainPage.html", session, writer);
    }

    private void render(String path, HttpSession session, PrintWriter writer) throws IOException {
        String text = Files.toString(new File(path), Charsets.UTF_8);
        for (String placeHolder : placeHolders.keySet()) {
            text = text.replaceAll("\\$\\{" + placeHolder + "\\}",
                    getAttribute(placeHolders.get(placeHolder), session).toString());
        }
        writer.write(text);
        writer.flush();
    }

    private Object getAttribute(String name, HttpSession session) {
        if (session.getAttribute(name) == null) {
            session.setAttribute(name, 0);
        }
        return session.getAttribute(name);
    }
}