import java.util.ArrayList;

/**
 * Created by Terawa on 08/01/2017.
 */
public class ConsoleDisplay {
	private ArrayList<String> algorithm;
	public ConsoleDisplay(ArrayList<String> algorithm) {
		this.algorithm = algorithm;
	}
	public void display(int current) {
		String dataStr = "";
		String str = new String(new char[10]).replace('\0', '"') + new String(new char[78]).replace('\0', ' ') + new String(new char[11]).replace('\0', '"') + "\n";
		str += "|  CODE  |" + new String(new char[78]).replace('\0', ' ') + "| DONNEES |\n";
		str += new String(new char[87]).replace('\0', '"') + " " + new String(new char[46]).replace('\0', '"') +"\n";

		for(int i = 0; i < (algorithm.size() > 40? 40: algorithm.size()); i++) {
			if(i == 0) {
				dataStr = "|    NOM    |    TYPE    |    VALEUR         |\n";
			}
			else {
				dataStr = "\n";
			}
			str += (current == i ? "|>":"| ") + String.format("%2d", i) + " " + String.format(algorithm.get(i).contains("◄—")?"%-79s":"%-80s", algorithm.get(i)) + " | " + dataStr;
		}
		str += new String(new char[87]).replace('\0', '"') + "\n";
		System.out.println(str);
	}
}
