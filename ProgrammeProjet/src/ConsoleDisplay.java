import engine.type.*;

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
	private ArrayList<String> algorithm;
	private AlgoInterpreter   interpreter;
	private final String background = "\033[37m" + "\033[40m";


	/**
	 * Constructeur de ConsoleDisplay
	 *
	 * @param filePath Le chemin vers le fichier .algo à interpréter
	 */
	public ConsoleDisplay( String filePath )
	{
		this.algorithm = new AlgoReader( filePath ).getAlgorithm();
		interpreter = new AlgoInterpreter( algorithm );
		interpreter.run();
	}


	/**
	 * Méthode d'affichage de l'algorithme, de la trace des variables et de la trace d'exécution
	 *
	 * @param current La ligne courante
	 */
	public void display( int current )
	{
		/*ArrayList<Variable> variables = interpreter.getData();
		ArrayList<Variable> variables = new ArrayList<Variable>();
		variables.add(new IntegerVar("x", "5"));
		variables.add(new IntegerVar("y", "2"));
		variables.add(new StringVar("CACA", "\"Coucou\""));
		variables.add(new DoubleVar("z", "12.5"));*/

		StringBuilder dataStr = new StringBuilder();

		StringBuilder str = new StringBuilder();
		str.append( background + new String( new char[10] ).replace( '\0', '"' ) +
					new String( new char[78] ).replace( '\0', ' ' ) +
					new String( new char[11] ).replace( '\0', '"' ) + "\n" );

		str.append( "|  CODE  |" + new String( new char[78] ).replace( '\0', ' ' ) +
					"| DONNEES" + " |\n" );

		str.append( new String( new char[87] ).replace( '\0', '"' ) + " " +
					new String( new char[44] ).replace( '\0', '"' ) + "\n" );

		int iVar = 0;

		for ( int i = 0; i < ( algorithm.size() > 40 ? 40 : algorithm.size() ); i++ )
		{
			if ( i == 0 )
			{
				dataStr.append( background + "|" + String.format( "%-10s", "NOM" ) + "|"
								+ String.format( "%-10s", "TYPE" ) + "|"
								+ String.format( "%-20s", "VALEUR" ) + "|\n" );
			}
			else if ( iVar < interpreter.getAlData().size() )
			{
				dataStr.append( "|" +
								String.format( "%-10s",
											   interpreter.getAlData().get( iVar ).getName() )
								+ "|" +
								String.format( "%-10s",
											   interpreter.getAlData().get( iVar ).getType() )
								+ "|" +
								String.format( "%-20s", interpreter.getAlData().
										get( iVar ).getStrValue() == null ?
										"" : interpreter.getAlData().get( iVar ).getStrValue() ) +
								"|\n" );
				iVar++;
			}
			else if ( iVar == interpreter.getAlData().size() )
			{
				dataStr.append( new String( new char[44] ).replace( '\0', '"' ) + "\n" );
				iVar++;
			}
			else
			{
				dataStr.append( "\n" );
			}

			str.append( background + "| " +
						String.format( "%-80s", ( current == i ? "\033[47m" + "\033[30m" : "" ) +
												String.format( "%2d", i ) + " " +
												String.format( algorithm.get( i ).contains(
														"◄—" ) ? "%-79s" : "%-80s",
															   algorithm.get( i ) ) ) + background +
						" | " + dataStr );
		}

		str.append( new String( new char[87] ).replace( '\0', '"' ) + "\n" );

		System.out.println( str );
	}

	/**
	 * Méthode permettant d'obtenir l'algorithme à interpréter
	 *
	 * @return l'algorithme sour forme d'ArrayList de String
	 */
	public ArrayList<String> getAlgorithm()
	{
		return algorithm;
	}
}
