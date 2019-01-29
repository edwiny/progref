package io;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Slurp {
    public static void main(String[] args) {

        List<String> lines ;
        try {
            lines = Files.readAllLines(Paths.get("src/io/testfile.txt"), StandardCharsets.UTF_8);
            for(String s: lines) {
                System.out.printf("Read line: [%s]\n", s);
            }
        } catch (java.io.IOException e) {
            System.out.printf("Could not read from file - %s\n", e.getMessage());
            System.exit(1);
        }
    }
}
