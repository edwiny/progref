# Adaptor

The Adaptor Pattern converts the interface of a class into another
interface that the client expects. Adapter lets classes work together
that couldn't otherwise because of incompatible interfaces.

"If it walks like a duck and quacks like a duck, the it might be
a turkey wrapped in a duck adapter."

Note everything must already be implementing interfaces or abstract 
classes for this to work.

Adaptor class are **proxies** to other classes.

There are 2 kinds:
 * object adaptor
 * class adaptor - done via multiple inheritance (not available in Java)

## Example class list

* Duck (interface)
  * quack()
  * fly()
* MallardDuck inplements Duck
* Turkey (interface)
  * public TurkeyAdapter(Turkey t) { this.t = t }
  * gobble()
  * fly()
* WildTurkey implements Turkey
* TurkeyAdapter implements Duck
  * quack() -> this.gobble()

Now Turkey objects instantiated via the Adapter can be treated as Ducks.
   

# Facade

Provides a unified interface to a set of **interfaces** in a subsystem.
Facade defines a higher level interface that makes the subsystem easier
to use.

Notes:
* does not (neccessarily) add new functionality
* does not encapsulate, the subsystem can still be used directly.

## Class list

* FacadeClass
  * public FacadeClass(SubsystemInterface1 sub1, SubsystemInterface2 sub2)
  * public simplifiedAction1()
* SubsystemInterface1 interface
* ConcreteSubsystem1 implements SubsystemInterface1
* etc

Note that when constructing facades, try to follow the design **Principle of
Least Knowledge**.





## Comparison between decorator type pattenrs:

| Pattern   | Intent                                              |
| ----------| --------------------------------------------------- |
| Decorator | Wraps an object to add new behaviours               |
| Adaptor   | Wraps an object to change its interface             |
| Facade    | Simplifies an interface of a subsystem              |