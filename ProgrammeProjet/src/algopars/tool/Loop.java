package algopars.tool;


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


	@Override
	public boolean equals( Object obj )
	{
		Loop other = (Loop)obj;
		return this.condition.equals( other.condition ) && this.startIndex == other.startIndex;
	}
}
