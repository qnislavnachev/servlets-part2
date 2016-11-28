import adapter.ClickedLinks;
import core.Link;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class ClickingLinksTest {
  private Link clickeLinks = new ClickedLinks(new LinkedHashMap<String, Integer>() {{
    put("link1", 0);
    put("link2", 0);
    put("link3", 0);
  }});

  @Test
  public void clickOneLink() throws Exception {
    clickeLinks.click("link1");
    Integer actual = 1;
    Integer expected = clickeLinks.getClicksFor("link1");
    assertThat(actual, is(expected));
  }

  @Test
  public void clickLinkTwoTimes() throws Exception {
    clickeLinks.click("link1");
    clickeLinks.click("link1");
    Integer actual = 2;
    Integer expected = clickeLinks.getClicksFor("link1");
    assertThat(actual, is(expected));
  }

  @Test
  public void clickManyLinks() throws Exception {
    clickeLinks.click("link1");
    clickeLinks.click("link1");
    clickeLinks.click("link1");
    clickeLinks.click("link2");
    clickeLinks.click("link2");
    clickeLinks.click("link3");
    clickeLinks.click("link3");
    clickeLinks.click("link3");
    clickeLinks.click("link3");
    LinkedHashMap<String, Integer> actual = new LinkedHashMap<>();
    actual.put("link1", 3);
    actual.put("link2", 2);
    actual.put("link3", 4);
    LinkedHashMap<String, Integer> exlpected = clickeLinks.getClicks();
    assertThat(actual, is(exlpected));
  }
}
