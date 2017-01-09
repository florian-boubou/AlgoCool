package tool;

/**
 * Classe Regex, utilitaire qui permet de vérifier la bonne forme de tous les mots-clés de l'algorithme
 *
 * @author Antoine WARET
 * @version 1.0.0a
 * @date 01/08/2017
 */
public class Regex
{
	public static final String REGEX_VARIABLE = "^[a-z]\\w*(_\\w*)*$";
	public static final String REGEX_CONSTANT = "^[A-Z][0-9A-Z]*(_[0-9A-Z]*)*$";
	public static final String REGEX_INTEGER  = "^[0-9]+$";
	public static final String REGEX_DOUBLE   = "^[0-9]+,[0-9]+$";
	public static final String REGEX_BOOLEAN  = "^(vrai|faux)$";
	public static final String REGEX_STRING   = "^\"([^\"]|\\\\\")*\"$";
	public static final String REGEX_CHAR     = "^'[^']'$";
	public static final String REGEX_FUNCTION = "^[a-z][a-zA-Z]*\\( *(([a-z]\\w*(_\\w*)*)+|[0-9]+|\"([^\"]|\\\\\")*\") *([+×,&\\-\\\\] *(([a-z]\\w*(_\\w*)*)+|[0-9]+|\"([^\"]|\\\\\")*\") *)* *\\)$";
	public static final String REGEX_OPERATION= "^[0-9]+,?[0-9]* *([+&×\\-\\\\]|(MOD|DIV)){1} *[0-9]+,?[0-9]*$";
	public static final String REGEX_CONDITION= "^\\s*si .+ alors\\s*$";
	
	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard de variable
	 *
	 * @param s
	 * 		La chaîne à analyser
	 *
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isVariable(String s)
	{
		return s.matches(REGEX_VARIABLE);
	}
	
	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard de constante
	 *
	 * @param s
	 * 		La chaîne à analyser
	 *
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isConstant(String s)
	{
		return s.matches(REGEX_CONSTANT);
	}
	
	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard des entiers
	 *
	 * @param s
	 * 		La chaîne à analyser
	 *
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isInteger(String s)
	{
		return s.matches(REGEX_INTEGER);
	}
	
	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard des réels
	 *
	 * @param s
	 * 		La chaîne à analyser
	 *
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isDouble(String s)
	{
		return s.matches(REGEX_DOUBLE);
	}
	
	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard des booléens
	 *
	 * @param s
	 * 		La chaîne à analyser
	 *
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isBoolean(String s)
	{
		return s.matches(REGEX_BOOLEAN);
	}
	
	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard des chaînes de caractères
	 *
	 * @param s
	 * 		La chaîne à analyser
	 *
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isString(String s)
	{
		return s.matches(REGEX_STRING);
	}
	
	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard des caractères
	 *
	 * @param s
	 * 		La chaîne à analyser
	 *
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isCharacter(String s)
	{
		return s.matches(REGEX_CHAR);
	}
	
	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard des primitives
	 *
	 * @param s
	 * 		La chaîne à analyser
	 *
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isFunction(String s)
	{
		return s.matches(REGEX_FUNCTION);
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien à une opération
	 *
	 * @param s
	 * 		La chaîne à analyser
	 *
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isOperation(String s)
	{
		return s.matches(REGEX_OPERATION);
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien à une opération
	 *
	 * @param s
	 * 		La chaîne à analyser
	 *
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isCondition(String s)
	{
		return s.matches(REGEX_CONDITION);
	}
}
