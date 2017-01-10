package algopars.util.color;

/**
 * Created by clemence on 10/01/17.
 */
public class Color {

    public static String WHITE;
    public static String BLACK;
    public static String BLUE;
    public static String CYAN;
    public static String YELLOW;
    public static String GREEN;
    public static String RED;
    public static String BACKGROUND_WHITE;
    public static String BACKGROUND_BLACK;
    public static String BOLD;

    static {
        OS os = OS.getOS();
        if(os == OS.LINUX || os == OS.MAC)
        {
            WHITE  = "\033[37m";
            BLACK  = "\033[30m";
            BLUE   = "\033[34m";
            CYAN   = "\033[36m";
            YELLOW = "\033[33m";
            GREEN  = "\033[32m";
            RED    = "\033[31m";
            BACKGROUND_WHITE = "\033[47m";
            BACKGROUND_BLACK = "\033[40m";
            BOLD = "\033[1m";
        }

        else if(os == OS.WINDOWS)
        {
            WHITE  = "\\x1b[37m";
            BLACK  = "\\x1b[30m";
            BLUE   = "\\x1b[34m";
            CYAN   = "\\x1b[36m";
            YELLOW = "\\x1b[33m";
            GREEN  = "\\x1b[32m";
            RED    = "\\x1b[31m";
            BACKGROUND_WHITE = "\\x1b[47m";
            BACKGROUND_BLACK = "\\x1b[40m";
            BOLD = "\\x1b[1m";
        }


    }
}
