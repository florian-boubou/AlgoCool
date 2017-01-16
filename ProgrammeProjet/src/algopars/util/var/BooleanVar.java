package algopars.util.var;


/**
 * Classe représentant une variable de var booléen
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
		type = "booleen";
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


	@Override
	public String getJavaType()
	{
		return "boolean";
	}


	/**
	 * Méthode permettant de modifier la valeur de la variable
	 *
	 * @param value1 la future valeur de la variable
	 */
	public void setValue( String value1 )
	{
		if( !constant && value1 != null) {
			if(value1.equals("true"))
				value1 = "vrai";
			if(value1.equals("false"))
				value1 = "faux";
			
			strValue = value1;
			
			if (value1.equals("vrai"))
				value = true;
			if(value1.equals("faux"))
				value = false;
		}
	}
}