
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

Java uses the concept of a **stream** extensively to represent a sequence of items read from a source (producer) and written to a destination (consumer).

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
A program that uses character streams in place of byte streams automatically adapts to the local character set and is ready for internationalization.

The char stream readers and writers all inherit from the java.io.Reader/Writer classes.

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

### Token based IO

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

### Standard Input and Output

 defined automatically and do not need to be opened:
 
* System.out 
* System.in
* System.err

They're byte streams, not character streams.
'Out' and 'err' are`PrintStream` objects which have a internal character stream object and can be treated as character streams.
'In' is a `InputStream` object with no internal character stream object and needs to be wrapped in one:

InputStreamReader cin = new InputStreamReader(System.in);

### Data Streams

You can read and write binary data with the `DataInputStream` and `DataOutputStream` classes, which have methods to write out data like:
* writeDouble / readDouble
* writeUTF / readUTF

Example:

```
out = new DataOutputStream(new BufferedOutputStream(
              new FileOutputStream(dataFile)));

for (int i = 0; i < prices.length; i ++) {
    out.writeDouble(prices[i]);
    out.writeInt(units[i]);
    out.writeUTF(descs[i]);
}

```
### Object streams

Any object that implements the `Serializable` interface can be written to or read from streams.

The classes are `ObjectInputStream` and `ObjectOutputStream`. They have magic to traverse any referenced objects inside the objects being written, and to dedup multiple references.

## NIO.2

NIO.2 was introduced in Java SE 7 and is used for manipulating the file system.

### Path class

Primary entry point is the `Path` class, which represents a location in the filesystem.

```
import java.nio.file.*;

Path p1 = Paths.get("/tmp/foo");

//the Paths.get method is shorthand for the following:
Path p4 = FileSystems.getDefault().getPath("/users/sally");
```

It has some nifty methods:

* resolve(partial_path): combines two paths
* relativize(another_path): construct one path in relative terms to another
* equals(other_path): compares two paths
* beginsWith(path), endsWith(path): compares first and last segments of a path

`Path` implements the Iterable interface.


### Files class

For reading, writing, and manipulating files and directories. The Files methods work on instances of Path objects

All methods that access the file system can throw an IOException. It is best practice to catch these exceptions by embedding these methods into a try-with-resources statement, introduced in the Java SE 7 release. 

Exceptions are common when working with files so best practice is to always catch and handle exceptions when dealing with files.


Example:

```
try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
    writer.write(s, 0, s.length());
} catch (IOException x) {
    System.err.format("IOException: %s%n", x);
}
```

If you want to do it explicitly / traditionally, then use a finally block to close it manually:

```
...
} finally {
    if (writer != null) writer.close();
}
```


* `Files.exists(p)` - check if exists
* `Files.delete(p)` - delete file or dir
* `Files.copy(Path, Path, CopyOption...)`    - copies files or dirs. Takes vararg enum of REPLACE_EXISTING, COPY_ATTRIBUTES, NOFOLLOW_LINKS.
* Various `size(p)`, `isDirectory(p)`, `getLastModifiedTime(p)` methods to access stat-type info but if you're going to read multiple attributes at once, rather use the `readAttributes()` method.


To do a unix-style `stat` try:

```
Path file = ...;
PosixFileAttributes attr =
    Files.readAttributes(file, PosixFileAttributes.class);
System.out.format("%s %s %s%n",
    attr.owner().getName(),
    attr.group().getName(),
    PosixFilePermissions.toString(attr.permissions()));
```



-------------------------------------------------------------------------------

## Common Interfaces

### java.io.Closeable

A Closeable is a source or destination of data that can be closed. The close method is invoked to release resources that the object is holding (such as open files).

