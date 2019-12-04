# Design Patterns

## Creational 

###  Abstract Factory

Provide a interface into creating families of related or dependent objects without specifying their concrete classes.


###  Builder

Separate the creation of a complex class from its representation so that the same creation process can be create different representations.


### Factory Method

Define a interface for creating an object, but let subclasses decide which class to instatiate. Factory Method lets a class defer instantiation to subclasses.

### Prototype

Specify the kinds of objects to create using a prototypical instance, and create new objects via this prototype.

### Singleton

Ensure a class has only one instance, and provide a global point to access it. 

## Structural

### Adapter

Convert the interface of a class to another inteface that clients expect. 

### Bridge

Decouple an abstraction from it's implementation, so that they can vary independently.

### Composite

Composte objects into tree structures to represent part-whole hierarchies. Composites lets clients treat individual objects the same as compisitions of objects.

### Decorator

Attach additional responsibilities to an object dynamically. Provides a flexible alternative to subclassing.

### Facade

Provide a unified interface to a set of interfaces in a subsystem. Facade defines a higher level interface that makes the subsystem easier to use..

### Flyweight

Use sharing to support large numbers of fine-grained objects efficiently.

### Proxy

Provide a surrogate or placeholder for another object to control access to it.

## Behaviour

### Chain of responsibility

Avoid coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. Chain the receiving objects and pass the request along the chain until a object handles it.

### Command

Encapsulate a request as an object thereby letting you parameerise clients with different requests, queue or log requests, and support undoable actions.

### Interpreter

Given a language, define a representation for its grammar along with an interpreter that uses that representation to interpret sentences in that language.

### Iterator

Provide a way to access the elements of a aggregate object sequentially without exposing its underlying representation.

### Mediator

Define a object that encapsulates how a set of objects interact. Mediator promotes loose coupling by keeping objects from referring to each other explicitly, and lets you vary their interaction independently.

### Memento

Without violating encapsulation, capture and externalise a objects' internal state so it can be saved and restored later.

### Observer

Define a one to many dependency between objects so that when one object changes state, all it's dependencies are notified and updated automatically.

### State

Allow an object to change its behaviour when it changes state. The object will appear to change its class.

### Strategy

Define a family of algorithms, encapsulate each one, and make them interchangable. Strategy lets the algorithm vary independently from clients that use it.

### Template Method

Define the skeleton of an algorithm in an operation, deferring some steps to subclasses. Lets subclasses redefine certain steps of the algorithm without changing the algorithm's structure.

### Visitor

Represent an operation to be performed on the elements of an object structure. Visitor lets you define a new operation without changing the classes of the elements on which it operates.
