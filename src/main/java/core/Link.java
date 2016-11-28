package core;

import java.util.LinkedHashMap;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public interface Link {
  void click(String name);

  Integer getClicksFor(String link);

  LinkedHashMap<String,Integer> getClicks();
}
