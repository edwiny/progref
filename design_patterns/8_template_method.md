# Template Method

The Template Method defines the steps of an algorithm and allows subclasses to provide an implementation for one or more steps.

OR

Defines the skeleton of a algorithm in a method, deferring some steps to subclasses. Template Method lets subclasses redefine certain steps of the algorithm without changing the algorithm's structure.

## Example

```
public abstract class CaffeineBeverage {
    //the template method
    final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if(customerWantsCodiments()) {
            addCondiments();
        }
    }
    abstract void brew();
    abstract void addCondiments();
    void boilWater() {
        System.out.println("Boiling water...");
    }
    void pourInCup() {
        System.out.println("Pouring...");
    }

    //this is the "hook"
    boolean customerWantsCondiments() {
        return true;
    }
}

public class Coffee extends CaffeineBeverage() {
    //implement the abstract methods and optionally
    //override the hook
}

public class Tea extends CaffeineBeverage() {
    //implement the abstract methods and optionally
    //override the hook
}
```

## The hook

The hook allows subclasses some measure of **optional** control of the algorithm in the template.
method.

It's an example of the **Hollywood Method** (don't call us, we'll call you)


## Real life examples

* The Java Collections `sort()` method and `Comparable` interface.
* Custom lists with `AbstractList`
