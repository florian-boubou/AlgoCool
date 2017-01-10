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


/**
 * Classe algopars.util.AlgoInterpreter (classe principale metier) qui gère l'interprétation des algorithmes
 *
 * @author Antoine WARET, Mathieu CHOUGUI
 * @version 1.0.0a
 * @date 01/08/2017
 */
public class AlgoInterpreter
{
	private Interpreter   		interpreter;
	private DataFactory   		df;
	private SyntaxChecker 		syntaxChecker;

	private ArrayList<String>   algorithm;
	private int                 lineIndex;
	private ArrayList<Variable> alData;

	private Stack<Boolean> 		conditionsStack;
	private Stack<Loop>    		loopsStack;

	/**
	 * Constructeur d'algopars.util.AlgoInterpreter
	 *
	 * @param algorithm L'ArrayList<String> représentant l'algorithme à interpréter
	 */
	public AlgoInterpreter( ArrayList<String> algorithm )
	{
		try
		{
			interpreter = new Interpreter();
			df = new DataFactory();
			syntaxChecker = new SyntaxChecker( algorithm );

			this.algorithm = algorithm;
			lineIndex = 0;

			alData = new ArrayList<>();
			conditionsStack = new Stack<>();
			loopsStack = new Stack<>();
		}
		catch( Exception e )
		{
			System.err.println( e.getMessage() );
		}

		this.initializeData();

	}

	private void initializeData()
	{
		syntaxChecker.dataCheck();
		this.declareData( syntaxChecker.gethData() );

		for( String s : df.getHMapData().keySet() )
			alData.add( df.getHMapData().get( s ) );

		for( Variable v : alData )
		{
			try
			{
				interpreter.eval( v.getName() + " = " + v.getStrValue() );
			}
			catch( EvalError e )
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

		for( String valName : data.keySet() )
		{
			if( Regex.isConstant( valName ) )
			{
				try
				{
					df.dataDeclaration( valName, null, data.get( valName ) );
				} catch( Exception e )
				{
					System.err.println( e.toString() );
				}
			} else
			{

				try
				{
					df.dataDeclaration( valName, data.get( valName ), null );
				} catch( Exception e )
				{
					System.err.println( e.toString() );
				}
			}
		}
	}


	/**
	 * Méthodant interprétant l'algorithme ligne par ligne
	 *
	 * @return Ce qui sera possiblement à afficher après l'interprétation de cette ligne
	 */
	public String processLine()
	{
		String line = algorithm.get( lineIndex ).trim();

		if( line.equals( "fsi" ) )
			conditionsStack.pop();
		else if( line.equals( "sinon" ) )
			conditionsStack.push( !conditionsStack.pop() );
		else if( line.equals( "ftq" ) )
		{
			loopsStack.peek().setConditionValue(
					evaluateCondition( loopsStack.peek().getCondition() ) );

			if( loopsStack.peek().getConditionValue() )
				this.lineIndex = loopsStack.peek().getStartIndex();

			line = algorithm.get( lineIndex );
		}

		if( conditionsStack.empty() || !conditionsStack.empty() && conditionsStack.peek() )
		{
			if( algopars.tool.Regex.isFunction( line ) )
			{
				String[] fonc = line.split( "\\(" );
				switch( fonc[0] )
				{
					case "ecrire":
						return write( fonc[1].replace( ')', ' ' ).trim() );
					default:
						break;
				}
			}
			else if( line.indexOf( "◄—" ) != -1 )
			{
				this.assignement( line );
			}
			else if( algopars.tool.Regex.isCondition( line ) )
			{
				line = new String( line.replaceAll( "\\s+", " " ) ).trim();
				line = line.substring( line.indexOf( "si" ) + 2, line.indexOf( "alors" ) ).trim();
				this.conditionsStack.push( this.evaluateCondition( line ) );
			}
			else if( algopars.tool.Regex.isLoop( line ) )
			{
				this.loopsStack.push( new Loop( lineIndex + 1,
				                                line.substring( line.indexOf( "que" ) + 3,
				                                                line.indexOf( "faire" ) ) ) );

				this.loopsStack.peek().setConditionValue(
						evaluateCondition( loopsStack.peek().getCondition() ) );
			}
		}
		lineIndex++;
		return null;
	}

	/**
	 * Méthode permettant d'interpréter les lignes d'affectation de variables
	 *
	 * @param line
	 *      La ligne comprenant l'affectation
	 */
	public void assignement( String line )
	{
		String var = line.split( "◄—" )[0].trim();
		String val = line.split( "◄—" )[1].trim();

		for( Variable v : alData )
			if( v.getName().equals( var ) )
			{
				v.setValue( val );
				try
				{
					interpreter.eval( (v.getName() + " = " + v.getStrValue()) );
					v.setValue( String.valueOf( interpreter.get( v.getName() ) ) );//PROBLEME
					System.out.println( "AFFECTATION : " + v.getName() + " = " + v.getStrValue()
					                  );//TEST
				} catch( EvalError e )
				{
					System.err.println( e.toString() );
				}
			}
	}

	/**
	 * Méthode permettant d'évaluer une expression booléenne donnée en pseudo-code
	 *
	 * @param condition
	 *      L'expression booléenne en pseudo-code
	 * @return
	 *      La valeur de l'expression booléenne à évaluer
	 */
	public Boolean evaluateCondition( String condition )
	{
		condition = algopars.tool.Transformer.transformCondition( condition );

		try
		{
			System.out.println( "CONDITION : " + condition + " = " + (Boolean) interpreter.eval(
					condition ) );//TEST
			return (Boolean) interpreter.eval( condition );
		} catch( EvalError evalError )
		{
			evalError.printStackTrace();
		}

		return false;
	}

	/**
	 * Méthode qui gère la primitive "ecrire"
	 *
	 * @param toWrite
	 *      La chaîne représentant ce qu'il y a à écrire
	 * @return
	 *      La chaîne de ce qui sera écrit
	 */
	public String write( String toWrite )
	{
		return this.process( toWrite );
	}

	/**
	 * Méthode permettant d'évaluer une expression passée en paramètre
	 *
	 * @param statement
	 *      L'expression à évaluer
	 * @return
	 *      L'évaluation de l'expression
	 */
	public String process( String statement )
	{
		String processed = null;

		try
		{
			if( Regex.isOperation( statement ) )
				processed = (String) interpreter.eval( statement );
		}
		catch( EvalError e )
		{
			System.err.print( e.getErrorText() );
		}

		return processed;
	}

	/**
	 * Méthode permettant d'obtenir une ArrayList<Variable> représentant toutes les données utilisées dans l'algorithme
	 *
	 * @return
	 *      Une ArrayList<Variable> représentant toutes les données utilisées dans l'algorithme
	 */
	public ArrayList<Variable> getAlData()
	{
		return alData;
	}
}
