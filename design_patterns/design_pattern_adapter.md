# The Adaptor Pattern


## Definition

Converts the interface of a class into another interface that clients expect. It allows classes to work together that couldn't otherwise because of incompatible interfaces.


## Typical use cases

- Swapping out vendor code without needing to change your application's core code too much.


## How this works in practice


* Client - the thing using the service. It would need to create a instance of the adaptee class and pass it to the constructor of the Adapter.

The client may look like this:


```
//Test a duck
Duck duck = new MallardDuck();
testDuck(duck);

//Now test a turkey using the same testDuck method:
Turkey turkey = new WildTurkey();
Duck turkeyAdapter new TurkeyAdapter(turkey);
testDuck(turkeyAdapter);
```



* Target - interface

This is the thing that your code expects to work with.


* Adapter - implements the interface. Using composition, a reference to the adaptee is stored as a property. (aka it is "composed" with the adaptee)


Naming convention: Adapter$Adaptee

Example code:

```
public class TurkeyAdaptor implement Duck {
    Turkey turkey;

    public TurkeyAdaptor(Turkey turkey) {
        this.turkey = turkey;
    }

    public void quack() {
        turkey.gobble();
    }
}

```



* Adaptee - the vendor class




