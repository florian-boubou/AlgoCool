package engine.type;


/**
 * Created by cathi on 06/01/2017.
 */
public class StringVar extends Variable
{
	private String value;

	public StringVar( String name, String strValue )
	{
		super( name, strValue );
		this.value = strValue;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue( String value1 )
	{
		if( !constant ) this.value = value1;
	}
}
