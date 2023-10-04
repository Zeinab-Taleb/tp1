package tp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Tropcomp {

	public static void main(String[] args) {
		// si l'utilisateur ne met rien en entree
		if (args.length < 1) {
			System.out.println("veuiller mettre un ficheir d'entree");
			System.exit(1);
		}

		// la liste qui va contenir les donnees de sorties
		List<List<String>> data = new ArrayList<>();

		// fichier d'entre
		String fileInput;
		File fichier;
		double seuil;
		// si il ya un output
		if (args.length == 4) {
			fileInput = args[2];
			fichier = new File(fileInput);
			seuil = Double.valueOf(args[3]) / 100;

		} else {
			fileInput = args[0];
			fichier = new File(fileInput);
			seuil = Double.valueOf(args[1]) / 100;
		}
		// sile fichier n'existe pas
		if (!fichier.exists()) {
			System.out.println("Le fichier : " + fileInput + " n'existe pas ");
			System.exit(1);
		}

		// si l'entree est un dossier
		if (fichier.isDirectory()) {
			dossier(fichier, data);
		}
		// si l'entree est un fichier
		else {

			if (fileInput.contains(".java")) {
				List<String> donneesFichier = new ArrayList<>();
				lireFichier(fileInput, donneesFichier);
				data.add(0, donneesFichier);
			}
		}

		List<List<String>> compTloc = new ArrayList<>();
		List<List<String>> compTls = new ArrayList<>();

		// Copie des données depuis data dans compTloc
		for (List<String> sublist : data) {
			List<String> sublistCopy = new ArrayList<>(sublist);
			compTloc.add(sublistCopy);
		}

		// Copie des données depuis data dans compTls
		for (List<String> sublist : data) {
			List<String> sublistCopy = new ArrayList<>(sublist);
			compTls.add(sublistCopy);
		}

		//System.out.println(data.size());
		// trier les deux liste
		compareTlocTls(compTloc, 3);
		compareTlocTls(compTls, 5);
		// construire la liste de l'intersection
		List<List<String>> instersction = findIntersection(compTloc, compTls, seuil);

		if (args.length == 4 && args[0].equals("-o")) {
			try (PrintWriter writer = new PrintWriter(new FileWriter(args[1]))) {
				writer.println(
						"chemin du fichier, nom du paquet, nom de la classe, tloc de la classe, tassert de la classe, tcmp de la classe");
				// ecrire le contenu de data dans un fichier de sorti
				for (List<String> sousList : instersction) {
					// System.out.println(sousList);
					String sousString = String.join(",", sousList);
					writer.println(sousString);

				}

				System.out.println("Resultats ecrits dans le fichier: " + args[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		 else {
		
			 for (int i=0;i<=instersction.size()-1;i++) {
					for (int j=0;j<=instersction.get(i).size()-1;j++) {
						System.out.print(instersction.get(i).get(j)+" ");
						if (j < instersction.get(i).size() - 1) {
				            System.out.print(", ");
				        }
					}
					System.out.println("\n");
				}
	 }
	}

	// dossier prend en parametre File fichier et liste de string
	public static void dossier(File fichier, List<List<String>> temp) {

		File[] fichiersDossier = fichier.listFiles();

		int f = 0;
		// si le contenue de dossier est pas null traiter le contenue de chaque fichier
//		if (fichiersDossier != null) {

		for (int i = 0; i <= fichiersDossier.length - 1; i++) {

			if (fichiersDossier[i].isFile()) {

				String nomFichier = fichiersDossier[i].getName();

				if (nomFichier.contains(".java")) {

//            			System.out.println(nomFichier);

					List<String> donneesFichier = new ArrayList<>();
					lireFichier(fichiersDossier[i].getPath(), donneesFichier);
					if (donneesFichier.size() != 0) {
						
						temp.add(f, donneesFichier);
						f = f + 1;
					}
				}
			} else {
				if (fichiersDossier[i].isDirectory()) {

					dossier(fichiersDossier[i], temp);
				}
			}
		}
	}

	// }

	// lireFichier permet de extraire tloc tcmp nombre d'assert class et package
	// on donne en argument le chemin du fichier et une tableau de string
	public static List<String> lireFichier(String fichier, List<String> temp) {
		// lire fichier ligne par ligne
		
		try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
			String pack = "none";
			String cls = "none";
			int tloc = 0;
			int tassert = 0;
			boolean comnt = false;
			double tcmp = 0.0;
			String line;
			Pattern patternAssert = Pattern.compile("assert[a-zA-Z]+\\(");
			while ((line = reader.readLine()) != null) {
				// si cest un paragraph de commentaire
				if (line.contains("/*")) {
					comnt = true;
					continue;
				}

				else {
					// si la ligne contient class mais pas en commentaire
					if (line.contains("class") && comnt == false && !line.startsWith("//")) {
						// extraire le nom de la class
						String[] ligneClasse = line.split("\\s+");
						for (String token : ligneClasse) {
							if (token.equals("class")) {
								cls = ligneClasse[2];
							}
						}

					}

					// si la ligne contient le mot package et n'est pas en commentaire
					if (line.contains("package") && comnt == false && !line.startsWith("//")) {
						// extraire le nom du package
						String[] lignePack = line.split("\\s+");
						for (String token : lignePack) {
							if (token.equals("package")) {
								pack = lignePack[1];
								pack = pack.replace(";", "");
							}
						}
					}

					// si ce n'est pas un commentaire ajouter 1 a tloc
					if ((!line.startsWith("//") )&& comnt == false && !line.isEmpty()) {
						tloc += 1;
					}
					// si sa contient un assert ou fail et n'est pas un commentaire
					Matcher mAssert = patternAssert.matcher(line);	
	            	if (mAssert.find()||line.contains("fail(") && comnt == false && !line.startsWith("//")) {
						tassert+=1;
					}
					// fin du paragraph
					if (comnt == true && line.contains("*/")) {
						comnt = false;
					}
				}
			}
			
			// calcule tcmp si cest un fichier de test
			if (tassert != 0 && tloc!=0) {
				
				tcmp =  Math.round((double )tloc/tassert * 10) / 10.0;
				System.out.println(tcmp);
				// ajouter les element a la liste
				temp.add(fichier);
				temp.add(pack);
				temp.add(cls);
				temp.add(Integer.toString(tloc));
				temp.add(Integer.toString(tassert));
				temp.add(String.valueOf(tcmp));
			
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return temp;

	}

	// trier une liste de liste de string selon une colonne prend en parametre la
	// liste et l index de la colonne
	public static void compareTlocTls(List<List<String>> temp, final int index) {
		Collections.sort(temp, new Comparator<List<String>>() {

			public int compare(List<String> o1, List<String> o2) {

				if (estDouble(o1.get(5)) == true) {
					double val1 = Double.parseDouble(o1.get(index));
					double val2 = Double.parseDouble(o2.get(index));
					return Double.compare(val2, val1);

				}

				else {

					int val1 = Integer.parseInt(o1.get(index));
					int val2 = Integer.parseInt(o2.get(index));
					return Integer.compare(val2, val1);
				}
			}
		});
	}

	// verifier si cest une instante de double prend en parametre un string (utile
	// pour la methode compare)
	public static boolean estDouble(String chaine) {
		try {
			double nombre = Double.parseDouble(chaine);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// trouver l'intersection
	// return une liste qui contient des liste de tous les instersection prend en
	// parametre deux double liste string et une seuil
	public static List<List<String>> findIntersection(List<List<String>> list1, List<List<String>> list2,
			double seuil) {
		List<List<String>> result = new ArrayList<>();
		int longueur = (int) Math.round(list1.size() * seuil -1 );
		
		if (longueur >= 0) {

			for (int i = 0; i <= longueur; i++) {
				for (int j = 0; j <= longueur; j++) {

					if (list1.get(i).equals(list2.get(j))) {

						if (!result.contains(list1.get(i))) {
							result.add(list1.get(i));
						}
					}

				}
			}
		}

		return result;
	}

}
