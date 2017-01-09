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
		/*Interpreter bsh = new Interpreter();
		try
		{
			bsh.eval("a = 2");
			System.out.println(bsh.get("a"));
		}
		catch(Exception e)
		{
			System.err.println("Erreur");
		}*/
		ConsoleDisplay cd = new ConsoleDisplay("/test.algo");
		
		
		for(int i = 0 ; i < (algorithm.size() > 40 ? 40 : algorithm.size()) ; i++)
		{
			//interpreter ici
			cd.display(i);
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
		}
	}
}
