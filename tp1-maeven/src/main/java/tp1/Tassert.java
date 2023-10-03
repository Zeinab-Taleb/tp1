package tp1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


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
        if (pwd.contains(".java")) {
        int count = countTAssert(pwd);
        System.out.println("nombre de test d'assert : " + count);}
        else {System.out.print("pas de fichier java");}
    }
    

    private static int countTAssert(String fichier) {
        int nAssert = 0;
        

        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String line;
            Pattern patternAssert = Pattern.compile("assert[a-zA-Z]+\\(");
            
            while ((line = reader.readLine()) != null) {
                //TODO assert grep
            	Matcher mAssert = patternAssert.matcher(line);	
            	if (mAssert.find()|| line.contains("fail("))  {
                    nAssert++;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
            System.exit(2);
        }
        
        return nAssert;
    }
}
