package ift3913;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class TLS {
    public static void main(String[] args) {
        Path folder_path;

        // if the folder path is given in the command
        if (args.length > 0) {
            folder_path = Paths.get(args[0]);
        }
        // ask the user to input the folder path
        else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter the file path: ");

            folder_path = Paths.get(scanner.nextLine());
            scanner.close();
        }
        
        getTLS(folder_path);
    }

    public static void getTLS(Path folder_path) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder_path)) {
            // go through the files in the folder
            for (Path file : stream) {
                String path = file.toString();
                
                String[] pkgAndCalss = getPackageAndClass(path);

                String packageName = pkgAndCalss[0];
                String className = pkgAndCalss[1];

                int tloc = TLOC.getTLOC(path);
                int tassert = TASSERT.getTassert(path);
                int tcmp = tloc / tassert;

                System.out.println(path + ", " + packageName + ", " + 
                    className + ", " + tloc + ", " + tassert + ", " + tcmp);            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getPackageAndClass(String file_path) {
        String packageName = "";
        String className = "";

        try {
            FileReader fr = new FileReader(file_path);
            try (BufferedReader reader = new BufferedReader(fr)) {
                String line;
                // go through the file line by line
                while ((line = reader.readLine()) != null) {
                    // if the line starts with package
                    if (line.startsWith("package")) {
                        packageName = line.split(" ")[1];
                    }
                    // if the line starts with public class
                    if (line.startsWith("public class")) {
                        className = line.split(" ")[2];
                    }

                    // if both package and class names are found then stop
                    if (!packageName.equals("") && !className.equals("")) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return package and class names
        String pkgAndClassArr[] = { packageName, className };
        return pkgAndClassArr;
    }
}
