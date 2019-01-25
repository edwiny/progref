
# Standard Library

## Documentation sources

The official Java 8 SE API ref: https://docs.oracle.com/javase/8/docs/api/index.html

## Text parsing

`java.util.Scanner` class provides abilities to scan a input stream for tokens.


### Reading a string from stdin:

```
Scanner sc = new Scanner(System.in);

System.out.print("Enter a name: ");
String answer = sc.next();

```


-------------------------------------------------------------------------------

## IO

Java uses the concept of a **stream** extensively to represent a sequence of items read from a source (producer) and a destination (consumer).

Note: always make sure streams are explicitly closed or resource leaking will occur.



### Byte Streams

Lowest-level IO in Java. Generally avoid using it.

Example:

```
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

...

FileInputStream in = null;
FileOutputStream out = null;

try {
    in = new FileInputStream("xanadu.txt");
    out = new FileOutputStream("outagain.txt");
    int c;

    while ((c = in.read()) != -1) {
        out.write(c);
    }
} finally {
    if (in != null) {
        in.close();
    }
    if (out != null) {
        out.close();
    }
}
```

### Character Streams

Character stream I/O automatically translates the internal representation of characters to and from the local character set.
A program that uses character streams in place of byte streams automatically adapts to the local character set and is ready for internationalization

The char steam readers and writers all inherit from the java.io.Reader/Writer classes.

Example: 
```
import java.io.FileReader;       // these actually use FileInputStream for doing the io
import java.io.FileWriter;       //
import java.io.IOException;

# use exactly the same as the above byte stream example
# EXCEPT return value for read() is int, not byte
```


For **line based** IO, use BufferedReader and BufferredWriter:

```
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

public class CopyLines {
    public static void main(String[] args) throws IOException {

        BufferedReader inputStream = null;
        PrintWriter outputStream = null;

        try {
            inputStream = new BufferedReader(new FileReader("xanadu.txt"));
            outputStream = new PrintWriter(new FileWriter("characteroutput.txt"));

            String l;
            while ((l = inputStream.readLine()) != null) {
                outputStream.println(l);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
```

## Token based IO

You can use the Scanner class to break input down into a sequence of tokens aka logical units of specific types, ie. words, ints, floats, etc. NOTE it doesn't read the binary representation of all those types, but instead the textual representations.



```
import java.io.*;
import java.util.Scanner;

public class ScanXan {
    public static void main(String[] args) throws IOException {

        Scanner s = null;

        try {
            s = new Scanner(new BufferedReader(new FileReader("xanadu.txt")));

            while (s.hasNext()) {
                System.out.println(s.next());
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}
```

When reading numbers, *Locale* should be specified:

```
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Locale;

public class ScanSum {
    public static void main(String[] args) throws IOException {

        Scanner s = null;
        double sum = 0;

        try {
            s = new Scanner(new BufferedReader(new FileReader("usnumbers.txt")));
            s.useLocale(Locale.US);

            while (s.hasNext()) {
                if (s.hasNextDouble()) {
                    sum += s.nextDouble();
                } else {
                    s.next();
                }   
            }
        } finally {
            s.close();
        }

        System.out.println(sum);
    }
}
```


-------------------------------------------------------------------------------
