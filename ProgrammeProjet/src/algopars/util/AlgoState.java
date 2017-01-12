package algopars.util;

import algopars.tool.Loop;
import algopars.util.var.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Created by Terawa on 11/01/2017.
 */
public class AlgoState {
	private int currentLine;
	private char lastConditionValue;
	private List<Variable> alData;
	private ArrayList<String> alExec;
	private Stack<Boolean> conditionsStack;
	private Stack<Loop> loopStack;

	public AlgoState(int currentLine, char lastConditionValue, List<Variable> alData,
					 ArrayList<String> alExec, Stack<Boolean> conditionsStack, Stack<Loop> loopStack) {
		this.currentLine = currentLine;
		this.lastConditionValue = lastConditionValue;
		this.alData = alData;
		this.alExec = alExec;
		this.conditionsStack = conditionsStack;
		this.loopStack = loopStack;
	}

	public int getCurrentLine() {
		return currentLine;
	}

	public char getLastConditionValue() {
		return lastConditionValue;
	}

	public List<Variable> getAlData() {
		return alData;
	}

	public ArrayList<String> getAlExec() {
		return alExec;
	}

	public Stack<Boolean> getConditionsStack() {
		return conditionsStack;
	}

	public Stack<Loop> getLoopStack() {
		return loopStack;
	}
}

