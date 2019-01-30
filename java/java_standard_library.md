
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

Prior to the Java SE 7 release, the java.io.File class was the mechanism used for file I/O, but it had several drawbacks - not scalable, did not consitently throw exceptions.


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


### Method 1: Reading and Writing files by Slurping

Use the `readAllBytes(Path)` or `readAllLines(Path, Charset)` mehods

These should not be used for reading large files!

```
Path file = ...;
byte[] fileArray;
fileArray = Files.readAllBytes(file);
```

Or readling by lines:

```
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
```

### Method 2: Buffered IO for text files

Use the `newBufferedReader` and `Writer` methods:

```
Charset charset = Charset.forName("US-ASCII");
try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
    String line = null;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException x) {
    System.err.format("IOException: %s%n", x);
}
```

When writing, you can pass in a `OpenOption` arg to `newBufferedWriter` to control creation behaviour.

```
Charset charset = Charset.forName("US-ASCII");
String s = ...;
try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
    writer.write(s, 0, s.length());
} catch (IOException x) {
    System.err.format("IOException: %s%n", x);
}
```

### Method 3: Unbuffered Streams Interoperable with java.io classes

Use the `newOutputStream(Path, OpenOption...)` method.

Example:

```
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;

public class LogFileTest {

  public static void main(String[] args) {

    // Convert the string to a
    // byte array.
    String s = "Hello World! ";
    byte data[] = s.getBytes();
    Path p = Paths.get("./logfile.txt");

    try (OutputStream out = new BufferedOutputStream(
      Files.newOutputStream(p, CREATE, APPEND))) {
      out.write(data, 0, data.length);
    } catch (IOException x) {
      System.err.println(x);
    }
  }
}
```

## Method 4: Using Channel IO

Channel IO enables buffered reading and writing and also seeking.

The following two methods are available:
* `newByteChannel(Path, OpenOption...)`
* `newByteChannel(Path, Set<? extends OpenOption>, FileAttribute<?>...)`

These return  an instance of a `SeekableByteChannel`. With a default file system, you can cast this seekable byte channel to a FileChannel providing access to more advanced features such mapping a region of the file directly into memory for faster access, locking a region of the file so other processes cannot access it, or reading and writing bytes from an absolute position without affecting the channel's current position.

Example (Unix/POSIX only):

```
import static java.nio.file.StandardOpenOption.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;

public class LogFilePermissionsTest {

  public static void main(String[] args) {
  
    // Create the set of options for appending to the file.
    Set<OpenOption> options = new HashSet<OpenOption>();
    options.add(APPEND);
    options.add(CREATE);

    // Create the custom permissions attribute.
    Set<PosixFilePermission> perms =
      PosixFilePermissions.fromString("rw-r-----");
    FileAttribute<Set<PosixFilePermission>> attr =
      PosixFilePermissions.asFileAttribute(perms);

    // Convert the string to a ByteBuffer.
    String s = "Hello World! ";
    byte data[] = s.getBytes();
    ByteBuffer bb = ByteBuffer.wrap(data);
    
    Path file = Paths.get("./permissions.log");

    try (SeekableByteChannel sbc =
      Files.newByteChannel(file, options, attr)) {
      sbc.write(bb);
    } catch (IOException x) {
      System.out.println("Exception thrown: " + x);
    }
  }
}

```

### Creating empty files

Use `createFile(Path, FileAttribute<?>)` method.

Example:

```
Path file = ...;
try {
    // Create the empty file with default permissions, etc.
    Files.createFile(file);
} catch (FileAlreadyExistsException x) {
    System.err.format("file named %s" +
        " already exists%n", file);
} catch (IOException x) {
    // Some other sort of failure, such as permissions.
    System.err.format("createFile error: %s%n", x);
}
```

### Creating temp files

Use the `createTempFile(Path, String, String, FileAttribute<?>)` method.

Example:

```
try {
    Path tempFile = Files.createTempFile(null, ".myapp");
    System.out.format("The temporary file" +
        " has been created: %s%n", tempFile)
;
} catch (IOException x) {
    System.err.format("IOException: %s%n", x);
}

```

### Listing Directory Contents

Use the `newDirectoryStream(Path)` method.

This method returns an object that implements the `DirectoryStream` interface. The class that implements the DirectoryStream interface also implements `Iterable`.

Note the object is also a stream so must be propery closed, preferably with a try-with-resources block.

Exmaple:

```
Path dir = ...;
try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
    for (Path file: stream) {
        System.out.println(file.getFileName());
    }
} catch (IOException | DirectoryIteratorException x) {
    // IOException can never be thrown by the iteration.
    // In this snippet, it can only be thrown by newDirectoryStream.
    System.err.println(x);
}
```

You can also filter like:

```
DirectoryStream<Path> stream =
     Files.newDirectoryStream(dir, "*.{java,class,jar}")
```

### Walking directory tree

You need to implement a class with the `FileVisitor` interface.

It contains these methods that are all optional to implement:
* `preVisitDirectory` – Invoked before a directory's entries are visited.
* `postVisitDirectory` – Invoked after all the entries in a directory are visited. If any errors are encountered, the specific exception is passed to the method.
* `visitFile` – Invoked on the file being visited. The file's BasicFileAttributes is passed to the method, or you can use the file attributes package to read a specific set of attributes. For example, you can choose to read the file's DosFileAttributeView to determine if the file has the "hidden" bit set.
* `visitFileFailed` – Invoked when the file cannot be accessed. The specific exception is passed to the method. You can choose whether to throw the exception, print it to the console or a log file, and so on.

Instead of implementing the FileVisitor interface, you can extend the `SimpleFileVisitor` class. This class, which implements the FileVisitor interface, visits all files in a tree and throws an IOError when an error is encountered. You can extend this class and override only the methods that you require.

Then call with either of these

* `walkFileTree(Path, FileVisitor)`
* `walkFileTree(Path, Set<FileVisitOption>, int, FileVisitor)`

Behaviour such as early termination, skipping sub trees or files, can be controlled with the return codes of the interface methods.

### Watching for file changes

Java supports file change notification integration for filesystems that support it through the `WatchService` class.

See https://docs.oracle.com/javase/tutorial/essential/io/notification.html for further details.

### Get the default file system

```
PathMatcher matcher =
    FileSystems.getDefault().getPathMatcher("glob:*.*");
```

### Getting path seperator

```
String separator = FileSystems.getDefault().getSeparator();
```

### Getting root volumes (aka File Stores)


To retrieve a list of all the file stores for the file system, you can use the getFileStores method. This method returns an Iterable, which allows you to use the enhanced for statement to iterate over all the root directories.

```
for (FileStore store: FileSystems.getDefault().getFileStores()) {
   ...
}
```
If you want to retrive the file store where a particular file is located, use the getFileStore method in the Files class, as follows:

```
Path file = ...;
FileStore store= Files.getFileStore(file);
```

-------------------------------------------------------------------------------

## Collections

Collections are used to store, retrieve, manipulate, and communicate aggregate data. It contains interfaces, implmementations, and algorithms.

### Interfaces

```
                           Collection                              Map
                               |                                    |
 --------------------------------------------------                 |
 |               |             |                  |             SortedMap        
Set            List         Queue                Deque 
 |
SortedSet
```
* Collection - top level interface
* Set - can only contain unique items
* List - array-like sequence
* Queue - FIFO
* Deque - Can be either FIFO or LIFO
* Map   - Maps values to keys
* SortedSet - maintains elements in order
* SortedMap - keys are ordered

### The Collection interface

The `Collection` interface contains general methods:

* a 'conversion constructor' that enables you to convert a colletion of one subtype to another
* `int size()`
* `boolean isEmpty()`
* `boolean contains(Object element)`
* `boolean add(E element)` - returns true if the Collection changes as a result of the call
* `boolean remove(Object element)` - return true if the Collection was modified as a result.
* `Iterator<E> iterator()`

**Bulk operation methods**:

Bulk operations perform an operation on an entire Collection. You could implement these shorthand operations using the basic operations, though in most cases such implementations would be less efficient.


* `containsAll(Collection<?> c)` - returns true if the target Collection contains all of the elements in the specified Collection.
* `addAll(Collection<? extends E> c)`- adds all of the elements in the specified Collection to the target Collection.
* `boolean removeAll(Collection<?> c)` - removes from the target Collection all of its elements that are also contained in the specified Collection.
* `boolean retainAll(Collection<?> c)` - retains only those elements in the target Collection that are also contained in the specified Collection.
* `void clear()` - removes all elements from the Collection.
* `Object[] toArray()`
* `<T> T[] toArray(T[] a)`
* `Stream<E> stream()` (JDK8 and later) - for "aggregate operations"
* `Stream<E> parallelStream()` (JDK8 and later) - for "aggregate operations"


#### Traversal method 1: Aggregate Operations (JDK8 and later)

In JDK 8 and later, the preferred method of iterating over a collection is to obtain a stream and perform aggregate operations on it. Aggregate operations are often used in conjunction with lambda expressions.

```
myShapesCollection.stream()
   .filter(e -> e.getColor() == Color.RED)
   .forEach(e -> System.out.println(e.getName()));
```

OR if running on multiple cpu cores:

```
myShapesCollection.parallelStream()
   .filter(e -> e.getColor() == Color.RED)
   .forEach(e -> System.out.println(e.getName()));
```

#### Traversal method 2: for-each construct

```
for (Object o : collection)
    System.out.println(o);
```


#### Traversal method 3: Iterators

Note that Iterator.remove is the only safe way to modify a collection during iteration; the behavior is unspecified if the underlying collection is modified in any other way while the iteration is in progress.

Use Iterator instead of the for-each construct when you need to:

* Remove the current element. The for-each construct hides the iterator, so you cannot call remove. Therefore, the for-each construct is not usable for filtering.
* Iterate over multiple collections in parallel.

Example to remove an item:

```
static void filter(Collection<?> c) {
    for (Iterator<?> it = c.iterator(); it.hasNext(); )
        if (!cond(it.next()))
            it.remove();
}
```

#### Collections.singleton

Collections.singleton is a static factory method that returns an immutable `Set` containing only the specified element.

As a simple example of the power of bulk operations, consider the following idiom to remove all instances of a specified element, e, from a Collection, c.

```
c.removeAll(Collections.singleton(e));
```

More specifically, suppose you want to remove all of the null elements from a Collection.

```
c.removeAll(Collections.singleton(null));
```

#### Collection Interface Array Functions

The toArray methods are provided as a bridge between collections and older APIs that expect arrays on input. The array operations allow the contents of a Collection to be translated into an array.

For example, suppose that c is a `Collection`. The following snippet dumps the contents of c into a newly allocated array of Object whose length is identical to the number of elements in c.

```
Object[] a = c.toArray();
```

Suppose that c is known to contain only strings (perhaps because c is of type Collection<String>). The following snippet dumps the contents of c into a newly allocated array of String whose length is identical to the number of elements in c.

```
String[] a = c.toArray(new String[0]);
```

### The Set interface

A `Set` is a `Collection` that cannot contain duplicate elements. It models the mathematical set abstraction. 

The Set interface contains only methods inherited from Collection and adds the restriction that duplicate elements are prohibited. 

`Set` also adds a stronger contract on the behavior of the equals and hashCode operations, allowing `Set` instances to be compared meaningfully even if their implementation types differ. Two `Set` instances are equal if they contain the same elements.


#### Set implementations

* HashSet - uses hash table to store elements. Best performance but no guarantee about order.
* TreeSet - uses a red-black tree, orders its elements based on their values. Worst performance.
* LinkedHashSet - hash table with a linked list running through it, uses insertion order to order elements. Medium performance.

Always refer to the collection as `Set`, not one of the implementations, because
* you're guaranteed to use only the standard functions
* provides flexibility to change the implementation later just by changing the constructor



#### Set usage examples

Suppose you want to deduplicate a list of words:


Using JDK 8 Aggregate Operations:

```
import java.util.*;
import java.util.stream.*;

public class FindDups {
    public static void main(String[] args) {
        Set<String> distinctWords = Arrays.asList(args).stream()
        .collect(Collectors.toSet()); 
        System.out.println(distinctWords.size()+ 
                           " distinct words: " + 
                           distinctWords);
    }
}
```

Using the for-each Construct:

```
import java.util.*;

public class FindDups {
    public static void main(String[] args) {
        Set<String> s = new HashSet<String>();
        for (String a : args)
               s.add(a);
               System.out.println(s.size() + " distinct words: " + s);
    }
}
```

Here's a simple but useful Set idiom. Suppose you have a Collection, c, and you want to create another Collection containing the same elements but with all duplicates eliminated. 

```
Collection<Type> noDups = new HashSet<Type>(c);
```

Or, if using JDK 8 or later, you could easily collect into a Set using aggregate operations:

```
c.stream()
.collect(Collectors.toSet()); // no duplicates
```

Here's a slightly longer example that accumulates a Collection of names into a TreeSet (sorting it alphabetically):

```
Set<String> set = people.stream()
.map(Person::getName)
.collect(Collectors.toCollection(TreeSet::new));
```

And the following is a minor variant of the first idiom that preserves the order of the original collection while removing duplicate elements:

```
Collection<Type> noDups = new LinkedHashSet<Type>(c);
```

The following is a generic method that encapsulates the preceding idiom, returning a Set of the same generic type as the one passed.

```
public static <E> Set<E> removeDups(Collection<E> c) {
    return new LinkedHashSet<E>(c);
}
```

#### Set bulk operations aka set-algebrai operations

* `s1.containsAll(s2)` — returns true if s2 is a subset of s1
* `s1.addAll(s2)` - union
* `s1.retainAll(s2)` - intersection
* `s1.removeAll(s2)` - transforms s1 into the (asymmetric) set difference of s1 and s2.

To perform these operations without changing the collections, you need to copy them:

```
Set<Type> union = new HashSet<Type>(s1);
union.addAll(s2);

Set<Type> intersection = new HashSet<Type>(s1);
intersection.retainAll(s2);

Set<Type> difference = new HashSet<Type>(s1);
difference.removeAll(s2);
```





-------------------------------------------------------------------------------

## Common Interfaces

### java.io.Closeable

A Closeable is a source or destination of data that can be closed. The close method is invoked to release resources that the object is holding (such as open files).


### java.lang.Iterable

`public interface Iterable<T>`

Implementing this interface allows an object to be the target of the "for-each loop" statement. 

```
public interface Iterator<E> {
    boolean hasNext();
    E next();
    void remove(); //optional
}
```

