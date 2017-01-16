package algopars.util;

import algopars.tool.BasicFunction;
import algopars.tool.Transformer;
import bsh.EvalError;
import bsh.Interpreter;
import algopars.util.parsing.SyntaxChecker;
import algopars.util.var.*;
import algopars.tool.Loop;
import algopars.tool.Regex;

import java.io.*;
import java.util.*;

/**
 * Classe algopars.util.AlgoInterpreter (classe principale metier) qui gère l'interprétation des algorithmes
 *
 * @author Antoine WARET, Mathieu CHOUGUI, Florian BOULANT, Clémence EDOUARD, Corentin ATHINAULT
 * @version 1.0.0a
 * @date 01/08/2017
 */
public class AlgoInterpreter implements Serializable
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
	private Stack<String>  expressionsStack;
	
	
	/**
	 * Constructeur d'algopars.util.AlgoInterpreter
	 *
	 * @param algorithm
	 * 		L'ArrayList<String> représentant l'algorithme à interpréter
	 */
	public AlgoInterpreter(ArrayList<String> algorithm)
	{
		try
		{
			
			this.interpreter = new Interpreter();
			this.df = new DataFactory();
			this.syntaxChecker = new SyntaxChecker(algorithm);
			
			this.lineIndex = 0;
			this.algorithm = algorithm;
			
			this.alData = new ArrayList<>();
			this.tracedVar = new ArrayList<>();
			this.alConsole = new ArrayList<>();
			
			this.conditionsStack = new Stack<>();
			this.loopsStack = new Stack<>();
			this.expressionsStack = new Stack<>();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		
		this.initializeData();
		lineIndex = algorithm.size() - syntaxChecker.getBody().size();
	}
	
	
	private void initializeData()
	{
		syntaxChecker.dataCheck();
		this.declareData(syntaxChecker.gethData());
		
		for(String s : df.getHMapData().keySet())
		{
			alData.add(df.getHMapData().get(s));
		}
		
		for(Variable v : alData)
		{
			try
			{
				if(v instanceof ArrayVar)
				{
					ArrayVar aV = (ArrayVar) v;
					aV.setSize((Integer) interpreter.eval(
							"(new " + aV.getJavaType() + "[" + aV.getStrSize() + "]).length"));
					
				}
				else
				{
					interpreter.eval(v.getJavaType() + " " + v.getName());
				}
			}
			catch(EvalError e)
			{
				System.err.println(e.toString());
			}
		}
	}
	
	
	/**
	 * Méthode stockant toutes les variables déclarées dans la algopars.util.var.DataFactory
	 *
	 * @param data
	 * 		L'ArrayList<String> correspondante à la partie données de l'algorithme
	 */
	public void declareData(HashMap<String, String> data)
	{
		
		for(String valName : data.keySet())
		{
			if(Regex.isConstant(valName))
			{
				try
				{
					df.dataDeclaration(valName, null, data.get(valName));
				}
				catch(Exception e)
				{
					System.err.println(e.toString());
				}
			}
			else
			{
				
				try
				{
					df.dataDeclaration(valName, data.get(valName), null);
				}
				catch(Exception e)
				{
					System.err.println(e.toString());
				}
			}
		}
	}
	
	
	/**
	 * Méthodant interprétant l'algorithme ligne par ligne
	 */
	public boolean processLine()
	{
		String line = algorithm.get(lineIndex).trim();
		
		if(algopars.tool.Regex.isComment(line))
		{
			line = line.substring(0, line.indexOf("//"));
		}
		
		if(line.equals("ftq"))
		{
			//On ré-évalue la valeur de la condition de boucle
			conditionsStack.pop();
			conditionsStack.push(evaluateCondition(loopsStack.peek().getCondition()));
			
			if(conditionsStack.peek())
			{
				this.lineIndex = loopsStack.peek().getStartIndex() - 1;
				line = algorithm.get(lineIndex);
			}
			else
			{
				conditionsStack.pop();
				loopsStack.pop();
			}
		}
		else if(line.equals("fsi"))
		{
			//On enlève la dernière valeur de condtion
			conditionsStack.pop();
			if(conditionsStack.empty())
			{
				lineIndex++;
				return true;
			}
		}
		
		if(line.equals("sinon"))
		{
			//Inverse la dernière valeur booléenne de la pile de conditions
			conditionsStack.push(!conditionsStack.pop());
			
			if(containsFalse() && isCurrentConditionFirstFalse())
			{
				lineIndex++;
				return true;
			}
			
		}
		
		if(algopars.tool.Regex.isCondition(line))
		{
			line = line.replaceAll("\\s+", " ").trim();
			//On ne récupère que la valeur de la condition
			line = line.substring(line.indexOf("si") + 2, line.indexOf("alors")).trim();
			//On récupère la valeur de la condition
			this.conditionsStack.push(this.evaluateCondition(line));
			
			if(containsFalse() && isCurrentConditionFirstFalse())
			{
				lineIndex++;
				return true;
			}
		}
		else if(algopars.tool.Regex.isLoop(line))
		{
			Loop l = new Loop(lineIndex,
			                  line.substring(line.indexOf("que") + 3,
			                                 line.indexOf("faire")));
			
			if(isLoopAlreadyStacked(l))
			{
				lineIndex++;
				return true;
			}
			
			//On ajoute à la pile une nouvelle Loop symbolisant notre boucle
			this.loopsStack.push(l);
			
			//On ajoute la valeur de la condition de la boucle à la pile
			conditionsStack.push(evaluateCondition(loopsStack.peek().getCondition()));
			
			if(containsFalse() && isCurrentConditionFirstFalse())
			{
				lineIndex++;
				return true;
			}
		}
		
		//Toutes les opération à ignorer si un faux est trouvé : permet de quand même construire
		// la pile
		if(!containsFalse())
		{
			if(algopars.tool.Regex.isFunction(line))
			{
				String[] fonc = line.split("\\(|\\)");
				switch(fonc[0])
				{
					case "ecrire":
						write(fonc[1].replace(')', ' ').trim());
						break;
					case "lire":
						read(fonc[1]);
						break;
					default:
						break;
				}
			}
			else if(line.indexOf("◄—") != -1)
			{
				this.assignement(line);
			}
		}
		
		lineIndex++;
		
		return conditionsStack.empty() ? true : !containsFalse();
	}
	
	/**
	 * Méthode permettant de vérifier si la pile de conditions en contient une fausse
	 *
	 * @return Un booléen indiquant si la pile contient au moins une fausse condition
	 */
	private boolean containsFalse()
	{
		for(Boolean b : conditionsStack)
		{
			if(!b)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Méthode permettant de savoir si la condition courante est fausse
	 *
	 * @return Un booléen indiquant si la condition courante est fausse
	 */
	private boolean isCurrentConditionFirstFalse()
	{
		boolean noOtherFalseFound = true;
		
		for(int i = 0 ; i < conditionsStack.size() - 1 ; i++)
		{
			noOtherFalseFound = noOtherFalseFound & conditionsStack.get(i);
		}
		
		return noOtherFalseFound;
		
	}
	
	
	/**
	 * Méthode permettant d'interpréter les lignes d'affectation de variables
	 *
	 * @param line
	 * 		La ligne comprenant l'affectation
	 */
	public void assignement(String line)
	{
		String var      = line.split("◄—")[0].trim();
		String val      = line.split("◄—")[1].trim();
		int    indexVar = -1;
		
		if(Regex.isArrayVar(var))
		{
			try
			{
				indexVar = ((Integer) interpreter.eval(var.substring(var.indexOf("[") + 1, var.indexOf("]"))));
			}
			catch(EvalError evalError)
			{
				evalError.printStackTrace();
			}
			var = line.substring(0, line.indexOf("["));
		}
		
		if(Regex.isArrayVar(val))
		{
			int indexVal = 0;
			try
			{
				indexVal = (Integer) interpreter.eval(val.substring(val.indexOf("[") + 1, val.indexOf("]")));
			}
			catch(EvalError evalError)
			{
				evalError.printStackTrace();
			}
			val = val.substring(0, val.indexOf("["));
			for(Variable v : alData)
			{
				if(v.getName().equals(val))
				{
					val = ((ArrayVar) v).getValue(indexVal);
				}
			}
		}
		else if(!Regex.isString(val))
		{
			val = this.process(val);
		}
		
		for(Variable v : alData)
		{
			if(v.getName().equals(var))
			{
				if(indexVar != -1)
				{
					((ArrayVar) v).setCellValue(indexVar, val);
				}
				else
				{
					try
					{
						interpreter.eval((var + " = " + val));
						v.setValue(String.valueOf(interpreter.get(v.getName())));
					}
					catch(EvalError e)
					{
						System.err.println(e.toString());
					}
				}
			}
		}
	}
	
	
	/**
	 * Méthode permettant d'évaluer une expression booléenne donnée en pseudo-code
	 *
	 * @param condition
	 * 		L'expression booléenne en pseudo-code
	 *
	 * @return La valeur de l'expression booléenne à évaluer
	 */
	public Boolean evaluateCondition(String condition)
	{
		condition = algopars.tool.Transformer.transformCondition(condition);
		
		try
		{
			return (Boolean) interpreter.eval(condition);
		}
		catch(EvalError evalError)
		{
			evalError.printStackTrace();
		}
		
		return false;
	}
	
	
	/**
	 * Méthode qui gère la primitive "ecrire"
	 *
	 * @param toWrite
	 * 		La chaîne représentant ce qu'il y a à écrire
	 */
	public void write(String toWrite)
	{
		String processed = new String(toWrite);
		
		if(Regex.isString(processed))
		{
			processed = BasicFunction.supressQuotes(processed);
		}
		else if(Regex.isVariable(processed))
		{
			for(Variable v : this.alData)
			{
				if(processed.equals(v.getName()))
				{
					processed = v.getStrValue();
					break;
				}
			}
		}
		else
		{
			processed = process(processed);
		}
		
		this.alConsole.add(processed);
	}
	
	
	/**
	 * Méthode permettant d'évaluer toute expression composée
	 *
	 * @param toProcess
	 * 		La chaîne à évaluer
	 *
	 * @return Le résultat de cette évaluation
	 */
	public String process(String toProcess)
	{
		String expression = new String(toProcess);
		try
		{
			String[] bracesContents = expression.split("[\\(\\)]");
			//Première boucle où l'on traite les contenus de parenthèses qui ne sont pas gérés par l'interpreter (fonctions notamment)
			for(int i = 0 ; i < bracesContents.length ; i++)
			{
				if(Regex.isVariable(bracesContents[i]) && this.variableExists(bracesContents[i]))
				{
					expression = expression.replace(bracesContents[i], this.getVariableValue(bracesContents[i]));
				}
				if(Regex.isOperation(bracesContents[i]))
				{
					String operation = bracesContents[i];
					operation = Transformer.transformExpression(operation);
					expression = expression.replace(bracesContents[i], interpreter.eval(operation).toString());
				}
			}
			
			String[] parts = expression.split("[+\\-×/&]");
			for(int i = 0 ; i < parts.length ; i++)
			{
				if(Regex.isFunction(parts[i]))
				{
					expression = expression.replace(parts[i], BasicFunction.chooseBasicFunction(parts[i]));
				}
				if(Regex.isVariable(parts[i]) && this.getVariableValue(parts[i]) != null)
				{
					expression = expression.replace(parts[i], this.getVariableValue(parts[i]));
				}
			}
			
			if(Regex.isVariable(expression) && variableExists(expression))
			{
				expression = this.getVariableValue(expression);
			}
			else if(!Regex.isString(expression))
			{
				expression = Transformer.transformExpression(expression);
				expression = interpreter.eval(expression).toString();
			}
		}
		catch(EvalError e)
		{
			e.printStackTrace();
		}
		
		return expression;
	}
	
	/**
	 * Méthode permettant de savoir si la Variable dont le nom est passé en paramètre existe
	 *
	 * @param supposedVariable
	 * 		Le nom de la supposée Variable
	 *
	 * @return Un booléen indiquant si la Variable dont le nom est passé en paramètre existe
	 */
	private boolean variableExists(String supposedVariable)
	{
		for(Variable v : this.alData)
		{
			if(v.getName().equals(supposedVariable))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Méthode qui gère la primitive "lire"
	 *
	 * @param vars
	 * 		La chaîne représentant ce qu'il y a à lire
	 */
	public void read(String vars)
	{
		String[]       tabS   = vars.split("\\,");
		BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
		for(int i = 0 ; i < tabS.length ; i++)
		{
			for(Variable var : alData)
			{
				if(Regex.isArrayVar(tabS[i]) && tabS[i].substring(0, tabS[i].indexOf("[")).equals(var.getName()))
				{
					System.out.println("entrez une valeur pour " + tabS[i]);
					String value = null;
					try
					{
						value = entree.readLine();
						((ArrayVar) var).setCellValue(((Integer) interpreter.eval(
								tabS[i].substring(tabS[i].indexOf("[") + 1, tabS[i].indexOf("]")))), value);
					}
					catch(Exception e)
					{
					}
					
				}
				else if(var.getName().equals(tabS[i]))
				{
					try
					{
						System.out.println("entrez une valeur pour " + var.getName());
						String value = entree.readLine();
						interpreter.eval(var.getName() + "=" + value);
						var.setValue(value);
					}
					catch(Exception e)
					{
					}
				}
			}
		}
	}
	
	/**
	 * Méthode permettant de faire choisir à l'utilisateur les variables qu'il souhaite tracer
	 */
	public void chooseVar()
	{
		Scanner sc;
		for(Variable var : this.alData)
		{
			System.out.println(
					"voulez vous tracer la variable " + var.getName() + " ? (oui ou non)");
			sc = new Scanner(System.in);
			String s = sc.nextLine();
			if(s.equals("oui")) tracedVar.add(var);
		}
	}
	
	
	/**
	 * Méthode permettant d'obtenir une ArrayList<Variable> représentant toutes les données à tracer
	 *
	 * @return Une ArrayList<Variable> représentant toutes les données à tracer
	 */
	public ArrayList<Variable> getAlData()
	{
		return this.tracedVar;
	}
	
	
	/**
	 * Méthode permettant d'obtenir une ArrayList<String> représentant la trace d'éxécution
	 *
	 * @return Une ArrayList<String> représentant la trace d'exécution
	 */
	public ArrayList<String> getAlConsole()
	{
		return this.alConsole;
	}
	
	/**
	 * Méthode permettant de connaître la valeur de vérité de la condition la plus haute dans la pile
	 *
	 * @return Un booléen indiquant la valeur de vérité de la condition la plus haute dans la pile
	 */
	public char getLastConditionValue()
	{
		if(conditionsStack.empty())
		{
			return 'n';
		}
		
		return conditionsStack.peek() ? 'g' : 'r';
	}
	
	/**
	 * Méthode permettant de savoir si le Loop passé en paramètre est mis en pile
	 *
	 * @param loop
	 * 		Le Loop dont on veut savoir si il est mis en pile
	 *
	 * @return Un booléen indiquant si le Loop passé en paramètre est mis en pile
	 */
	public boolean isLoopAlreadyStacked(Loop loop)
	{
		for(Loop l : loopsStack)
		{
			if(l.equals(loop))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Méthode permettant de connaître la ligne courante sur laquelle se trouve l'interpréteur
	 *
	 * @return La ligne courante sur laquelle se trouve l'interpréteur
	 */
	public int getLineIndex()
	{
		return lineIndex;
	}
	
	/**
	 * Méthode permettant de cloner l'AlgoInterprer courant
	 *
	 * @return Le clone de l'AlgoInterprer courant
	 *
	 * @throws IOException, ClassNotFoundException
	 */
	public AlgoInterpreter deepClone(){
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (AlgoInterpreter) ois.readObject();
		} catch (IOException e) {
			System.err.println(e.toString());
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * Méthode permettant de d'obtenir la valeur d'une variable dont le nom est passé en paramètre
	 *
	 * @param variableName
	 * 		Le nom de la variable dont on cherche la valeur
	 *
	 * @return La valeur liée au nom de variable passé en paramètre
	 */
	public String getVariableValue(String variableName)
	{
		for(Variable v : this.alData)
		{
			if(v.getName().equals(variableName))
			{
				return v.getStrValue();
			}
		}
		
		return null;
	}

	/**
	 * Setter de LineIndex
	 *
	 * @param lineIndex
	 * 		L'indice de la ligne à traiter
	 */
	public void setLineIndex(int lineIndex) {
		this.lineIndex = lineIndex;
	}
}
