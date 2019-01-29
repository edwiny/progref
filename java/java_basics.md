# Java Syntax

NOTE most of these notes have been assembled from the [Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/)



## Types


* byte  - 8 bit signed int
* short - 16 bit signed int
* int   - 32 bit signed int
* long  - 64 bit signed int
* float - 32 bit double
* double - 64 bit double
* boolean - 'true' or 'false', size is not precisely defined
* char   - 16 bit unicode char. min/max value is '\u0000' / '\uffff'
* String - special, implemmented by java.lang.String

The types listed above, with the exception of String, are primitive types.

They all have class representation too, all deriving from the `Number` class:
* Byte
* Integer
* Double
* Short
* Float
* Long

They share similar methods, that can be used to cast them to related types, or compare:

* byte byteValue()
* short shortValue()
* int intValue()
* long longValue()
* float floatValue()
* double doubleValue()
* int compareTo(Byte anotherByte)
* int compareTo(Double anotherDouble)
* int compareTo(Float anotherFloat)
* int compareTo(Integer anotherInteger)
* int compareTo(Long anotherLong)
* int compareTo(Short anotherShort)
* boolean equals(Object obj)

They also have methods useful for converting to and from String:

* static Integer decode(String s) -	Decodes a string into an integer. Can accept string representations of decimal, octal, or hexadecimal numbers as input.
* static int parseInt(String s) - Returns an integer (decimal only).
* static int parseInt(String s, int radix) - Returns an integer, given a string representation of decimal, binary, octal, or hexadecimal (radix equals 10, 2, 8, or 16 respectively) numbers as input.
* String toString() - Returns a String object representing the value of this Integer.
* static String toString(int i) - Returns a String object representing the specified integer.
* static Integer valueOf(int i) - Returns an Integer object holding the value of the specified primitive.
* static Integer valueOf(String s) - Returns an Integer object holding the value of the specified string representation.
* static Integer valueOf(String s, int radix) - Returns an Integer object holding the integer value of the specified string representation, parsed with the value of radix. For example, if s = "333" and radix = 8, the method returns the base-ten integer equivalent of the octal number 333.

Finally, the Number classes include constants and useful class methods. The MIN_VALUE and MAX_VALUE.

To print primitive types use java.io.PrintStream classes `format` and `printf`.


There is also a Character class that provides methods like `isLetter`, `isDigit`, and `toString`.



### String

Not a traditional primitive type. Special logic in Java to automatically create objects of type `java.lang.String` when you
use double quotes to enclose a string. 

Strings objects are immutable.


There are many overloaded constructs in the String class to create strings from various data formats.

E.g.

```
char[] helloArray = { 'h', 'e', 'l', 'l', 'o', '.' };
String helloString = new String(helloArray);
```
#### String Length

Use the String method  `length()`  e.g.

```
String palindrome = "Dot saw I was Tod";
int len = palindrome.length();
```
#### String concatenation

`string1.concat(string2); `

OR:

`"Hello," + " world" + "!"`



Note: The Java programming language does not permit literal strings to span lines in source files, so you must use the + concatenation operator at the end of each line in a multi-line string. For example:


### Literals

**integers**


```
// The number 26, in decimal
int decVal = 26;
//  The number 26, in hexadecimal
int hexVal = 0x1a;
// The number 26, in binary
int binVal = 0b11010;
```

Can use underscores to make numbers easier to read:

```
long creditCardNumber = 1234_5678_9012_3456L;
```


**floats**

"123.4f" - to force to float, if omitted will be double
"123e2"  or "123E2" - scientific notation


**strings and chars**

Literals of types char and String may contain any Unicode (UTF-16) characters.
You can use a "Unicode escape" such as '\u0108' (capital C with circumflex).

**class literals**

Taking any class and appending `.class` at the end will yield the class name.

### Arrays

Declaration

```
// declares an array of integers. Size is not part of declaration.
int[] anArray;

// allocates memory for 10 integers
anArray = new int[10];
```

**Multi-dimensional arrays**

 multidimensional array is an array whose components are themselves arrays. This is unlike arrays in C or Fortran. A consequence of this is that the rows are allowed to vary in length, e.g.:

 
```
 String[][] names = {
            {"Mr. ", "Mrs. ", "Ms. "},
            {"Smith", "Jones"}
        };
```

**Array Length**

can use the built-in `length` property to determine the size of any array. The following code prints the array's size to standard output:


```
 System.out.println(anArray.length);
```

**Array manipulation**

Copy: use System.arraycopy

public static void arraycopy(Object src, int srcPos,
                             Object dest, int destPos, int length)	


There's a host of utility functions for arrays in `java.util.Arrays`


## Enum types

Example syntax:

```
public enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY 
}
```

All enums implicitly extend java.lang.Enum.

The enum declaration defines a class. The enum class body can include methods and other fields. The compiler automatically adds some special methods when it creates an enum. For example, they have a static values method that returns an array containing all of the values of the enum in the order they are declared. This method is commonly used in combination with the for-each construct to iterate over the values of an enum type:

```
for (Planet p : Planet.values()) {
    System.out.printf("Your weight on %s is %f%n",
                      p, p.surfaceWeight(mass));
}
```
Enums can have composite values:

```
public enum Suit { 
        DIAMONDS (1, "Diamonds"), 
        CLUBS    (2, "Clubs"   ), 
        HEARTS   (3, "Hearts"  ), 
        SPADES   (4, "Spades"  );
        
        private final int value;
        private final String text;
        Suit(int value, String text) {
            this.value = value;
            this.text = text;
        }
        public int value() {return value;}
        public String text() {return text;}
    }
```



## Operators



```
postfix	expr++ expr--
unary	++expr --expr +expr -expr ~ !
multiplicative	* / %
additive	+ -
shift	<< >> >>>
relational	< > <= >= instanceof
equality	== !=
bitwise AND	&
bitwise exclusive OR	^
bitwise inclusive OR	|
logical AND	&&
logical OR	||
ternary	? :
assignment	= += -= *= /= %= &= ^= |= <<= >>= >>>=
```

Use of `instanceof` operator
```
System.out.println("obj1 instanceof Parent: "
            + (obj1 instanceof Parent));
```

## Control flow

### if-then-else

```
void applyBrakes() {
    if (isMoving) {
        currentSpeed--;
    } else {
        System.err.println("The bicycle has already stopped!");
    } 
}
```

### switch

A switch works with the byte, short, char, and int primitive data types. 
It also works with enumerated types (discussed in Enum Types), the String class, and a few special classes that wrap certain primitive types: Character, Byte, Short, and Integer (discussed in Numbers and Strings).


```
int month = 8;
        String monthString;
        switch (month) {
            case 1:  monthString = "January";
                     break;
            case 2:  monthString = "February";
                     break;
            default: monthString = "Invalid month";
                     break;
         }
```

### while loop

```
while (count < 11) {
            System.out.println("Count is: " + count);
            count++;
        }
```

### do-while loop

```
int count = 1;
        do {
            System.out.println("Count is: " + count);
            count++;
        } while (count < 11);
```

### for loops

```
for(int i=1; i<11; i++){
              System.out.println("Count is: " + i);
         }
```


### enhanced for loops aka 'for-each construct'

The for statement also has another form designed for iteration through `Collections` and `arrays`. This form is sometimes referred to as the enhanced for statement and should be used wherever possible:

```
 int[] numbers =  {1,2,3,4,5,6,7,8,9,10};
         for (int item : numbers) {
             System.out.println("Count is: " + item);
         }
```

The object being iterated must implement the `Iterable` interface.


### break and continue

These work as expected in loops.

-------------------------------------------------------------------------------


## Classes Overview

An object stores its state in **fields**  and exposes its behavior through **methods**. Methods operate on an object's internal state and serve as the primary mechanism for object-to-object communication. Hiding internal state and requiring all interaction to be performed through an object's methods is known as data encapsulation.

A class's fields, methods, and nested types are collectively called its **members**.


Benefits of objects:
* Modularisation - code for an object can be written and maintained independently of other objects
* Encapsulation  - by interacting only with a object's methods, you are able to hide the implementation details from outside
* Re-use         - allows specialist units of code to be re-used
* Pluggability   - (and debug ease) can replace one object for another that contains more diagnostics



### Syntax

Definition:
```
class Bicycle {


}
```

Inheritance:

```
class MountainBike extends Bicycle {

    // new fields and methods defining 
    // a mountain bike would go here

}
```

Interface:

An interface is a group of related methods with empty bodies. 
Implementing an interface allows a class to become more formal about the behavior it promises to provide. Interfaces form a contract between the class and the outside world, and this contract is enforced at build time by the compiler

```
interface Bicycle {

 
    void changeGear(int newValue);

    void speedUp(int increment);
}

```

to implement:

```
class ACMEBicycle implements Bicycle {

    int cadence = 0;
    int speed = 0;
    int gear = 1;

   // The compiler will now require that methods
   // changeGear, speedUp, all be implemented. Compilation will fail if those
   // methods are missing from this class.


    void changeGear(int newValue) {
         gear = newValue;
    }

    void speedUp(int increment) {
         speed = speed + increment;   
    }
}
```

 Package:

 Package is a namespace that organizes a set of related classes and interfaces. 



-------------------------------------------------------------------------------



## Variables

Variable types are based on where they are declared:

  * Fields - member variables in a class.
  	* fields can be broken down into instance, class, and final variables
  * Local variables - in a method of block of code- Local variables and parameters are always classified as "variables" (not "fields"). 
  * Parameters - in a method declararation


All variable types have default values, except local variables. Referencing a local var without explicitly initialising it will result in compile time error.

### instance variables 

Regular vars, one per instance of each class. 

```
int cadence = 0;
```

### class variables (static)
Only one copy per class, regardless of the number of objects of that class.

```
static int numGears = 6
```

Class variables are access by the class name and variable name, e.g `Bicycle.numberGears`
Can do it via object reference as well (e.g. bike1.numberGears) but this is discouraged cause it doesn't make it clear it's a class variable.



### final variables ([static] final)

```
static final numGears = 6;
```
Value can be assigned only once.

### Access Modifiers


#### public

Can be accessed by all classes.
To promote encapsulation, fields should not be public but private or protected and accessed via public getter/setter methods.

#### private

Can be accessed only by current class

#### protected 

Can be accessed by own class and any sub classes.

### Naming convention

- first letter of field names should be lowercase, subsequent words capitalised

-------------------------------------------------------------------------------

## Methods

Basic syntax:

```
public double calculateAnswer(double wingSpan, int numberOfEngines,
                              double length, double grossTons) {
    //do the calculation here
}
```

The only required elements of a method declaration are the method's return type, name, a pair of parentheses, (), and a body between braces, {}

**method signature: the method's name and the parameter types**

### Naming convention

Should be a verb in lowercase or a multi-word name that begins with a verb in lowercase, followed by adjectives, nouns, etc. In multi-word names, the first letter of each of the second and following words should be capitalized

### Overloading methods

You can define multiple methods with the same name if their parameter signature differs, e.g. draw(String s), draw(int i)

The compiler does not consider return type when differentiating methods, so you cannot declare two methods with the same signature even if they have a different return type. (*NOTE* unless it's a covariant return type - see below)

Overloaded methods should be used sparingly, as they can make code much less readable.

### Covariant return types

Methods can return a class. The object actually returned can be of the same class in the method declaration, OR a subclass of it.

Although return types can be typically not be overridden, you can override the return type as long as the return type is a subclass of the type returned in the original method.

### Parameters vs Arguments

 Parameters refers to the list of variables in a method declaration. Arguments are the actual values that are passed in when the method is invoked. When you invoke a method, the arguments used must match the declaration's parameters in type and order.

### Passing methods into methods

Use a lambda expression or a method reference.

### Varargs

To pass an arbitrary number of values to a method (including none)

Use elipses followed by name of parameter. Inside the method, use  

```
public Polygon polygonFrom(Point... corners) {
    int numberOfSides = corners.length;
    double squareOfSide1, lengthOfSide1;
    squareOfSide1 = (corners[1].x - corners[0].x)
                     * (corners[1].x - corners[0].x) 
                     + (corners[1].y - corners[0].y)
                     * (corners[1].y - corners[0].y);
    lengthOfSide1 = Math.sqrt(squareOfSide1);

    // more method body code follows that creates and returns a 
    // polygon connecting the Points
}

```

The method can be called either with an array or with a sequence of arguments

If arguments are of different types, use like printf:

```
public PrintStream printf(String format, Object... args)
```


### Passing arguments by value or reference

Primitive arguments, such as an int or a double, are passed into methods by value. This means that any changes to the values of the parameters exist only within the scope of the method. When the method returns, the parameters are gone and any changes to them are lost

Objects also passed into methods by value. This means that when the method returns, the passed-in reference still references the same object as before. However, the values of the object's fields can be changed in the method, if they have the proper access level.

### Class (static) methods

Declared with `static` keyword.

Like class vars, can be used without a object instantiation. 
Cannot access instance fields.
Cannot use 'this' keyword


-------------------------------------------------------------------------------

## Member Visibility Summary 

There are two levels of access:

* top-level: i.e. at the class level
	* public
	* no modifier (package-private - default)
* at member level (remmber member refers to methods and fields)
	* public
	* protected
	* no modifier (package-private, default)
	* private


There are 4 categories of access that needs to be controlled:

* Class: methods of the same class
* Package: classes in the same package
* Subclass: subclasses of the class outside of package
* Workd: all classes

The 4 access modifiers controls the access that methods of classes from the 4 categories has to a class's members like this:

* `public`: 						class, package, subclass, world
* `protected`: 						class, package, subclass
* no modifier (*package-private*): 	class, package
* `private`: 						class


### Notes on Source File Declarations

- Can only declare one PUBLIC  class per file.
- Name of file should be same as the public class name + .java
- If class part of a package, then package statement must be first statement in the source file.
- If importing classes, the import statement must be between package statement (if any) and first class declaration
- Import and package statements will imply to all the classes present in the source file. It is not possible to declare different import and/or package statements to different classes in the source file.


### Access Control at Class Level


Keyword | Visibility                                    
--------|-------------------------------------------------------------
(none)  | Package-private.  Class only visible within it's own package 
public  | Class is visible to all other classes 

Classes cannot be protected or private.


### Access Control at Method level

Modifier | Class | Package | Subclass | World
---------|-------|---------|----------|------
public   | Y     | Y       | Y        | Y
protected| Y     | Y       | Y        | N
no modifi| Y     | Y       | N        | N
private  | Y     | N       | N        | N

The first data column indicates whether the class itself has access to the member defined by the access level. As you can see, a class always has access to its own members. The second column indicates whether classes in the same package as the class (regardless of their parentage) have access to the member. The third column indicates whether subclasses of the class declared outside this package have access to the member. The fourth column indicates whether all classes have access to the member.

Best practice:
Make members private unless you have good reason not to.


### Modifiers

- Static - for class methods and variables
- Final

A final variable can be explicitly initialized only once. A reference variable declared final can never be reassigned to refer to an different object.
However, the data within the object can be changed. So, the state of the object can be changed but not the reference.
With variables, the final modifier often is used with static to make the constant a class variable.

A final method cannot be overridden by any subclasses. As mentioned previously, the final modifier prevents a method from being modified in a subclass.

Final classes - prevent the class from being subclassed.


#### Abstract

An abstract class can never be instantiated. If a class is declared as abstract then the sole purpose is for the class to be extended.
An abstract class does not need to contain abstract methods.

An abstract method is a method declared without any implementation. The methods body (implementation) is provided by the subclass. 
The abstract method ends with a semicolon. Example: public abstract sample();

#### Synchronized

The synchronized keyword used to indicate that a method can be accessed by only one thread at a time. The synchronized modifier can be applied with any of the four access level modifiers.

#### Transient

An instance variable is marked transient to indicate the JVM to skip the particular variable when serializing the object containing it.
This modifier is included in the statement that creates the variable, preceding the class or data type of the variable.

E.g.: public transient int limit = 55; // will not persist

#### Volatile


The volatile modifier is used to let the JVM know that a thread accessing the variable must always merge its own private copy of the variable with the master copy in the memory.
Accessing a volatile variable synchronizes all the cached copied of the variables in the main memory. Volatile can only be applied to instance variables, which are of type object or private. A volatile object reference can be null.



### Naming Conventions

Use of underscores and dollars permitted should not be used (except in constants)

* class name           - start with Upper Case and be a Noun 
* interface name       - start with Upper Case and be a Adjective, e.g. Runnable, Remote
* method name          - start with lower case and be a verb e.g. actionPerformed()
* variables and fields - start with lower case e.g. firstName
* package name         - all lowercase
* constants            - all uppercase



### Inner Classes and Static Nested Classes

A nested class is a member of its enclosing class. Non-static nested classes (inner classes) have access to other members of the enclosing class, even if they are declared private. 

Static nested classes do not have access to other members of the enclosing class. As a member of the OuterClass, a nested class can be declared private, public, protected, or package private. (Recall that outer classes can only be declared public or package private.)




-------------------------------------------------------------------------------
## Classes - Detail

### Constructors

 Constructor declarations look like method declarations—except that they use the name of the class and have no return type:

```
 public Bicycle(int startCadence, int startSpeed, int startGear) {
    gear = startGear;
    cadence = startCadence;
    speed = startSpeed;
}
```

Constructors can call other constructors (see description of `this` keyword). 
If present, the invocation of another constructor must be the *first line* in the constructor.

A class can have multiple constructors, as per the rules of method overloading.

Constructors are optional. The compiler automatically provides a no-argument, default constructor for any class without constructors. This default constructor **will call the no-argument constructor of the superclass**. In this situation, the compiler will complain if the superclass doesn't have a no-argument constructor so you must verify that it does. If your class has no explicit superclass, then it has an implicit superclass of Object, which does have a no-argument constructor.

### Creating objects

To create a new object from a class:

```
Bicycle myBike = new Bicycle(30, 0, 8);
```

You can only instantiate a object through the `new` keyword, followed by a constructor. This will allocate memory.

Objects can only be declared dynamically, via new. If you declare it as a local variable,
it will only declare a typed reference to that class. 


### Using this keyword

Within an instance method or a constructor, this is a reference to the current object — the object whose method or constructor is being called. You can refer to any member of the current object from within an instance method or a constructor by using this.

From within a constructor, you can also use the this keyword to call another constructor in the same class. Doing so is called an explicit constructor invocation. E.g.

```
public class Rectangle {
    private int x, y;
    private int width, height;
        
    public Rectangle() {
        this(0, 0, 1, 1);
    }
    public Rectangle(int width, int height) {
        this(0, 0, width, height);
    }
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    ...
}

```


### Initialising static and instance members

In member declarations you can initialise only to simple values. To do more complex initialisation you can
use initialiser blocks.


#### static initializer blocks

A class can have multiple blocks like this:

```
static {
	# do complex initialisation here
}
```
They are guaranteed to execute in order of declaration.

There is an alternative to static blocks — you can write a private static method.


#### instance member initializer blocks

look just like static initializer blocks, but without the static keyword:

```
 {
 	# do complex init here
 }
```


The Java compiler copies initializer blocks into every constructor. Therefore, this approach can be used to share a block of code between multiple constructors.


### Nested classes

It is a way of logically grouping classes that are only used in one place.

Can be either "inner" (instance) or "static":

```
class OuterClass {
    ...
    static class StaticNestedClass {
        ...
    }
    class InnerClass {
        ...
    }
}
```

Static nested classes cannot refer to any instance members of the enclosing class.

Instantiate by:

```
OuterClass.StaticNestedClass nestedObject =
     new OuterClass.StaticNestedClass();
```

A inner classe is associated with an instance of its enclosing class and has direct access to that object's methods and fields. Also, because an inner class is associated with an instance, it cannot define any static members itself.


To instantiate an inner class, you must first instantiate the outer class. Then, create the inner object within the outer object with this syntax:

```
OuterClass.InnerClass innerObject = outerObject.new InnerClass();

```
### Local and anonymous classes

These are special types of inner classes. 

Local class is defined within the body of a method.

Anonomous classes are same as local class but without class name.

#### Local classes

You can define a local class inside any block (anywhere a expression is valid syntax).

A local class has access to the members of its enclosing class.

a local class has access to local variables. However, a local class can only access local variables that are declared final, or are *effectuvely final*.

If you declare the local class in a method, it can access the method's parameters.

Declarations of a type (such as a variable) in a local class shadow declarations in the enclosing scope that have the same name

#### Local classes vs inner classes

* Local classes are similar to inner classes because they cannot define or declare any static members
* Local classes are non-static because they have access to instance members of the enclosing block. Consequently, they cannot contain most kinds of static declarations.
* You cannot declare static initializers or member interfaces in a local class.

#### Anonymous classes

 They enable you to make your code more concise. They enable you to declare and instantiate a class at the same time. They are like local classes except that they do not have a name. Use them if you need to use a local class only once.

Anonymous classes are expressions, so you can declare them in other expressions:

Example:


```

public class HelloWorldAnonymousClasses {
  
    interface HelloWorld {
        public void greet();
        public void greetSomeone(String someone);
    }
  
    public void sayHello() {
        
        //this is a named local class
        class EnglishGreeting implements HelloWorld {
            String name = "world";
            public void greet() {
                greetSomeone("world");
            }
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Hello " + name);
            }
        }
      
        HelloWorld englishGreeting = new EnglishGreeting();
        
        //here is the anon class, see it's smaller

        //after 'new', its interface to implmenet or class to extend
        //you can also pass args to constructor (in case of interface, it's always empty)

        HelloWorld frenchGreeting = new HelloWorld() { 
            String name = "tout le monde";
            public void greet() {
                greetSomeone("tout le monde");
            }
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Salut " + name);
            }
        };
        //note also how the anon class is part of an expression ( in
        	// this case, it's assigned to a variable)
        	// also note the semi-colon at the end
        
  
        englishGreeting.greet();
        frenchGreeting.greetSomeone("Fred");
    }

    public static void main(String... args) {
        HelloWorldAnonymousClasses myApp =
            new HelloWorldAnonymousClasses();
        myApp.sayHello();
    }            
}
```

Like with local classes:
* An anonymous class has access to the members of its enclosing class.
* An anonymous class cannot access local variables in its enclosing scope that are not declared as final or effectively final.
*  declaration of a type (such as a variable) in an anonymous class shadows any other declarations in the enclosing scope that have the same name
* You cannot declare static initializers or member interfaces in an anonymous class.
* An anonymous class can have static members provided that they are constant variables.
*  you cannot declare constructors in an anonymous class.


-------------------------------------------------------------------------------



## Lambda expressions

For a good tutorial see http://tutorials.jenkov.com/java/lambda-expressions.html

Lambdas are just anonymous methods where the compiler auto matches type and arguments based on the context where it is expressed.

One issue with anonymous classes is that if the implementation of your anonymous class is very simple, such as an interface that contains only one method, then the syntax of anonymous classes may seem unwieldy and unclear. In these cases, you're usually trying to pass functionality as an argument to another method, such as what action should be taken when someone clicks a button. Lambda expressions enable you to do this, to treat functionality as method argument, or code as data.


Although a anonymous class is often more concise than a named class, for classes with only one method, even an anonymous class seems a bit excessive and cumbersome. Lambda expressions let you express instances of single-method classes more compactly.

Lambdas vs anon classes:
* Lamda expressions are stateless where anon classes can define instance members and keep state


Lambdas rely on functional interfaces for type matching.
**functional interface** is any interface that contains only one abstract method. A functional interface may contain one or more default methods or static methods. Because a functional interface contains only one abstract method, you can omit the name of that method when you implement it. To do this, instead of using an anonymous class expression, you use a lambda expression.


Examples. Consider following interface:
```
public interface StateChangeListener {

    public void onStateChange(State oldState, State newState);

}

```

In Java 7 you could use a anonomous class to implement the interface:

```
StateOwner stateOwner = new StateOwner();

stateOwner.addStateListener(new StateChangeListener() {

    public void onStateChange(State oldState, State newState) {
        // do something with the old and new state.
    }
});
```

In Java 8, you can in stead use a lambda to express the same:

```
StateOwner stateOwner = new StateOwner();

stateOwner.addStateListener(
    (oldState, newState) -> System.out.println("State changed")
);
```

The lambda is type matched to the interface `StateChangeListener` via the context of the method definition. The interface has only
one method (`onStateChange`) and from here the compiler infers the arguments to the method call `(State oldState, State newState)`

NOTE that an interface is still a functional interface even if it contains default and static methods, as long as the interface only contains a single unimplemented (abstract) method.

Lambdas can have zero or more parameters:

* zero: `() -> System.out.println("Zero parameter lambda");`
* one: `param -> System.out.println("One parameter: " + param);`  (NOTE that parenthesis can be omitted)
* multiple: `(p1, p2) -> System.out.println("Multiple parameters: " + p1 + ", " + p2);`
* typed: `(Car car) -> System.out.println("The car is: " + car.getName());`

The function body:
 * if it returns a value then can omit braces and return statement. Eg

 ```
 (a1, a2) -> { return a1 > a2; }
  ```
  is equivalent to

  ```
  (a1, a2) -> a1 > a2;
  ```

Lambda expressions are objects, so you can assign them to vars and pass them around:

```
MyComparator myComparator = (a1, a2) -> return a1 > a2;

boolean result = myComparator.compare(2, 5);
```


### method references

See http://tutorials.jenkov.com/java/lambda-expressions.html#single-method-interface

Sometimes a lambda expression does nothing but call an existing method.  It's often clearer to refer to the existing method by name.


Use the `::` syntax to denote a method reference.

These two snippets are equivalent:

```
Arrays.sort(rosterAsArray,
    (a, b) -> Person.compareByAge(a, b)
);
```

same as

```
Arrays.sort(rosterAsArray, Person::compareByAge);
```


-------------------------------------------------------------------------------



## Annotations

Supposed to provide compile time, build-time and runtime instructions to compiler.

Syntax:

```
//Basic
@Entity

@Entity(tableName = "vehicles")

//Convention, if only one argument, to call it 'value'
@InsertNew(value = "yes")

//Convention is to leave 'value' out
@InsertNew("yes")

```
You can place Java annotations above classes, interfaces, methods, method parameters, fields and local variables. Here is an example annotation added above a class definition.

### Built-in Java annotations

**@Deprecated**

The compiler will give you a warning if using class, method or interface marked with this.

**@Override**

used above methods that override methods in a superclass. If the method does not match a method in the superclass, the compiler will give you an error.
This is used to let the compiler warn you when you are expecting to override a method in a base class, and the method does not exist in the base class. 

**SuppressWarnings**

makes the compiler suppress warnings for a given method. For instance, if a method calls a deprecated method, or makes an insecure type cast

### Creating your own annotations

```
@interface MyAnnotation {

    String   value() default "";

    String   name();
    int      age();
    String[] newNames();

}
```
This would be used like this:

```
@MyAnnotation(
    value="123",
    name="Jakob",
    age=37,
    newNames={"Jenkov", "Peterson"}
)
public class MyClass {


}
```

To make annotation visisble during runtime, use **@Retention** annotation to annotate your annotation:

```
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

@interface MyAnnotation {

    String   value() default "";

}
```

Use **@Target** to specify which Java element to apply annotation to (e.g. constructors, methods, classes, etc)

```
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
public @interface MyAnnotation {

    String   value();
}
```

You can use the Java Reflection API to retrieve annotations at runtime. See https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AnnotatedElement.html



-------------------------------------------------------------------------------



## Interfaces

interface is a reference type, similar to a class, that can contain only constants, method signatures, default methods, static methods, and nested types. Method bodies exist only for default methods and static methods. Interfaces cannot be instantiated—they can only be implemented by classes or extended by other interfaces.

A class that implements an interface **must implement all the methods** declared in the interface.

Syntax:

```
public interface OperateCar {

   // constant declarations, if any

   // method signatures
   
   // An enum with values RIGHT, LEFT
   int turn(Direction direction,
            double radius,
            double startSpeed,
            double endSpeed);
}
```

To use an interface, you write a class that implements the interface. When an instantiable class implements an interface, it provides a method body for each of the methods declared in the interface.


An interface can extend other interfaces, just as a class subclass or extend another class. However, whereas a class can extend only one other class, an interface can extend any number of interfaces. The interface declaration includes a comma-separated list of all the interfaces that it extends.


The interface body can contain:
* abstract methods - methods with no braces
* default methods - methods with `default` keyword
* static methods - 
* constants - all constant values defined in an interface are implicitly public, static, and final. Uou can omit these modifiers.

To declare a class that implements an interface, you include an `implements` clause in the class declaration. Your class can implement more than one interface, so the implements keyword is followed by a comma-separated list of the interfaces implemented by the class. By convention, the implements clause follows the `extends` clause, if there is one.


### default methods
In  general you cannot extend existing interfaces without breaking the compilation of every class that implements that interface.

Default methods enable you to add new functionality to existing interfaces and ensure binary compatibility with code written for older versions of those interfaces. In particular, default methods enable you to add methods that accept lambda expressions as parameters to existing interfaces.

#### Standard Java functional interfaces

defined in `java.util.function`


-------------------------------------------------------------------------------

## Inheritance

Java classes cannot extend multiple classes, only one. It does however support *multiple type inheritance* which means it can implement multiple interfaces.

The access specifier for an overriding method can allow more, but not less, access than the overridden method. For example, a protected instance method in the superclass can be made public, but not private, in the subclass.


### Overriding instance methods

Methods with same signature in subclass override the parent. The overriding method can also return a subtype of the parent's return type (called *covariance*). 

It's good practice to use the `@Override` annotation.

### Overriding static methods

Static methods that override static methods in the superclass **hides** the superclass method.

The one that is invoked depends on where it is called from-  it depends on whether it is invoked from the superclass or the subclass.


### Polymorphism

Subclasses of a class can define their own unique behaviors and yet share some of the same functionality of the parent class.

As a user of these classes, different subclasses may look the same as parent class to you but have behaviour specific to their types.


### Accessing super class methods

You can invoke the overridden method through the use of the keyword super. You can also use super to refer to a hidden field (although hiding fields is discouraged). 

E.g.

```
 public void printMethod() {
        super.printMethod();
        System.out.println("Printed in Subclass");
    }
```

### Accessing super class constructors

Invocation of a superclass constructor **must be the first line** in the subclass constructor.

```
super();  
//or:
super(parameter list);
```

*NOTE: If a constructor does not explicitly invoke a superclass constructor, the Java compiler automatically inserts a call to the no-argument constructor of the superclass. If the super class does not have a no-argument constructor, you will get a compile-time error. Object does have such a constructor.*

### Object as a superclass

Every class is a descendant, direct or indirect, of the `Object` class.

It has some useful methods.  if you choose to do so, you may need to override them with code that is specific to your class:

* `protected Object clone() throws CloneNotSupportedException` - Creates and returns a copy of this object.
* `public boolean equals(Object obj)` - Indicates whether some other object is "equal to" this one.
* `protected void finalize() throws Throwable` - Called by the garbage collector on an object when garbage collection determines that there are no more references to the object
* `public final Class getClass()` - Returns the runtime class of an object.
* `public int hashCode()`-  Returns object's memory address in hex, used for checking of objects are equal. If you override `equals()` method you also need to override hashCode.
* `public String toString()` - Returns a string representation of the object.


### Final Classes and methods

ou can declare some or all of a class's methods final. You use the final keyword in a method declaration to indicate that the method cannot be overridden by subclasses. The Object class does this—a number of its methods are final.

Methods called from constructors should generally be declared final. If a constructor calls a non-final method, a subclass may redefine that method with surprising or undesirable results.

Declaring entire class final means it cannot be subclassed - useful for creating immutable classes.

### Abstract classes

An abstract class is a class that is declared `abstract` —it may or may not include abstract methods. Abstract classes cannot be instantiated, but they can be subclassed

If a class includes abstract methods, then the class itself must be declared abstract, as in:

```
public abstract class GraphicObject {
   // declare fields
   // declare nonabstract methods
   abstract void draw();
}
```

Abstract vs interfaces:

Abstract classes can keep state whereas interfaces cannot. However classes can only extend one abstract class whereas it can implement multiple interfaces.

-------------------------------------------------------------------------------
## Generics / Templates

Parameterised types / classes. 
Declaration syntax: 

```
class name<T1, T2, ..., Tn> { /* ... */ }
```


Example:

```
public class Box<T> {
    // T stands for "Type"
    private T t;

    public void set(T t) { this.t = t; }
    public T get() { return t; }
}
```
A type variable can be any non-primitive type.

Naming conventions:


* E - Element (used extensively by the Java Collections Framework)
* K - Key
* N - Number
* T - Type
* V - Value
* S,U,V etc. - 2nd, 3rd, 4th types

Instatiating a type:


```
Box<Integer> integerBox = new Box<Integer>();
```

Type parameter: T
Type argument: Integer

Can use *type inference* to omit the 2nd `Integer` in above example (Java SE 7 and up):

```
Box<Integer> integerBox = new Box<>();

```


## Exceptions

Definition of exception: An exception is an event, which occurs during the execution of a program, that disrupts the normal flow of the program's instructions.

When an error occurs within a method, the method creates an object and hands it off to the runtime system. The object, called an exception object, contains information about the error, including its type and the state of the program when the error occurred. Creating an exception object and handing it to the runtime system is called throwing an exception.

After a method throws an exception, the runtime system attempts to find something to handle it. The set of possible "somethings" to handle the exception is the call stack.

The runtime system searches the call stack for a method that contains a block of code that can handle the exception. This block of code is called an exception handler. The search begins with the method in which the error occurred and proceeds through the call stack in the reverse order in which the methods were called. When an appropriate handler is found, the runtime system passes the exception to the handler. An exception handler is considered appropriate if the type of the exception object thrown matches the type that can be handled by the handler.

The exception handler chosen is said to catch the exception. If the runtime system exhaustively searches all the methods on the call stack without finding an appropriate exception handler, as shown in the next figure, the runtime system (and, consequently, the program) terminates.

### The "Catch-or-Specify" requirement

Code that might throw certain exceptions must be enclosed by either of the following:

* A try statement that catches the exception. The try must provide a handler for the exception.
* A method that specifies that it can throw the exception. The method must provide a throws clause that lists the exception.

### Checked vs Unchecked Exceptions

Checked exceptions:
* all exceptions execpt exceptions derived from the Error or RuntimeException classes
* subject to the "catch-or-specify" requirement
* programmer can typically anticipate these and make application recoverable

Unchecked exceptions
* Derived from Error class: typically hardware faults that the app cannot recover from
* Derived from RuntimeException class: logic error (e.g. unexpected Null), app cannot recover, it's better to show failure to highlight the bug
* not subject to catch-or-specify, will cause application stack trace runtime abort

### Syntax

```
try {

} catch (IndexOutOfBoundsException e) {
    System.err.println("IndexOutOfBoundsException: " + e.getMessage());
} catch (IOException e) {
    System.err.println("Caught IOException: " + e.getMessage());
}
```

### The finally block

Always executes, even if no exception caught. Good practice to do cleanup of resources here:
```
finally {
    if (out != null) { 
        System.out.println("Closing PrintWriter");
        out.close(); 
    } else { 
        System.out.println("PrintWriter not open");
    } 
} 
```
### try with resources

Shortcut for using a finally block, with some subtle semantic differences.
The classes instantiated in the try argument list must all implement  the `java.lang.AutoCloseable` interface.
Their `close()` method will be called when an exception occurs or if the method finishes normally.

Multiple objects can be instantiated in the try argument list, their close methods will be called in reverse order.


```
static String readFirstLineFromFile(String path) throws IOException {
    try (BufferedReader br =
                   new BufferedReader(new FileReader(path))) {
        return br.readLine();
    }
}
```

A try-with-resources statement can have catch and finally blocks just like an ordinary try statement. In a try-with-resources statement, any catch or finally block is run **after** the resources declared have been closed.


### Suppressed exceptions

When an exception occurs in a finally block or catch 
If an exception occurs in the 'try-with-resources' block and in the try block, the latter will be propagated by the block and the former will be surpressed. It can be accessed with the `Throwable.getSuppressed` method on the exception thrown by the try block.

### Specifying the exceptions thrown by a method

`public void writeList() throws IOException, IndexOutOfBoundsException {`


Remember that IndexOutOfBoundsException is an unchecked exception; including it in the throws clause is not mandatory. You could just write the following.

`public void writeList() throws IOException {`



### Throwing exceptions

You can throw only objects that inherit from the java.lang.Throwable class

```
if (size == 0) {
        throw new EmptyStackException();
    }
```

### The Throwable class hierarchy

```
                Throwable
    Error                       Exception
                                RuntimeException
```
* Error: serious error, typically reserved for Java runtime, do not use in typical progreams
* Exception: checked. A problem occurred but not serious, the typical use case
* RuntimeExeception: unchecked, incorrect use of an API


### Chained Exceptions

It's common for the catch clause of a try block to generate a new exception if a exception has been caught:

```
try {

} catch (IOException e) {
    throw new SampleException("Other IOException", e);
}
```
The original exception is passed into the new exception through a constructor arg.

`Throwable` also has these methods to set and get a cause for an existing exception:
* `getCause()`
* `initCause()`

### Getting stack trace info from an exception

```
catch (Exception cause) {
    StackTraceElement elements[] = cause.getStackTrace();
    for (int i = 0, n = elements.length; i < n; i++) {       
        System.err.println(elements[i].getFileName()
            + ":" + elements[i].getLineNumber() 
            + ">> "
            + elements[i].getMethodName() + "()");
    }
}
```
### Writing your own exception classes

There is a lof of existing exceptions classes part of the standard java runtime, but you can decide to write your own if any of the following holds true:

* Do you need an exception type that isn't represented by those in the Java platform?
* Would it help users if they could differentiate your exceptions from those thrown by classes written by other vendors?
* Does your code throw more than one related exception?
* If you use someone else's exceptions, will users have access to those exceptions? A similar question is, should your package be independent and self-contained?

### checked vs unchecked

If a client can reasonably be expected to recover from an exception, make it a checked exception. If a client cannot do anything to recover from the exception, make it an unchecked exception.

-------------------------------------------------------------------------------
