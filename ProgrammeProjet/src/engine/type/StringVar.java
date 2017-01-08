package engine.type;

/**
 * Classe StringVar gérant les différents types de Variables
 *
 * @author Corentin ATHINAULT, Mathieu CHOUGUI, Clémence EDOUARD
 * @version 1.0.0a
 * @date 01/08/2017
 */
public class StringVar extends Variable
{
	private String value;
	
	/**
	 * Constructeur de StringVar
	 *
	 * @param name
	 * 		Nom de la StringVar
	 * @param strValue
	 * 		Chaîne représentant la valeur de la StringVar
	 */
	public StringVar(String name, String strValue)
	{
		super(name, strValue);
		if(strValue != null)
			this.value = strValue;
	}
	
	/**
	 * Méthode permettant d'obtenir la valeur de la StringVar courante
	 *
	 * @return La valeur de la StringVar courante
	 */
	public String getValue()
	{
		return value;
	}
	
	/**
	 * Méthode permettant de modifier la valeur de la StringVar
	 *
	 * @param value1
	 * 		La valeur à donner
	 */
	public void setValue(String value1)
	{
		if(!constant) this.value = value1;
	}
}
