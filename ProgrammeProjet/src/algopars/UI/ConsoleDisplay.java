package algopars.UI;


import algopars.util.*;
import algopars.util.color.Color;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe ConsoleDisplay qui se charge d'afficher tout le tintouin
 *
 * @author Antoine WARET, Clémence EDOUARD
 * @version 1.0.0a
 * @date 01/08/2017
 */
public class ConsoleDisplay
{
	private         ArrayList<String>       algorithm;
	private         ArrayList<String>       consoleTrace;
	private         AlgoInterpreter         interpreter;

	private static final Color              color = new Color();
	private final   String                  background = color.WHITE + color.BACKGROUND_BLACK;
	private final   String                  backgroundCurrent = color.BLACK + color.BACKGROUND_WHITE;
	private static  HashMap<String, String> textColors = new HashMap<String, String>()
	{{
		put("ecrire", color.BLUE);
		put("lire", color.YELLOW);
		put("^[f]?si[non]?\\s$", color.CYAN);
		put("fsi", color.CYAN);
		put("sinon", color.CYAN);
		put("alors", color.CYAN);
		put("tant que", color.CYAN);
		put("faire", color.CYAN);
		put("ftq", color.CYAN);
		put("selon", color.CYAN);
		put("choix", color.CYAN);
		put("defaut", color.CYAN);
	}};


	/**
	 * Constructeur de ConsoleDisplay
	 *
	 * @param algorithm
	 * 		L'arrayList contenant l'algorithme
	 */
	public ConsoleDisplay(ArrayList<String> algorithm)
	{
		this.consoleTrace = new ArrayList<>();
		this.algorithm = algorithm;

		interpreter = new AlgoInterpreter(algorithm);
		interpreter.run();
	}

	public void refresh(){
		interpreter.processLine();
	}

	/**
	 * Méthode d'affichage de l'algorithme, de la trace des variables et de la trace d'exécution
	 *
	 * @param current
	 * 		La ligne courante
	 */
	public void display(int current)
	{
		String dataStr;

		StringBuilder str = new StringBuilder();
		str.append(background + new String(new char[10]).replace('\0', '"') +
				new String(new char[78]).replace('\0', ' ') +
				new String(new char[11]).replace('\0', '"') + "\n");

		str.append("|  CODE  |" + new String(new char[78]).replace('\0', ' ') +
				"| DONNEES" + " |\n");

		str.append(new String(new char[87]).replace('\0', '"') + " " +
				new String(new char[44]).replace('\0', '"') + "\n");

		int iVar = 0;

		//Boucle pour afficher le code et les données
		for(int i = 0 ; i < (algorithm.size() > 40 ? 40 : algorithm.size()) ; i++)
		{
			if(i == 0)
			{
				dataStr = background + "|" + String.format("%-10s", "NOM") + "|"
						+ String.format("%-10s", "TYPE") + "|"
						+ String.format("%-20s", "VALEUR") + "|\n";
			}
			else if(iVar < interpreter.getAlData().size())
			{
				dataStr = "|" +
						String.format("%-10s",
								interpreter.getAlData().get(iVar).getName())
						+ "|" +
						String.format("%-10s",
								interpreter.getAlData().get(iVar).getType())
						+ "|" +
						String.format("%-20s", interpreter.getAlData().
								get(iVar).getStrValue() == null ?
								"" : interpreter.getAlData().get(iVar).getStrValue()) +
						"|\n";
				iVar++;
			}
			else if(iVar == interpreter.getAlData().size())
			{
				dataStr = new String(new char[44]).replace('\0', '"') + "\n";
				iVar++;
			}
			else
			{
				dataStr = "\n";
			}

			str.append(background + "| " +
					String.format("%-80s", (current == i ? color.BACKGROUND_WHITE + color.BLACK : "") +
							String.format("%2d", i) + " " +
							String.format(algorithm.get(i).contains(
									"◄—") ? "%-79s" : "%-80s",
									algorithm.get(i))) + background +
					" | " + dataStr);

			String line = str.toString();
			for(String element : textColors.keySet())
			{
				if(line.indexOf(element) != -1)
				{
					line = line.replaceAll(element, textColors.get(element)
							+ (current == i ? color.BACKGROUND_WHITE : color.BACKGROUND_BLACK)
							+ element
							+ (current == i ? backgroundCurrent : background)
					);
				}
			}

			str.replace(0, str.length()-1, line);
		}

		str.append(new String(new char[87]).replace('\0', '"') + "\n\n");

		str.append(new String(new char[11]).replace('\0', '"') + "\n" +
				"| CONSOLE |\n" + new String(new char[87]).replace('\0', '"') + "\n");

		//Boucle pour afficher le code et les données
		for(int i = 3 ; i >= 0 ; i--)
		{
			if(consoleTrace.size() != 0 && consoleTrace.size() - i >= 0)
			{
				str.append(background + String.format("|%-85s|\n", consoleTrace.get(consoleTrace.size() - i)
				));
			}
			else
			{
				str.append(background + String.format("|%-85s|\n", new String(new char[85])
						.replace('\0', ' ')));
			}

		}

		str.append(new String(new char[87]).replace('\0', '"') + "\n");

		System.out.println(str);
	}
}


