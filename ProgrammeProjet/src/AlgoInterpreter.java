import bsh.Interpreter;
import engine.SyntaxChecker;
import engine.type.Variable;
import tool.Regex;

import java.util.ArrayList;
import java.util.HashMap;


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
	private DataFactory df;
	private SyntaxChecker syntaxChecker;
	private DataFactory data;
	
	private ArrayList<String> algorithm;
	private ArrayList<Variable> alData;
	
	/**
	 * Constructeur d'AlgoInterpreter
	 *
	 * @param algorithm
	 * 		L'ArrayList<String> représentant l'algorithme à interpréter
	 */
	public AlgoInterpreter(ArrayList<String> algorithm)
	{
		try {
			interpreter = new Interpreter();
			df = new DataFactory();
			syntaxChecker = new SyntaxChecker(algorithm);
			this.algorithm = algorithm;
			alData = new ArrayList<>();
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Méthode lançant l'interprétation
	 */
	public void run()
	{
		syntaxChecker.dataCheck();
		this.declareData(syntaxChecker.gethData());
		for(String s : df.getHMapData().keySet()){
			alData.add(df.getHMapData().get(s));
		}
		/*for(String s : algorithm)
		{
			this.processLine(s);
		}*/
	}
	
	/**
	 * Méthode stockant toutes les variables déclarées dans la DataFactory
	 *
	 * @param data
	 * 		L'ArrayList<String> correspondante à la partie données de l'algorithme
	 */
	public void declareData(HashMap<String, String> data)
	{

			for(String valName : data.keySet()) {
				if (Regex.isConstant(valName)) {
					try {
						df.dataDeclaration(valName, null, data.get(valName));
					} catch (Exception e) {
						System.err.println(e.toString());
					}
				}
				else {

					try {
						df.dataDeclaration(valName, data.get(valName), null);
					} catch (Exception e) {
						System.err.println(e.toString());
					}
				}
			}
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

	public ArrayList<Variable> getAlData(){
		return alData;
	}
	
	/**
	 * Point d'entrée du programme d'interprétation
	 *
	 * @param args
	 * 		Contiendra une chaîne représentant le chemin vers le fichier contenant l'algorithme à interpréter
	 */
	/*public static void main(String[] args)
	{
		AlgoReader      algoReader    = new AlgoReader(args[0]);
		AlgoInterpreter algoInterpreter;
		SyntaxChecker   syntaxChecker = new SyntaxChecker(algoReader.getAlgorithm());
		if(syntaxChecker.headerCheck() && syntaxChecker.dataCheck() && syntaxChecker.bodyCheck())
		{
			algoInterpreter = new AlgoInterpreter(algoReader.getAlgorithm());
			algoInterpreter.run();
		}
	}*/
}
