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

Function parameters can have default values e.g. `fun read( b: Array<Byte>, off: Int = 0)` to reduce number of method overloads

### Named arguments

When calling a function you can include the name of the parameter and mix it with positional arguments e.g.:

'
reformat(
    'String!',
    false,
    upperCaseFirstLetter = false,
    divideByCamelHumps = true,
    '_'
)
`

Any named arguments omitted will use the default values.
            

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


## Lambdas

Functions are *first-class* i.e. they can be stored in variables and passed around as arguments.

Higher-order functions are functions that take functions are arguments or return them.

Lambda expressions are often used to *instantiate* specific higher-order function types.

Example of passing in a lambda expression as an argument:

```
acc: Int, i: Int -> 
    print("acc = $acc, i = $i, ") 
    val result = acc + i
    println("result = $result")
    // The last expression in a lambda is considered the return value:
    result
```

The paramater types are optional as they can be inferred, and the value of the last expression is returned, so this enables us to shorten to:

```
acc, i -> acc + i
```

Context of a function call:

```
val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })
```


### Function types

There a 3 classes of function types - regular function types, receiver function types, and suspend function types:

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


### Instantiating function types

#### Lambdas

Lambda expressions are basically instantiating function types.

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


## Strings

* Strings are immutable
* Chars in a string can be accessed as `s[i]`
* Can concatenate strings with `+`. 

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


## Classes

Class declarations consist of the class name, class header (optional) and class body (optional).

### Constructors

**Primary constructor** goes in the class header:

```
class Person constructor(firstName: String) { /* class body */ }

// OR if no annotations required on constructor:

class Person(firstName: String) { /* class body */ }

```
The primary constructor cannot contain any code. 

You can use `val` and `var` in the primary constructor parameter list to automatically defines class properties from
the parameter list and assign the values passed in at create time. 

When you write val/var within the constructor, it declares a property inside the class. When you do not write it, it is simply a parameter passed to the primary constructor, where you can access the parameters within the init block or use it to initialize other properties. 

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


### Data classes

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





## Control flow

### if

If statements are expressions in Kotlin, i.e. they return values. That is why there's no ternary operator:

```
val max = if (a > b) a else b
```

The last expression is the value of the block:

```
val max = if (a > b) {
    print("Choose a")
    a
} else {
    print("Choose b")
    b
}
```

If you're using if as an expression rather than a statement (for example, returning its value or assigning it to a variable), the expression is required to have an else branch.



### when

Replaces the `switch` statement from other languages:

```
when (x) {
    1 -> print("x == 1")
    2 -> print("x == 2")
    
    # can use commas to group common matches
    0, 1 -> print("x == 0 or x == 1")
    
    in 1..10 -> print("x is in the range")
    in validNumbers -> print("x is valid")
    
    else -> { // Note the block
        print("x is neither 1 nor 2")
    }
}
```

Can use to replace if statements (no argument supplied):

```
when {
    x.isOdd() -> print("x is odd")
    y.isEven() -> print("y is even")
    else -> print("x+y is even.")
}
```

Can also be treated as expression, i.e. assign to a variable:

```
fun Request.getBody() =
        when (val response = executeRequest()) {
            is Success -> response.body
            is HttpError -> throw HttpException(response.status)
        }
```



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


## Object Expressions

Useful for when you need to create a slight modification of an object without needing to declare a subclass.

### Object Expressions

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


### Object declarations



## Enums


* Each enum constant is an object.
* 

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


Enum classes in Kotlin have synthetic methods allowing to list the defined enum constants and to get an enum constant by its name:
(EnumClass represents the particular enum you're working with)

```
EnumClass.valueOf(value: String): EnumClass
EnumClass.values(): Array<EnumClass>
```
Throws `IllegalArgumentException` if value doesn't exist.




