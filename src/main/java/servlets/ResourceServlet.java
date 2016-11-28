package servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class ResourceServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String resourceName = req.getRequestURI().split("\\/")[2];
    InputStream resourceAsStream = ResourceServlet.class.getResourceAsStream(resourceName);

    if (resourceName.endsWith(".css")) {
      resp.setContentType("text/css");
    } else if (resourceName.endsWith(".js")) {
      resp.setContentType("text/javascript");
    } else if (resourceName.endsWith(".html")) {
      resp.setContentType("text/html");
    }

    byte[] content = convertToBytes(resourceAsStream);
    ServletOutputStream outputStream = resp.getOutputStream();
    outputStream.write(content);
    outputStream.flush();
  }

  private byte[] convertToBytes(InputStream inStream) {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    byte data[] = new byte[16384];
    int nRead;
    try {
      while ((nRead = inStream.read(data, 0, data.length)) != -1) {
        buffer.write(data, 0, nRead);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      buffer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return buffer.toByteArray();
  }
}
