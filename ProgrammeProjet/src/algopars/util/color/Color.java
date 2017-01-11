package algopars.util.color;

/**
 * Classe Color qui permet d'obtenir les caractères ANSI de couleur en fonction de l'OS de la machine courante
 *
 * @author Clémence EDOUARD
 * @version 1.0.0a
 * @date 01/10/2017
 */
public class Color
{
    public static String WHITE;
    public static String BLACK;
    public static String BLUE;
    public static String CYAN;
    public static String YELLOW;
    public static String GREEN;
    public static String RED;
    public static String BACKGROUND_WHITE;
    public static String BACKGROUND_BLACK;
    public static String BACKGROUND_GREEN;
    public static String BACKGROUND_RED;

    public static String BOLD;
    
    /**
     * Construction de l'ensemble des valeurs selon l'OS
     */
    static
    {
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
            BACKGROUND_GREEN = "\033[42m";
            BACKGROUND_RED = "\033[41m";
            BOLD = "\033[1m";
        }

        else if(os == OS.WINDOWS)
        {
            WHITE  = "\u001b[37m";
            BLACK  = "\u001b[30m";
            BLUE   = "\u001b[34m";
            CYAN   = "\u001b[36m";
            YELLOW = "\u001b[33m";
            GREEN  = "\u001b[32m";
            RED    = "\u001b[31m";
            BACKGROUND_WHITE = "\u001b[47m";
            BACKGROUND_BLACK = "\u001b[40m";
            BACKGROUND_GREEN = "\u001b[42m";
            BACKGROUND_RED = "\u001b[41m";
            BOLD = "\u001b[1m";
        }
    }
}
