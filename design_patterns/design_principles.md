# Design Principals 

## Single Responsibility

## Open-closed

Classes should be open for extension but closed for modification.

## Loose Coupling

Strive for loosely coupled designs between objects that interact. Objects interact with each other without knowing too much about each other.
Drives the following design patterns:

* Observer

## Favour Composition over Inheritance

Composition establishes a HAS-A relationship between classes (where Inheritance creates a IS-A  relationship).

Classes should achieve code reuse using composition rather than inheritance from a superclass.


## Program to an interface, not an implementation

Clients remain unaware of the specific types of objects they use, as long as 
the objects adhere to the interface that the clients expect.

## Encapsulates what varies

Identify aspects of your application that vary and separate them from 
what stays the same.

Drives the following patterns:

* Strategy
* Iterator
* Factory

## Traditional OO - Encapsulation

## Traditional OO - Abstraction

## Traditional OO - Polymorphism

Dynamic binding - lets you substitute objects with the same interface for each other at runtime.
## Traditional OO - Inheritance

Inheritance expresses a IS-A relationship between classes.

WARNING: Overusing inheritance leads to designs that are inflexiable and difficult to change.

if all your class relationships are IS-A relationships, take a closer look at your design.


