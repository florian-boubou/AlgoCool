package engine.data;


/**
 * Created by cathi on 06/01/2017.
 */
public class CharVar extends Variable implements Comparable<CharVar>
{
	private char value;

	public CharVar(String name,String strValue)
	{
		super(name,strValue);
		this.value = strValue.charAt( 0 );
	}

	public char getValue()
	{
		return value;
	}

	public void setValue(char value1)
	{
		if(!constant) this.value=value1;
	}

	public int compareTo( CharVar autre )
	{
		return (int)this.value - autre.value;
	}
}
