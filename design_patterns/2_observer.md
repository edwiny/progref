# The Observer Design Pattern


## Defintiton

Defines a one to many dependency between objects so that when one object changes, all of its dependents are notified and updated automatically.

## Analogy

This pattern is basically the Publisher/Subscriber model. 

## Use cases

Where there is a lot of objects coming and going over the course of a computation and those objects need to be kept up to date on a important piece of data or set of events.


## How it works


You have the Subject (Publisher) and Dependents (aka Observers aka Subscribers).


* SubjectInterface
    * registerObserver()
    * removeObserver()
    * notifyObservers()
    * observers
* ConcreteSubject implements SubjectInterface
    * registerObserver()
    * removeObserver()
    * notifyObservers() //calls the update() method on the list of observers registered
    * getState()
    * setState()
* ObserverInterface
    * update()
* ConcreteObserver implements ObserverInterface
    * update()

The ConcreteSubject will call the ConcreteObserver's update() method when its value changes. Depending on implementation choice either it will pass the new state to the Observer, or the Observer will request the latest copy of the state.



## Popular uses

* JavaBeans
* Swing libraries


