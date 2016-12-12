package pageloader;

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

    public String evaluate(String message) {
        for (String placeHolder : placeHolders.keySet()) {
            message = message.replaceAll("\\$\\{" + placeHolder + "}", placeHolders.get(placeHolder));
        }
        return message;
    }
}