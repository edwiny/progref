# Kotlin Standard Library

## File IO

The core class for file processing is File from the java.io package. Kotlin provides additional functionality over Java implementation through Extension Functions. This means that by just importing java.io.File into your project, you can use functionality from both Java and Kotlin File implementations

### Read the entire contents into one string

```
val fileName = "src/reading.txt"
val lines = File(fileName).readText()
print(lines)
```


### Check for file existence

```
val fileName = "src/reading.txt"
val file = File(fileName)
if (file.exists()) {
..
```

Note that `File(filename)` doesn't actually open the file, it's just a reference.

### Recommended way of reading large files (>2GB) line by line

```
val fileName = "src/reading.txt"
File(fileName).forEachLine { println(it) }
```



### Reading line by line

```
val lines = File(fileName).readLines()
for (line in lines){
    println(line)
} 
```
### Reading bytes

```
val lines = File(fileName).readLines()
for (line in lines){
    println(line)
} 
```

Note, this function returns the `ByteArray`. The Array structure is similar to the `MutableList`, you cannot resize it, but you can modify elements. You can easily convert `MutableList` to `ByteArray` and vice versa with `toByteArray()` and `toMutableList()` functions.

### Writing files

```
import java.io.File
...

val myFile = File("MyFile.txt")
myFile.writeText("blah")
```

Appending:

```
File(fileName).appendText("blah")
```

### Writing byte arrays

```
val arrayOfBytes = byteArrayOf(1, 2, 3) // create an array
// another way:
// val arrayOfBytes = mutableListOf<Byte>(1, 2, 3).toByteArray() 

File(fileName).writeBytes(arrayOfBytes)
```


## Logging

## Math

```
import kotlin.math.*
```

Common functions, works with Int, Long, Float, Double:

* abs(n)
* min(a,b)
* max(a,b)
* x.pow(n)
* sqrt(n)
* exp(n)
* log(x, base)
* floor(x)
* ceil(x)
* round(x)
* roundToInt() 

## Random

```
import kotlin.random.Random

// generates an integer value between 1 (inclusive) and 100 (exclusive)
Random.nextInt(1, 100) 

// generates a double value between 0.0 (inclusive) and 5.0 (exclusive)
Random.nextDouble(0.0, 5.0) 
```

Seeding:

```
val randomGenerator = Random(42)
OR: 
val defaultGenerator = Random.Default 
randomGenerator.nextInt(100)
```



