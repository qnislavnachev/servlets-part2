package counter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class Counter extends HttpServlet {
    public Counter() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(30);
        String parameter = req.getParameter("linkName");
        if (session.getAttribute(parameter) == null) {
            session.setAttribute(parameter, 0);
        }
        int count = (int) session.getAttribute(parameter) + 1;
        session.setAttribute(parameter, count);

        String page = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "    <a href=\"?linkName=first\" >Link1</a>&nbsp"+ nullToEmpty(session.getAttribute("first")) + "<br>\n" +
                "    <a href=\"?linkName=second\" >Link2</a>&nbsp"+ nullToEmpty(session.getAttribute("second")) + "<br>\n" +
                "    <a href=\"?linkName=third\" >Link3</a>&nbsp"+ nullToEmpty(session.getAttribute("third")) + "\n" +
                "</body>\n" +
                "</html>";

        PrintWriter writer = resp.getWriter();
        writer.println(page);
        writer.flush();
    }

    private Object nullToEmpty(Object o) {
        if(o == null) {
            return "";
        }
        return o;
    }

}
