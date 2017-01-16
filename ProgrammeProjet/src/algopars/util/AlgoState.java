package algopars.util;


/**
 * Created by Terawa on 11/01/2017.
 */
public class AlgoState {
	private AlgoInterpreter algoInterpreter;

	public AlgoState(AlgoInterpreter algoInterpreter) {
		this.algoInterpreter = algoInterpreter;
	}

	public AlgoInterpreter getAlgoInterpreter() {
		return algoInterpreter;
	}
}

