package algopars;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import algopars.UI.ConsoleDisplay;
import algopars.util.AlgoReader;
import algopars.util.AlgoInterpreter;
import algopars.util.var.Variable;

public class Controller
{
	private AlgoInterpreter	algoInterpreter;
	private ConsoleDisplay	consoleDisplay;

	private ArrayList<String> algorithm;

	/**
	 * Constructeur de Controller
	 * @param filePath
	 * 		Le chemin vers le fichier contenant l'algorithme à interpréter
	 */
	public Controller(String filePath)
	{
		this.algorithm			= new AlgoReader( filePath ).getAlgorithm();
		
		this.algoInterpreter	= new AlgoInterpreter( this.algorithm );
		this.consoleDisplay		= new ConsoleDisplay ( this );

		this.algoInterpreter.chooseVar();

		for(int i = 0; i < (algorithm.size() > 40 ? 40 : algorithm.size());i++)
		{
			algoInterpreter.processLine();
			consoleDisplay.display(i);
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
		}
	}
	
	/**
	 * Méthode permettant d'obtenir l'ArrayList<String> représentant l'algorithme à interpréter
	 * @return
	 * 		L'ArrayList<String> représentant l'algorithme à interpréter
	 */
	public ArrayList<String> getAlgorithm()
	{
		return algorithm;
	}
	
	/**
	 * Méthode permettant d'appeler le processLine de AlgoInterpréteur
	 */
	public void processLine()
	{
		this.algoInterpreter.processLine();
	}
	
	/**
	 * Méthode permettant d'obtenir l'ensemble des variables à tracer
	 * @return
	 *      Le dit ensemble de Variable
	 */
	public List<Variable> getAlData()
	{
		return this.algoInterpreter.getAlData();
	}

	/**
	 * Point d'entrée du programme
	 * @param args
	 * 		Chemin vers le fichier contenant l'algorithme à interpréter
	 */
	public static void main(String[] args)
	{
		Controller c = new Controller(args[0]);
	}
	
	/**
	 * Méthode permettant d'obtenir l'ArrayList<String> représentant la trace d'éxécution
	 * @return
	 * 		L'ArrayList<String> représentant la trace d'éxécution
	 */
	public ArrayList<String> getAlConsole()
	{
		return this.algoInterpreter.getAlConsole();
	}
}
