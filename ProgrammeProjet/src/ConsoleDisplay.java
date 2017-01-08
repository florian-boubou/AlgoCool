import engine.type.*;

import java.util.ArrayList;

/**
 * Created by Terawa on 08/01/2017.
 */
public class ConsoleDisplay {
	private ArrayList<String> algorithm;
	private AlgoInterpreter interpreter;
	private final String background = "\033[37m" + "\033[40m";

	public ConsoleDisplay(ArrayList<String> algorithm) {
		this.algorithm = algorithm;
	}

	public void display(int current) {
		//ArrayList<Variable> variables = interpreter.getData();
		ArrayList<Variable> variables  = new ArrayList<Variable>();
		variables.add(new IntegerVar("x", "5"));
		variables.add(new IntegerVar("y", "2"));
		variables.add(new StringVar("CACA", "\"Coucou\""));
		variables.add(new DoubleVar("z", "12.5"));

		String dataStr = background + "";
		String str = background + new String(new char[10]).replace('\0', '"') + new String(new char[78]).replace('\0', ' ') + new String(new char[11]).replace('\0', '"') + "\n";
		str += "|  CODE  |" + new String(new char[78]).replace('\0', ' ') + "| DONNEES |\n";
		str += new String(new char[87]).replace('\0', '"') + " " + new String(new char[46]).replace('\0', '"') +"\n";

		int iVar = 0;
		for(int i = 0; i < (algorithm.size() > 40? 40: algorithm.size()); i++) {
			if(i == 0) {
				dataStr = background + "|" + String.format("%-10.10s", "NOM") + "|" + String.format("%-10.10s", "TYPE") + "|" + String.format("%-20.20s","VALEUR") + "|\n";
			}
			else{
				if(iVar < variables.size()) {
					dataStr = background + variables.get(iVar).toString() + "\n";
					iVar++;
				}
				else
					dataStr = background + "\n";
			}
			str += background + "| " + String.format("%-88.88s",(current == i ?  "\033[47m" + "\033[30m" : "") + String.format("%2d", i) + " " + String.format(algorithm.get(i).contains("◄—")?"%-79s":"%-80s", algorithm.get(i))) + " | " + dataStr;
		}
		str += new String(new char[87]).replace('\0', '"') + "\n";
		System.out.println(str);
	}
}
