import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

import bsh.Interpreter;

public class Test
{
	static ArrayList<String> algorithm;

	public static void main(String[] args)
	{
		Interpreter bsh = new Interpreter();
		try
		{
			System.out.println(bsh.eval("3 + 2"));
		}
		catch(Exception e)
		{
			System.err.println("Erreur");
		}

		/*ConsoleDisplay cd = new ConsoleDisplay("/test.algo");
		algorithm = cd.getAlgorithm();
		
		for(int i = 0 ; i < (algorithm.size() > 40 ? 40 : algorithm.size()) ; i++)
		{
			//interpreter ici
			cd.display(i);
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
		}*/
	}
}
