package adapter;

import core.Link;

import java.util.LinkedHashMap;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class ClickedLinks implements Link {
  private LinkedHashMap<String, Integer> links;

  public ClickedLinks(LinkedHashMap<String, Integer> links) {
    this.links = links;
  }

  @Override
  public void click(String name) {
    Integer newValue = links.get(name);
    links.put(name, (newValue + 1));

  }

  @Override
  public Integer getClicksFor(String name) {
    return links.get(name);
  }

  @Override
  public LinkedHashMap<String, Integer> getClicks() {
    return links;
  }
}
