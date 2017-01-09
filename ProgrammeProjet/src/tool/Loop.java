package tool;


/**
 * Created by clemence on 09/01/17.
 */
public class Loop
{
	private int startIndex;
	private String condition;
	private boolean conditionValue;

	public Loop( int startIndex, String condition)
	{
		this.startIndex = startIndex;
		this.condition = condition;
		this.conditionValue = false;
	}


	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public boolean isConditionValue() {
		return conditionValue;
	}

	public void setConditionValue(boolean conditionValue) {
		this.conditionValue = conditionValue;
	}
}
