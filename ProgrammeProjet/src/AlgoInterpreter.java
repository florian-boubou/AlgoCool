import bsh.Interpreter;
import engine.type.Variable;

import java.util.ArrayList;

/**
 * Created by Terawa on 08/01/2017.
 */
public class AlgoInterpreter {
	private Interpreter interpreter;
	private ArrayList<Variable> data;

	public AlgoInterpreter(){
		interpreter = new Interpreter();
	}

	public void declareData(String line) {
		String varName;
		if(!line.trim().isEmpty()){
			if (line.contains("◄—")) {
				varName = line.split("◄—")[0].trim();

			}
		}
	}

	public void processline(String line){

	}
}
