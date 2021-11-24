# Design Principals 


## Encapsulates what varies

Identify aspects of your application that vary and separate them from 
what stays the same.

Drives the following patterns:

* Strategy
* Iterator
* Factory

## Program to an interface, not an implementation

Clients remain unaware of the specific types of objects they use, as long as 
the objects adhere to the interface that the clients expect.

Generally, everytime you use `new` you are programming against an implementation.
In stead, use Factory pattern and/or Dependency Injection.


## Favour Composition over Inheritance

Composition establishes a HAS-A relationship between classes (where Inheritance creates a IS-A  relationship).

Classes should achieve code reuse using composition rather than inheritance from a superclass.

Example pattern:
* Strategy




## Loose Coupling

Strive for loosely coupled designs between objects that interact. Objects interact with each other without knowing too much about each other.

Typically this can be achieved by using minimal interfaces.

Drives the following design patterns:

* Observer

## Open-closed

Classes should be open for extension but closed for modification.
The goal is to allow classes to be easily extended to incorporate new
behaviour without modifying the class.

## Dependency Inversion Principle

Depend on abstractions, not on concrete classes.

Idealistic guidelines:
* no variable should hold a reference to a concrete class
* no class should derive from a concrete class
* no method should override an implemented method of any of it's base classes.

These are aspirational. Think of it as a typical system containing two parts: code and data.
The principles apply to the code part. For the data part is where the concrete classes are instantiated.

## Single Responsibility



## Traditional OO - Encapsulation

## Traditional OO - Abstraction

## Traditional OO - Polymorphism

Dynamic binding - lets you substitute objects with the same interface for each other at runtime.
## Traditional OO - Inheritance

Inheritance expresses a IS-A relationship between classes.

WARNING: Overusing inheritance leads to designs that are inflexiable and difficult to change.

if all your class relationships are IS-A relationships, take a closer look at your design.


