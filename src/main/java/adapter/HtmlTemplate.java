package adapter;

import core.Template;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class HtmlTemplate implements Template {
  private final Map<String, String> placeHolderToValue = new LinkedHashMap<String, String>();
  private final String templateValue;

  public HtmlTemplate(String templateValue) {
    this.templateValue = templateValue;
  }

  public void put(String placeHolder, String value) {
    placeHolderToValue.put(placeHolder, value);
  }

  public String evaluate() {
    String evalResult = templateValue;
    for (String placeHolder : placeHolderToValue.keySet()) {
      evalResult = evalResult.replaceAll("\\$\\{" + placeHolder + "\\}", placeHolderToValue.get(placeHolder));
    }
    return evalResult;
  }
}
