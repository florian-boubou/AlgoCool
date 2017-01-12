package algopars.util;

import algopars.util.var.Variable;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Terawa on 11/01/2017.
 */
public class AlgoState {
	private int currentLine;
	private char lastConditionValue;
	private List<Variable> alData;
	private ArrayList<String> alExec;

	public AlgoState(int currentLine, char lastConditionValue, List<Variable> alData, ArrayList<String> alExec) {
		this.currentLine = currentLine;
		this.lastConditionValue = lastConditionValue;
		this.alData = alData;
		this.alExec = alExec;
	}

	public int getCurrentLine() {
		return currentLine;
	}

	public char getLastConditionValue() { return lastConditionValue; }

	public List<Variable> getAlData() {
		return alData;
	}

	public ArrayList<String> getAlExec() {
		return alExec;
	}
}

