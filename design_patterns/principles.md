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
The principles apply to the code part. For the data part is where the concrete classes are 
instantiated.

## Single Responsibility Principle

A class should have only one reason to change.

Every responsibility of a class is a potential reason for change. More than one responsibility means more than one reason to change.

**Cohesion** is the measure of how well a class is designed around a set of related functions.
Unrelated functions means the class has low cohesion.

## Principle of Least Knowledge

Talk only to your immediate friends.

In practice this means don't directly call a method on an object returned from another
object in your class.

Guidelines:

In your class methods, only talk to:
* your own methods, members (incl any composed objects in your class)
* objects passed in as paramaters to your method
* any objects you instantiate inside your method

Example:

AVOID:

```
public float getTemp() {
    Thermometer thermometer = this.station.getThermometer()
    return thermometer.getTemperature();
}
```

ALSO AVOID:
```
public float getTemp() {
    return station.getThermometer.getTemperature();
}
```

INSTEAD implement a wrapper method on the `station` class:

```
public float getTemp() {
    return station.getTemperature();
}
```

## The Hollywood Principle

Don't call us, we'll call you. Helps to prevent "dependency rot" where dependencies between classes build up over time.

Example usage: the Factory Method's "hook". It allows subclasses to participate in the computation without the subclass referencing the parent class directly.

Other patterns making use of this principle:
* Factory Method
* Observer



## Traditional OO - Encapsulation

## Traditional OO - Abstraction

## Traditional OO - Polymorphism

Dynamic binding - lets you substitute objects with the same interface for each other at runtime.
## Traditional OO - Inheritance

Inheritance expresses a IS-A relationship between classes.

WARNING: Overusing inheritance leads to designs that are inflexiable and difficult to change.

if all your class relationships are IS-A relationships, take a closer look at your design.


