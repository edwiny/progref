# The Builder Pattern

## When to use

* Object creation has many parameters - can be easier to read vs many setter methods
* Useful when some or all paramaters are optional


## How it works

* Each method returns the object reference it is called from.
* The build() method returns a fully constructed object.


* First create a base class with all-args constructor
* Then create a builder class that has attributes and setter methods for all arguments in the base class. The attributes must be the same as what the base class has.
* Eech setter should return a reference to the builder class
* Then create a build() method on the builder class that will construct a base class (using the base class's all-args constructor and the values from the builder class' attributes) and return it.

