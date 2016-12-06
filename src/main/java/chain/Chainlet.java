package chain;

import com.google.common.collect.ImmutableList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Petar Nedelchev (peter.krasimirov@gmail.com)
 */
public class Chainlet extends HttpServlet {
    private final List<Link> allLinks = ImmutableList.of(
            new Link("page1", "<a href=\"/page1\" >Go to page 1</a><br>"),
            new Link("page2", "<a href=\"/page2\" >Go to page 2</a><br>"),
            new Link("page3", "<a href=\"/page3\" >Go to page 3</a><br>")
    );

    public Chainlet() {}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String origin = req.getHeader("referer");
        if(origin == null) {
            origin = "home";
        }

        final String pageName = req.getRequestURI().substring(1);

        List<Link> links = allLinks.stream().filter(link -> {
            if (pageName.equals(link.linkName)) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        StringBuilder pageContent = new StringBuilder();
        pageContent.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>I came from " + origin + "</div>\n");

        for (Link each: links) {
            pageContent.append(each.linkContent);
        }

        pageContent.append("</body>\n" +
                "</html>");

        PrintWriter writer = resp.getWriter();
        writer.println(pageContent);
        writer.flush();
    }


}
