import engine.type.*;
import tool.Regex;

import java.util.LinkedHashMap;

/**
 * Classe AlgoInterpreter (classe principale metier) qui gère l'interprétation des algorithmes
 *
 * @author Antoine WARET
 * @version 1.0.0a
 * @date 01/08/2017
 */
public class DataFactory
{
	private enum Type
	{
		INTEGER("entier", Regex.REGEX_INTEGER),
		DOUBLE("double", Regex.REGEX_DOUBLE),
		CHAR("caractere", Regex.REGEX_CHAR),
		STRING("string", Regex.REGEX_STRING),
		BOOLEAN("booleen", Regex.REGEX_BOOLEAN);
		
		private String pCType;
		private String regexType;
		
		/**
		 * Constructeur de Type
		 *
		 * @param pcType
		 * 		Le nom du type
		 * @param regexType
		 * 		La regex de bonne forme du type
		 */
		Type(String pcType, String regexType)
		{
			this.pCType = pcType;
			this.regexType = regexType;
		}
		
		/**
		 * Méthode retournant le nom du type
		 *
		 * @return Le nom du type
		 */
		public String getpCType()
		{
			return pCType;
		}
		
		/**
		 * Méthode retournant la regex de bonne forme du type
		 *
		 * @return La regex de bonne forme du type
		 */
		public String getRegexType()
		{
			return regexType;
		}
	}
	
	private LinkedHashMap<String, Variable> hMapData;
	
	/**
	 * Constructeur de DataFactory
	 */
	public DataFactory()
	{
		hMapData = new LinkedHashMap<>();
	}
	
	
	/**
	 * Méthode permettant d'ajouter une variable déclarée dans l'algorithme à la DataFActory
	 *
	 * @param varName
	 * 		Le nom de la variable
	 * @param type
	 * 		Le type de la variable
	 * @param value
	 * 		La valeur de la variabel
	 *
	 * @throws Exception
	 * 		En cas de type invalide
	 */
	public void dataDeclaration(String varName, String type, String value) throws Exception
	{
		if(type == null)
		{
			type = determineType(value);
		}
		switch(type)
		{
			case "entier":
				hMapData.put(varName, new IntegerVar(varName, value));
				break;
			case "double":
				hMapData.put(varName, new DoubleVar(varName, value));
				break;
			case "caractere":
				hMapData.put(varName, new CharVar(varName, value));
				break;
			case "chaine":
				hMapData.put(varName, new StringVar(varName, value));
				break;
			case "booleen":
				hMapData.put(varName, new BooleanVar(varName, value));
				break;
			default:
				throw new Exception("Erreur: type invalide");
		}
	}
	
	/**
	 * Méthode permettant de déterminer le type d'une variable dont la valeur est passée en paramètre
	 *
	 * @param value
	 * 		La valeur de la variable
	 *
	 * @return Le type determiné de la variable
	 */
	private String determineType(String value)
	{
		String ret = null;
		for(Type t : Type.values())
		{
			if(value.matches(t.getRegexType()))
			{
				ret = t.getpCType();
				break;
			}
		}
		return ret;
	}
	
	/**
	 * Méthode permettant d'obtenir la HashMap de Variable
	 *
	 * @return La HashMap de Variable
	 */
	public LinkedHashMap<String, Variable> getHMapData()
	{
		return hMapData;
	}
}
