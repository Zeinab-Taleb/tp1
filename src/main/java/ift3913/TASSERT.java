package ift3913;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TASSERT {

    public static void main(String[] args) {
        int count = 0;
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
        try {
            FileReader fr = new FileReader(file_path);
            try (BufferedReader reader = new BufferedReader(fr)) {
                String line;
                // go through the file line by line
                while ((line = reader.readLine()) != null) {
                    // if the line contains an assertion
                    if (line.contains("assert")) {
                        count++;
                    }
                }
            // print number of lines containing assertion
            System.out.println(count);
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
