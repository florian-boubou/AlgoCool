package algopars.UI;

/**
 * Classe Keyword qui permet d'associer un mot clé avec une couleur et une RegEx de vérification
 *
 * @author Clémence EDOUARD, Florian BOULANT
 * @version 1.0.0a
 * @date 01/10/2017
 */
public class Keyword
{
    private String regex;
    private String keyword;
    private String color;
    
    /**
     * Constructeur de Keyword
     * @param regex
     *      La RegEx de vérification du mot-clé
     * @param keyword
     *      le mot-clé
     * @param color
     *      La couleur associée au mot-clé
     */
    public Keyword(String regex, String keyword, String color)
    {
        this.regex      = regex;
        this.keyword    = keyword;
        this.color      = color;
    }
    
    /**
     * Méthode permettant d'obtenir la RegEx de vérification
     * @return
     *      La RegEx de vérification
     */
    public String getRegex()
    {
        return regex;
    }
    
    /**
     * Méthode permettant d'obtenir le mot-clé
     * @return
     *      Le mot-clé
     */
    public String getKeyword()
    {
        return keyword;
    }
    
    /**
     * Méthode permettant d'obtenir la couleur
     * @return
     *      La couleur
     */
    public String getColor()
    {
        return color;
    }
}
