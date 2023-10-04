# tp1
vendredi 6 oct 2023
etudiant 1:
matricule :20102655
nom :zeinab taleb
etudiant 2:
matricule: 20159028
nom: Dana Seif Eddine

repositoire: https://github.com/Zeinab-Taleb/tp1

devoir 1 
ce devoir est compose de 5 partie independentes:

partie 1 :
creer la class Tcomp pour le fichier Tcomp.java
cette class permet de retourner le nombre de ligne de code qui ne sont pas vide ni en commentaire 
utilisation de la class ou fichier jar:
ce fichier est lance en ligne de commande : il faut donner en argument sur la ligne de commande un fichier qui comporte de code java. La sortie de ce code est sur le terminal 

partie 2:
creer la class Tassert  pour le fichier Tassert.java 
cette class permet de retourner le nombre de methode test (assert). 
utilisation de la class ou fichier jar:
ce fichier est lance en ligne de commande : il faut donner en argument sur la ligne de commande un fichier qui comporte de code test dans java. La sortie de ce code est sur le terminal.


partie 3:
creer la class Tls  pour le fichier Tls.java 
cette class permet de retourner des information sur un/des fichier/s java telque:
-le chemin vers le input
-nom du package
-nom de la class
-tcomp
-tassert
-tls =tcomp/tassert
sous l'un de deux forme :
-sois sur le terminal 
-sois dans un fichier csv
utilisation de la class ou fichier jar:
ce fichier est lance en ligne de commande : il faut donner en argument sur la ligne de commande :
  -o nom de ficheir csv (optionel) ce parmetre permet de sauvegarder les donne dans un ficheir csv 
  un fichier qui comporte de code test dans java. ou un dossier qui contient du test java
(en cas d'utilisation de fichier de sortie csv il faut fournir le fichier de sorti puis les fichier de test java
exemple: java .jar tls -o fichier.csv test.java)  
La sortie de ce code est sur le terminal ou dans un fichier csv


partie 4:
creer la class Tropcomp  pour le fichier Tropcomp.java 
cette class permet de fournir le top  des information sur un/des fichier/s java selon une seuil fourni pour l'intersection entre le tcomp et tls triers telque:
-le chemin vers le input
-nom du package
-nom de la class
-tcomp
-tassert
-tls =tcomp/tassert

sous l'un de deux forme :
-sois sur le terminal 
-sois dans un fichier csv
utilisation de la class ou fichier jar:
ce fichier est lance en ligne de commande : il faut donner en argument sur la ligne de commande :
  -o nom de ficheir csv (optionel) ce parmetre permet de sauvegarder les donne dans un ficheir csv 
  un fichier qui comporte de code test dans java. ou un dossier qui contient du test java
  -une seuil en dernier argument la seuil doit etre sous forme d'un entier (1-100) en dernier argument 
(en cas d'utilisation de fichier de sortie csv il faut fournir le fichier de sorti puis les fichier de test java
exemple: java .jar tropcomp -o fichier.csv test/   )  
La sortie de ce code est sur le terminal ou dans un fichier csv

partie5
java --> jar
test sur un code 
fichier de sortie csv pour trois seuil : 1 5 et 10
