package ift3913;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TLOC {

    public static void main(String[] args) {
        String file_path = "";

        // if the file path is given in the command
        if (args.length > 0) {
            file_path = args[0];            
        } 
        // ask the user to input the file path
        else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter the file path: ");
            
            file_path = scanner.nextLine();
            scanner.close();
        }
        
        int count = getTLOC(file_path);
        // print number of non-empty / commented lines
        System.out.println(count);
    }

    public static int getTLOC(String file_path) {
        int count = 0;
        
        try {
            FileReader fr = new FileReader(file_path);
            try (BufferedReader reader = new BufferedReader(fr)) {
                String line;
                // go through the file line by line
                while ((line = reader.readLine()) != null) {
                    // if the line is not empty or doesn't start with a comment
                    if (!line.isEmpty() && !line.trim().startsWith("/") && !line.trim().startsWith("*")) {
                        count++;
                    }
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }
}