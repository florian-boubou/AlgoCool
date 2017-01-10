package algopars.util.color;

/**
* Enum OS qui stocke les valeurs d'OS possibles (les plus courants) et permet d'obtenir le système de la machine courante
*
* @author Clémence EDOUARD
* @version 1.0.0a
* @date 01/10/2017
*/
public enum OS {

    WINDOWS("win"),
    MAC("mac"),
    LINUX("nix", "nux", "aix"),
    SOLARIS("sunos")
    ;

    private String[] patterns;
    
    /**
     * Constructeur de OS
     * @param patterns
     *      La valeur de création de l'OS
     */
    OS(String... patterns)
    {
        this.patterns = patterns;
    }
    
    /**
     * Méthode permettant de savoir si la chaîne passée en paramètre est une valeur d'OS
     * @param os
     *      La chaîne à tester
     * @return
     *      La réponse
     */
    private boolean match(String os)
    {
        for (String p : patterns)
        {
            if (os.contains(p))
                return true;
        }
        return false;
    }

    private static       OS     OS;
    private static final String P_OS = System.getProperty("os.name").toLowerCase();
    
    /**
     * Méthode permettant d'obtenir l'OS du système
     * @return
     *      L'OS
     */
    public static OS getOS()
    {
        if (OS == null)
        {
            for (OS os : values())
            {
                if (os.match(P_OS))
                {
                    OS = os;
                    break;
                }
            }
        }
        return OS;
    }
}
