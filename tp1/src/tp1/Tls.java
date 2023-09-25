package tp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Tls {
	public static void main(String[] args) {
		//si l'utilisateur ne met rien en entree
		if (args.length < 1) {
            System.out.println("veuiller mettre un ficheir d'entree");
            System.exit(1);
		}
		List<String> data = new ArrayList<>();
		 String pwd = args[0];
		 File fichier = new File(pwd);
         //si le fichier n'existe pas
         if (!fichier.exists()) {
         	System.out.println("Le fichier : " + pwd +" n'existe pas ");
         	System.exit(1);
         }

		 //ajouter le chemin du fichier
		 data.add(String.valueOf(args[0]));
		 lireFichier(pwd,data);
		 print(data);
	}


//lireFichier permet de extraire tloc tcmp nombre d'assert class et package 
	//on donne en argument le chemin du fichier et une tableau de string
public static void lireFichier( String fichier,List<String> data) {
	//lire fichier ligne par ligne
	try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
        String pack = "none";
        String cls = "none";
        int tloc = 0;
        int tassert = 0;
        boolean comnt=false;
        double tcmp=0.0;
        String line;
        
        while ((line = reader.readLine()) != null) {
        	//si cest un paragraph de commentaire
        	if (line.contains("/*")) {
    			comnt=true;
    			continue;
    		}
        	
        	else {
        		//si la ligne contient class mais pas en commentaire
        		if (line.contains("class") && comnt==false && !line.startsWith("//")) {
        			//extraire le nom de la class
        			String[] ligneClasse = line.split("\\s+");
        			for (String token : ligneClasse) {
                        if (token.equals("class") ) {
                        	cls= ligneClasse[2];}
                        	}
        			
            	}
        		//si la ligne contient le mot package et n'est pas en commentaire
        		if (line.contains("package")&& comnt ==false &&!line.startsWith("//") ) {
        			//extraire le nom du package
        			String[] lignePack = line.split("\\s+");
        			for (String token : lignePack) {
                        if (token.equals("package") ) {
                        	pack= lignePack[1];
                        	pack=pack.replace(";", "");}//enlever le point virgule
                        	}
        		}
        		
        		//si ce n'est pas un commentaire ajouter 1 a tloc
            	if (!line.startsWith("//") && comnt==false &&!line.isEmpty() ) {
            		tloc+=1;
            	}
            	//si sa contient un assert ou fail et n'est pas un commentaire	
            	if (line.contains("Assert.")|| line.contains("fail(") && comnt ==false &&!line.startsWith("//") )  {
                    tassert++;
                }
            	//fin du paragraph
                if (comnt==true && line.contains("*/")) {
                	comnt=false;
                }
        		}
        	
        }
        //calcule tcmp
        if (tloc!=0 && tassert!=0) {
        tcmp=tloc/tassert;}
        //ajouter element au data
        data.add(pack);
        data.add(cls);
        data.add(Integer.toString(tloc));
        data.add(Integer.toString(tassert));
        data.add(String.valueOf(tcmp));
        
        } 
	
	
		catch (IOException e) {
            e.printStackTrace();
        }


}
//print data
public static void print(List<String> data) {
    int taille = data.size();
    String lastElem=data.get(taille -1);
	for (String n : data) {
		if (n!= lastElem) {
        System.out.print( n+", ");
        }
		else {
			System.out.print(n);
		}
        
    }
}
}





