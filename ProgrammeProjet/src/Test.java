import java.io.*;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args){
        ArrayList<String> code = new ArrayList<String>();
        String line = null;

        try {
            //InputStream ips = Test.class.getResourceAsStream("../test.algo");
            //InputStreamReader ipsr = new InputStreamReader(ips);
            FileReader fr = new FileReader("test.algo");
            BufferedReader br = new BufferedReader(fr);
            while((line = br.readLine()) != null) {
                code.add(line);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }

        SyntaxChecker sc = new SyntaxChecker(code);
        System.out.println("Test Header :");
        System.out.println(sc.headerCheck());
        System.out.println("Test Data :");
        System.out.println(sc.dataCheck());
        System.out.println("Test Body :");
        System.out.println(sc.bodyCheck());

    }
}
