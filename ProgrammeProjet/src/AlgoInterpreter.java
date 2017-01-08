import bsh.Interpreter;
import engine.type.Variable;

import java.util.ArrayList;

/**
 * Created by Terawa on 08/01/2017.
 */
public class AlgoInterpreter
{
	private Interpreter         interpreter;
	private ArrayList<Variable> data;
	
	public AlgoInterpreter()
	{
		interpreter = new Interpreter();
	}
	
	public void declareData(String line)
	{
		String varName;
		if(!line.trim().isEmpty())
		{
			if(line.contains("◄—"))
			{
				varName = line.split("◄—")[0].trim();
				
			}
		}
	}
	
	public String processLine(String line)
	{
		return "";
	}
		
	public static void main(String[] args)
	{
		String line = "\t\tecrire(joie)\n";
		line = new String(line.replaceAll("\\s", ""));
		
		if(tool.Regex.isFunction(line))
		{
			String[] fonc = line.split("\\(");
			if(fonc[0].equals("ecrire"))
			{
				System.out.println(fonc[1].replace(')', ' ').trim());
			}
		}
	}
}
