package tp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Tls {
	public static void main(String[] args) {
		//si l'utilisateur ne met rien en entree
		if (args.length < 1) {
            System.out.println("veuiller mettre un ficheir d'entree");
            System.exit(1);
			}
		
		// la liste qui va contenir les donnees de sorties
		List<String> data = new ArrayList<>();
		
		//fichier d'entre
		
		String fileInput;
		File fichier;

		//si il ya un output
		if (args.length == 3) {
			fileInput = args[2];
			fichier = new File(fileInput);

			
		}
		else {
			 fileInput = args[0];
			 fichier = new File(fileInput);

		}
		
		//si le fichier n'existe pas
		if (!fichier.exists()) {
	         System.out.println("Le fichier : " + fileInput +" n'existe pas ");
	         System.exit(1);
	         }
		
		//si l'entree est un dossier
		if(fichier.isDirectory()) {
			dossier(fichier,data);
			}
		//si l'entree est un fichier
		else {
			
        	if (fileInput.contains(".java")){
			lireFichier(fileInput,data);
			}}
		//si on donne un chemin de sorti
	

		 if (args.length == 3 && args[0].equals("-o")) {
			 	
	            try (PrintWriter writer = new PrintWriter(new FileWriter(args[1]))) {
	            	writer.println("chemin du fichier, nom du paquet, nom de la classe, tloc de la classe, tassert de la classe, tcmp de la classe");
	            	//ecrire le contenu de data dans un fichier de sorti
	            	for (String line : data) {
	                    writer.println(line);
	                }
	            	
	                System.out.println("Resultats ecrits dans le fichier: " + args[1]);	
	            }
	            catch (IOException e) {
	            	e.printStackTrace();
	            }
	     	}
		 //sinon imprimer le contenu de data
		 else {
			 print(data);
		 }
	}


	//dossier prend en parametre File fichier et liste de string 
	public static void dossier(File fichier,List<String> temp) {
		
		File[] fichiersDossier = fichier.listFiles();
		
		//si le contenue de dossier est pas null traiter le contenue de chaque fichier
		if (fichiersDossier != null) {
            for (File n : fichiersDossier) {
            	if (n.isDirectory()) {
            		
            		dossier(n,temp);
            }
            else { 
            	String nomFichier=n.getName();
            	if (nomFichier.contains(".java")){
            	if(n.isFile()) {  
                	lireFichier(n.getPath(),temp);} 
                	}}
             
                	
             	}
         	}
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
	        Pattern patternAssert = Pattern.compile("assert[a-zA-Z]+\\(");
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
	                        	cls= ligneClasse[2];
	                        	}
	                        }
	        			
	            		}
	        		//si la ligne contient le mot package et n'est pas en commentaire
	        		if (line.contains("package")&& comnt ==false &&!line.startsWith("//") ) {
	        			//extraire le nom du package
	        			String[] lignePack = line.split("\\s+");
	        			for (String token : lignePack) {
	                        if (token.equals("package") ) {
	                        	pack= lignePack[1];
	                        	pack=pack.replace(";", "");
	                        	}
	                        }
	        		}
	        		
	        		//si ce n'est pas un commentaire ajouter 1 a tloc
	            	if (!line.startsWith("//") && comnt==false &&!line.isEmpty() ) {
	            		tloc+=1;
	            	}
	            	//si sa contient un assert ou fail et n'est pas un commentaire	
	            	Matcher mAssert = patternAssert.matcher(line);	
	            	if (mAssert.find()|| line.contains("fail(") && comnt ==false &&!line.startsWith("//") )  {
	                    tassert++;
	                }
	            	//fin du paragraph
	                if (comnt==true && line.contains("*/")) {
	                	comnt=false;
	                	}
	        		}	
	        }
	        //calcule tcmp
	        if (tassert!=0) {
	        if (tloc!=0 ) {
	        tcmp=Math.round((double )tloc/tassert * 10) / 10.0;
	        	}
	        //mettre tous les donnes d'un ficheir dans un string temp
	        String temp=fichier+", "+pack+", "+cls+", "+Integer.toString(tloc)+", "+Integer.toString(tassert)+", "+String.valueOf(tcmp);
	        //ajouter tempcomme element au data
	        data.add(String.valueOf(temp));
	        } 
		}
		
			catch (IOException e) {
	            e.printStackTrace();
	        }
	
	
	}
	//print data
public static void print(List<String> data) {

	for (String n : data) {
		
        System.out.println( n);
       
		}
        
    }
}






