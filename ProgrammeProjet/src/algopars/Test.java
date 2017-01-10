package algopars;


import java.util.Scanner;

import algopars.UI.ConsoleDisplay;
import algopars.util.AlgoReader;

public class Test
{
	public static void main(String[] args)
	{
		/*Interpreter bsh = new Interpreter();
		try
		{
			System.out.println(bsh.eval("3 + 2"));
		}
		catch(Exception e)
		{
			System.err.println("Erreur");
		}*/
		AlgoReader ar = new AlgoReader( "/test.algo");
		ConsoleDisplay cd = new ConsoleDisplay( ar.getAlgorithm());
		
		for(int i = 0 ; i < (ar.getAlgorithm().size() > 40 ? 40 : ar.getAlgorithm().size()) ; i++)
		{
			//interpreter ici
			cd.refresh();
			cd.display(i);
			Scanner scanner = new Scanner(System.in);

			scanner.nextLine();
		}
	}
}
