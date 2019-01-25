package BasePart1;

/* Print out the size of a file.


Note that java doesn't have a concept of a stat().



 */

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FortyFiveSizeOfFile {

    public static void main(String[] args) {

        String filename = "hello.txt";
        Path path = FileSystems.getDefault().getPath("src/BasePart1", filename);
        long size = 0;
        try {
            size = Files.size(path);
        }
        catch(IOException e) {
            System.out.println("file does not exist");
            System.exit(1);
        }

        System.out.printf("[method 2] Size of [%s] is %d\n", filename, size);


        System.out.printf("[method 1] Size of [%s] is %d\n", filename,
                new File("src/BasePart1/hello.txt").length());
    }
}
