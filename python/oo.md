# Classes in Python

## Creating a class

```
class MyClass:
   def __init__(self, arg1, arg2):
      self.arg1 = arg1
      self.arg2 = arg2
      # if no starting value required:
      self.arg3 = None
      

    def doSomething(self, some_arg):
       for thing self.things:
          something(some_arg)
```


Usually, instance attributes are created within the `__init__` method since it's the constructor

Calling methods on classes:

Two ways:

```
# self.method()
volga.get_info()
# The length of the Volga is 3530 km

# class.method(self)
River.get_info(volga)
# The length of the Volga is 3530 km
```

## Class properties

Here, `all_rivers` is a class property (a global value shared by all instances)

```
class River:
    # list of all rivers
    all_rivers = []
    
    def __init__(self, name, length):
        self.name = name
        self.length = length
        # add current river to the list of all rivers
        River.all_rivers.append(self)
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

```
isinstance(object, classname)
```



## Calling super class method


```
super().func()  # no need to add self
```


## magic methods aka dunders (double underscores)

### __new__

Gets called prior to object creation, can implement a singleton like:

```
class Sun:
    n = 0  # number of instances of this class

    def __new__(cls):
        if cls.n == 0: 
            cls.n += 1
            return object.__new__(cls)  # create new object of the class

```

### __str__ and __repr__

__repr__: should be able to reconstruct the object from it. Define it first since it's the fallback for `str()`
__str__: presentation


