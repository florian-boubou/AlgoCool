package algopars.util.var;


import algopars.tool.Regex;

import java.util.LinkedHashMap;

/**
 * Classe algopars.util.AlgoInterpreter (classe principale metier) qui gère l'interprétation des algorithmes
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
		STRING("chaine", Regex.REGEX_STRING),
		BOOLEAN("booleen", Regex.REGEX_BOOLEAN);
		
		private String pCType;
		private String regexType;
		
		/**
		 * Constructeur de Type
		 *
		 * @param pcType
		 * 		Le nom du var
		 * @param regexType
		 * 		La regex de bonne forme du var
		 */
		Type(String pcType, String regexType)
		{
			this.pCType = pcType;
			this.regexType = regexType;
		}
		
		/**
		 * Méthode retournant le nom du var
		 *
		 * @return Le nom du var
		 */
		public String getpCType()
		{
			return pCType;
		}
		
		/**
		 * Méthode retournant la regex de bonne forme du var
		 *
		 * @return La regex de bonne forme du var
		 */
		public String getRegexType()
		{
			return regexType;
		}
	}
	
	private LinkedHashMap<String, Variable> hMapData;
	
	/**
	 * Constructeur de algopars.util.var.DataFactory
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
	 * 		Le var de la variable
	 * @param value
	 * 		La valeur de la variabel
	 *
	 * @throws Exception
	 * 		En cas de var invalide
	 */
	public void dataDeclaration(String varName, String type, String value) throws Exception
	{
		if(type == null)
		{
			type = determineType(value);
		}

		if(Regex.isArray(type))
		{
			hMapData.put(varName, new ArrayVar(varName, type));
		}
		else {
			switch (type) {
				case "entier":
					hMapData.put(varName, new IntegerVar(varName, value));
					break;
				case "double":
					hMapData.put(varName, new DoubleVar(varName, value.replace(',', '.')));
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
					throw new Exception("Erreur: var invalide");
			}
		}
	}
	
	/**
	 * Méthode permettant de déterminer le var d'une variable dont la valeur est passée en paramètre
	 *
	 * @param value
	 * 		La valeur de la variable
	 *
	 * @return Le var determiné de la variable
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
