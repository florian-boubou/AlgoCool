import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Terawa on 07/01/2017.
 */
public class AlgoReader {
	private ArrayList<String> algorithm;

	public AlgoReader(String filePath) {
		String line;
		algorithm = new ArrayList<String>();

		try {
			InputStream ips = this.getClass().getResourceAsStream(filePath);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);

			while ((line = br.readLine()) != null) {
				algorithm.add(line);
			}
			ipsr.close();
		} catch (Exception e) {
			System.err.println("Erreur: fichier introuvable");
		}
	}

	public ArrayList<String> getAlgorithm() {
		return algorithm;
	}
}
