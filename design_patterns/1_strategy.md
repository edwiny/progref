
# Design Pattern: Strategy

## Strategy Pattern Definition

Defines a family of algorithms, encapsulates each one, and makes them interchangable. This lets the algorithm vary independently from clients that use it.

My take: a pattern of using composition in stead of inheritance.


## Revisiting Inheritance

* Warning sign: all you classes are in a IS-A relationship.
* Over using inheritance can lead to inflexible programs that are hard to change.
 * Variety in subclasses: When adding new features you will typically add it to the base class, but in practice it might not make sense to add this feature to all subclasses, that means you will need to override the new methods in the subclasses where the new feature does not make sense.
  * Example: having a Duck superclass with MallardDuck and RubberDuck as subclasses. When adding a fly() method to the base class, the RubberDuck would have to override it as it cannot fly.
* Simple changes can affect all subclasses
* Looking at the superclass might not give you good knowledge of the business logic because much of it would be overridden in subclasses.
* Interfaces would solve some of the problems, however if you have a large number of subclasses that may or may not implement the same interface, you would not get the benefit of code-reuse. Changing the interface would also be very time consuming.



## A different way

The following two design principles guides us in coming up with a better way:

* Encapsulate what varies (and separate them from what remains the same)
* Program to a interface, not a implementation.

The basic idea is to turn **IS-A** relationships into **HAS-A** ones by turning variable code into **behaviours** that are utilised by subclasses via composition (e.g. each object has a property that holds a reference to the class implementing the behaviour).

Take the method that varies between the subclasses, and turn it into a interface, with different classes implementing the different variations of behaviour. In the original base class, make the method a property that holds a reference to class implementing that behaviour.

Subclasses can now choose their behaviour (using the HAS-A relationships) and you have code re-use between subclasses with the same behaviour.

## Example

Using the Duck example, the superclass would look like this:


```
public abstract class Duck {

    //FlyBehaviour is a interface
    FlyBehaviour flyBehaviour;
    abstract void display();
    public Duck {}
    public void performFly() {
        flyBehaviour.fly();
    }

    //all ducks swim
    public void swim() {
        System.out.println("I'm swimming!");
    }
}


public class MallardDuck extends Duck {

    public MallardDuck() {
        flyBehaviour = new FlyWithWings();
    }
    public void display() {
        System.out.println("I'm a real MallardDuck");
    }

}

```

## The Strategy Pattern Class Diagram


* Superclass:
    * Properties 
        * AlgorithmInterface behaviour
    * Methods
        * setBehaviour()
        * performBehaviour()


* AlgorithmInterface:
   * Methods
        * doAlgorithm()


* AlgorithmImpl_1:
   * Methods
        * doAlgorithm()






