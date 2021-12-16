# Classes in Python

## Creating a class

```
class MyClass:
   def __init__(self, arg1, arg2):
      self.arg1 = arg1
      self.arg2 = arg2

    def doSomething(self, some_arg):
       for thing self.things:
          something(some_arg)
```


## Inheritance

* Inheritance in python is used for re-using implementation, not polymorphism / interfaces.

* All classes implicitly derive from the `object` class, **except** from Exception classes which must derive from `Exception`.


```
def ChildClass(BaseClass):

   # in case the child class constructor is different from the parent:
   def __init__(self, arg1, arg2):
      super().__init__(arg1)
      self.arg2 = arg2
```

## Interfaces

In python you don't need to explicitly declare an interface due to *duck typing*.
(if it behaves like a duck, then it's a duck). This means if your class has all the required
members and methods expected of the interface, then it doesn't have to inherit from another class.

## Check if a object is of a certain class

```isinstance(object, classname)```
