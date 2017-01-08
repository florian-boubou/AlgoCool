package tool;

/**
 * Created by Terawa on 06/01/2017.
 */
public class Regex {

    public static boolean isVariable(String s) {return s.matches("^[a-z][0-9A-Za-z]*((-|_)[0-9a-zA-Z]*)*$");}

    public static boolean isConstant(String s) {return s.matches("^[A-Z][0-9A-Z]*(_[0-9A-Z]*)*$");}

    public static boolean isInteger(String s) {return s.matches("[0-9]*");}

    public static  boolean isDouble(String s) {return s.matches("[0-9]*,[0-9]+");}

    //public static boolean isBoolean(String s) {return s.matches();}

    public static boolean isString(String s) {return s.matches("^\"([^\"]|\\\\\")*\"$");}

    public static boolean isCharacter(String s) {return s.matches("^'[^']'$");}
}
