
# Standard Library

These notes are based off the Oracle JDK 1.8 documentation.

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
### sprintf

```
String result = String.format("%4d", i * j);
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

### Character Streams (Reader / Writer)

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

### Walking a directory tree

You need to implement a class with the `FileVisitor` interface.

It contains these methods that are all optional to implement:

* `preVisitDirectory` – Invoked before a directory's entries are visited.
* `postVisitDirectory` – Invoked after all the entries in a directory are visited. If any errors are encountered, the specific exception is passed to the method.
* `visitFile` – Invoked on the file being visited. The file's BasicFileAttributes is passed to the method, or you can use the file attributes package to read a specific set of attributes. For example, you can choose to read the file's DosFileAttributeView to determine if the file has the "hidden" bit set.
* `visitFileFailed` – Invoked when the file cannot be accessed. The specific exception is passed to the method. You can choose whether to throw the exception, print it to the console or a log file, and so on.

Instead of implementing the FileVisitor interface, you can extend the `SimpleFileVisitor` class. This class, which implements the FileVisitor interface, visits all files in a tree and throws an IOError when an error is encountered. You can extend this class and override only the methods that you require.

Then call with either of these:

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

* `HashSet` - uses hash table to store elements. Best performance but no guarantee about order.
* `TreeSet` - uses a red-black tree, orders its elements based on their values. Worst performance.
* `LinkedHashSet `- hash table with a linked list running through it, uses insertion order to order elements. Medium performance.
* `EnumSet` - specialised set for storing Enum values.

Always refer to the collection as `Set`, not one of the implementations, because
* you're guaranteed to use only the standard functions
* provides flexibility to change the implementation later just by changing the constructor


#### Immutable Sets

```
Set<String> emptySet = Set.of();
Set<String> persons = Set.of("A", "B", "C");
Set<Integer> numbers = Set.of(100, 200, 300, 400);
```

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

#### Set bulk operations aka set-algebraic operations

NOTE: these are methods on the Collection interface so could be done on all the sub types, not just Sets.

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

### The List interface

Lists may contain duplicate elements. In addition to the operations inherited from Collection, the List interface includes operations for the following:

* `get, set, add, addAll, and remove` - positional acccess
* `indexOf and lastIndexOf` - search for element and return index
* `listIterator` - iterators
* 'sublist' - range operations


To set at index:

```
list.set(pos, val);
```


#### List Implementations

* `ArrayList` - faster in most cases
* `LinkedList` - better under some? circumcstances



#### List operations

Inherited froom Collection interface:

* `remove` - always removes first occurrance of element
* `add` and `addAll` - always appends to end of list


Creating immutable lists from initialiser, use the `List.of()` static method:


```
List<String> emptyList = List.of();
List<String> names = List.of("John", "Joe", "Jack");
```

Another 2 ways to create a immutable list:

```
List<String> list = Arrays.asList("mon", "tue");

numbers = Collections.unmodifiableList(numbers);
```


Checking list equality:

```
Objects.equals(numbers, List.of(1, 2, 3));
```



Iterating:

List returns the regular `Iterator` but also offers a special `ListIterator` which can go backwards:

```
for (ListIterator<Type> it = list.listIterator(list.size()); it.hasPrevious(); ) {
    Type t = it.previous();
    ...
}
```
The list.size argument indicates where the iterator should start.

Range-view:

`subList(int fromIndex, int toIndex)` -  returns a List view of the portion of this list whose indices range from fromIndex, inclusive, to toIndex, exclusive.
Changes to elements in the view go through to the base list and vice versa.

You can use the sublist in any place you use the List.


The semantics of the List returned by subList become undefined if elements are added to or removed from the backing List in any way other than via the returned List. Thus, it's highly recommended that you use the List returned by subList only as a transient object

Most polymorphic algorithms in the Collections class apply specifically to List. Having all these algorithms at your disposal makes it very easy to manipulate lists. Here's a summary of these algorithms, which are described in more detail in the Algorithms section.


* `sort` — sorts a List using a merge sort algorithm, which provides a fast, stable sort. (A stable sort is one that does not reorder equal elements.)
* `shuffle` — randomly permutes the elements in a List.
* `reverse` — reverses the order of the elements in a List.
* `rotate` — rotates all the elements in a List by a specified distance.
* `swap` — swaps the elements at specified positions in a List.
* `replaceAll` — replaces all occurrences of one specified value with another.
* `fill` — overwrites every element in a List with the specified value.
* `copy` — copies the source List into the destination List.
* `binarySearch` — searches for an element in an ordered List using the binary search algorithm.
* `indexOfSubList` — returns the index of the first sublist of one List that is equal to another.
* `lastIndexOfSubList` — returns the index of the last sublist of one List that is equal to another.


### The Queue interface


A Queue is a collection for holding elements prior to processing. Besides basic Collection operations, queues provides:

* `add()`   - adds element unless exceeds bounds then will throw exception (`IllegalStateException`)
* `boolean offer()` - attempt to add an element but return special value if queue is bounded and full
* `element()` - returns but does not remove element at head - if empty will throw `NoSuchElementException`
* `peek()`   - returns but does not remove element at head - if empty will return spesh val
* `poll()`   - returns and removes element at head - will return special value if empty
* `remove()` - returns and removes element at head - will throw exception if empty

Each Queue method exists in two forms: (1) one throws an exception if the operation fails, and (2) the other returns a special value if the operation fails (either null or false, depending on the operation).

Type of operation | Throws exception | Returns special value
----------------- | ---------------- | --------------------- 
insert            | add(e)           | offer(e)
remove            | remove()         | poll()
examine           | element()        | peek()


Queue implementations generally do not allow insertion of null elements. The LinkedList implementation, which was retrofitted to implement Queue, is an exception. For historical reasons, it permits null elements, but you should refrain from taking advantage of this, because null is used as a special return value by the poll and peek methods.

#### Implementations

* `LinkedList` - implements the Queue interface
* `PriorityQueue` - An unbounded priority queue based on a priority heap

### The Deque Interface

 A double-ended-queue is a linear collection of elements that supports the insertion and removal of elements at both end points. 
 It implements both the Stack and Queue interfaces.

 Implementations:

 * `ArrayDeque`
 * `LinkedList`

#### Deque Operations

**Insert**

* `addFirst` - add at head and throw exception if bounded and full
* `offerFirst` - add at head but don't throw exception if bounded and full
* `addLast` - add to end and throw exception if bounded and full
* 'offerLast' - obvious

**Remove**

* `removeFirst` - throws exception if empty
* `pollFirst` - returns null if empty
* `removeLast` - throws exception if empty
* `pollLast`  - returns null if empty

**Retrieve**

Get but don't remove elements.

* `getFirst` - throws exception if deque is empty
* `peekFirst`
* `getLast` - throws excepion if deque is empty
* `peekLast` 

### The Map interface

A Map is an object that maps keys to values. A map cannot contain duplicate keys: Each key can map to at most one value. It models the mathematical function abstraction. 

#### Map implementations

Same as with Set implementations:

* `HashMap` - best performance but no ordering
* `TreeMap` - worst performance but retains ordering
* `LinkedHashMap` - keeps insertion order at medium performance

#### Map operations

* `V put(K key, V value)` -
* `<E> get(Object key)`  - 
* `default V getOrDefault(Object key, V defaultValue)` 
* `containsKey(Object)`    -
* `containsValue(Object)`  -
* `size`     -
* `isEmpty`  -

Bulk operations
* `clear`    - removes everything
* `putAll`   - adds one map to another. useful to provide defaut mappings.


Collection view methods (allow parts of the Map to be treated as a Collection):

* `keySet` - the `Set` of keys contained in the Map.
* `values` - The `Collection` of values contained in the `Map`. This `Collection` is not a `Set`, because multiple keys can map to the same value.
* `entrySet` - the `Set` of key-value pairs contained in the `Map`. The `Map` interface provides a small nested interface called `Map.Entry`, the type of the elements in this `Set`.

The Collection views support element removal in all its many forms — remove, removeAll, retainAll, and clear operations, as well as the Iterator.remove operation. 
However you can't add new elements via the Collection views.

You can also use the Bulk Operations of the Collection views to do map algebra:

Checking if one map is contained by another:

```
if (m1.entrySet().containsAll(m2.entrySet())) {
    ...
}
```

or check if two maps contain mappings for the same keys:

```
if (m1.keySet().equals(m2.keySet())) {
    ...
}
```

or get the common keys between two maps:

```
Set<KeyType>commonKeys = new HashSet<KeyType>(m1.keySet());
commonKeys.retainAll(m2.keySet());
```




#### Iterating Map entries

Can only do so by using the `Collection` view methods:

```
for (KeyType key : m.keySet())
    System.out.println(key);
```

or using a `Iterator`:


```
for (Iterator<Type> it = m.keySet().iterator(); it.hasNext(); )
    if (it.next().isBogus())
        it.remove();
```


iterating over the entries:

```
//since java 10, use var to let compiler figure out type
for (var entry: map.entrySet()) {
    System.out.println(e.getKey() + ": " + e.getValue());
}
```

Prior to Java 10:


```
for (Map.Entry<KeyType, ValType> e : m.entrySet())
    System.out.println(e.getKey() + ": " + e.getValue());

```


#### Map examples

Print frequency of words
```
import java.util.*;

public class Freq {
    public static void main(String[] args) {
        Map<String, Integer> m = new HashMap<String, Integer>();

        // Initialize frequency table from command line
        for (String a : args) {
            Integer freq = m.get(a);
            m.put(a, (freq == null) ? 1 : freq + 1);
        }

        System.out.println(m.size() + " distinct words:");
        System.out.println(m);
    }
}
```

Making a copy of a Map using the *conversion constructor*:

```
Map<K, V> copy = new HashMap<K, V>(m);
```

Using aggregate operations:

```
// Group employees by department
Map<Department, List<Employee>> byDept = employees.stream()
.collect(Collectors.groupingBy(Employee::getDepartment));
```

```
// Compute sum of salaries by department
Map<Department, Integer> totalByDept = employees.stream()
.collect(Collectors.groupingBy(Employee::getDepartment,
Collectors.summingInt(Employee::getSalary)));
```

```
 Classify Person objects by city
Map<String, List<Person>> peopleByCity
         = personStream.collect(Collectors.groupingBy(Person::getCity));
```

### Sorting objects in Collections

All native types implements the `Comparable` interface and can thus be sorted:

```
Collections.sort(myList);
```

This would sort the objects into that class' 'natural order.'

To sort by a different order (e.g. employee age rather than mame), create a comparison class
that implements the `Comparator` interface. It works just like `Comparable` but takes as arguments
two objets to be sorted in stead of one. Example:

```
import java.util.*;
public class EmpSort {
    static final Comparator<Employee> SENIORITY_ORDER = 
                                        new Comparator<Employee>() {
            public int compare(Employee e1, Employee e2) {
                int dateCmp = e2.hireDate().compareTo(e1.hireDate());
                if (dateCmp != 0)
                  return dateCmp;     

                return (e1.number() < e2.number() ? -1 : (e1.number() == e2.number() ? 0 : 1));
            }
    };

    // Employee database
    static final Collection<Employee> employees = ... ;

    public static void main(String[] args) {
        List<Employee> e = new ArrayList<Employee>(employees);
        Collections.sort(e, SENIORITY_ORDER);
        System.out.println(e);
    }
}
```

A inline example:

```
// Sort anagram groups according to size
Collections.sort(winners, new Comparator<List<String>>() {
    public int compare(List<String> o1, List<String> o2) {
        return o2.size() - o1.size();
    }});

```

Note that, when implementing Comparators,  ensure it produces an ordering that is compatible with `equals`. In other words, tweak it so that the only elements seen as equal when using compare are those that are also seen as equal when compared using `equals`.

### The SortedSet interface

A SortedSet is a Set that maintains its elements in ascending order, sorted according to the elements' natural ordering or according to a `Comparator` provided at SortedSet creation time. In addition to the normal Set operations, the SortedSet interface provides operations for the following:

* Range view — allows arbitrary range operations on the sorted set
* Endpoints — returns the first or last element in the sorted set
* Comparator access — returns the Comparator, if any, used to sort the set

The interface looks like this:

```
public interface SortedSet<E> extends Set<E> {
    // Range-view
    SortedSet<E> subSet(E fromElement, E toElement);
    SortedSet<E> headSet(E toElement);
    SortedSet<E> tailSet(E fromElement);

    // Endpoints
    E first();
    E last();

    // Comparator access
    Comparator<? super E> comparator();
}
```
The base `Set` class' methods works as normal except that:
* The `Iterator` returned by the iterator operation traverses the sorted set in order.
* The array returned by `toArray` contains the sorted set's elements in order.


Range-views on SortedSets are useful because they retain their validity even if the underlying sets are modified (unlike with Lists).

For `subSet(From, to)` the returned subset will contain all elements equal to or greater than the first parameter, and smaller than the second parameter, according to the sort order used by the SortedSet.

You also use objects from the sets to indicate the endpoints, not indices.

Same examples of using range-views:

```
// how many words between doorbell and pickle in a dictionary:
int count = dictionary.subSet("doorbell", "pickle").size();
```

```
// removes all the elements beginning with the letter f.

dictionary.subSet("f", "g").clear();
```

### The SortedMap interface

A `SortedMap` is a `Map` that maintains its entries in ascending order, sorted according to the keys' natural ordering, or according to a `Comparator` provided at the time of the SortedMap creation. 

The interface looks like this:

```
public interface SortedMap<K, V> extends Map<K, V>{
    Comparator<? super K> comparator();
    SortedMap<K, V> subMap(K fromKey, K toKey);
    SortedMap<K, V> headMap(K toKey);
    SortedMap<K, V> tailMap(K fromKey);
    K firstKey();
    K lastKey();
}
```

Like with `SortedSet`, the operations `SortedMap` inherits from `Map` behave identically on sorted maps and normal maps with two exceptions:

* The `Iterator` returned by the iterator operation on any of the sorted map's Collection views traverse the collections in order.
* The arrays returned by the Collection views' `toArray` operations contain the keys, values, or entries in order.

Because this interface is a precise Map analog of SortedSet, all the idioms and code examples in The SortedSet Interface section apply to SortedMap with only trivial modifications.


### Collection implementations

* `Set` - `HashSet` 
    * Peformance: constant-time
    * Good general purpose implemention
    * Wrong iniital bucket size / capacity (default 16) can cause overheads.  Pick a number that's about twice the size to which you expect the set to grow

* `Set` - `TreeSet`
    * Performance: log-time
    * Value-ordered iteration
* `Set` - `LinkedHashSet` 
    * Performane: nearly as fast as `HashSet`
    * Insertion-order iteration
* `Set` - `EnumSet`
    * High performance for Enums
    * Internally uses a bit vector.
    * Supports range operations
* `Set` - `CopyOnWriteArraySet`
    * Mutative operations makes a new copy, so super thread safe
    * Mutative operations require time proportional to the size
    * Appropriate for sets often iterated but seldom changed, like event handlers with no duplication
* `List` - `ArrayList`
    * Performance: constant time
    * General purpose
    * Performance may be bad if you frequently insert items at the start, or deleting from the middle.
* `List` - `LinkedList`
    * Performance is better for insertion at the start or deletion from the middle (constant-time)
    * Performance is worse for position access (linear time).
* `List` - `CopyOnWriteArrayList`
    * Super thread safe
    * Use when change is infrequent, and traversal is frequent and potentially time-consuming.
* `Map` - `HashMap`
    * Performance: maximum speed
    * No ordering
* `Map` - `TreeMap`
    * Performance: lowest
    * Key based iteration order
* `Map` - `LinkedHashMap`
    * Performance: almost as good as HashMap
    * Insertion based order
    * Can double as cache - Can override ordering policy to order based on key access, with options to remove oldest entries (`removeEldestEntry`)
* `Map` - `EnumMap`
    *  If you want to map an enum to a value
    * internally implemented as array
* `Map` - `WeakHashMap`
    *  allows a key-value pair to be garbage-collected when its key is no longer referenced outside of the WeakHashMap
* `Map` - `IdentityHashMap`
    * useful for topology-preserving object graph transformations, such as serialization or deep-copying. To perform such transformations, you need to maintain identity-based "node table" that keeps track of which objects have already been seen. 
    * performance: good
* `Map` - `ConcurrentHashMap`
    * highly concurrent, high-performance. Never blocks on retrievals
    * drop-in replacement for `Hashtable`
* `Queue` - `LinkedList`
    * LinkedList implements the Queue interface.
* `Queue` - `PriorityQueue` 
    * priority queue based on the heap data structure
    * orders elements according to the order specified at construction time, which can be the elements' natural ordering or the ordering imposed by an explicit Comparator.
    * poll, remove, peek, and element — access the element at the head of the queue. The head of the queue is the least element with respect to the specified ordering. If multiple elements are tied for least value, the head is one of those elements; ties are broken arbitrarily.
* `BlockingQueue` - `LinkedBlockingQueue`
    * optionally bounded FIFO blocking queue backed by linked nodes
* `BlockingQueue` - `ArrayBlockingQueue`
    * a bounded FIFO blocking queue backed by an array
* `BlockingQueue` - `PriorityBlockingQueue`
    * an unbounded blocking priority queue backed by a heap
* `BlockingQueue` - `DelayQueue`
    * a time-based scheduling queue backed by a heap
* `BlockingQueue` - `SynchronousQueue`
    * a simple rendezvous mechanism that uses the BlockingQueue interface
* `TransferQueue` - `LinkedTransferQueue`
    * a specialized BlockingQueue in which code that adds an element to the queue has the option of waiting (blocking) for code in another thread to retrieve the element.
* `Deque` - `LinkedList`
    * Implements the Deque interface
    * Gppd general purpose
* `Deque` - `ArrayDeque`
    * Also a good general purpose implementation
    * Resizable array implmentation
    * More efficient than linkedlist for adding and removing at the ends, also less memory intensive.
    * Supports 
* `Deque` - `LinkedBlockingQueue`
    * For concurrent acccess
    * Will block on `takeFirst` or `takeLast` if deque is empty.

### Collection Wrappers 

Wrapper implementations delegate all their real work to a specified collection but add extra functionality on top of what this collection offers. For design pattern fans, this is an example of the decorator pattern.

#### Synchronisation Wrappers

Adds automatic synchronization (thread-safety) to an arbitrary collection. Each of the six core collection interfaces — Collection, Set, List, Map, SortedSet, and SortedMap — has one static factory method. e.g.

* `public static <T> Collection<T> synchronizedCollection(Collection<T> c);`
* `public static <T> Set<T> synchronizedSet(Set<T> s);`
* `public static <T> List<T> synchronizedList(List<T> list);`
* `public static <K,V> Map<K,V> synchronizedMap(Map<K,V> m);`
* `public static <T> SortedSet<T> synchronizedSortedSet(SortedSet<T> s);`
* `public static <K,V> SortedMap<K,V> synchronizedSortedMap(SortedMap<K,V> m);`

All access needs to go through the returned collection, none through the backing collection. Best is to discard the reference to the backing collection, e.g.:

```
List<Type> list = Collections.synchronizedList(new ArrayList<Type>());
```

**NB** when iterating over the collection you must manually synchronise your iteration like this:

```
Collection<Type> c = Collections.synchronizedCollection(myCollection);
synchronized(c) {
    for (Type e : c)
        foo(e);
}
```
One minor downside of using wrapper implementations is that you do not have the ability to execute any noninterface operations of a wrapped implementation. 

#### Unmodifiable Wrappers

Makes a collection immutable, by throwing an exception (`UnsupportedOperationException`) if there are any write attempts. Also create via factory methods:

* `public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> c);`
* `public static <T> Set<T> unmodifiableSet(Set<? extends T> s);`
* `public static <T> List<T> unmodifiableList(List<? extends T> list);`
* `public static <K,V> Map<K, V> unmodifiableMap(Map<? extends K, ? extends V> m);`
* `public static <T> SortedSet<T> unmodifiableSortedSet(SortedSet<? extends T> s);`
* `public static <K,V> SortedMap<K, V> unmodifiableSortedMap(SortedMap<K, ? extends V> m);`

#### Checked Interface Wrappers

Provides runtime type safety by throwing an exception when a unexpected type is added to the collection (that's because templates / generics are compile time only.)

### Convenience Collection Implementations

These mini-implementations  can be more convenient and more efficient than general-purpose implementations when you don't need their full power. All the implementations in this section are made available via static factory methods rather than public classes.

#### List view of an array

`Arrays.asList()`

* Changes to the List write through to the array and vice versa.
* The size of the collection is that of the array and cannot be changed.
* If the add or the remove method is called on the List, an `UnsupportedOperationException` will result.

This is normally used as a bridge between arrays and collections.

It's also useful to create fixed size lists in a efficient implementation:

```
List<String> list = Arrays.asList(new String[size]);
```

Can also use it as initialiser list:

```
List<Person> roster = new ArrayList<Person>(
        Arrays.asList(
                new Person("Mary", Person.Sex.FEMALE),
                new Person( "Sue", Person.Sex.FEMALE),
                new Person("Ian", Person.Sex.MALE)
        )
);
```

#### Immutable Multiple-Copy List

You can create an immutable list containing copies of the same element.
Useful if you want to initialise a collection to some value. Examples:

```
List<Type> list = new ArrayList<Type>(Collections.nCopies(1000, (Type)null);
```

```
lovablePets.addAll(Collections.nCopies(69, "fruit bat"));
```
#### Immutable Singleton Set

Sometimes you'll need an immutable singleton Set, which consists of a single, specified element.

One use of this implementation is to remove all occurrences of a specified element from a Collection.

```
c.removeAll(Collections.singleton(e));
```

One more use of this implementation is to provide a single input value to a method that is written to accept a collection of values.

#### Empty Set, List, and Map Constants

When you want to pass empty arguments to a function expecting a collection:

The `Collections` class provide methods `emptySet`, `emptyList`, `emptyMap`. 

### Aggregate Operations on Collections

A pipeline is a sequence of aggregate operations.

A stream is a sequence of elements. Unlike a collection, it is not a data structure that stores elements. Instead, a stream carries values from a source through a pipeline. 

Compare with stream approach vs for-each approach:

```
roster
    .stream()
    .filter(e -> e.getGender() == Person.Sex.MALE)
    .forEach(e -> System.out.println(e.getName()));
```

Compare this example to the following that prints the male members contained in the collection roster with a for-each loop:

```
for (Person p : roster) {
    if (p.getGender() == Person.Sex.MALE) {
        System.out.println(p.getName());
    }
}
```

The pipeline contains these components:
* **A source**: This could be a collection, an array, a generator function, or an I/O channel
* **Zero or more intermediate operations**: An intermediate operation, such as filter, produces a new stream.
* **A terminal operation**. A terminal operation, such as `forEach`, produces a non-stream result, such as a primitive value (like a double value), a collection, or in the case of forEach, no value at all.


Example:

```
double average = roster
    .stream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .mapToInt(Person::getAge)
    .average()
    .getAsDouble();
```

The `mapToInt` operation returns a new stream of type `IntStream`. The operation applies the function specified in its parameter to each element in a particular stream. In this example, the function is `Person::getAge,` which is a *method reference* that returns the age of the member. (Alternatively, you could use the lambda expression `e -> e.getAge()`.) Consequently, the `mapToInt` operation in this example returns a stream that contains the ages of all male members in the collection roster.

The average operation calculates the average value of the elements contained in a stream of type IntStream. It returns an object of type `OptionalDouble`. If the stream contains no elements, then the average operation returns an empty instance of OptionalDouble, and invoking the method getAsDouble throws a NoSuchElementException. The JDK contains many terminal operations such as average that return one value by combining the contents of a stream. 

#### Differences Between Aggregate Operations and Iterators

Aggregate operations such as `forEach` appears to be an iterator but:

* They use internal iteration - With internal delegation, your application determines what collection it iterates, but the JDK determines how to iterate the collection. With external iteration, your application determines both what collection it iterates and how it iterates it. However, external iteration can only iterate over the elements of a collection sequentially. Internal iteration does not have this limitation. It can more easily take advantage of parallel computing
* They process elements from a stream - Aggregate operations process elements from a stream, not directly from a collection.
* They support behavior as parameters - You can specify lambda expressions as parameters for most aggregate operations.


#### Aggregate Reduction

JDK provides many single value producing termination functions such as `sum()`, `average()`, `min()`, and `count()`.  
There are also 2 special reduction operations that are general purpose:

Note that both these functions return a single value.

**stream.reduce()**

Consider examples:

Traditional method:

```
Integer totalAge = roster
    .stream()
    .mapToInt(Person::getAge)
    .sum();
```

using general-purpose `reduce()`:

```
Integer totalAgeReduce = roster
   .stream()
   .map(Person::getAge)
   .reduce(
       0,
       (a, b) -> a + b);
```

The reduce operation in this example takes two arguments:

* **identity**: The identity element is both the initial value of the reduction and the default result if there are no elements in the stream. 
* **accumulator**: The accumulator function takes two parameters: a partial result of the reduction (in this example, the sum of all processed integers so far) and the next element of the stream (in this example, an integer). It returns a new partial result. 

The reduce operation always returns a new value.

**stream.collect()**

Unlike the `reduce` method, which always creates a new value when it processes an element, the `collect` method modifies, or mutates, an existing value.

The `collect` method has these args:

* `supplier` - factory method that creates new instances of the result container. It's a lambda expression (or a method reference) in stead of value like with `reduce`. It will ultimately determine the return type of the collect method.
* `accumulator` - incorporates a stream element into a result container. (e.g. if you were averaging, it would add one value to the count and sum). No return value.
* `combiner` - combines two result objects - used for parallelism. No return value.

Here is an example:

```
class Averager implements IntConsumer
{
    private int total = 0;
    private int count = 0;
        
    public double average() {
        return count > 0 ? ((double) total)/count : 0;
    }
        
    public void accept(int i) { total += i; count++; }
    public void combine(Averager other) {
        total += other.total;
        count += other.count;
    }
}
// The following pipeline uses the Averager class and the collect method to calculate the average age of all male members:

Averager averageCollect = roster.stream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .map(Person::getAge)
    .collect(Averager::new, Averager::accept, Averager::combine);
                   
System.out.println("Average age of male members: " +
    averageCollect.average());
```

Another way to use it is to use the `Collectors` class that encapsulates the supplier, accumulator, and combiner functionality.
The Collectors class contains many useful reduction operations, such as accumulating elements into collections and summarizing elements according to various criteria. These reduction operations return instances of the class Collector, so you can use them as a parameter for the collect operation.

Example to build a list of male employee names:

```
List<String> namesOfMaleMembersCollect = roster
    .stream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .map(p -> p.getName())
    .collect(Collectors.toList());
```

Group by gender:

```
Map<Person.Sex, List<Person>> byGender =
    roster
        .stream()
        .collect(
            Collectors.groupingBy(Person::getGender));
```
You can use a **multilevel reduction** to perform more complex queries. This is where you run another `collect` inside the `collect`.
Use the `groupingBy` operation to make a multilevel reduction. It takes two parameters:
* a classification function 
* an instance of Collector. 

The Collector parameter is called a *downstream collector*. This is a collector that the Java runtime applies to the results of another collector. Consequently, the groupingBy operation enables you to apply a collect method to the List values created by the groupingBy operator. 

This example applies the collector `mapping`, which applies the mapping function `Person::getName` to each element of the stream. Consequently, the resulting stream consists of only the names of members:

```
Map<Person.Sex, List<String>> namesByGender =
    roster
        .stream()
        .collect(
            Collectors.groupingBy(
                Person::getGender,                      
                Collectors.mapping(
                    Person::getName,
                    Collectors.toList())));
```

The groupingBy method also offers a `reduction` operation:

```
Map<Person.Sex, Integer> totalAgeByGender =
    roster
        .stream()
        .collect(
            Collectors.groupingBy(
                Person::getGender,                      
                Collectors.reducing(
                    0,
                    Person::getAge,
                    Integer::sum)));

```
The `reducing` method takes the same arguments as the `Stream.reduce` method:

* `identity`: Like the Stream.reduce operation, the identity element is both the initial value of the reduction and the default result if there are no elements in the stream. In this example, the identity element is 0; this is the initial value of the sum of ages and the default value if no members exist.
* `mapper`: The reducing operation applies this mapper function to all stream elements. In this example, the mapper retrieves the age of each member.
* `operation`: The operation function is used to reduce the mapped values. In this example, the operation function adds Integer values.

Here is a more basic example:

```
Map<Person.Sex, Double> averageAgeByGender = roster
    .stream()
    .collect(
        Collectors.groupingBy(
            Person::getGender,                      
            Collectors.averagingInt(Person::getAge)));

```
#### Parallelism

Collections are not thread-safe! However it does provide synchronisation wrappers. Aggregate operations and parallel streams enable you to implement parallelism with non-thread-safe collections provided that you do not modify the collection while you are operating on it.

Streams can be either serial (default) or parallel, which can be created by one of these methods:

* `Collection.parallelStream`
* `BaseStream.parallel` 

E.g.

```
double average = roster
    .parallelStream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .mapToInt(Person::getAge)
    .average()
    .getAsDouble();
```
You can also parallelise the groupBy functionality:


```
ConcurrentMap<Person.Sex, List<Person>> byGender =
    roster
        .parallelStream()
        .collect(
            Collectors.groupingByConcurrent(Person::getGender));
```

Note the ConcurrentMap return type.

This is called a concurrent reduction. The Java runtime performs if all of these are true for a collect operation:

* The stream is parallel.
* The parameter of the collect operation, the collector, has the characteristic `Collector.Characteristics.CONCURRENT`. 
* Either the stream is unordered, or the collector has the characteristic `Collector.Characteristics.UNORDERED`. 

**Note about ordering**

* The stream operations have their internal iteration mechanism and doesn't use the underlying Collection's ordering.
* Executing in parallel may change the ordering.
* You can force ordering with a `forEachOrdered` but you're likely to loose parallelism:

```
listOfIntegers
    .parallelStream()
    .forEachOrdered(e -> System.out.print(e + " "));
```
#### Side effects

Operations like `forEach` and `peek` are designed for side effects; 

#### Things to watch out for in parallel stream operations

* Side effect functions can be executed concurrently from multiple threads, could have unintended consequences.
* Laziness - intermediate operations may not execute until the termination command
* Interference - mutating state in lambda expressions passed to collect or reduce will have unintended consequences.
* Stateful lambdas - may execute in parallel so make sure the lambda does not depend on state other than what is passed in

### Common Collection Algorithms

* There's a bunch of polymorphic algorithms provided by the JDK
* Static functions in the `Collectoins` class
* Most operate on `List`



#### Sorting

Uses a merge sort algorithm - runs in log(n) time.
It's stable meaning it won't reorder equal elements.

Example:

```
import java.util.*;

public class Sort {
    public static void main(String[] args) {
        List<String> list = Arrays.asList(args);
        Collections.sort(list);
        System.out.println(list);
    }
}
```
Can also use `Comparators` - see Sorting objects in Collections section in these notes.

#### Searching

`Collections.binarySearch(list, key)` returns the index into the list where the key was found, or (-(insertion point) - 1). E.g.:

```
int pos = Collections.binarySearch(list, key);
if (pos < 0)
   l.add(-pos-1, key);
```

#### Other functions

The `Collections` class also provides these static functions:


* `frequency` - counts the number of times a specified items occurs in a collection
* `disjoint` - checks if there are no common elements between two lists
* `reverse` - reverses the order of the elements in a List
* `fill` - overwrites every value in a list with the specified element
* `copy` - copies one list to another. Only copies as much as the destination can hold without resizing it.
* `swap` -  swap two elements
* `addAll` - bulk add to another collection

### Extending Collections

You would need to implement a class that extends one of these abstract classes. They already provide a lot of functionality for you.

* `AbstractCollection` — a Collection that is neither a Set nor a List. At a minimum, you must provide the iterator and the size methods.
* `AbstractSet` — a Set; use is identical to AbstractCollection.
* `AbstractList` — a List backed up by a random-access data store, such as an array. At a minimum, you must provide the positional access methods (get and, optionally, set, remove, and add) and the size method. The abstract class takes care of listIterator (and iterator).
* `AbstractSequentialList` — a List backed up by a sequential-access data store, such as a linked list. At a minimum, you must provide the listIterator and size methods. The abstract class takes care of the positional access methods. (This is the opposite of AbstractList.)
* `AbstractQueue` — at a minimum, you must provide the offer, peek, poll, and size methods and an iterator supporting remove.
* `AbstractMap` — a Map. At a minimum you must provide the entrySet view. This is typically implemented with the AbstractSet class. If the Map is modifiable, you must also provide the put method.

### Design tips for using Collections in APIs


**Method arguments**
Always use the abstract type in public facing method arguments, never the implementation types.

Further, you should always use the least-specific type that makes sense. For example, don't require a List or a Set if a Collection would do. It's not that you should never require a List or a Set on input; it is correct to do so if a method depends on a property of one of these interfaces. For example, many of the algorithms provided by the Java platform require a List on input because they depend on the fact that lists are ordered. As a general rule, however, the best types to use on input are the most general: Collection and Map.

**Return types**
In one sense, return values should have the opposite behavior of input parameters: It's best to return the most specific applicable collection interface rather than the most general. The user can decide if they want to keep it specific or past a generic interface to it.


-------------------------------------------------------------------------------
## Configuration

### Properties and System Properties

The `java.util.Properties` class provides a way to read and write application config.

Typically, you provide a default set of properties that will be used to "fall back" to
if a property cannot be find in the main properties file (can be specified in the 
Constructor.)




`Properties` extends `java.util.Hashtable`. It has these methods:

* `load(Reader) / store(Writer, String)` - read/write from/to streams
* `String  getProperty(String key, String defaultValue)` - gets property, forcing use of Strings
* `setProperty(String key, String value)` - sets property, forcing use of Strings
* `Set<String>  stringPropertyNames()` - returns the list of property keys including that of the default list.
* methods inherited from `java.util.Hashtable`: `get()`, `forEach()`, `keys()`, `put()`



Care should be taken to always use only the `String` versions of methods and args.

Example of loading in properties files:


```
. . .
// create and load default properties
Properties defaultProps = new Properties();
FileInputStream in = new FileInputStream("defaultProperties");
defaultProps.load(in);
in.close();

//saving props

FileOutputStream out = new FileOutputStream("appProperties");
applicationProps.store(out, "---No Comment---");
out.close();


```

The java runtime environment maintains a couple of **system properties**, e.g.:

* `"file.separator"`
* `"java.class.path"`
* `"os.name"`
* `"user.home"`

Values can be read in one of 2 two ways:

* `System.getProperty("some.property", "default value");`
* `getProperties` method, which returns a `Properties` object.


### Environment variables

`System.getenv()` returns a `Map` of env vars:


```
import java.util.Map;

public class EnvMap {
    public static void main (String[] args) {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                              envName,
                              env.get(envName));
        }
    }
}

```

### Command line arguments

* `args[0]` contains the first argument, not the name of the program.
* Use `args.length` to determine number of args



## System operations

### Security Manager

If no security manager this will return null:

```SecurityManager appsm = System.getSecurityManager();```

Applications can check their acccess via `checkxxx()` methods.

Functions that don't declare they throw it can generate security exceptions.

### Misc System functions

* `System.currentTimeMillis`
* `System.nanoTime` - these two returns timing info - dependent on OS
* `System.exit` - exit program


-------------------------------------------------------------------------------
## Optional return values


Can contain either a value of a given type or nothing. Use of Optional is best practice as opposed to using null return values, in order to limit application errors due to unchecked null references.

In stead of:

```
String version = "UNKNOWN";
if(computer != null){
  Soundcard soundcard = computer.getSoundcard();
  if(soundcard != null){
    USB usb = soundcard.getUSB();
    if(usb != null){
      version = usb.getVersion();
    }
  }
}
```

You can use:

```
String name = computer.flatMap(Computer::getSoundcard)
                          .flatMap(Soundcard::getUSB)
                          .map(USB::getVersion)
                          .orElse("UNKNOWN");
```

### Creating optionals

Creating empty Optional:

```
Optional<Soundcard> sc = Optional.empty();
```

Creating with a value:

```
SoundCard soundcard = new Soundcard();
Optional<Soundcard> sc = Optional.of(soundcard);
```

If the value may be null:

```
// if soundcard is null, sc will be created empty, otherise null pointer exception will occur
Optional<Soundcard> sc = Optional.ofNullable(soundcard);
```


### Checking the values of Optionals

In stead of 

```
SoundCard soundcard = ...;
if(soundcard != null){
  System.out.println(soundcard);
}
```

You can:

```
Optional<Soundcard> soundcard = ...;
soundcard.ifPresent(System.out::println);
```


To be super safe you can use it like this - HOWEVER this is NOT the idiomatic way of using Optionals

```
if(soundcard.isPresent()){
  System.out.println(soundcard.get());
}
```

THe preferred way is:

```
// maybeSoundcard is a Optional
Soundcard soundcard = maybeSoundcard.orElse(new Soundcard("default"));

// alternatively
Soundcard soundcard = 
  maybeSoundCard.orElseThrow(IllegalStateException::new);
```

### More info

(https://www.oracle.com/technical-resources/articles/java/java8-optional.html)



## Logging

In each class where you want to do logging:

```
//imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//in your class
private static final Logger log = LoggerFactory.getLogger($NAME_OF_CLASS.class);
 ```


 When you want to log:

 ```
 log.info("your msg");
 log.debug("debug message")
 //etc
 ```


For dependencies, the implementation jars typically contain dependencies encoded back for slf4j.

To get the log4j one:


```
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-log4j12</artifactId>
  <version>1.7.28</version>
</dependency>

```

### slf4j

It's an abstraction and provides no actual logging capability of itself. However it provides a consistent interface and documentation for various different logging providers.

### java.util.logging (aka "JUL")

The standard logging implementation in Java but it's not very popular as it is seen as inflexible and slow.

### log4j and logback

Popular logging implementations that work with slf4j





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

### java.lang.Comparable

Lists (and arrays) of objects that implement this interface can be sorted automatically by `Collections.sort` (and `Arrays.sort`). Objects that implement this interface can be used as keys in a sorted map or as elements in a sorted set, without the need to specify a `comparator`.


Interface:
```
public interface Comparable<T> {
    public int compareTo(T o);
}
```
Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.


----------------------------------------------------------------------------------------------


## Date/Time managment API


### Legacy


Get unix time:

```
long unixTime = System.currentTimeMillis() / 1000L;
```


### Java 8 hotness

Broken down into 2 categories: date/time without zone, date/time with zone


```
LocalDateTime.now();
LocalDateTime.of(2015, Month.FEBRUARY, 20, 06, 30);
var localDateTime = LocalDateTime.parse("2015-02-20T06:30:00");

```

with zones:

```
ZoneId zoneId = ZoneId.of("Europe/Paris");
ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
```



Get unix time:

```
Instant.now().toEpochMilli()
```




