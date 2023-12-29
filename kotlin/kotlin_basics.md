# Kotlin basics

## Tidbits

* No semi colon required at end of statements. Kotlin will cotinue as far as it can to make the previous lines parse.

```
return
  foo()
```


foo() will never be called, though. 


* main function:

```
fun main(args: Array<String>) {
    println("Hello World!")
}

```

## Running from cmdline

```
sdk install kotlin # on linux
kotlinc main.kt -include-runtime -d main.jar
java -jar main.jar

# OR

kotlin -classpath main.jar MainKt

```

Running at kotlin script:

**NB**: Script file extension must be `.kts`.

Example script:
```
import java.io.File

val file = File(args[0])
val lines = file.readLines()
val sorted = lines.sortedByDescending { it.length }


println(sorted.first().length)

```

Then run with:



```
kotlinc -script longest.kts -- words_sequence.txt
```



## Variables

Variables are declared either mutable (`var`) or immutable (`val'):

```
var mutableVar = "kotlin"
val immutableVar = "also kotlin"

```

The assignments above uses  *type inference* to assign a type to the variables. To explicitly declare type:

```
val text: String = "Hello, I am studying Kotlin now."
val n: Int = 1
```

Note you can defer initialisation of a val:
```
val n: Int

n  = 50
```

The `val` keyword implies immutable reference.


Variables should by default be immutable. Only make it mutable
when you absolutely have to. Start with immutable and change it later if needed.


## Basic Types

Short - 16 bits
Int   - 32 bits
Long  - 64 bits
Float - 32
Double- 64
Char  - 16
Boolean - size not precisely defined
String




DOES NOT VARY between hardware architectures and operating systems.

Max values:

```
Int.MIN_VALUE
Int.MAX_VALUE
```
## Type casting

```
val n: Long = i.toLong()
```

Avoid casting from Double or Float directly to Short or Char. Cast to int first.

To conver to and from string you can do:
```
n.toString()
"8".toInt()
"true".toBboolean()

```

## Implicit Type casting
(char, byte, short) -> Inference

Kotlin will automatically cast values in an expression to the widest type,
according to this rule:
nt

```
(int, long) -> Long
(int, float) -> Float
(*, double) -> Double
```

The sequence is

* Int
* Long
* Float (this is kinda suprising but Long goes up to Float)
* Double


# Constants

```
const val MYCONST = 1204
```

NOTE: can only be declared outside of any functions.


## Integer Operations

Note: you cannot compare Int and Long for equality! Has to be cast to Long first.

< and > works, though.


## Strings

* Strings are immutable
* Chars in a string can be accessed as `s[i]`
* Can concatenate strings with `+`. 

Printing unicode chars:

```
println("\u0394")  //code point is in hexadecimal
```



Accessing individual chars:

```
val greeting = "Hello"

val first = greeting[0]  // 'H'
val last = greeting[greeting.length - 1] // 'o'
```

Convenience functions (same as ImmutableList):

```
println(greeting.first())   // 'H'
println(greeting.last())    // 'o'
println(greeting.lastIndex) // 4
```

Checking for empty string:
```
val emptyString = ""
println(emptyString.length == 0) //true
println(emptyString.isEmpty()) //true
```

Strings generally are not modified, in stead new ones are created.

Comparing strings:
```
val first = "first"
val second = "second"
var str = "first"

println(first == str)    // true
println(first == second) // false
println(first != second) // true
```

Can use ranges to iterate through a string:

```
val str = "Hello!"
for (ch in str) {
    println(ch)    
}
```

Getting substrings:

`.substring()` startIndex is inclusive, lastIndex exclusive:

```
val greeting = "Hello"
println(greeting.substring(0, 3)) // "Hel"
//these also take a delimiter arg
println(greeting.substringAfter('l'))  // "lo"
println(greeting.substringBefore('o')) // "Hell"
println(greeting.substringBefore('z')) // "Hello"
println(greeting.substringAfterLast('l'))  // "o"
println(greeting.substringBeforeLast('l')) // "Hel"
```

Replacing:

Note that strings are immutable so these functions return a new string.

```
val example = "Good morning..."
//replaces all occurances in source string
println(example.replace("morning", "bye")) // "Good bye..."
println(example.replaceFirst("one", "two"))
```

Case:

```
println(example.lowercase())
```
Note: toLowerCase() and toUpperCase() is deprecated


Splitting:

```
var(x, y) = coordInput.split(" ", limit = 2).map { it.toInt() }.toIntArray()
```



### Raw Strings

Delimited by """

```
val text = """
    for (c in "foo")
        print(c)
"""
```

Using String.trimMargin() you can ensure the whitespace in your source code indenting is not included in the string:


```
val text = """
    |Tell me and I forget.
    |Teach me and I remember.
    |Involve me and I learn.
    |(Benjamin Franklin)
    """.trimMargin()
    
```

### String templates

```
val i = 10
println("i = $i") // prints "i = 10"

// OR

val s = "abc"
println("$s.length is ${s.length}") // prints "abc.length is 3"
```

Works also in raw strings.


## Casting

### is operator

Can check type of any object:

```
if (obj is String) {
    print(obj.length)
}
```

### Smart Casts

Kotlin compiler "caches" the `is` checks and can automaticall insert a cast after a `is` check has been done:


```
fun demo(x: Any) {
    if (x is String) {
        print(x.length) // x is automatically cast to String
    }
}
```

Example: the following Java code:

```
public int eval(Expr expr) {
    if (expr instanceof Num) {
        return ((Num) expr).getValue();
    }
    if (expr instanceof Sum) {
        Sum sum = (Sum) expr;
        return eval(sum.getLeft()) + eval(sum.getRight());
    }
    throw new IllegalArgumentException("Unknown expression");
}
```

can be rewritten in Kotlin as:


```
fun eval(expr: Expr): Int =
        when (expr) {
            is Num -> expr.value
            is Sum -> eval(expr.left) + eval(expr.right)
            else -> throw IllegalArgumentException("Unknown expression")
        }

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr
```




## MutableLists

```
val myList = mutableListOf('elem1', 'elem2')
val initialisedList = MutableList(2) { "_", "_" }
```

Creating a empty mutable list:

```
val arr = mutableListOf<Int>()
```

Setting an element:

* `add(element)` is a method that adds an extra element to your list.
* `set(index, element)` replaces the element at the specified position with the specified element. Laconic form:
mutableList[index] = element
* `addAll(elements)` adds all of the elements of the specified collection to the end of the list.

Removing elements:

* `removeAt(index)`
* `remove(element)`
* `clear()`


### Useful functions

`.joinToString()`: join to comma seperate list (or other specified delimiter)

Joining lists:

```
val newList = list1 + list2

//OR
list1.addAll(list2)

//OR
list1 += list2

```

Can do a *structural equality* test:

```
println(firstlist == secondlist)  //checks elements of the lists (must be same order)
```



`.add(elem)` - appends to end
`.add(index, elem)` - inserts at pos
`.remove(elem)` - returns true if found
`.removeAt(index)` - removes it at pos and return elem removed
`.clear()` - deletes all elems from list

Copy a list:

```
val copyList = mutableLisOf<Int>()
copyList.addAll(list)
```

Check if empty:
```
list.isEmpty()
```

Search list:
```
element in list

//OR
list.indexOf(element) //returns -1 if not found
```

Sort a list by natural order:
```
list.sorted() 
list.sortedDescending()
```

Sort by custom order:

```
val sorted = lines.sortedByDescending { it.length }
```


Iterating through a list:

```
for(element in list) {
 println(element)
}
```

if you need to iterate by index:

```
 for (i in products.indices) {
        if(products[i] == product) print("$i ")
    }
```

## Immutable Lists

Creating a list, the idiomatic way:

```
val list = listOf("a", "b", "c")
```

Other ways:

```
val cars = listOf<String>("BMW", "Honda", "Mercedes")
```
Can also use type inference to omit type:

```
val cars = listOf("BMW", "Honda", "Mercedes")
```

To create empty list:

```
val staff emptyList<String>()
```



## Comparison / Equality

*Structural equality* is when the values of two objects are the same.  E.g.:

```
val msg1 = "Hi"
val msg2 = "Hi"

val c1 = Colour("red")
val c2 = Colour("red")
```

To check, use `==` or `!=`

*Referential equality* is when two varables are pointing at the same data. E.g.:

```
val c1 = Colour("red")
val c2 = c1
```

To check, use the `===` or '!==` operators.

## Ranges

```
println(5 in 5..15)  // true
val withinExclRight = c in a..b - 1 // a <= c && c < b
val notWithin = 100 !in 10..99 // true
val within = c in 5..10 || c in 20..30 || c in 40..50 
```
Cam also save ranges in vars:
```
val range = 100..200
println(300 in range) // false
```

Cam also generate strings:
```
println('k' in 'a'..'e') // false

println("hello" in "he".."hi") // true
```

## Arrays

Arrays work a bit differently in Kotlin. Arrays of primitive types have a specific type such as:

```
IntArray, LongArray, DoubleArray, FloatArray, CharArray, ShortArray, ByteArray, BooleanArray
```

They can be created by the 'xxxArrayOf` special functions:

```
val numbers = intArrayOf(1, 2, 3, 4, 5)
```

To create array of specified size: (values are initialised to 0)
```
val numbers = IntArray(5) 
```
Can pass in initialisation code:

```
val numbers = IntArray(5) { readLine()!!.toInt() } 

//OR if on one line:

val numbers = readLine()!!.split(" ").map { it.toInt() }.toTypedArray()

//OR use regex:

val regex = "\\s+".toRegex()
val nums = str.split(regex).map { it.toInt() }.toTypedArray()

```

NOTE: Arrays cannot change size!

Like with lists, Kotlin provides the following convenience methods for arrays:

```
println(alphabet.first())   
println(alphabet.last())    
println(alphabet.lastIndex) 
```

Can compare arrays:

```
val numbers1 = intArrayOf(1, 2, 3, 4)
val numbers2 = intArrayOf(1, 2, 3, 4)

println(numbers1.contentEquals(numbers2)) // true
```

For arrays of strings or objects, use `arrayOf` function:

```
val stringArray = arrayOf("array", "of", "strings")
//OR
val stringArray = arrayOf<String>("array", "of", "strings")
```

Create empty array:

```
val newEmptyArray = emptyArray<String>()
```


## Loops

`repeat (count) { ... }`

`while(expression) { ... }`

`do { ... } while (expression)`

Can `break` out.

Can use ranges:

```
for (element in source) { ... }
```

Can also go backward:

```
for (i in 4 downTo 1) {
    println(i)
}
```

Excluding upper limit:
```
for (i in 1 until 4) {
    println(i)
}
```

Can specify a step:
```
for (i in 1..7 step 2) {
    println(i)
}
```

Using ranges in loops is kotlin idiomatic.

Can also use `forEach()` function:

```
val numbers = listOf("one", "two", "three", "four")
numbers.forEach {
    println(it)
}
```

Return to labels:

```
loop@ for (i in 1..100) {
    for (j in 1..100) {
        if (...) break@loop
    }
}
```

For lambdas:

```
fun foo() {
    listOf(1, 2, 3, 4, 5).forEach lit@{
        if (it == 3) return@lit // local return to the caller of the lambda - the forEach loop
        print(it)
    }
    print(" done with explicit label")
}
```


## When (switch / case)

```
when (op) {
    "+", "plus" -> println(a + b)
    "-", "minus", -> println(a - b) // trailing comma
    "*", "times" -> println(a * b)
    else -> println("Unknown operator")
}
```

Can also use blocks:
```
"+", "plus" -> {
        val sum = a + b
        println(sum)
    }
```

Can be used in expression form:

```
println(when(op) {
    "+" -> a + b
    // ...
    else -> "Unknown operator"
})
```
When using it like this there:
* must be a else
* if using block, then last line must contain a value or expression

You can also use expressions before the arrow:

```
 println(when (c) {
        a + b -> "$c equals $a plus $b"
        a - b -> "$c equals $a minus $b"
        a * b -> "$c equals $a times $b"
        else -> "We do not know how to calculate $c"
    })
```
Can also use ranges:

```
when (n) {
    0 -> println("n is zero")
    in 1..10 -> println("n is between 1 and 10 (inclusive)")
    in 25..30, in 40..50 -> println("some other range")
    else -> println("n is outside a range")
}
```

Can also use without when argument:

```
val n = readLine()!!.toInt()
    
    when {
        n == 0 -> println("n is zero")
        n in 100..200 -> println("n is between 100 and 200")
        n > 300 -> println("n is greater than 300")
        n < 0 -> println("n is negative")
        // else-branch is optional here
    }
```

## Reading input

```
val line = readline!!()
```

Can also use Java Scanner library:

```
val scan = Scanner(System.`in`)

val n = scan.nextLine().trim().toInt()
```
in is a Kotlin reserved word

To read multiple ints across multiple lines:

```
    val scanner = Scanner(System.`in`)
    while(scanner.hasNextInt()) {
        val i = scanner.nextInt()
        println("Int is $i")
    }

```


## Collections

The 3 basic types are:

* List - store elements in order and provides indexed access
* Set - store unique elements with order undefined
* Map - store key-value pairs with keys unique

These exist with Mutable and Immutable variations.

All collections have the following common methods:

* `size` returns the size of your collection.
* `contains(element)` checks if the element is in your collection. (NOTE: but use 'in' instead)
* `containsAll(elements)` checks if all elements of the collection elements are in your collection.
* `isEmpty()` shows whether the collection is empty or not.
* `joinToString()` converts the collection to a string.
* `indexOf(element)` returns the index of the first entry of the element, or -1 if the element is not in the collection.

Mutable collections have:

* `clear()` removes all elements from the collection.
* `remove(element)` removes the first occurrence of an element from your collection.
* `removeAll(elements)` removes from your collection all elements contained in the collection elements.

Idiom: 
```
println(elem in collection)
in stead of
println(collection.contains(elem))
```

## Maps

Entries in map are represented by special `Pair` type.

```
val (name, grade) = Pair("Joe", 5)
```

It has special `first` and `second` properties:

```
val p = Pair(2, 3)
println("${p.first} ${p.second}") // 2 3
```

The `to` construct is shorthand for creating a `Pair`:

```
val (name, grade) = "Vlad" to 4
// same as

val (name, grade) = Pair("Vlad", 4)
```


Initialising maps:

```
val staff = mapOf<String, Int>("John" to 1000)
//OR
val staff = mapOf("Mike" to 1500)  //Idiomatic way
val carsPerYear = mutableMapOf(1999 to 30000, 2021 to 202111)
```

Creating empty maps:


```
val emptyStringToDoubleMap = emptyMap<String, Double>()
```


Test key exists:


```
val isWanted = employees.containsKey("Jim")
```

Can also check if value exists with `containsValue()`

Iterating a Map:

```
val employees = mapOf(
    "Mike" to 1500,
    "Jim" to 500,
    "Sara" to 1000
)

for (employee in employees)
    println("${employee.key} ${employee.value}")

for ((k, v) in employees) //Idiomatic way!
    println("$k $v")
```

Mutatating actions:

Setting / adding element:

```
staff["Nika"] = 999

// or

staff.put("Mike", 999)
```

Removing element:

```
map.remove(key)

// OR

cars -= "Kia"  
```

## Exceptions


Exception hierarchy:

* Throwable
  * Error  -> serious errors that app should not try to handle
  * Exception -> can be handled
    * RuntimeException -> normally means insufficient validation in program
       * ArithmeticException - div by zero
       * NumberformatException - when converting text to number and text is not number
       * IndexOutOfBoundException - array index greater than max index
     
     
Throwing exceptions:

`throw Exception("Value can't be negative")`

Catching Exeptions:

```
try {
    // code that may throw an exception
} catch (e: SomeException) {
    // code for handling the exception
    println(e.message)
    
}
```

Can catch multiple exceptions by adding additional catch clauses:

```
try {
    // code that throws exceptions
}
catch (e: IOException) {
    // handling the IOException and its subtypes   
}
catch (e: Exception) {
    // handling the Exception and its subtypes
}
```
The catch block with the base type has to be written below all the blocks with subtypes. In other words


Finally:

All the statements present in this block will always execute regardless of whether an exception occurs in the try block or not.

```
try {
    // code that may throw an exception
}
catch (e: Exception) {
    // exception handler
}
finally {
    // code is always executed
    //close a file
    
}
```
It is possible to omit the catch statement. 
The `finally` block will run even if we don't catch the exception.


Exceptions can be treated as expressions, ie they can yield a value:

```
val number: Int = try { "abc".toInt() } catch (e: NumberFormatException) { 0 }
```
This is also the kotlin-idiomatic way of handling exceptions.

Value must be returned in either the last expression in the try block or the last expression in the catch block(s). The contents of the finally block do not affect the result of the expression


## Functions

Functions return type can be inferred:

```
fun sum(a: Int, b: Int) = a + b
```



Remarks:

* Void functions don't need to declare a return type (but inferred return type is `Unit`.
* Can use a **trailing comma** at the end of function parameters.
* When the only argument to a function is a lambda, you can omit the parenthesis in the function call e.g. `run { println("...") }`


### Default parameters

Function parameters can have default values e.g. 

```
fun read( b: Array<Byte>, off: Int = 0)
```


to reduce number of method overloads

### Named arguments

When calling a function you can include the name of the parameter and mix it with positional arguments e.g.:

```
reformat(
    'String!',
    false,
    upperCaseFirstLetter = false,
    divideByCamelHumps = true,
    '_'
)
```



Any named arguments omitted will use the default values.

Reasons for using it:
* Improve readability
* Arguments order no longer important

            

### Single-expression functions

When returning a single expression, braces can be omitted:

```
fun double(x: Int): Int = x * 2

//OR let compiler figure out return type:

fun double(x: Int) = x * 2
```

Block body functions must explicitly define a return type - unless it returns nothing.


### Varargs

The last parameter can be declared as vararg like this:

```
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}
```

Inside the function the argument is treated as an array.


When passing values, you can expand an array with the **spread operator** (the * ):

```
val list = asList(-1, 0, *a, 4)
```

### Function scope

In Kotlin functions can be declared at top level in a file, meaning you do not need to create a class to hold a function.
.Kotlin functions can also be declared local, as member functions and extension functions.



### Function types


The type of the following function:

```
fun sum(a: Int, b: Int): Int = a + b
```
is:
```
(Int, Int) -> Int
```

For:
```
fun sayHello() {
    println("Hello")
}
```
Type is:
```
() -> Unit
```

### Function references

You can obtain a reference to a function using the `::` operator.

If there is a `sum()` function at the global scope, then reference would be:
`::sum``

This can be assigned to a var like:

```
// type inferred
val sumObject = ::sum

// type explicit
val sumObject: (Int, Int) -> Int = ::sum


sumObject(10, 20)
```

Functions can return references to functions, e.g.:

```
fun getScoringFunction(isCheater: Boolean): (Double) -> Double {
    if (isCheater) {
        return ::getGradeWithPenalty
    }

    return ::getRealGrade
}
```

Functions can reference functions as parameters:

```
fun applyAndSum(a: Int, b: Int, transformation: (Int) -> Int): Int {
    return transformation(a) + transformation(b)
}
```

This forms the basis of **functional programming** - functions taking other functions as arguments.


There 3 classes of function types - regular function types, receiver function types, and suspend function types:

```
(A,B) -> C
A.(B) -> C    # afaict, for methods
suspend (A,B) -> C   OR suspend A.(B) -> C
```

You can optionally include the name of the parameters, e.g.

```
(param1: Int, param2: Int) -> Int
```

To specifcy the type is nullable:

```
((Int, Int) -> Int)?
```

Can embed function types in another function type:

```
(Int) -> ((Int) -> Unit)

# right associative so can also do:

(Int) -> (Int) -> Unit 
```

Can create a typdef alias like

```
typealias ClickHandler = (Button, ClickEvent) -> Unit
```



## Lambdas

Lambda expressions are basically instantiating function types. Aka anonymous functions.

* `fun(arguments): ReturnType { body }` - anonymous function
* `{ arguments -> body }` - lambda expression


E.g:

```
fun(a: Int, b: Int): Int {
    return a * b
}

{ a: Int, b: Int -> a * b }
```


You can also define a lambda without arguments, then don't need the arrow sign:

`{ body }`


Progression through syntactic sugar:
These are all equivalent:

```
fun isNotDot(c: Char): Boolean = c != '.'
val originalText = "I don't know... what to say..."
val textWithoutDots = originalText.filter(::isNotDot)
```
Rewrite to a lambda expression:
```
val originalText = "I don't know... what to say..."
val textWithoutDots = originalText.filter({ c: Char -> c != '.' })
```

Types for the parameters can be inferred:

```
originalText.filter({ c -> c != '.' })
```

If lamdba is last parameter, we can put it outside the ():
```
originalText.filter() { c -> c != '.' }
```

If parameter list is empty, we can omit ():

```
originalText.filter { c -> c != '.' }
```

If there is only 1 parameter to the lambda, it can use the implicit paramater `it`:
```
val originalText = "I don't know... what to say..."
val textWithoutDots = originalText.filter { it != '.' }
```

### Multiline lambdas

The value of the last expression is returned.

E.g.:

```
val textWithoutSmallDigits = originalText.filter {
    val isNotDigit = !it.isDigit()
    val stringRepresentation = it.toString()

    isNotDigit || stringRepresentation.toInt() >= 5
}
```

Can return early in a lambda using a *qualified return statement* - normally the function name that the lambda is a argument to:

```
val textWithoutSmallDigits = originalText.filter {
    if (!it.isDigit()) {
        return@filter true
    }
        
    it.toString().toInt() >= 5
}
```

### Capturing variables

One of the bid advantages of lambdas are that they can access the variables from the same
scope as where the lambda is defined.

When the lambda references a variable defined outside the lambda it's said to be *capturing* that variable.

For example:

```
var count = 0

val changeAndPrint = {
    ++count
    println(count)
}

println(count)    // 0
changeAndPrint()  // 1
```


### Examples

Valid anon fn and lambda expressions:

```
fun(number: Int) = number.toString()

fun(number: Int): String { return number.toString() }

{ number: Int -> number.toString() }

```

Incorrect expressions:

```
(number: Int) -> { number.toString() }

(number: Int) => { number.toString() }

{ number: Int => number.toString() }
```


### Notes from the standard docs

A lambda expression is always surrounded by curly braces, parameter declarations in the full syntactic form go inside curly braces and have optional type annotations, the body goes after an -> sign. If the inferred return type of the lambda is not Unit, the last (or possibly single) expression inside the lambda body is treated as the return value.


For example, instantiating the following function type:

```
val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }

# types can be inferred from the expression, so can abbreviate to:

val sum = { x: Int, y: Int -> x + y }

```



**Trailing lambdas**

Convention: if the last parameter of a function is a function, then a lambda expression passed as the corresponding argument can be placed outside the parentheses:

```
val product = items.fold(1) { acc, e -> acc * e }
```

If the lambda is the only argument to the function call, the parenthesis can be omitted entirely:

```
run { println("...") }
```

**it: implicit name of single paramater**

t's very common that a lambda expression has only one parameter.

If the compiler can figure the signature out itself, it is allowed not to declare the only parameter and omit ->. The parameter will be implicitly declared under the name it:

```
ints.filter { it > 0 } // this literal is of type '(it: Int) -> Boolean'
```

**Returning a value**

Value of last expression is returned. Otherwise can use a *qualified* return:

```
ints.filter {
  val shouldFilter = it > 0
  return@filter shouldFilter
}

```

**Using underscore for unused parameters**

```
map.forEach { _, value -> println("$value!") }
```


### Anonymous functions


Lambda expressions can't specify the return type as it's inferred. If you want control over the return type, use
anonymous functions instead. 

They look  like regular functions but with name omitted.


```
//expression form
fun(x: Int, y: Int): Int = x + y


//block form

fun(x: Int, y: Int): Int {
    return x + y
}

```


The parameter types can be omitted if they can be inferred:

```
ints.filter(fun(item) = item > 0)
```


A return statement without a label always returns from the function declared with the fun keyword. This means that a return inside a lambda expression will return from the enclosing function, whereas a return inside an anonymous function will return from the anonymous function itself.



### Function literals with receiver

These function types are methods on objects. 

The type looks like this, as explained above:

```
A.(B) -> C
```

Inside the body of the function literal, the receiver object passed to a call becomes an implicit this, so that you can access the members of that receiver object without any additional qualifiers, or access the receiver object using a this expression.

Example function literal with receiver:


```
val sum: Int.(Int) -> Int = { other -> plus(other) }
```

Anonymous function syntax allows you to specify the type of the receiver explicitly in a function type literal:

```
val sum = fun Int.(other: Int): Int = this + other
```



## Classes

Class declarations consist of the class name, class header (optional) and class body (optional).

NOTE: Kotlin calls methods "member functinos".

### Constructors

**Primary constructor** goes in the class header:

```
class Person(firstName: String) 
{ /* class body */ }

//OR if annotations required:

class Person constructor(firstName: String) 
{ /* class body */ }



```
The primary constructor cannot contain any code. 

You can use `val` and `var` in the primary constructor parameter list to automatically defines class properties from
the parameter list and assign the values passed in at create time. 

When you write val/var within the constructor, it declares a property inside the class.

**NB**:
When you do not write it, it is simply a parameter passed to the primary constructor, where you can access the parameters within the init block or use it to initialize other properties. 

If all you're going to do with the argument is pass to super class, then do not use var or val.


Compare Kotlin:

```
class Person constructor(val name: String, val age: Int? = null)
```

with Java:

```
class PersonJava {
    final String name;
    final Integer age;
 
    public PersonJava(String name) {
        this.name = name;
        this.age = null;
    }
 
    public PersonJava(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
```

**Initializer blocks**

Any number of `init` blocks can be placed in the class body and will execute **after** the primary constructor:

```
class InitOrderDemo(name: String) {
    val firstProperty = "First property: $name".also(::println)
    
    init {
        println("First initializer block that prints ${name}")
    }
}
```

The properties defined in the constructor paramater list can be accessed from the init blocks.

They effectively become part of the primary constructor (i.e. they are guaranteed to execute before
any secondary constructors)

**Secondary Constructors**

Secondary constructors must delegate to the primary constructor either directly or indirectly via another secondary constructor.
It is done via the `this()` keyword, and put in a special place after parameters and before constructor body.

```
class Person(val name: String) {
    var children: MutableList<Person> = mutableListOf<>()
    constructor(name: String, parent: Person) : this(name) {
        parent.children.add(this)
    }
}
```

If a non-abstract class does not declare any constructors (primary or secondary), it will have a generated primary public constructor with no arguments. 


### Initialising class properties

Most initialisation outside of the constructer can be done via the top level property initialisation. 

*NOTE*: when initialising the top level properties you can access the constructor parameters.

Tip: Use the `apply()` function / expression in the property initialisers:

```
// Do
class UsersClient(baseUrl: String, appName: String) {
    private val usersUrl = "$baseUrl/users"
    private val httpClient = HttpClientBuilder.create().apply {
        setUserAgent(appName)
        setConnectionTimeToLive(10, TimeUnit.SECONDS)
    }.build()
    fun getUsers(){
        //call service using httpClient and usersUrl
    }
}
```


### Order of initialisation


1. primary constructor 
2. top-level class property declarations
3. code in `init` blocks
4. secondary constructors


### Creating classes

There's no `new` keyword:

```
val customer = Customer("Joe Smith")
```

### Inheritance

All Kotlin classes have common superclass `Any`. This class has methods `equals()`, `hashCode()` and `toString()`
so they are defined for all classes.

By default all classes are final (cannot be inherited). To make it inheritable, use `open` keyword in class definition.


The base class must be initialised in the class declaration:

NOTE: the constructer argument does not have 'val' or 'var'.
```
open class Base(p: Int)

class Derived(p: Int) : Base(p)

```

If the derived class has no primary constructor, then each secondary constructor has to initialize the base type using the super keyword, or to delegate to another constructor which does that.


**Overriding methods**

The base class method must be *explicitly specified* to be derivable, with the `open` keyword:

```
open class Shape {
    //note the open keyword
    open fun draw() { /*...*/ }
    fun fill() { /*...*/ }
}

class Circle() : Shape() {

    //override keyword required
    override fun draw() { /*...*/ }
}

```
**Overriding properies**

Works same way as overriding methods, with `open` and `override` keyword requirements. 
You can use the `override` keyword as part of the property declaration in a primary constructor.

```
interface Shape {
    val vertexCount: Int
}

class Rectangle(override val vertexCount: Int = 4) : Shape // Always has 4 vertices

class Polygon : Shape {
    override var vertexCount: Int = 0  // Can be set to any number later
}
```


Can override a `val` property with a `var` but not vice versa.

**Gotchas with base class initialisation**

When designing a base class, you should therefore avoid using open members in the constructors, property initializers, and init blocks.
https://kotlinlang.org/docs/reference/classes.html#derived-class-initialization-order


**Abstract classes**

```
abstract class Rectangle : Polygon() {
    abstract override fun draw()
}
```
open is assumed.


**Sealed classes and interfaces**

Sealed classes cannot be subclassed from outside of the module where they are declared.




### Properties

* `var` - mutable
* `val` - readonly



Compare this Kotlin code:

```
val isEmpty: Boolean
```

the equivalent Java code is:

```
private final Boolean isEmpty;

public Boolean isEmpty() {
    return isEmpty;
}
```

You can only access the underlying field via getters and setters, which will be auto generated for you if 
you don't specify them.



The syntax for declaring properties:

```
var <propertyName>[: <PropertyType>] [= <property_initializer>]
    [<getter>]
    [<setter>]
```


The following two property declarations are equivalent:

```
var someProperty: String = "defaultValue"
```
and

```
var someProperty: String = "defaultValue"
    get() = field
    set(value) { field = value }
```


By convention, the name of the setter parameter is `value`.

The underlying (backing) field can be accessed by the `field` keyword. A backing field will be generated for a property if it uses the default implementation of at least one of the accessors, or if a custom accessor references it through the field identifier.

For example, in the following case there will be no backing field:

```
val isEmpty: Boolean
    get() = this.size == 0
```


If you need to change the visibility of an accessor or to annotate it, but don't need to change the default implementation, you can define the accessor without defining its body:

```
var setterVisibility: String = "abc"
    private set // the setter is private and has the default implementation

var setterWithAnnotation: Any? = null
    @Inject set // annotate the setter with Inject
```


### Compile time constants


If the value of a read-only property is known at the compile time, mark it as a compile time constant using the const modifier. 
Seems to work like a macro in C.

```
const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"

@Deprecated(SUBSYSTEM_DEPRECATED) fun foo() { ... }
```

### Late-Initialised properties and values


Normally, properties declared as having a non-null type must be initialized in the constructor. However, fairly often this is not convenient. For example, properties can be initialized through dependency injection or setup method in unit test. 

Get around it by using the `lateinit` modifier:

```
ublic class MyTest {
    lateinit var subject: TestSubject

    @SetUp fun setup() {
        subject = TestSubject()
    }

    @Test fun test() {
        subject.method()  // dereference directly
    }
}
```

Accessing a lateinit property before it has been initialized throws a special exception that clearly identifies the property being accessed and the fact that it hasn't been initialized.

To check if it's initialised, use isInitialized on the property *reference*:

```
if (foo::bar.isInitialized) {
    println(foo.bar)
}

```
### Nested Classes vs Inner Classes

Can declare a class inside another clawss - this is a Nested Class. It can't access the properties or methods from the outer class.

To create nested class, it works like a class method:

```
val inner = Outer.Inner()`
```

Can be declared with special keyword `inner` whuch grants access to our classes data and methods.

To access the inner class, an instance of the outer class first needs to be created:

```
val outer = Outer()
val inner = outer.Inner()
```

To access a shadowed instance of a parent class property:

```
val x = this@ParentClass.color
```


When to use inner classes:
* Inner classes are somewhat hidden so as such helps to improve the encapsulation and/or the organisation of a larger, more complex class.


### Data Classes

Classes whose only purpose is to hold data, not code.

Example:

```
if (foo::bar.isInitialized) {
    println(foo.bar)
}
```

Kotlin will auto generate appropriate methods for:
* `equals()` / `hashCode()`
* `toString()`
* `componentN()` - for destructuring 
* `copy()` 


On the JVM, if the generated class needs to have a parameterless constructor, default values for all properties have to be specified:

```
data class User(val name: String = "", val age: Int = 0)
```

To exclude properties from copy or equals, define them outside primary constructor, in the class body.


## Null safety

A big advantage of Kotlin is it aims to eliminate null references.

A regular variable of type String can not hold null:

```
var a: String = "abc" // Regular initialization means non-null by default
a = null // compilation error
val l = a.length  // guaranteed to be safe

```

To allow null, add a `?` to the end:

```
var b: String? = "abc" // can be set null
b = null // ok
print(b)
```

However, accessing the property without a null check will cause compilation failure:

```
val l = b.length  // won't compile
```

**Check for null**

You can explicitly check for null, this is tracked by the compiler:

```
val l = if (b != null) b.length else -1
```

**Safe calls**

Add `?` after receiver:

```
val a = "Kotlin"
val b: String? = null
println(b?.length)
println(a?.length) // Unnecessary safe call
```

This returns b.length if b is not null, and null otherwise. The type of this expression is `Int?`.

Can be chained like this - will evaluate to null if any one component is null:

```
bob?.department?.head?.name

// can also use on LHS in assignment

/ If either `person` or `person.department` is null, the function is not called:
person?.department?.head = managersPool.getManager()

```

To iterate over a list that main contain nulls, use `let`:

```
val listWithNulls: List<String?> = listOf("Kotlin", null)
for (item in listWithNulls) {
    item?.let { println(it) } // prints Kotlin and ignores null
}
```

There is also filterNotNull()

```
val intList: List<Int> = nullableList.filterNotNull()
```


Can use `lateinit var` to prevent excessive null checking for non-null type variables that initially won't have a value.



**Elvis operator**

`?:` - use 2nd value if 1st value is null:

```
val l: Int = if (b != null) b.length else -1

// can be written as

val l = b?.length ?: -1
```

It's also useful to generate exceptions:

```
val name = node.getName() ?: throw IllegalArgumentException("name expected")
```


**Null Pointer Exception operator**


Kotlin discourage this use but you can convert a nullable value to a non-null value by added the NPE operator in the reference, 
which will generate a null pointer exception if the value is null:

```
val l = b!!.length
```


**Example**

The follwing java code can be rewritten like so:

```
ublic void sendMessageToClient(
    @Nullable Client client,
    @Nullable String message,
    @NotNull Mailer mailer
) {
    if (client == null || message == null) return;

    PersonalInfo personalInfo = client.getPersonalInfo();
    if (personalInfo == null) return;

    String email = personalInfo.getEmail();
    if (email == null) return;

    mailer.sendMessage(email, message);
}
```

as:

```
val email = client?.personalInfo?.email
 if (email != null && message != null) {
        mailer.sendMessage(email, message)
 }
```


### Extension functions

Write new functions for classes that you don't own.

Notice use of `this`:

```
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}
```

Now just call this fuction like any other method on the class:

```
val list = mutableListOf(1, 2, 3)
list.swap(0, 2) // 'this' inside 'swap()' will hold the value of 'list'
```



## Assertions

Helps to enforce *invariants* (conditions that require correct operation of the program)


* `check`: when you want to validate the state of an object
* `require` when checking the value of an argument passed. Throws an Exception if the condition in the parentheses is evaluated to false

E.g.:
```
class Cat(val name: String, val age: Int) {
    val enoughCat = false // Of course, it's a false, there are never enough cats!
    init {
        check(!enoughCat) { "You cannot add a new cat" } // IllegalStateException
        require(age >= 0) { "Invalid age: $age" }        // IllegalArgumentException
    }
}
```




## Object Expressions

Useful for when you need to create a slight modification of an object without needing to declare a subclass.


This is essentially an *anonymous class*:

```
window.addMouseListener(object : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) { /*...*/ }

    override fun mouseEntered(e: MouseEvent) { /*...*/ }
})
```

If a supertype has a constructor, appropriate constructor parameters must be passed to it, using a commma to seperate constructor calls if there are multiple super types:

```
open class A(x: Int) {
    public open val y: Int = x
}

interface B { /*...*/ }

val ab: A = object : A(1), B {
    override val y = 15
}
```

If the class definition is trivial, you can actually just omit the super type and constructor calls:

```
fun foo() {
    val adHoc = object {
        var x: Int = 0
        var y: Int = 0
    }
    print(adHoc.x + adHoc.y)
}
```


*NOTE*: code in the anonymous object can access the variables from the enclosing scope.


## Object declarations / nested objects

### Singletons

Useful to manage global vars and static methods.

The `object` keyword allows you to create a class as a singleton. You can't instantiate this class, but you can use it like a static class:

```

object Single {
    var refs = 0
}

...
val o1 = Single.refs
```

This can be used to implement defaults e.g.:

```
class Cell(var width: Int = BaseProperties.width,
           var height: Int = BaseProperties.height) {
           
   object BaseProperties {
       var width = 10
       var height = 10
    }
}
```
Can also do it like:

```
class Cell {
    object BaseProperties {
        var width = 10
        var height = 10
    }
    var width = BaseProperties.width
    var height = BaseProperties.height
}

```


### Factory

```
class Player(val id: Int) {
    /* creates a new instance of Player */
    object Factory {
        fun create(playerId: Int): Player {
            return Player(playerId)
        }
    }
}



val p13 = Player.Factory.create(13)

```

### Static variables and methods

Can declare a `object` as a nested class to implement static methods and vars (Kotlin doesn't have `static`):

```
class Player(val id: Int) {
    object Properties {
        val defaultSpeed = 7
    }
    //NOTE we use it like a static class
    val superSpeed = Properties.defaultSpeed * 2 // 14
}
...

val s = Player.Properties.defaultSpeed
```
NOTE: the nested object cannot access members from the outer class.


## Companion objects

tl;dr: provides "global variables" per class.

Very similar to nested objects but differs in these ways:

* can omit name, making it shorter to access the companion object's members
* can have only one per class
* can only exist within the context of a super class

Similarities:
* also a singleton


Example:

```
class Dog {
    companion object {
        val numOfPaws: Int = 4
        fun createSound(): String = "WUF-WUF"
    }
}
/*prints WUF-WUF*/
println(Dog.createSound())
```

Outer class can access the companion object's members but not the other way around:

```
class Deck {
    companion object {
        val size = 10
        val height = 2
        fun volume(bottom: Int, height: Int) = bottom * height
    }

    val square = size * size             //100
    val volume = volume(square, height)  //200
}
```
Companion objects can be named.
It has a default name if left unspecified: `Companion`

When companion and outer class has same variable names,
the class will shadow the companion.

Can use `Companion` to still reference them:

```
class Deck {
    companion object {
        val size = 10
    }
    val size = 2
    val square = Companion.size * Companion.size // 100
}
```



## Enums


* Each enum constant is an object.

Basic usage:

```
enum class Direction {
        NORTH, SOUTH, WEST, EAST
}


// or 
enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}
```

Every enum constant has properties to obtain it's name and position:

```
val name: String
val ordinal: Int
```


Enum builtin methods:
```
EnumClass.valueOf(value: String): EnumClass
```
The value in this case is string version of the enum value typically in upper case.


```
EnumClass.values(): Array<EnumClass>
```
Throws `IllegalArgumentException` if value doesn't exist.

If you want to extend the Enum you need to use a Companion object:

```
enum class Rainbow(val color: String, val rgb: String) {
    RED("Red", "#FF0000"),
    ORANGE("Orange", "#FF7F00"),
    YELLOW("Yellow", "#FFFF00"),
    GREEN("Green", "#00FF00"),
    BLUE("Blue", "#0000FF"),
    INDIGO("Indigo", "#4B0082"),
    VIOLET("Violet", "#8B00FF"),
    NULL("", "");

    companion object {
        fun findByRgb(rgb: String): Rainbow {
            for (enum in Rainbow.values()) {
                if (rgb == enum.rgb) return enum
            }
            return NULL
        }
    }

    fun printFullInfo() {
        println("Color - $color, rgb - $rgb")
    }
}
```


