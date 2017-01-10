package algopars.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Classe algopars.util.AlgoReader qui lit le fichier contenant l'algorithme et le transforme en ArrayList<String>
 *
 * @author Antoine WARET
 * @version 1.0.0a
 * @date 01/08/2017
 */
public class AlgoReader
{
	private ArrayList<String> algorithm;
	
	/**
	 * Constructeur d'algopars.util.AlgoReader
	 *
	 * @param filePath
	 * 		String décrivant le chemin faire le fichier contenant l'algorithme
	 */
	public AlgoReader(String filePath)
	{
		String line;
		algorithm = new ArrayList<String>();
		
		try
		{
			InputStream       ips  = this.getClass().getResourceAsStream(filePath);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader    br   = new BufferedReader(ipsr);
			
			while((line = br.readLine()) != null)
			{
				algorithm.add(line);
			}
			ipsr.close();
		}
		catch(Exception e)
		{
			System.err.println("Erreur: fichier introuvable");
		}
	}
	
	/**
	 * Méthode permettant d'obtenir l'ArrayList de String consrtuite sur la base du fichier
	 *
	 * @return l'ArrayList<String> représentant l'algorithme
	 */
	public ArrayList<String> getAlgorithm()
	{
		return algorithm;
	}
}
