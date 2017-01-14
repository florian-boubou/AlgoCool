package algopars;


import java.util.*;

import algopars.UI.ConsoleDisplay;
import algopars.tool.Loop;
import algopars.util.AlgoReader;
import algopars.util.AlgoInterpreter;
import algopars.util.AlgoState;
import algopars.util.var.Variable;

public class Controller
{
	private AlgoInterpreter algoInterpreter;
	private ConsoleDisplay  consoleDisplay;

	private ArrayList<String> algorithm;
	private LinkedList<AlgoState> memory;
	private boolean displayed;


	/**
	 * Constructeur de Controller
	 *
	 * @param filePath Le chemin vers le fichier contenant l'algorithme à interpréter
	 */
	public Controller( String filePath )
	{
		this.algorithm = new AlgoReader( filePath ).getAlgorithm();

		this.algoInterpreter = new AlgoInterpreter( this.algorithm );
		this.consoleDisplay = new ConsoleDisplay( this );
		memory = new LinkedList<>();
		displayed = true;

		this.algoInterpreter.chooseVar();
		processLine();
		consoleDisplay.display(0, algoInterpreter.getLastConditionValue(), getAlData(), getAlConsole());

		do{
			Scanner sc = new Scanner(System.in);
			switch(sc.nextLine()){
				case "b":
					previous();
					break;
				default :
					next();
					break;
			}
		}while(algoInterpreter.getLineIndex() < (algorithm.size() > 40 ? 40 : algorithm.size()));
	}
	
	/**
	 * Méthode permettant de passer à la ligne suivante
	 */
	public void next(){
		int line;
		do{
			line = algoInterpreter.getLineIndex();

			if(line > memory.peekLast().getAlgoInterpreter().getLineIndex() && displayed){
				try{
					this.algoInterpreter = (AlgoInterpreter)(memory.peekLast().getAlgoInterpreter()).clone();
				}
				catch(Exception e){}
				System.err.println("AVANT" + line);
				line = memory.size() >= 1 ? algoInterpreter.getLineIndex() : 1;
				System.err.println(line);
			}
			processLine();
		}while(!displayed);

		consoleDisplay.display(line, algoInterpreter.getLastConditionValue(), getAlData(), getAlConsole());
	}
	
	/**
	 * Méthode permettant de passer à la ligne précédente
	 */
	public void previous(){
		if(memory.size() > 1)
			memory.pollLast();
		AlgoInterpreter ai = memory.peekLast().getAlgoInterpreter();
		consoleDisplay.display(ai.getLineIndex() - 1, ai.getLastConditionValue(), ai.getAlData(), ai.getAlConsole());
	}


	/**
	 * Méthode permettant d'obtenir l'ArrayList<String> représentant l'algorithme à interpréter
	 *
	 * @return L'ArrayList<String> représentant l'algorithme à interpréter
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
		displayed = this.algoInterpreter.processLine();

		if(displayed) {
			try{
				memory.add(new AlgoState((AlgoInterpreter) algoInterpreter.clone()));
			}
			catch(Exception e){}
		}
	}


	/**
	 * Méthode permettant d'obtenir l'ensemble des variables à tracer
	 *
	 * @return Le dit ensemble de Variable
	 */
	public List<Variable> getAlData()
	{
		return this.algoInterpreter.getAlData();
	}


	/**
	 * Point d'entrée du programme
	 *
	 * @param args Chemin vers le fichier contenant l'algorithme à interpréter
	 */
	public static void main( String[] args )
	{
		Controller c = new Controller( args[0] );
	}


	/**
	 * Méthode permettant d'obtenir l'ArrayList<String> représentant la trace d'éxécution
	 *
	 * @return L'ArrayList<String> représentant la trace d'éxécution
	 */
	public ArrayList<String> getAlConsole()
	{
		return this.algoInterpreter.getAlConsole();
	}
}
