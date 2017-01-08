package engine.type;


/**
 * Classe représentant une variable de type booléen
 *
 * @author Corentin Athinault, Mathieu Chougui, Clémence Edouard
 * @version 1.0b du 06/01/2017
 */
public class BooleanVar extends Variable
{
	private boolean value;

	/**
	 * Constructeur de la classe.
	 *
	 * @param name     Le nom de la variable
	 * @param strValue La valeur de la variable
	 */
	public BooleanVar( String name, String strValue )
	{
		super( name, strValue );
		if(strValue != null) {
			if (strValue.equals("vrai")) value = true;
			else value = false;
		}
	}

	/**
	 * Méthode permettant d'obtenir la valeur de la variable
	 *
	 * @return La valeur de la variable
	 */
	public boolean getValue()
	{
		return value;
	}

	/**
	 * Méthode permettant de modifier la valeur de la variable
	 *
	 * @param value1 la future valeur de la variable
	 */
	public void setValue( boolean value1 )
	{
		if( !constant ) this.value = value1;
	}
}