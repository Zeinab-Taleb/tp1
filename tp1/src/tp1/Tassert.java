package tp1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tassert {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("veuiller mettre un fichier en entree");
            System.exit(1);
        }

        String pwd = args[0];
        File file = new File(pwd);
        if (!file.exists()) {
        	System.out.println("Le fichier : " + pwd +" n'existe pas ");
        	System.exit(1);
        }
        int count = countTAssert(pwd);
        System.out.println("nombre de test d'assert : " + count);
    }
    

    private static int countTAssert(String fichier) {
        int nAssert = 0;
        

        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                
            	if (line.contains("Assert.")|| line.contains("fail("))  {
                    nAssert++;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
            System.exit(2);
        }

        return nAssert;
    }
}
