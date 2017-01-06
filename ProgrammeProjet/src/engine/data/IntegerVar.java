package engine.data;


/**
 * Created by cathi on 06/01/2017.
 */
public class IntegerVar extends Variable implements Comparable<IntegerVar>
{
	private int value;

	public IntegerVar(String name,String strValue)
	{
		super(name,strValue);
		this.value = Integer.parseInt( strValue );
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value1)
	{
		if(!constant) this.value=value1;
	}

	public int compareTo(IntegerVar other)
	{
		return(this.value-other.getValue());
	}
}
