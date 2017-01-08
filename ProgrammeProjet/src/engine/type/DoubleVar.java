package engine.type;


/**
 * Created by cathi on 06/01/2017.
 */
public class DoubleVar extends Variable implements Comparable<DoubleVar>
{
	private double value;

	public DoubleVar(String name,String strValue)
	{
		super(name,strValue);
		this.value = Double.parseDouble( strValue );
	}

	public double getValue()
	{
		return value;
	}

	public void setValue(double value1)
	{
		if(!constant) this.value=value1;
	}
	public int compareTo( DoubleVar other )
	{
		return (int)( this.value - other.getValue());
	}
}
