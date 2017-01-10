package algopars.util.parsing;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Classe HashMapConfig qui sert à réserver les mots-clés de pseudo-code
 *
 * @author Antoine WARET
 * @version 1.0.0a
 * @date 01/08/2017
 */
public class HashMapConfig
{
	private HashMap<String, HashSet<String>> reserved;
	
	/**
	 * Constructeur de HashMapConfig
	 */
	public HashMapConfig()
	{
		reserved = new HashMap<String, HashSet<String>>();
		String line = null;
		String key  = null;
		
		try
		{
			InputStream       ips  = this.getClass().getResourceAsStream("/AlgoPars.config");
			InputStreamReader ipsr = new InputStreamReader(ips);;
			BufferedReader br = new BufferedReader(ipsr);
			while((line = br.readLine()) != null)
			{
				line.replaceAll("\\t| ", "");
				if(line.matches("^\\/\\*.*"))
				{
					key = line.substring(2);
					reserved.put(key, new HashSet<String>());
				}
				else
				{
					if(!line.matches("^\\/\\/.*") && !line.equals("\n"))
					{
						reserved.get(key).add(line);
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	/**
	 * Méthode permettant d'obtenir la HashMap de mots-clés réservés
	 *
	 * @return La HashMap de mots-clés réservés
	 */
	public HashMap<String, HashSet<String>> getHashMapConfig()
	{
		return reserved;
	}
}
