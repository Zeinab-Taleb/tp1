package devoir1;


import java.io.*;
import java.io.InputStreamReader;

import java.util.*;
public class Dictionnaire {
	public String initText;
	public String ecran;
	public String[] tabDic;
	public String[] tabTexte;
	protected ArrayList<String> ListDic;
	public ArrayList<String> ListTexte;

	//cette methode permet de lire un dictionnaire et cree la liste
	public void lireDictionnaire(String pathFichier) throws IOException {

        Reader reading = new InputStreamReader(new FileInputStream(pathFichier), "UTF-8");
        BufferedReader bufferedReader =  new BufferedReader(reading);
 
        String line = "";
        /*dsjbibcbbr
        norenvonrv
        evnienviure
        */
        while((line = bufferedReader.readLine())!= null){    
        	initText += line + "\n";  

        }
        this.tabDic = initText.split("\\n");
        
        this.ListDic = new ArrayList<String>();

        
        for( String mot:tabDic ) {
			
				this.ListDic.add(mot);
		}

		bufferedReader.close();
        
        

	}

   	//cette methode permet de creer la liste du text lu
	public void lireTexte(String texte) throws IOException {


        this.ecran = texte.toLowerCase();
 
        String txte = ecran.replaceAll("\\p{Punct}", "");
        
       
	     this.tabTexte = txte.split(" +");

	
	}

	//cette methode permet d'ajouter les mot incorrect
	public void ajouter() {
		this.ListTexte = new ArrayList<String>();
	
		
		
		
		for (String mot:tabTexte) {
			if (!ListDic.contains(mot)){			
				if(! ListTexte.contains(mot)) {
					this.ListTexte.add(mot);
				}			
			}
	
		} 	
	}
	//cette methode permet de calcule le minimum	
	public static int min(int a,int b,int c) {
		return Math.min(Math.min(a, b), c);
	}
	
	//cette methode permet le calcule du distance de levenshtein
	public int levenshtein(String mot1 ,String mot2) {
		int coûtSubstitution;
		int[][] calcule=new int[mot1.length()+1][mot2.length()+1];
		for (int i=0;i<=mot1.length();i++) {
			for (int j =0 ;j<=mot2.length();j++) {
				if (i==0) {
					calcule[i][j]=j;
				}
				else if (j==0) {
					calcule[i][j]=i;
				}
				else {
					if (mot1.charAt(i-1)==mot2.charAt(j-1)) {
						coûtSubstitution=0;
						}
						else {
						coûtSubstitution=1;}
					
					calcule[i][j] = min(calcule[i - 1][j - 1]
							+ coûtSubstitution, 
							calcule[i - 1][j] + 1, 
							calcule[i][j - 1] + 1);
				}
	
			}
		}
		
		return calcule[mot1.length()][ mot2.length()];
				
	}
	
	
	//cette methde permet de calculer la distance de chaque mot
	public String [] itere(String mot) {
		ArrayList<Integer> number= new ArrayList<Integer>();
		String [] choix;
		
			for (String mean:ListDic) {
					
				int difference=levenshtein(mot,mean);
					
						number.add(difference);
						
			
			}
		int mini=Collections.min(number);
		choix=new String[5];
		int j =0;
		while (j<5) {
			for (int i=0;i<number.size();i++) {

				if (number.get(i)==mini) {
			
					if (j<5) {
						choix[j]=ListDic.get(i);
						j+=1;
				
					}
			
				}

			}
		if (j<5) {
			mini=mini+1;}
		
		}

		return choix;
		
	}
}
