package algopars.util;

import bsh.EvalError;
import bsh.Interpreter;
import algopars.util.parsing.SyntaxChecker;
import algopars.util.var.DataFactory;
import algopars.util.var.Variable;
import algopars.tool.Loop;
import algopars.tool.Regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Classe algopars.util.AlgoInterpreter (classe principale metier) qui gère l'interprétation des algorithmes
 *
 * @author Antoine WARET, Mathieu CHOUGUI
 * @version 1.0.0a
 * @date 01/08/2017
 */
public class AlgoInterpreter
{
	private Interpreter   interpreter;
	private DataFactory   df;
	private SyntaxChecker syntaxChecker;

	private ArrayList<String> algorithm;
	private int               lineIndex;

	private ArrayList<Variable> alData;
	private ArrayList<Variable> tracedVar;
	private ArrayList<String>   alConsole;

	private Stack<Boolean> conditionsStack;
	private Stack<Loop>    loopsStack;


	/**
	 * Constructeur d'algopars.util.AlgoInterpreter
	 *
	 * @param algorithm L'ArrayList<String> représentant l'algorithme à interpréter
	 */
	public AlgoInterpreter( ArrayList<String> algorithm )
	{
		try
		{

			this.interpreter = new Interpreter();
			this.df = new DataFactory();
			this.syntaxChecker = new SyntaxChecker( algorithm );

			this.lineIndex = 0;
			this.algorithm = algorithm;

			this.alData = new ArrayList<>();
			this.tracedVar = new ArrayList<>();
			this.alConsole = new ArrayList<>();

			this.conditionsStack = new Stack<>();
			this.loopsStack = new Stack<>();
		} catch ( Exception e )
		{
			System.err.println( e.getMessage() );
		}

		this.initializeData();

	}


	private void initializeData()
	{
		syntaxChecker.dataCheck();
		this.declareData( syntaxChecker.gethData() );

		for ( String s : df.getHMapData().keySet() )
			alData.add( df.getHMapData().get( s ) );

		for ( Variable v : alData )
		{
			try
			{
				interpreter.eval( v.getJavaType() + " " + v.getName() );
			} catch ( EvalError e )
			{
				System.err.println( e.toString() );
			}
		}
	}


	/**
	 * Méthode stockant toutes les variables déclarées dans la algopars.util.var.DataFactory
	 *
	 * @param data L'ArrayList<String> correspondante à la partie données de l'algorithme
	 */
	public void declareData( HashMap<String, String> data )
	{

		for ( String valName : data.keySet() )
		{
			if ( Regex.isConstant( valName ) )
			{
				try
				{
					df.dataDeclaration( valName, null, data.get( valName ) );
				} catch ( Exception e )
				{
					System.err.println( e.toString() );
				}
			}
			else
			{

				try
				{
					df.dataDeclaration( valName, data.get( valName ), null );
				} catch ( Exception e )
				{
					System.err.println( e.toString() );
				}
			}
		}
	}


	/**
	 * Méthodant interprétant l'algorithme ligne par ligne
	 */
	public boolean processLine()
	{
		String line = algorithm.get( lineIndex ).trim();

		if( algopars.tool.Regex.isComment(line))
		{
			line = line.substring(0, line.indexOf("//"));
		}

		if ( line.equals( "ftq" ) )
		{
			//On ré-évalue la valeur de la condition de boucle
			conditionsStack.pop();
			conditionsStack.push( evaluateCondition( loopsStack.peek().getCondition() ) );

			if ( conditionsStack.peek() )
			{
				this.lineIndex = loopsStack.peek().getStartIndex();
				line = algorithm.get( lineIndex );
			}
			else
			{
				conditionsStack.pop();
				loopsStack.pop();
			}
		}
		else if ( line.equals( "fsi" ) )
			//On enlève la dernière valeur de condtion
			conditionsStack.pop();

		if ( line.equals( "sinon" ) )
		{
			//Inverse la dernière valeur booléenne de la pile de conditions
			conditionsStack.push( ! conditionsStack.pop() );

			if(containsFalse() && isCurrentConditionFirstFalse())
			{
				lineIndex++;
				return true;
			}

		}

		if ( algopars.tool.Regex.isCondition( line ) )
		{
			line = line.replaceAll( "\\s+", " " ).trim();
			//On ne récupère que la valeur de la condition
			line = line.substring( line.indexOf( "si" ) + 2, line.indexOf( "alors" ) ).trim();
			//On récupère la valeur de la condition
			this.conditionsStack.push( this.evaluateCondition( line ) );

			if(containsFalse() && isCurrentConditionFirstFalse())
			{
				lineIndex++;
				return true;
			}
		}
		else if ( algopars.tool.Regex.isLoop( line )  )
		{
			Loop l = new Loop( lineIndex,
							   line.substring( line.indexOf( "que" ) + 3,
											   line.indexOf( "faire" ) ) );

			if(isLoopAlreadyStacked(l))
			{
				lineIndex++;
				return true;
			}

			//On ajoute à la pile une nouvelle Loop symbolisant notre boucle
			this.loopsStack.push( l );

			//On ajoute la valeur de la condition de la boucle à la pile
			conditionsStack.push( evaluateCondition( loopsStack.peek().getCondition() ));

			if(containsFalse() && isCurrentConditionFirstFalse())
			{
				lineIndex++;
				return true;
			}
		}

		//Toutes les opération à ignorer si un faux est trouvé : permet de quand même construire
		// la pile
		if(! containsFalse())
		{
			if ( algopars.tool.Regex.isFunction( line ) )
			{
				String[] fonc = line.split( "\\(|\\)" );
				switch ( fonc[0] )
				{
					case "ecrire":
						write( fonc[1].replace( ')', ' ' ).trim() );
					case "lire":
						read( fonc[1] );
					default:
						break;
				}
			}
			else if ( line.indexOf( "◄—" ) != - 1 )
			{
				this.assignement( line );
			}
		}

		lineIndex++;

		return conditionsStack.empty()?true:!containsFalse();
	}


	private boolean containsFalse()
	{
		for(Boolean b : conditionsStack )
			if(!b)
				return true;

		return false;
	}

	private boolean isCurrentConditionFirstFalse()
	{
		boolean noOtherFalseFound = true;

		for(int i=0; i < conditionsStack.size()-1; i++)
			noOtherFalseFound = noOtherFalseFound & conditionsStack.get( i );

		return noOtherFalseFound;

	}


	/**
	 * Méthode permettant d'interpréter les lignes d'affectation de variables
	 *
	 * @param line La ligne comprenant l'affectation
	 */
	public void assignement( String line )
	{
		String var = line.split( "◄—" )[0].trim();
		String val = line.split( "◄—" )[1].trim();

		for ( Variable v : alData )
			if ( v.getName().equals( var ) )
			{
				try
				{
					interpreter.eval( ( var + " = " + val ) );
					v.setValue( String.valueOf( interpreter.get( v.getName() ) ) );
				} catch ( EvalError e )
				{
					System.err.println( e.toString() );
				}
			}
	}


	/**
	 * Méthode permettant d'évaluer une expression booléenne donnée en pseudo-code
	 *
	 * @param condition L'expression booléenne en pseudo-code
	 * @return La valeur de l'expression booléenne à évaluer
	 */
	public Boolean evaluateCondition( String condition )
	{
		condition = algopars.tool.Transformer.transformCondition( condition );

		try
		{
			return (Boolean)interpreter.eval( condition );
		} catch ( EvalError evalError )
		{
			evalError.printStackTrace();
		}

		return false;
	}


	/**
	 * Méthode qui gère la primitive "ecrire"
	 *
	 * @param toWrite La chaîne représentant ce qu'il y a à écrire
	 */
	public void write( String toWrite )
	{
		String processed = new String( toWrite );

		if ( Regex.isWritable( processed ) ) this.alConsole.add( processed );
	}


	/**
	 * Méthode qui gère la primitive "lire"
	 *
	 * @param vars La chaîne représentant ce qu'il y a à lire
	 */
	public void read( String vars )
	{
		String[]       tabS   = vars.split( "\\," );
		BufferedReader entree = new BufferedReader( new InputStreamReader( System.in ) );
		for ( Variable var : alData )
		{
			for ( int i = 0; i < tabS.length; i++ )
				if ( var.getName().equals( tabS[i] ) )
				{
					System.out.println( "entrez une valeur pour " + var.getName() );
					try
					{
						String value = entree.readLine();
						interpreter.eval( var.getName() + "=" + value );
						var.setValue( value );
					} catch ( Exception e ) {}
				}
		}
	}


	public void chooseVar()
	{
		Scanner sc;
		for ( Variable var : this.alData )
		{
			System.out.println(
					"voulez vous tracer la variable " + var.getName() + " ? (oui ou non)" );
			sc = new Scanner( System.in );
			String s = sc.nextLine();
			if ( s.equals( "oui" ) ) tracedVar.add( var );
		}
	}


	/**
	 * Méthode permettant d'obtenir une ArrayList<Variable> représentant toutes les données à tracer
	 *
	 * @return Une ArrayList<Variable> représentant toutes les données à tracer
	 */
	public ArrayList<Variable> getAlData() { return this.tracedVar; }


	/**
	 * Méthode permettant d'obtenir une ArrayList<String> représentant la trace d'éxécution
	 *
	 * @return Une ArrayList<String> représentant la trace d'exécution
	 */
	public ArrayList<String> getAlConsole() { return this.alConsole; }

	public static void main(String[] args)
	{
		ArrayList<String> algo = new ArrayList<>(  );
		algo.add("ALGORITHME Machin");
		algo.add("variable :");
		algo.add("x, y : entier");
		algo.add( "DEBUT" );
		algo.add("x ◄— 5");
		algo.add("y ◄— x - 2");
		algo.add("FIN");
		AlgoInterpreter in = new AlgoInterpreter( algo );

		in.lineIndex = 4;
		in.processLine();
		in.lineIndex = 5;
		in.processLine();

		System.out.println(in.alData.get( 1 ));
	}

	public char getLastConditionValue()
	{
		if(conditionsStack.empty())
			return 'n';

		return conditionsStack.peek() ? 'g':'r';
	}


	public int getLineIndex()
	{
		return lineIndex;
	}


	public boolean isLoopAlreadyStacked(Loop loop)
	{
		for(Loop l : loopsStack)
			if(l.equals( loop ))
				return true;

		return false;
	}
}
