package algopars.util.var;


import java.io.Serializable;

/**
 * Classe Variable gérant les différents types de Variables
 *
 * @author Corentin ATHINAULT, Mathieu CHOUGUI, Clémence EDOUARD
 * @version 1.0.0a
 * @date 01/08/2017
 */
public abstract class Variable implements Serializable
{
	public       String  name;
	public       String  type;
	public       String  strValue;
	public final boolean constant;

	/**
	 *Constructeur de Variable
	 *
	 * @param name
	 * 		Le nom de la Variable
	 * @param strValue
	 * 		La chaîne représentant sa valeur
	 */
	public Variable(String name, String strValue)
	{
		this.name = name;
		this.strValue = strValue;
		this.constant = (strValue != null);
	}
	
	/**
	 * Méthode permettant d'obtenir la chaîne représentant la valeur de la Variable
	 *
	 * @return La chaîne représentant la valeur de la Variable
	 */
	public String getStrValue()
	{
		return this.strValue;
	}
	
	/**
	 * Méthode permettant d'obtenir le var de la variable sous forme de string
	 *
	 * @return Le var de la variable
	 */
	public String getType()
	{
		return this.type;
	}

	/**
	 * Méthode permettant d'obtenir le type Java de la variable sous forme de String
	 *
	 * @return Le type Java
	 */
	public abstract String getJavaType();

	/**
	 * Méthode permettant d'obtenir le nom de la variable
	 *
	 * @return Le nom de la variable
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Methode abstraite permettant de changer la valeur
	 * @param newValue nouvelle valeur a affecter
     */
	public abstract void setValue(String newValue);
	
	/**
	 * Méthode permettant d'obetnir une chaîne représentant l'état complet de l'objet Variable courant
	 *
	 * @return La chaîne l'état complet de l'objet Variable courant
	 */
	public String toString()
	{
		return this.name + " : " + this.strValue;
	}


}
