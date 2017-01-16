package algopars.tool;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe BasicFunction qui gère toutes les primitives
 *
 * @author Groupe 9 - (Antoine WARET, Clémence EDOUARD, BOULANT Florian, CHOUGUI Mathieu)
 * @version 1.0.0a
 * @date 01/11/2017
 */
public class BasicFunction
{
	
	public static final String ENCHAINE    = "ENCHAINE";
	public static final String ENENTIER    = "ENENTIER";
	public static final String ENREEL      = "ENREEL";
	public static final String CARD        = "CARD";
	public static final String ORD         = "ORD";
	public static final String PLANCHER    = "PLANCHER";
	public static final String PLAFOND     = "PLAFOND";
	public static final String ARRONDI     = "ARRONDI";
	public static final String AUJOURD_HUI = "AUJOURD_HUI";
	public static final String JOUR        = "JOUR";
	public static final String MOIS        = "MOIS";
	public static final String ANNEE       = "ANNEE";
	public static final String ESTREEL     = "ESTREEL";
	public static final String ESTENTIER   = "ESTENTIER";
	public static final String HASARD      = "HASARD";
	public static final String COSINUS     = "COSINUS";
	public static final String SINUS       = "SINUS";
	public static final String ABSOLUE     = "ABSOLUE";
	
	/**
	 * Méthode permettant de rediriger vers les fonctions correspondantes
	 *
	 * @param functions
	 * 		La chaîne correspondant à la fonction en pseudo-code
	 *
	 * @return La chaîne du résultat
	 */
	public static String chooseBasicFunction(String functions)
	{
		functions = functions.replaceAll("\\'", "_");
		String[] function = functions.split("\\(|\\)");
		function[0] = function[0].toUpperCase();
		switch(function[0].trim())
		{
			case ENCHAINE:
				return enChaine(function[1]);
			case ENENTIER:
				return enEntier(function[1]);
			case ENREEL:
				return enReel(function[1]);
			case CARD:
				return card(function[1]);
			case ORD:
				return ord(function[1]);
			case PLANCHER:
				return plancher(function[1]);
			case PLAFOND:
				return plafond(function[1]);
			case ARRONDI:
				return arrondi(function[1]);
			case AUJOURD_HUI:
				return aujourd_hui();
			case JOUR:
				return jour(function[1]);
			case MOIS:
				return mois(function[1]);
			case ANNEE:
				return annee(function[1]);
			case ESTREEL:
				return estReel(function[1]);
			case ESTENTIER:
				return estEntier(function[1]);
			case HASARD:
				return hasard(function[1]);
			case COSINUS:
				return cosinus(function[1]);
			case SINUS:
				return sinus(function[1]);
			case ABSOLUE:
				return absolue(function[1]);
			default:
				return "";
			
		}
		
	}




	/**
	 * Méthode renvoyant l'arrondi d'un réel
	 *
	 * @param s
	 * 		La chaîne représentant le réel
	 *
	 * @return L'arrondi du réel passé en paramètre
	 */
	private static String arrondi(String s)
	{
		return "" + Math.round(Double.parseDouble(s)) + ".0";
	}
	
	
	/**
	 * Méthode renvoyant le prochain entier plus grand que le réel en paramètre
	 *
	 * @param s
	 * 		Le réel à plafonner
	 *
	 * @return Le plafond du réel
	 */
	private static String plafond(String s)
	{
		return "" + Math.ceil(Double.parseDouble(s));
	}
	
	
	/**
	 * Méthode renvoyant le prochain entier plus petit que le réel passé en paramètre
	 *
	 * @param s
	 * 		Le réel à plancher
	 *
	 * @return Le plancher du réel
	 */
	private static String plancher(String s)
	{
		return "" + Math.floor(Double.parseDouble(s));
	}
	
	
	/**
	 * Méthode renvoyant la valeur ASCII du caractère passé en paramètre
	 *
	 * @param s
	 * 		Le caractère dont on doit renvoyer la valeur ASCII
	 *
	 * @return La valeur ASCII du caractère passé en paramètre
	 */
	private static String ord(String s)
	{
		return "" + (int) s.charAt(0);
	}
	
	
	/**
	 * Méthode permettant de transformer une chaine de caractère en réel
	 *
	 * @param s
	 * 		La chaîne de caractère à transformer en réel
	 *
	 * @return Le réel correspondant à la chaîne
	 */
	private static String enReel(String s)
	{
		s = supressQuotes(s);
		return Double.toString(Double.parseDouble(s));
	}
	
	
	/**
	 * Méthode permettant de transformer une chaine de caractère en entier
	 *
	 * @param s
	 * 		La chaîne de caractère à transformer en entier
	 *
	 * @return Le réel correspondant à la chaîne
	 */
	private static String enEntier(String s)
	{
		s = supressQuotes(s);
		String[] tab = s.split("\\.");
		return Integer.toString(Integer.parseInt(tab[0]));
	}
	
	/**
	 * Méthode permettant de donner le caractère correspondant à la valeur ASCII passée en paramètre
	 *
	 * @param s
	 * 		La valeur ASCII dont on cherche le caractère équivalent
	 *
	 * @return Le caractère correspondant à la valeur ASCII en paramètre
	 */
	private static String card(String s)
	{
		return "" + (char) Integer.parseInt(s);
	}
	
	
	/**
	 * Méthode permettant de transformer le paramètre en chaîne
	 *
	 * @param s
	 * 		Le paramètre à transformer en chaîne
	 *
	 * @return La chaîne correspondant au paramètre
	 */
	private static String enChaine(String s)
	{
		return "\"" + s + "\"";
	}
	
	
	/**
	 * Méthode permettant de retourner le résultat de la primitive aujourd'hui()
	 *
	 * @return Le résultat de la primitive aujourd'hui()
	 */
	private static String aujourd_hui()
	{
		DateTimeFormatter dtf      = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime     now      = LocalDateTime.now();
		String            date     = dtf.format(now);
		String[]          tabDate  = date.split(" ");
		String            toReturn = "\"" + tabDate[0] + "\"";
		return toReturn;
	}
	
	
	/**
	 * Méthode permettant de retourner le résultat de la primitive jour(chaine)
	 *
	 * @param function
	 * 		La date à décomposer
	 *
	 * @return Le résultat de la primitive jour(chaine)
	 */
	private static String jour(String function)
	{
		function = supressQuotes(function);
		String[] tab = function.split("\\\\");
		return tab[0];
	}
	
	
	/**
	 * Méthode permettant de retourner le résultat de la primitive mois(chaine)
	 *
	 * @param function
	 * 		La date à décomposer
	 *
	 * @return Le résultat de la primitive mois(chaine)
	 */
	private static String mois(String function)
	{
		function = supressQuotes(function);
		String[] tab = function.split("\\\\");
		return tab[1];
	}
	
	/**
	 * Méthode permettant de retourner le résultat de la primitive annee(chaine)
	 *
	 * @param function
	 * 		La date à décomposer
	 *
	 * @return Le résultat de la primitive annee(chaine)
	 */
	private static String annee(String function)
	{
		function = supressQuotes(function);
		String[] tab = function.split("\\\\");
		return tab[2];
	}
	
	/**
	 * Méthode permettant de retourner le résultat de la primitive estReel(chaine)
	 *
	 * @param function
	 * 		La chaine à analyser
	 *
	 * @return Le résultat de la primitive estReel(chaine)
	 */
	private static String estReel(String function)
	{
		function = supressQuotes(function).replaceAll("\\.", ",");
		if(Regex.isDouble(function))
		{
			return "vrai";
		}
		else
		{
			return "faux";
		}
	}
	
	/**
	 * Méthode permettant de retourner le résultat de la primitive estEntier(chaine)
	 *
	 * @param function
	 * 		La chaine à analyser
	 *
	 * @return Le résultat de la primitive estEntier(chaine)
	 */
	private static String estEntier(String function)
	{
		function = supressQuotes(function).replaceAll("\\.", ",");
		if(Regex.isInteger(function))
		{
			return "vrai";
		}
		else
		{
			return "faux";
		}
	}
	
	/**
	 * Méthode permettant de retourner le résultat de la primitive hasard(entier)
	 *
	 * @param function
	 * 		La chaine représentant l'entier définissant les bornes de la primitive
	 *
	 * @return Le résultat de la primitive hasard(entier)
	 */
	private static String hasard(String function)
	{
		int range = Integer.parseInt(function);
		return Integer.toString((int) (Math.random() * range));
	}
	/**
	 * Méthode permettant de retourner le résultat de la primitive cosinus(réel)
	 *
	 * @param function
	 * 		La chaine représentant le réel dont on cherche le cosinus
	 *
	 * @return Le résultat de la primitive cosinus(réel)
	 */
	private static String cosinus( String function )
	{
		return Double.toString(Math.cos( Double.parseDouble( function ) ));
	}
	/**
	 * Méthode permettant de retourner le résultat de la primitive sinus(réel)
	 *
	 * @param function
	 * 		La chaine représentant le réel dont on cherche le sinus
	 *
	 * @return Le résultat de la primitive sinus(réel)
	 */
	private static String sinus( String function )
	{
		return Double.toString(Math.sin( Double.parseDouble( function ) ));
	}
	/**
	 * Méthode permettant de retourner le résultat de la primitive absolue(réel) ou absolue(entier)
	 *
	 * @param function
	 * 		La chaine représentant le réel ou l'entier dont on cherche la valeur absolue
	 *
	 * @return Le résultat de la primitive absolue(entier) ou absolue(réel)
	 */
	private static String absolue( String function )
	{
		function = supressQuotes(function);
		if(function.contains("\\." )) return Double.toString(Math.abs( Double.parseDouble( function ) ));
		else return Integer.toString(Math.abs( Integer.parseInt( function ) ));
	}
	/**
	 * Méthode permettant de retirer les '"" en trop
	 *
	 * @param s
	 * 		La chaîne à trnasformer
	 *
	 * @return Une chaîne sans '"' en trop
	 */
	public static String supressQuotes(String s)
	{
		String[] tab = s.split("\"");
		return s.replaceAll("\"", "");
	}
	
}
