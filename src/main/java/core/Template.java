package core;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public interface Template {
  void put(String placeHolder, String value);

  String evaluate();
}
