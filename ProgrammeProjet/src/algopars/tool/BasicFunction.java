package algopars.tool;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Corentin Athinault on 11/01/2017.
 */
public class BasicFunction
{

	public static final String ENCHAINE			= "ENCHAINE";
	public static final String ENENTIER			= "ENENTIER";
	public static final String ENREEL			= "ENREEL";
	public static final String CARD				= "CARD";
	public static final String ORD				= "ORD";
	public static final String PLANCHER			= "PLANCHER";
	public static final String PLAFOND			= "PLAFOND";
	public static final String ARRONDI			= "ARRONDI";
	public static final String AUJOURD_HUI 		= "AUJOURD_HUI";
	public static final String JOUR 			= "JOUR";
	public static final String MOIS				= "MOIS";
	public static final String ANNEE	 		= "ANNEE";
	public static final String ESTREEL	 		= "ESTREEL";
	public static final String ESTENTIER		= "ESTENTIER";
	public static final String HASARD 			= "HASARD";

	public static String chooseBasicFunction(String functions)
	{
		functions = functions.replaceAll( "\\,|\\'","_" ).toUpperCase();
		String[] function = functions.split( "\\(|\\)" );
		if(function[0].equals(ENCHAINE))
		{
			return enChaine(function[1]);
		}
		if(function[0].equals(ENENTIER))
		{
			return enEntier(function[1]);
		}
		if(function[0].equals(ENREEL))
		{
			return enReel(function[1]);
		}
		if(function[0].equals(CARD))
		{
			return card(function[1]);
		}
		if(function[0].equals(ORD))
		{
			return ord(function[1]);
		}
		if(function[0].equals(PLANCHER))
		{
			return plancher(function[1]);
		}
		if(function[0].equals(PLAFOND))
		{
			return plafond(function[1]);
		}
		if(function[0].equals(ARRONDI))
		{
			return arrondi(function[1]);
		}
		if(function[0].equals(AUJOURD_HUI))
		{
			return aujourd_hui();
		}
		if(function[0].equals(JOUR))
		{
			return jour(function[1]);
		}
		if(function[0].equals(MOIS))
		{
			return mois(function[1]);
		}
		if(function[0].equals(ANNEE))
		{
			return annee(function[1]);
		}
		if(function[0].equals(ESTREEL))
		{
			return estReel(function[1]);
		}
		if(function[0].equals(ESTENTIER))
		{
			return estEntier(function[1]);
		}
		if(function[0].equals(HASARD))
		{
			return hasard(function[1]);
		}
		return "";
	}

	private static String arrondi( String s )
	{
		return ""+Math.round(Double.parseDouble( s ));
	}

	private static String plafond( String s )
	{
		return ""+Math.ceil( Double.parseDouble( s ) );
	}

	private static String plancher( String s )
	{
		return ""+Math.floor( Double.parseDouble( s ) );
	}

	private static String ord( String s )
	{
		return ""+(int)s.charAt( 0 );
	}

	private static String enReel( String s )
	{
		s = supressQuotes( s );
		System.out.print( s +"c'est la abruti");
		return Double.toString( Double.parseDouble( s ) );
	}

	private static String enEntier( String s )
	{
		s = supressQuotes( s );
		System.out.println(s);
		String[] tab = s.split( "\\." );
		System.out.println(tab[0]);
		return Integer.toString( Integer.parseInt(tab[0]));
	}

	private static String card( String s )
	{
		return ""+(char)Integer.parseInt( s );
	}


	private static String enChaine( String s )
	{
		return s;
	}

	private static String aujourd_hui(  )
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern( "dd/MM/yyyy HH:mm:ss" );
		LocalDateTime     now = LocalDateTime.now();
		String date = dtf.format(now);
		String[] tabDate = date.split( " " );
		return tabDate[0];
	}

	private static String jour( String function )
	{
		function = supressQuotes( function );
		String[] tab = function.split( "/" );
		return tab[0];
	}

	private static String mois( String function )
	{
		function = supressQuotes( function );
		String[] tab = function.split( "/" );
		return tab[1];
	}

	private static String annee( String function )
	{
		function = supressQuotes( function );
		String[] tab = function.split( "/" );
		return tab[2];
	}

	private static String estReel( String function )
	{
		function = supressQuotes( function );
		if(Regex.isDouble( function ))
			return "vrai";
		else
			return "false";

	}

	private static String estEntier( String function )
	{
		function = supressQuotes( function );
		if(Regex.isInteger( function ))
			return "vrai";
		else
			return "false";
	}

	private static String hasard( String function )
	{
		int  range = Integer.parseInt(function);
		System.out.println(range);
		return Integer.toString((int)(Math.random()*range));
	}

	private static String supressQuotes(String s)
	{
		String[] tab = s.split( "\"" );
		System.out.println(tab[0]);
		return s.replaceAll( "\"","" );
	}


}
