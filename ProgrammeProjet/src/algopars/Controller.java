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
		int beginIndex = this.algoInterpreter.getLineIndex();
		processLine();
		consoleDisplay.display(beginIndex, algoInterpreter.getLastConditionValue(), getAlData(), getAlConsole());

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
		algoInterpreter = memory.peekLast().getAlgoInterpreter().deepClone();
		consoleDisplay.display(algoInterpreter.getLineIndex(), algoInterpreter.getLastConditionValue(), algoInterpreter.getAlData(), algoInterpreter.getAlConsole());
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
		int currentIndex = algoInterpreter.getLineIndex();
		displayed = algoInterpreter.processLine();

		if(displayed) {
			AlgoInterpreter cloned = algoInterpreter.deepClone();
			cloned.setLineIndex(currentIndex);
			if(memory.size() < 1 || cloned.getLineIndex() != memory.getLast().getAlgoInterpreter().getLineIndex()){
				memory.add(new AlgoState(cloned));
			}
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
