package adapter;

import core.Template;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class TemplateTest {
  @Test
  public void singleVariable() throws Exception {
    Template template = new HtmlTemplate("This is a single HTML ${value}");
    template.put("value", "template");
    String actual = template.evaluate();
    String expected = "This is a single HTML template";
    assertThat(actual, is(expected));
  }

  @Test
  public void manyVariables() throws Exception {
    Template template = new HtmlTemplate("This template has not ${single} variable but ${many}");
    template.put("single", "one");
    template.put("many", "two");
    String actual = template.evaluate();
    String expected = "This template has not one variable but two";
    assertThat(actual, is(expected));
  }
}
