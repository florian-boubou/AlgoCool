package algopars.UI;

/**
 * Created by clemence on 10/01/17.
 */
public class Keyword {
    private String regex;
    private String keyword;
    private String color;

    public Keyword(String regex, String keyword, String color) {
        this.regex = regex;
        this.keyword = keyword;
        this.color = color;
    }

    public String getRegex() {
        return regex;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getColor() {
        return color;
    }
}
