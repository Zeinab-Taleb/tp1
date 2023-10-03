package ift3913;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TASSERT {

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

        int count = getTassert(file_path);
        // print number of lines containing assertion
        System.out.println(count);
    }

    public static int getTassert(String file_path) {
        int count = 0;

        try {
            FileReader fr = new FileReader(file_path);
            try (BufferedReader reader = new BufferedReader(fr)) {
                String line;
                // go through the file line by line
                while ((line = reader.readLine()) != null) {
                    // if the line contains an assertion
                    Pattern patternAssert = Pattern.compile("assert[a-zA-Z]+\\(");
                    Matcher mAssert = patternAssert.matcher(line);

                    if (mAssert.find() || line.contains("fail(")) {
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
