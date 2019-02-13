package collections;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/* Question:

 Write a program that reads a text file, specified by the first command line argument, into a List.
 The program should then print random lines from the file, the number of lines printed to be
 specified by the second command line argument. Write the program so that a correctly-sized
 collection is allocated all at once, instead of being gradually expanded as the file is read in.
 
 */

public class OracleQuestions3 {

    final static int AVG_LINE_SIZE = 80;

    public static void main(String[] args) {
        String fileName = args[0];
        int  numRandoms = Integer.parseInt(args[1]);
        int capacity = 0;

        Path filePath = Paths.get(fileName);


        try {
            capacity = (int)Files.size(filePath) / AVG_LINE_SIZE;
        } catch (IOException e) {
            System.out.println("Failed to get file size");
            System.exit(1);
        }

        List<String> lines = new ArrayList<>(capacity);

        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(filePath, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }


        } catch (IOException err) {
            System.out.println("Failed to read from file");
            System.exit(1);
        }

        Collections.shuffle(lines);
        for(String l: lines.subList(0, numRandoms)) {
            System.out.println("Line: " + l);
        }
    }
}
