import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Terawa on 2016/12/29.
 */
public class HashMapConfig {
    private HashMap<String, HashSet<String>> reserved;

    public HashMapConfig() {
        reserved = new HashMap<String, HashSet<String>>();
        String line = null;
        String key = null;

        try {
            InputStream ips = Test.class.getResourceAsStream( "test.algo" );
            FileReader fr = new FileReader();
            BufferedReader br = new BufferedReader();
            while((line = br.readLine()) != null) {
                line.replaceAll("\\t| ", "");
                if (line.matches("^\\/\\*.*")) {
                    key = line.substring(2);
                    reserved.put(key, new HashSet<String>());
                }
                else {
                    if (!line.matches("^\\/\\/.*") && !line.equals("\n")) {
                        reserved.get(key).add(line);
                    }
                }
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public HashMap<String, HashSet<String>> getHashMapConfig() {
        return reserved;
    }
}
