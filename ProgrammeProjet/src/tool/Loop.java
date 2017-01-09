package tool;


/**
 * Classe utilitaire Loop qui permet d'interpréter les boucles
 *
 * @author Clémence EDOUARD, Florian BOULANT
 * @version 1.0.0a
 * @date 01/09/2017
 */
public class Loop
{
	private int     startIndex;
	private String  condition;
	private boolean conditionValue;
	
	/**
	 * Constructeur de Loop
	 * @param startIndex
	 *      La ligne de départ du Loop
	 * @param condition
	 *      La condition d'entrée du Loop
	 */
	public Loop(int startIndex, String condition)
	{
		this.startIndex = startIndex;
		this.condition = condition;
		this.conditionValue = false;
	}
	
	/**
	 * Méthode retournant la ligne de départ du Loop
	 * @return
	 *      La ligne de départ du Loop
	 */
	public int getStartIndex()
	{
		return startIndex;
	}
	
	/**
	 * Méthode permettant d'obtenir la condition d'entrée du Loop
	 * @return
	 *      La condition d'entrée du Loop
	 */
	public String getCondition()
	{
		return condition;
	}
	
	/**
	 * Méthode permettant d'obtenir la valeur de vérité de la condition du Loop
	 * @return
	 *      La valeur de vérité de la condition du Loop
	 */
	public boolean getConditionValue()
	{
		return conditionValue;
	}
	
	/**
	 * Méthode permettant de modifier la valeur de vérité de la condition du Loop
	 * @param conditionValue
	 *      La nouvelle valeur de vérité de la condition du Loop
	 */
	public void setConditionValue(boolean conditionValue)
	{
		this.conditionValue = conditionValue;
	}
}
