# Iterator

Provides a way to access the elements of an aggregate object (aka collection) sequentially without
exposing the underlying implementation.


Relies on interface called `Iterator`:
* hasNext()
* next()
* remove() - optional, implementations can throw `UnsupportedOperationException`

# Usage

Need to do 2 things:
* Create a iterator implementation for the objects in your collection (`java.util.Iterator`)
* Your class containing the collection of objects must implement a method that
returns an iterator instance, initialised with your collection.


# Class List

* Aggregate
  * public Iterator createIterator()
* ConcreteIterator implements Iterator
  * hasNext()
  * next()


# Design principle

Moving the iteration functionality out of the aggregate object follows the principle of
Single Responsibility.


## Java Iterable interface and enhanced for loop

All collection types in Java inherits from the `Iterable` interface which provides:
* iterator()
* forEach()

It allows you to use the 'enhanced' for loop:

```
for (MenuItem item: menu) {
    System.out.println(item.getName())
}
# or

menu.forEach(item -> System.out.println(item.getName()));
```


# The Composite Pattern

Allows you to compose objects into tree structures to represent part-whole hierarchies.
Composite lets clients treat individual objects and compositions of objects uniformly.

Part-whole means tree of objects that is made of parts but can also be treated as a whole.
In other words, we can ignore the differences between compositions of objects and individual objects.


## Class list

* Component 
  * add(Component)
  * remove(Component)
  * getChild(int)
  * someOperation()  //optionally may implement some default behaviour
* Leaf extends Component
  * operation()
* Composite extends Component
  * add(Component)
  * remove(Component)
  * getChild(int)


## Examples

Implementing a menu system that contains menu items and sub menus.
