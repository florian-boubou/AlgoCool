package algopars.tool;


/**
 * Classe Regex, utilitaire qui permet de vérifier la bonne forme de tous les mots-clés de l'algorithme
 *
 * @author Antoine WARET
 * @version 1.0.0a
 * @date 01/08/2017
 */
public class Regex
{
	public static final String REGEX_VARIABLE       = "^\\s*[a-z]\\w*(_\\w*)*\\s*$";
	public static final String REGEX_CONSTANT       = "^\\s*[A-Z][0-9A-Z]*(_[0-9A-Z]*)*\\s*$";
	public static final String REGEX_INTEGER        = "^\\s*[0-9]+\\s*$";
	public static final String REGEX_DOUBLE         = "^\\s*[0-9]+(,|.)[0-9]+$\\s*";
	public static final String REGEX_BOOLEAN        = "^\\s*(vrai|faux)\\s*$";
	public static final String REGEX_STRING         = "^\\s*\"([^\"]|\\\\\")*\"\\s*$";
	public static final String REGEX_CHAR           = "^\\s*'[^']'\\s*$";
	public static final String REGEX_FUNCTION       = "^\\s*[a-z][a-zA-Z]*_?[a-zA-Z]*\\(.*\\)\\s*$";
	
	public static final String REGEX_OPERATION      = "^\\s*(([0-9]+,?[0-9]*))\\s*" +
	                                                  "([+×\\-/]|(MOD|DIV)){1}\\s*([0-9]+,?[0-9]*)\\s*" +
	                                                  "(([+×\\-/]|(MOD|DIV)){1}\\s*([0-9]+,?[0-9]*))*\\s*$";
	
	public static final String REGEX_CONCATENATION  = "^\\s*((\".*\")|[a-z]\\w*(_\\w*)*)\\s*&\\s*" +
	                                                  "((\".*\")|[a-z]\\w*(_\\w*)*)\\s*" +
	                                                  "(&\\s*((\".*\")|[a-z]\\w*(_\\w*)*))*\\s*$";
	
	public static final String REGEX_CONDITION      = "^\\s*si .+ alors\\s*$";
	public static final String REGEX_LOOP           = "^\\s*tant que .+ faire\\s*$";

	public static final String REGEX_COMMENT        = "^\\s*.*//.*\\s*$";

	public static final String REGEX_ARRAY          = "(^\\s*.*\\s*:\\s*Tableau\\[.*\\]\\s*(de|d\\').*\\s*$)|" +
													  "(^\\s*Tableau\\[.*\\]\\s*(de|d\\').*\\s*$)";
	public static final String REGEX_ARRAY_VAR     = "^.+\\[\\w+\\]$";


	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard de variable
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isVariable( String s )
	{
		return s.matches( REGEX_VARIABLE );
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard de constante
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isConstant( String s )
	{
		return s.matches( REGEX_CONSTANT );
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard des entiers
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isInteger( String s )
	{
		return s.matches( REGEX_INTEGER );
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard des réels
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isDouble( String s )
	{
		return s.matches( REGEX_DOUBLE );
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard des booléens
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isBoolean( String s )
	{
		return s.matches( REGEX_BOOLEAN );
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard des chaînes de caractères
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isString( String s )
	{
		return s.matches( REGEX_STRING );
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard des caractères
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isCharacter( String s )
	{
		return s.matches( REGEX_CHAR );
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien au standard des primitives
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isFunction( String s )
	{
		return s.matches( REGEX_FUNCTION );
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien à une opération mathématique
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isOperation( String s )
	{
		return s.matches( REGEX_OPERATION );
	}
	
	/**
	 * Méthode permettant de savoir si une chaîne correspond bien à une concaténation
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isConcatenation( String s )
	{
		return s.matches( REGEX_CONCATENATION );
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien à une opération
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isCondition( String s )
	{
		return s.matches( REGEX_CONDITION );
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien à une opération
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isLoop( String s )
	{
		return s.matches( REGEX_LOOP );
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien à une opération
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isComment( String s )
	{
		return s.matches( REGEX_COMMENT );
	}
	
	/**
	 * Méthode permettant de savoir si une chaîne correspond bien à quelque chose de passable dans un ecrire
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isExpression( String s )
	{
		return Regex.isInteger(s) || Regex.isDouble(s)      || Regex.isCharacter(s) || Regex.isString(s) 	||
		       Regex.isBoolean(s) || Regex.isVariable(s)    || Regex.isConstant(s)	|| Regex.isOperation(s)	||
			   Regex.isConcatenation(s);
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien à une opération
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isArray( String s )
	{
		return s.matches( REGEX_ARRAY );
	}

	/**
	 * Méthode permettant de savoir si une chaîne correspond bien à une opération
	 *
	 * @param s La chaîne à analyser
	 * @return Un booléen indiquant la réponse
	 */
	public static boolean isArrayVar( String s )
	{
		return s.matches( REGEX_ARRAY_VAR );
	}
}
