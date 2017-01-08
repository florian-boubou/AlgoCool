import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import bsh.Interpreter;

public class Test
{
	static ArrayList<String> algorithm;
	public static void main( String[] args )
	{
		/*Interpreter i = new Interpreter();
		try {
			i.eval("a = 2");
			System.out.println(i.get("a"));
		}
		catch(Exception e) {
			System.err.println("Erreur");
		}*/

		/*AlgoReader r = new AlgoReader("/test.algo");
		algorithm = r.getAlgorithm();
		ConsoleDisplay cd = new ConsoleDisplay(algorithm);


		for(int i = 0; i < (algorithm.size() > 40? 40: algorithm.size()); i++) {
			//interpreter ici
			cd.display(i);
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
		}*/
		Interpreter i = new Interpreter();
		i

	}
}
