package engine.type;


/**
 * Created by cathi on 06/01/2017.
 */
public class BooleanVar extends Variable
{
	private boolean value;

	public BooleanVar(String name,String strValue)
	{
		super(name,strValue);
		if(strValue.equals("vrai")) value = true;
		else value = false;
	}

	public boolean getValue()
	{
		return value;
	}

	public void setValue(boolean value1)
	{
		if(!constant) this.value=value1;
	}
}