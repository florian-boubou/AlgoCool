package engine.type;


/**
 * Classe représentant une variable de type caractère
 *
 * @author Corentin Athinault, Mathieu Chougui, Clémence Edouard
 * @version 1.0b du 06/01/2017
 */
public class CharVar extends Variable implements Comparable<CharVar>
{
	private char value;

	/**
	 * Constructeur de la classe.
	 *
	 * @param name     Le nom de la variable
	 * @param strValue La valeur de la variable
	 */
	public CharVar( String name, String strValue )
	{
		super( name, strValue );
		this.value = strValue.charAt( 0 );
	}

	/**
	 * Méthode permettant d'obtenir la valeur de la variable
	 *
	 * @return La valeur de la variable
	 */
	public char getValue()
	{
		return value;
	}

	/**
	 * Méthode permettant de modifier la valeur de la variable
	 *
	 * @param value1 la future valeur de la variable
	 */
	public void setValue( char value1 )
	{
		if( !constant ) this.value = value1;
	}

	/**
	 * Méthode permettant de comparer l'objet courant et un autre de même type
	 *
	 * @param other L'objet avec lequelle comparer l'objet courant
	 * @return Une valeur permettant de comparer les deux objets
	 */
	public int compareTo( CharVar other )
	{
		return (int) this.value - other.value;
	}
}