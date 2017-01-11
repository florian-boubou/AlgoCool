package algopars.UI;


import algopars.Controller;
import algopars.util.*;
import algopars.util.color.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Classe ConsoleDisplay qui se charge d'afficher tout le tintouin
 *
 * @author Antoine WARET, Clémence EDOUARD
 * @version 1.0.0a
 * @date 01/08/2017
 */
public class ConsoleDisplay
{
	private         		ArrayList<String>       algorithm;
	private         		ArrayList<String>       consoleTrace;
	private         		Controller				controller;

	private static 	final 	Color              		color               = new Color();
	private 		final 	String                  background          = color.WHITE + color.BACKGROUND_BLACK;
	private 	    final   String                  backgroundCurrent   = color.BLACK + color.BACKGROUND_WHITE;

    private static LinkedList<Keyword> keywords = new LinkedList<Keyword>(){{
        String regexCyan = "^.*(alors|tant que|faire|ftq|selon|choix|defaut).*$";
        String regexSi = "^.*\\s+si\\s+.*$";
        String regexSinon = "^.*sinon.*$";
        String regexFsi = "^.*fsi.*$";
        String regexBold = "^.*(ALGORITHME|DEBUT|FIN).*$";

        add(new Keyword("^\\s*ecrire\\(.*\\)\\s*$","ecrire",color.BLUE));
        add(new Keyword("^\\s*lire\\(.*\\)\\s*$", "lire", color.YELLOW));

        add(new Keyword(regexSi, "si", color.CYAN));//SI A RAJOUTER
        add(new Keyword(regexFsi, "fsi", color.CYAN));//SI A RAJOUTER
        add(new Keyword(regexSinon, "sinon", color.CYAN));//SI A RAJOUTER
        add(new Keyword(regexCyan, "alors", color.CYAN));//SI A RAJOUTER
        add(new Keyword(regexCyan, "tant que", color.CYAN));//SI A RAJOUTER
        add(new Keyword(regexCyan, "faire", color.CYAN));//SI A RAJOUTER
        add(new Keyword(regexCyan, "ftq", color.CYAN));//SI A RAJOUTER
        add(new Keyword(regexCyan, "selon", color.CYAN));//SI A RAJOUTER
        add(new Keyword(regexCyan, "choix", color.CYAN));//SI A RAJOUTER
        add(new Keyword(regexCyan, "defaut", color.CYAN));//SI A RAJOUTER

        add(new Keyword(regexBold,"ALGORITHME", color.BOLD));
        add(new Keyword(regexBold,"DEBUT", color.BOLD));
        add(new Keyword(regexBold,"FIN", color.BOLD));
    }};


	/**
	 * Constructeur de ConsoleDisplay
	 *
	 * @param controller
	 * 		Le lien entre l'affichage et le métier
	 */
	public ConsoleDisplay( Controller controller )
	{
		this.controller		= controller;
		this.consoleTrace	= new ArrayList<>();
		this.algorithm		= controller.getAlgorithm();
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
			else if(iVar < this.controller.getAlData().size())
			{
				dataStr = "|" +
						String.format("%-10s",
								this.controller.getAlData().get(iVar).getName())
						+ "|" +
						String.format("%-10s",
								this.controller.getAlData().get(iVar).getType())
						+ "|" +
						String.format("%-20s", this.controller.getAlData().
								get(iVar).getStrValue() == null ?
								"" : this.controller.getAlData().get(iVar).getStrValue()) +
						"|\n";
				iVar++;
			}
			else if(iVar == this.controller.getAlData().size())
			{
				dataStr = new String(new char[44]).replace('\0', '"') + "\n";
				iVar++;
			}
			else
			{
				dataStr = "\n";
			}

			String line = background + "| " +
					String.format("%-80s", (current == i ? color.BACKGROUND_WHITE + color.BLACK : "") +
							String.format("%2d", i) + " " +
							String.format(algorithm.get(i).contains(
									"◄—") ? "%-80s" : "%-80s",
									algorithm.get(i))) + background +
					" | " + dataStr;

			for(Keyword k:keywords)
			{
                boolean b = algorithm.get(i).matches(k.getRegex());
				if(b)
				{
					line = line.replaceAll(k.getKeyword(),
							(current == i ? backgroundCurrent : background)
							+ k.getColor()
							+ k.getKeyword()
							+ (current == i ? backgroundCurrent : background)
					);
				}
			}
			str.append(line);
		}


		str.append(new String(new char[87]).replace('\0', '"') + "\n\n");

		str.append(new String(new char[11]).replace('\0', '"') + "\n" +
				"| CONSOLE |\n" + new String(new char[87]).replace('\0', '"') + "\n");
		
		this.consoleTrace = this.controller.getAlConsole();
		
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


