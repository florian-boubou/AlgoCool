package engine.data;

/**
 * Created by cathi on 06/01/2017.
 */
public abstract class Variable
{
	public String name;
	public String strValue;
	public final boolean constant;

	public Variable(String name, String stValue, boolean constant)
	{
		this.name = name;
		this.strValue = strValue;
		this.constant = constant;
	}

	public Variable(String name, String stValue)
	{
		this.name = name;
		this.strValue = strValue;
		this.constant = false;
	}

	public String getStrValue() {return this.strValue;}

	public String getName()
	{
		return this.name;
	}

	public String toString()
	{
		return this.name+" : "+ this.strValue;
	}
}
