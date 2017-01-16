package algopars;


import java.util.*;

import algopars.UI.ConsoleDisplay;
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
	public Controller( String filePath, int displaySize )
	{
		this.algorithm = new AlgoReader( filePath ).getAlgorithm();
		displaySize = displaySize >= this.algorithm.size()? this.algorithm.size() - 1 : displaySize;

		this.algoInterpreter = new AlgoInterpreter( this.algorithm );
		this.consoleDisplay = new ConsoleDisplay( this , displaySize);
		memory = new LinkedList<>();
		displayed = true;

		this.algoInterpreter.chooseVar();
		int beginIndex = this.algoInterpreter.getLineIndex();
		processLine();
		consoleDisplay.display(beginIndex, algoInterpreter.getLastConditionValue(), getAlData(), getAlConsole());

		do{
			Scanner sc = new Scanner(System.in);
			String s = sc.nextLine();
			switch(s){
				case "b":
					previous();
					break;
				default :
					if(s.matches("^l[0-9]*$"))
						goTo(Integer.parseInt(s.split("l")[1]));
					else
						next();
					break;
			}
		}while(algoInterpreter.getLineIndex() < algorithm.size());
	}
	
	/**
	 * Méthode permettant de passer à la ligne suivante
	 */
	public void next(){
		do{
			processLine();
		}while(!displayed);

		consoleDisplay.display(algoInterpreter.getCurrentIndex(), algoInterpreter.getLastConditionValue(), getAlData(), getAlConsole());
	}
	
	/**
	 * Méthode permettant de passer à la ligne précédente
	 */
	public void previous(){
		if(memory.size() > 1)
			memory.pollLast();
		algoInterpreter = memory.peekLast().getAlgoInterpreter().deepClone();
		consoleDisplay.display(algoInterpreter.getCurrentIndex(), algoInterpreter.getLastConditionValue(), getAlData(), getAlConsole());
	}

	public void goTo(int index){
		if(index < algoInterpreter.getCurrentIndex()){
			index = index < memory.peekFirst().getAlgoInterpreter().getCurrentIndex() ? memory.peekFirst().getAlgoInterpreter().getCurrentIndex() : index;
			do{
				previous();
			}while(index < algoInterpreter.getCurrentIndex());
		}
		else if(index > algoInterpreter.getCurrentIndex()){
			index = index >= algorithm.size() ? algorithm.size() - 1 : index;
			do{
				next();
			}while(index > algoInterpreter.getCurrentIndex());
		}
		else{
			algoInterpreter = memory.peekLast().getAlgoInterpreter().deepClone();
			consoleDisplay.display(algoInterpreter.getCurrentIndex(), algoInterpreter.getLastConditionValue(), getAlData(), getAlConsole());
		}
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
		displayed = algoInterpreter.processLine();

		if(displayed)
		{
			AlgoInterpreter cloned = algoInterpreter.deepClone();
			if(memory.size() < 1 || cloned.getLineIndex() != memory.getLast().getAlgoInterpreter().getLineIndex())
			{
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
		Controller c = new Controller( args[0] , Integer.parseInt(args[1]));
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
