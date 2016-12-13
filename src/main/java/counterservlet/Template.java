package counterservlet;

import java.util.LinkedHashMap;
import java.util.Map;

public class Template {
    private Map<String, String> placeHolders;

    public Template() {
        placeHolders = new LinkedHashMap<>();
    }

    public void add(String placeHolder, String value) {
        placeHolders.put(placeHolder, value);
    }

    public String evaluate(String page) {
        for (String holder : placeHolders.keySet()) {
            page = page.replaceAll("\\$\\{" + holder + "\\}", placeHolders.get(holder));
        }
        return page;
    }
}