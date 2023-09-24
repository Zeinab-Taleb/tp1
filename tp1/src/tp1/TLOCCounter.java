package tp1;


	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.IOException;
	import java.io.File;


public class TLOCCounter {
	
	    public static void main(String[] args) {
	    	//si l'utilisateur ne rentre pas un fichier en ligne de commande
	        if (args.length == 0) {
	            System.out.println("veuiller ecrire le chemin du fichier en ligne de commande");
	        }else {
	            String pwd = args[0];
	            File fichier = new File(pwd);
	            //si le fichier n'existe pas
	            if (!fichier.exists()) {
	            	System.out.println("Le fichier : " + pwd +" n'existe pas ");
	                return;
	            }
	            
	           int a=calcul(pwd);
	           System.out.println(a);
	        }
	    }
public static int calcul(String file) {
	int count=0;
	try {
        FileReader fichierReader = new FileReader(file);
        //lire le fichier ligne par ligne
        BufferedReader bufferedReader = new BufferedReader(fichierReader);

        String ligne;
        boolean comnt=false;
        while ((ligne = bufferedReader.readLine()) != null) {
        	//si la ligne ne contient pas "//" donc ce n'est pas un commentaire
        	if (ligne.contains("/*")) {
    			comnt=true;
    			
    			continue;
    		}
        	if (!ligne.contains("//") && comnt==false &&!ligne.isEmpty() ) {
        		count+=1;
        	}
        		
        
        if (comnt==true && ligne.contains("*/")) {
        	comnt=false;
        }
        }
        bufferedReader.close();
    } catch (IOException e) {
        System.err.println("Erreur de lecture du fichier : " + e.getMessage());
    }
	return (count);
	}
}