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
		processLine(0);
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
			System.err.println(getAlData() + " | " + memory.size());
		}while(algoInterpreter.getLineIndex() < (algorithm.size() > 40 ? 40 : algorithm.size()));
	}

	public void next(){
		int line;
		do{
			line = algoInterpreter.getLineIndex();

			if(line > memory.peekLast().getCurrentLine() + 1 && displayed){
				line = memory.peekLast().getCurrentLine() + 1;
				algoInterpreter.setLineIndex(line);
				algoInterpreter.setConditionsStack((Stack<Boolean>) memory.peekLast().getConditionsStack().clone());
				algoInterpreter.setLoopsStack((Stack<Loop>) memory.peekLast().getLoopStack().clone());
			}
			processLine(line);
			System.out.println(displayed);
		}while(!displayed);
		consoleDisplay.display(line, algoInterpreter.getLastConditionValue(), getAlData(), getAlConsole());
	}

	public void previous(){
		if(!memory.isEmpty()){
			memory.pollLast();
			AlgoState as = memory.peekLast();
			consoleDisplay.display(as.getCurrentLine(), as.getLastConditionValue(), as.getAlData(), as.getAlExec());
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
	public void processLine(int line)
	{
		displayed = this.algoInterpreter.processLine();

		if(displayed) {
			ArrayList<Variable> clonedAL = new ArrayList<>();
			for (Variable v : getAlData()) {
				clonedAL.add((Variable) v.clone());
			}
			memory.add(new AlgoState(line, algoInterpreter.getLastConditionValue(), clonedAL, (ArrayList) getAlConsole().clone(),
					(Stack<Boolean>) algoInterpreter.getConditionsStack().clone(), (Stack<Loop>) algoInterpreter.getLoopsStack().clone()));
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
