package algopars;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
	public Controller( String filePath )
	{
		this.algorithm = new AlgoReader( filePath ).getAlgorithm();

		this.algoInterpreter = new AlgoInterpreter( this.algorithm );
		this.consoleDisplay = new ConsoleDisplay( this );
		memory = new LinkedList<>();
		int line;
		int memIndex = 1;
		displayed = true;

		this.algoInterpreter.chooseVar();
		processLine(0);
		line = algoInterpreter.getLineIndex();
		consoleDisplay.display(0, algoInterpreter.getLastConditionValue(), getAlData(), getAlConsole());


		do{
			Scanner scanner = new Scanner(System.in);

			if(scanner.nextLine().toString().equals("b")){
				memIndex = (memIndex <= 1 ? 1 : memIndex -1);
				consoleDisplay.display(memory.get(memIndex-1).getCurrentLine(), memory.get(memIndex-1).getLastConditionValue(), memory.get(memIndex-1).getAlData(), memory.get(memIndex-1).getAlExec());
			}
			else{
				if(memIndex < memory.size()){
					consoleDisplay.display(memory.get(memIndex).getCurrentLine(), memory.get(memIndex).getLastConditionValue(), memory.get(memIndex).getAlData(), memory.get(memIndex).getAlExec());
					line = memory.get(memIndex).getCurrentLine();
				}
				else{
					do{
						processLine(line);
					}while(!displayed);
					consoleDisplay.display(line, algoInterpreter.getLastConditionValue(), getAlData(), getAlConsole());
				}
				memIndex++;
			}
			line = algoInterpreter.getLineIndex();

		} while(line < (algorithm.size() > 40 ? 40 : algorithm.size()));
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
	public void processLine(int line)
	{
		boolean b = this.algoInterpreter.processLine();

		if(b) {
			ArrayList<Variable> clonedAL = new ArrayList<>();
			for (Variable v : getAlData()) {
				clonedAL.add((Variable) v.clone());
			}
			memory.add(new AlgoState(line, algoInterpreter.getLastConditionValue(), clonedAL, (ArrayList) getAlConsole().clone()));
		}

		this.displayed = b;
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
