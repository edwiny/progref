# Python iterators

Iterators allow you to process  datasets one item at a time without exhausting the memory resources of your system, which is one of the most attractive features of iterators.

## The Python Iterator Protocol

A object can be an iterator when it implements the following two dunder methods:

`__iter__()` - initialise or reset iterator and return an iterator object (typically self)
`__next__()` - return next value or throw `StopIteration` exception


Example:

```
def __init__(self, sequence):
    ...
    self._sequence = sequence
    self._index = 0   # where is iterator pointing to at the moment

def __iter__(self):
    return self

def __next__(self):
    if self._index < len(self._sequence):
            item = self._sequence[self._index]
            self._index += 1
            return item
        else:
            raise StopIteration

```


You can also inherit from the `collections.abc` class `Iterator` - this way you'll know your
class implements the correct methods, and you get the `_iter__()` method for free:

```
from collections.abc import Iterator

class SequenceIterator(Iterator):
    def __init__(self, sequence):
        self._sequence = sequence
        self._index = 0

    def __next__(self):
        if self._index < len(self._sequence):
            item = self._sequence[self._index]
            self._index += 1
            return item
        else:
            raise StopIteration
```

NOTE: you can't reset iterators. You have to create a new instance each time.


## Generator functions


Using the `yield` keyword, you can create an iterator just by using a function:

```
def sequence_generator(sequence):
     for item in sequence:
         yield item


for number in sequence_generator([1, 2, 3, 4]):
    print(number)
```


## Generator expressions

They work like list comprehensions but in stead of computing the whole list at once, gives you an iterator:



```
# note parenthesis in stead of square brackets

generator_expression = (item for item in [1, 2, 3, 4])
for item in generator_expression:
     print(item)

## or use list() which will call the iterator
numbers = list(generator_expression)
```


## Advantages of generators / iterators over list comprehensions

Iterators and generators are more memory efficient because you're processing only one item at a time.
With comprehension you assemble all the data at once in memory.



# Python iterables


An iterable is an object that you can iterate over. To perform this iteration, you’ll typically use a for loop.

Another way: iterables has iterators but are not iterators themselves. E.g. a list is not an iterator but you can still use it in a loop.

## The iterable protocol

The iterable protocol consists of a single special method: 
the `__iter__()` method which returns an iterator object, which usually **doesn’t** coincide with self unless your iterable is also an iterator.


The `iter()` function will call the `__iter__()` method on the iterable you passed it.

## The sequence protocol


`.__getitem__(index)` - takes an integer index starting from zero and returns the items at that index in the underlying sequence otherwise raise `IndexError` exception when the index is out of range.
`.__len()__` -



NOTE: Python's `iter()` function automatically builds an iterator from objects that implement the sequence protocol.



## Iterable unpacking

Python internally runs a quick loop over the iterable on the right-hand side to unpack its values into the target variables.


NOTE: the number of variables must match the number of values in the iterable.

```
numbers = [1, 2, 3, 4]

one, two, three, four = numbers
```

## Different ways to implement `__iter__()` methods:

```

def __iter__(self):
        return iter(self._items)

# OR

def __iter__(self):
        for item in self._items:
            yield item
# OR

def __iter__(self):
        yield from self._items

```


# Async iterators for parallelism


## The async iterator protocol

`.__aiter__()` returns an asynchronous iterator, typically self.
`.__anext__()` must return an awaitable object from a stream. It must raise a `StopAsyncIteration` exception when the iterator is exhausted.

```
import asyncio
from random import randint

class AsyncIterable:
    def __init__(self, stop):
        self._stop = stop
        self._index = 0

    def __aiter__(self):
        return self

    async def __anext__(self):
        if self._index >= self._stop:
            raise StopAsyncIteration
        await asyncio.sleep(value := randint(1, 3))
        self._index += 1
        return value
```


Using it:

```
import asyncio
async def main():
     async for number in AsyncIterable(4):
         print(number)
```

NOTE: TBD this doesn't parallelise it, it will only yield control when waiting for io while in the eventloop.
The asyncio event loop runs when you call the asyncio.run() function with your main() function as an argument.



