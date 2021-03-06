package algopars.util.var;


/**
 * Classe représentant une variable de var caractère
 *
 * @author Corentin Athinault, Mathieu Chougui, Clémence Edouard
 * @version 1.0b du 06/01/2017
 */
public class IntegerVar extends Variable implements Comparable<IntegerVar>
{
	private int value;

	/**
	 * Constructeur de la classe.
	 *
	 * @param name     Le nom de la variable
	 * @param strValue La valeur de la variable
	 */
	public IntegerVar( String name, String strValue )
	{
		super( name, strValue );
		type = "entier";
		if(strValue != null)
			this.value = Integer.parseInt( strValue );
	}

	/**
	 * Méthode permettant d'obtenir la valeur de la variable
	 *
	 * @return La valeur de la variable
	 */
	public int getValue()
	{
		return value;
	}

	@Override
	public String getJavaType()
	{
		return "int";
	}

	/**
	 * Méthode permettant de modifier la valeur de la variable
	 *
	 * @param value1 la future valeur de la variable
	 */
	public void setValue( String value1 )
	{
		if( !constant) {
			strValue = value1;
			this.value = Integer.parseInt( value1 );
		}
	}

	/**
	 * Méthode permettant de comparer l'objet courant et un autre de même var
	 *
	 * @param other L'objet avec lequelle comparer l'objet courant
	 * @return Une valeur permettant de comparer les deux objets
	 */
	public int compareTo( IntegerVar other )
	{
		return (this.value - other.getValue());
	}
}
