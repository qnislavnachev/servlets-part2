package clicktracker;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>
 */
public class HtmlTemplate {
    private final String html;
    private final Map<String, String> links;

    /**
     * Constructor for the html file and the hash map.
     *
     * @param html
     * @param links
     */
    public HtmlTemplate(String html, Map<String, String> links) {
        this.html = html;
        this.links = links;
    }

    /**
     * evaluate() method that replaces the placeHolders with the actual number of clicks.
     *
     * @return
     */
    public synchronized String evaluate() {
        String result = html;
        for (String placeHolder : links.keySet()) {
            result = result.replace("{" + placeHolder + "}", links.get(placeHolder));
        }
        return result;
    }
}
