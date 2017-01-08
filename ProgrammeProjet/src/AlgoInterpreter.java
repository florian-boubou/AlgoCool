import bsh.Interpreter;
import engine.SyntaxChecker;
import engine.type.Variable;

import java.util.ArrayList;


/**
 * Classe AlgoInterpreter (classe principale metier) qui gère l'interprétation des algorithmes
 *
 * @author Antoine WARET, Mathieu CHOUGUI
 * @version 1.0.0a
 * @date 01/08/2017
 */
public class AlgoInterpreter
{
	private Interpreter interpreter;
	private DataFactory data;
	
	private ArrayList<String> algorithm;
	
	/**
	 * Constructeur d'AlgoInterpreter
	 *
	 * @param algorithm
	 * 		L'ArrayList<String> représentant l'algorithme à interpréter
	 */
	public AlgoInterpreter(ArrayList<String> algorithm)
	{
		interpreter = new Interpreter();
		this.algorithm = algorithm;
	}
	
	/**
	 * Méthode lançant l'interprétation
	 */
	public void run()
	{
		
		this.declareData(null);
		for(String s : algorithm)
		{
			this.processLine(s);
		}
	}
	
	/**
	 * Méthode stockant toutes les variables déclarées dans la DataFactory
	 *
	 * @param data
	 * 		L'ArrayList<String> correspondante à la partie données de l'algorithme
	 */
	public void declareData(ArrayList<String> data)
	{
		
	}
	
	/**
	 * Méthodant interprétant l'algorithme ligne par ligne
	 *
	 * @param line
	 * 		La ligne à interpréter
	 *
	 * @return Ce qui sera possiblement à afficher après l'interprétation de cette ligne
	 */
	public String processLine(String line)
	{
		line = new String(line.replaceAll("\\s", ""));
		
		if(tool.Regex.isFunction(line))
		{
			String[] fonc = line.split("\\(");
			if(fonc[0].equals("ecrire"))
			{
				
			}
		}
		
		return null;
	}
	
	/**
	 * Point d'entrée du programme d'interprétation
	 *
	 * @param args
	 * 		Contiendra une chaîne représentant le chemin vers le fichier contenant l'algorithme à interpréter
	 */
	public static void main(String[] args)
	{
		AlgoReader      algoReader    = new AlgoReader(args[0]);
		AlgoInterpreter algoInterpreter;
		SyntaxChecker   syntaxChecker = new SyntaxChecker(algoReader.getAlgorithm());
		if(syntaxChecker.headerCheck() && syntaxChecker.dataCheck() && syntaxChecker.bodyCheck())
		{
			algoInterpreter = new AlgoInterpreter(algoReader.getAlgorithm());
			algoInterpreter.run();
		}
	}
}
