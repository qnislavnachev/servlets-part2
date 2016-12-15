package greetingpages;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Template {

    private final Map<String, String> placeHolders;

    public Template() {
        placeHolders = new LinkedHashMap<>();
    }

    public void add(String parameter, String value) {
        placeHolders.put(parameter, value);
    }

    public String getValue(String param) throws ServletException, IOException {
        return placeHolders.get(param);
    }
}