# The Factory Pattern


Instantiation or using the `new` keyword implies a hard dependency on a concrete class.
This is kinda bad. Think of this as configuration or data, which you want to keep
separate as much as possible from the code.

The factory methods all strive to move instantiation away from the main code and find
a spot where it is more appropriate.

There's 3 different patterns

## Simply Factory Pattern

Not really a pattern, more of a idiom.

Move object creation to a separate class or static method.


Typical classes:

* SimplePizzaFactory
  * createPizza()   //returns the concrete class
* PizzaStore
  * PizzaStore(SimplePizzaFactory pizzaFactory)
  * orderPizza()
* Pizza
  * ...
* CheesePizza implements Pizza
  * ...
* VeggiePizza implement Pizza
  * ...
* ClamPizza implements Pizza
  * ...

## Factory Method Pattern

Defines an *interface* for creating object, but lets subclasses decide which class to instantiate.
I.e. it lets a class defer instantiation to subclasses. Another way to think of it is the configuration
(i.e. the if statements that instantiates classes based on a string argument)
is encoded in the subclasses, while the main business logic sits in the parent class.

Typical classes:

* Creator  //abstract class
  * anOperation() //contains common code part, also calls factoryMethod()
  * abstract Product factoryMethod(String creatonArg)  //abstract so subclassses contain instantation
* ConcreteCreator1 inherits from Creator
  * Product factoryMethod(String creationArg) // instantiates the correct sub class of Product
* Product //abstract class
* ConcreteProduct1 inherits from Product
* ConcreteProduct2 inherits from Product


* Main code
  * Creator creator  = new ConcreteCreator1(String creationArg)
  * creator.anOperation()

The ConcreteCreator classes will have a `if` statement to instantiate the appropriate 
object based on the `creationArg`.

Note that the client / main code never directly instantiates the Product class.

## Abstract Factory Pattern

Provides an interface for creating families of related or dependent objects without specifying their concrete classes. This allows the client to be decoupled from directly instantiating concrete Product classes.

Except of course the factory object. The Factory is the thing that stays the same whereas the Products can vary.

Class list:

* AbstractFactory (interface)
  * CreateProductA(String creationArg) 
  * CreateProductB(String creationArg) 
* ConcreteFactory1 implements AbstractFactory
  * CreateProductA(String creationArg) 
  * CreateProductB(String creationArg) 
* AbstractProductA (interface) 
* ConcreateProductA implements AbstractProductA
* AbstractProductB (interface) 
* ConcreateProductB implements AbstractProductB

Client:
* Is written against AbstractFactory but instantiates a ConcreteFactory at runtime.


The Abstract Factory's methods are really just Factory Methods.

## Differences between Factory Method and Abstract Factory

| Factory Method                              | Abstract Factory                      |
| ------------------------------------------- | ------------------------------------- |
| Creates objects through inheritance         | Creates objects through composition   |
| Creates just one object                     | Creates family of objects             |





