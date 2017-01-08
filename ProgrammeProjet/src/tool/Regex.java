package tool;

/**
 * Created by Terawa on 06/01/2017.
 */
public class Regex {
    public static final String REGEX_VARIABLE = "^[a-z][0-9A-Za-z]*(_[0-9a-zA-Z]*)*$";
    public static final String REGEX_CONSTANT = "^[A-Z][0-9A-Z]*(_[0-9A-Z]*)*$";
    public static final String REGEX_INTEGER = "[0-9]*";
    public static final String REGEX_DOUBLE = "[0-9]*,[0-9]+";
    public static final String REGEX_BOOLEAN = "^true|false$";
    public static final String REGEX_STRING = "^\"([^\"]|\\\\\")*\"$";
    public static final String REGEX_CHAR = "^'[^']'$";

    public static boolean isVariable(String s) {return s.matches(REGEX_VARIABLE);}

    public static boolean isConstant(String s) {return s.matches(REGEX_CONSTANT);}

    public static boolean isInteger(String s) {return s.matches(REGEX_INTEGER);}

    public static  boolean isDouble(String s) {return s.matches(REGEX_DOUBLE);}

    public static boolean isBoolean(String s) {return s.matches(REGEX_BOOLEAN);}

    public static boolean isString(String s) {return s.matches(REGEX_STRING);}

    public static boolean isCharacter(String s) {return s.matches(REGEX_CHAR);}
}
