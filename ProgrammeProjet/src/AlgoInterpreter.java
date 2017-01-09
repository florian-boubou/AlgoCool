import bsh.EvalError;
import bsh.Interpreter;
import engine.SyntaxChecker;
import engine.type.Variable;
import tool.Regex;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Classe AlgoInterpreter (classe principale metier) qui gère l'interprétation des algorithmes
 *
 * @author Antoine WARET, Mathieu CHOUGUI
 * @version 1.0.0a
 * @date 01/08/2017
 */
public class AlgoInterpreter {
    private Interpreter interpreter;
    private DataFactory df;
    private SyntaxChecker syntaxChecker;
    private DataFactory data;

    private ArrayList<String> algorithm;
    private ArrayList<Variable> alData;
    private ArrayList<Boolean> conditionsStack;

    /**
     * Constructeur d'AlgoInterpreter
     *
     * @param algorithm L'ArrayList<String> représentant l'algorithme à interpréter
     */
    public AlgoInterpreter(ArrayList<String> algorithm) {
        try {
            interpreter = new Interpreter();
            df = new DataFactory();
            syntaxChecker = new SyntaxChecker(algorithm);
            this.algorithm = algorithm;
            alData = new ArrayList<>();
            conditionsStack = new ArrayList<Boolean>();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Méthode lançant l'interprétation
     */
    public void run() {
        syntaxChecker.dataCheck();
        this.declareData(syntaxChecker.gethData());
        for (String s : df.getHMapData().keySet()) {
            alData.add(df.getHMapData().get(s));
        }
        for (String s : algorithm) {
            this.processLine(s);
        }
    }


    /**
     * Méthode stockant toutes les variables déclarées dans la DataFactory
     *
     * @param data L'ArrayList<String> correspondante à la partie données de l'algorithme
     */
    public void declareData(HashMap<String, String> data) {

        for (String valName : data.keySet()) {
            if (Regex.isConstant(valName)) {
                try {
                    df.dataDeclaration(valName, null, data.get(valName));
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            } else {

                try {
                    df.dataDeclaration(valName, data.get(valName), null);
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
        }
    }


    /**
     * Méthodant interprétant l'algorithme ligne par ligne
     *
     * @param line La ligne à interpréter
     * @return Ce qui sera possiblement à afficher après l'interprétation de cette ligne
     */
    public String processLine(String line) {
        line = new String(line.replaceAll("\\s", ""));

        if (line.matches("^fsi\\s$"))
            conditionsStack.remove(conditionsStack.size() - 1);
        else if (line.matches("^sinon\\s$"))
        {
            Boolean b = conditionsStack.get(conditionsStack.size() - 1);
            b = !b;
        }

        if (conditionsStack.isEmpty() || !conditionsStack.isEmpty() && conditionsStack.get(conditionsStack.size() - 1)) {
            if (tool.Regex.isFunction(line)) {
                String[] fonc = line.split("\\(");
                switch (fonc[0]) {
                    case "ecrire":
                        return write(fonc[1].replace(')', ' ').trim());
                    default:
                        break;
                }
            } else if (line.indexOf("◄—") != -1) {
                this.assignement(line);
            } else if (tool.Regex.isCondition(line)) {
                this.conditionsStack.add(this.evaluateCondition(line.substring(line.indexOf("si" + 2), line.indexOf("alors"))));
            }
        }

        return null;
    }

    public void assignement(String line) {
        String var = line.substring(0, line.indexOf("◄—")).replaceAll(" ", "");
        String val = line.substring(line.indexOf("◄—") + 1).replaceAll(" ", "");

        for (Variable v : alData) {
            if (v.getName().equals(var)) {
                v.setValue(val);
            }
        }
    }

    public Boolean evaluateCondition(String condition) {
        condition = condition.replaceAll("/=", "!=");
        condition = condition.replaceAll("ET|et", "&&");
        condition = condition.replaceAll("OU|ou", "||");

        String[] tabS = condition.split("\\&\\&|\\|\\|");
        condition = null;

        for(int i =0; i<tabS.length;i++)
        {
            if(tabS[i].matches("[^></]="))
                tabS[i]=tabS[i].replaceAll("="," =");
            condition+=tabS[i];
        }

        try {
            return (Boolean) interpreter.eval(condition);
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        }
        return false;
    }

    /**
     * Méthode qui gère la primitive "ecrire"
     *
     * @param toWrite La chaîne représentant ce qu'il y a à écrire
     * @return La chaîne de ce qui sera écrit
     */
    public String write(String toWrite) {
        return this.process(toWrite);
    }

    /**
     * Méthode permettant d'évaluer une expression passée en paramètre
     *
     * @param statement L'expression à évaluer
     * @return L'évaluation de l'expression
     */
    public String process(String statement) {
        String processed = null;

        try {
            if (Regex.isOperation(statement))
                processed = (String) interpreter.eval(statement);
        } catch (EvalError e) {
            System.err.print(e.getErrorText());
        }

        return processed;
    }

    /**
     * Méthode permettant d'obtenir une ArrayList<Variable> représentant toutes les données utilisées dans l'algorithme
     *
     * @return Une ArrayList<Variable> représentant toutes les données utilisées dans l'algorithme
     */
    public ArrayList<Variable> getAlData() {
        return alData;
    }

    /**
     * Point d'entrée du programme d'interprétation
     *
     * @param args Contiendra une chaîne représentant le chemin vers le fichier contenant l'algorithme à interpréter
     */
    public static void main(String[] args) {
        AlgoReader algoReader = new AlgoReader(args[0]);
        AlgoInterpreter algoInterpreter;
        SyntaxChecker syntaxChecker = null;
        try {
            syntaxChecker = new SyntaxChecker(algoReader.getAlgorithm());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (syntaxChecker.headerCheck() && syntaxChecker.dataCheck() && syntaxChecker.bodyCheck()) {
            algoInterpreter = new AlgoInterpreter(algoReader.getAlgorithm());
            algoInterpreter.run();
        }
    }
}
