# Python basics


## Variables

Variables in Python are references to objects.
(that's why you can change the type of a var at runtime)


```
n = 4
```

Chained assignment:

```
n = m = o = 400
```

Multiple assignment:

```
a,b = 300, 400  #num left side must equal num right side
```

### Object references

* In python everything is an object, even the literals
* Variables are just references to those objects.
* Functions are also objects
* When there are no more references to an object, it gets garbage collected


### Object Identity (also, equivalence)

Object identity is the memory addresss of where the object is stored. Can inspect it with `id()`



Here python will create 2 integer objects at different addresss.

There's something called **small integer caching** though where python will reuse existing objects for integers between -5 and 256.

E.g.:
```
m = 300
n = 300
>> id(m) == id(n)
False
>>> m = 5
>>> n = 5
>>> id(m) == id(n)
True
```

Another way to check this is with the `is` operator:

```
x is not y
True
```

#### Non-boolean opearands in a boolean expression

When non-boolean operands are used in a boolean expression
then it changes to bash-style evaluation and the value becomes the result of the last operand that makes it through the expression. 


E.g.

```
>>> isinstance(n, int) or "blah"
True
>>> not isinstance(n, int) or "blah"
'blah'

```





### Naming conventions

* Generally use snake_case
* Classes use CapWords (same as Java)
* functions should be lower snake case
* Constants use upper case
* Check PEP-8

Check for reserved keywords (see `help("keywords")`)

### Operators

/ - float division
// - integer division
% - modulo

There's no `++` or '--` but there are the "augmented assignment operators":
*=
+=
-=
/=



## Basic Types

REPL tip: check the type of literal or var:

```
type(10)
```

### Integers

Integers can be arbitrarily long (internally they're handled as C longs unless a value exceeds the storage then it's auto converted to a bignum behind the scenes.

Int literals in various bases:

```
>>> 0b11
3
>>> 0x16
22
```

(to convert from decimal to binary, use the `bin()` internal func:
```
>>> bin(4)
'0b100'
```

### Floating point

On most platforms stored as 64 bits
 
Literals:

```
>>> .2
0.2

>>> .4e7
4000000.0
```
### Complex numbers:

Literals have a 'j' appended:

```
>>> 2+3j
(2+3j)
>>> type(2+3j)
<class 'complex'>

```

### Strings

* Strings are *Iterable*
* Literals can use either double quotes or single quotes.

```
type("blah")
<class 'str'>
```

Multiline literals: escape end of line with \.

Notable escape sequences:

\uxxxx 	Unicode character with 16-bit hex value xxxx
\Uxxxxxxxx 	Unicode character with 32-bit hex value xxxxxxxx
\ooo 	Character with octal value ooo
\xhh 	Character with hex value hh

```
>>> "\u2665"
'♥'
>>> print("a\141\x61")
aaa
```

Raw strings: escape sequences are not interpreted:

```
>>> print(r'foo\nbar')
foo\nbar
```

#### String operators

Note: these apply to lists as well

string + string - concatenates strings
string * int - multiplication operator, repeats the string n times ("blah" * 2) 
needle in haystack - membership operator, returns boolean
string[int] - indexing (cam use negative indexing)
string[int:int] - slicing. start and end offsets (but up to end not including it)
string[-1] - last item

#### Slicing

stirng[start:end] - up to but not including end-index
string[:end] - from 0 to end - 1
string[start:] - from start to len(string) - 1
string[:] - returns reference to original string (note with lists this returns  a new list)

if en is smaller than start then returns empty string

There's also a 'stride' argument you can add that will take every n'th element:

string[0:10:2] will return every 2nd char from the first 10 chars.

Works backwards as well

A quick way to reverse the list is:

```
>>> n = "123456789"
>>> n[::-1]
'987654321'
```

#### Strings are immutable

```
>>> n = "blah"
>>> n[1] = 'k'
Traceback (most recent call last):
  File "<ipython-input-106-24d3795d25ca>", line 1, in <module>
    n[1] = 'k'
TypeError: 'str' object does not support item assignment
```


#### Interpolating vars into strings aka f-strings

```
>>> n = 20
>>> f"The value is {n} and one more is {n + 1}"
'The value is 20 and one more is 21'
```

Formatting decimal places:

```
f"cost $ {price:..2f}"
```



#### builtin string functions
```
len() - length of string
chr() - convert into to char
ord() - converts char to int (returns the unicode code-point)
str() - string representation of object
```



#### Triple quoted strings (either single quote or double) 

Escape sequences still work in triple-quoted strings, but single quotes, double quotes, and newlines can be included without escaping them. This provides a convenient way to create a string with both single and double quotes in it.

```
>>> """Is this a
... triple
... quoted
... string?"""
'Is this a \ntriple\nquoted\nstring?'

```
#### String methods

The typical string manipulation functions are methods on the string class.

The functions all returns a new instance of the string

A couple of typical ones:

`.lower()` - convert to lower case
`.upper()` - 
`.count()` - count occurrances of sub string
`.endswith()`
`.find()` - returns lowest index where substr is found
`.index()` - similar to find but generates exception if not found
`.isdigit()`
'.isspace()`
'.center()'


#### Converting between strings and lists

List and tuples are Iterables. 
Strings are also Iterables!

E.g.:

```
>>> l = ["1", "2", "3", "4"]
>>> s = "1234"
>>> ",".join(l)
'1,2,3,4'
>>> ",".join(s)
'1,2,3,4'

```

`.split()` - returns a list of the split components

### Bytes and bytearrays

Literals:

b'1245'
b"1234"

Remove escape sequences:

rb'12\34' (length would be 5)

Can create in other ways:

`bytes(8)' - creates a bytes object contain 8 null chars

Many of the builtin and string functions also apply to bytes objects.

byte objects are immutable, like strings.

Byte arrays are similar, but
* can only be created by the `bytearray()` builtin function.
* are mutable

```
ba = bytearray('1245', 'utf8')
ba2 = bytearray([1, 2, 3, 4])
ba3 = bytearray(b'1234')
ba[1] = 1   # this won't generate exception
```


### Boolean

`True` or `False` (has to be capitalised)

To convert a string to boolean expression, use `bool(theString)`
(non empty string will be true, empty string false)

Boolean expressions can be stored in variables:

```
>>> n = 5
>>> x = (n == 5)
>>> x
True
>>> type(x)
<class 'bool'>
```

#### Boolean operators
`not`, `and`, or `or`

#### What is considered `False`?

* The Boolean value `False`
* Any value that is numerically zero (0, 0.0, 0.0+0.0j)
* An empty string
* An object of a built-in composite data type which is empty (typle, list, dict or set)
* The `None` keyword

In the REPL, you can check the "truthiness" with:

```
>>> bool(None)
False
>>> bool("None")
True
```

### Using boolean operators to supply default values

```
s = some_string or ""
```


### Lists and Tuples

An `Iterable` is either a list or tuple.

#### Operators and methods

Note that the same slicing logic covered earlier for Strings also apply to lists.

Incl:

* list + list - concatenation
* list * n - replication operator
* item in list - operator\
* `len(list)` - size of list
* `min(list) / max(list)`
* list += list



#### Lists

* Indexed via `[]`
* Mutable (individual elements can be changed)
* Can be nested
* Dynamic (elements can be added or deleted)
* Ordered. 2 Lists with same elements but in different order are not the same.


NB: unlike String methods, list methods modify the list in place (typically they don't return anything)



**Operators**


* `del a[3]` - delete element at index pos
* `a[2:5] = [1, 2]` - replace range
* `a[1] = [1,2]` - insert nested list
* `a += Iterable` - concatenation operator. appends a list. If Iterable is a string, then the individual chars are broken up and added as individual elements.


 

**Methods with no return value**:



`l.append(elem)` - appends a single item. If item is list, it's appended as nested list
`l.extend(iterable)` - works like the concatenation operator.
`l.remove(item)` - searches for and deletes item from the list
`l.clear()` - removes all elements from the list


`l.sort(key=str.upper)` - sorts using the specified key. Note that is sorts in place.
`l.sort(key=str.upper, reverse=True)`

`l.reverse()` - reverses in place. Compare with l[::-1]

**Methods with return values**

`l.pop(index)` - removes an element and returns it. By default the index is -1 so last item is removed, but pop(0) will remove the first item.
`l.index(item)` - returns index of given element or throw exception
`l.copy()` - returns shallow copy. Composite elements will get a reference copied.



#### Tuples

Basically same as lists except for:
* Defined differently
* Immutable
* Special notation for packing and unpacking tuples

Tuples are identical to lists except for:
* Definition - uses `()` in stead of `[]`
* Immutable

Tuples are more like Strings.

Creation:

```
t = ("jan", "feb", "mar")
t1 = () # empty tuple
t2 = "jan", "feb", "mar"
```

Are function / method arguments just tuples???

Gotcha:

If only one element in `()` then it will be evaluated as an arithmetic expression:

```
t = (2)
>>> type(t)
<class 'int'>
```

To force evaluation as a tuple, add trailing comma:

```
>>> t = (2,)
>>> type(t)
<class 'tuple'>
```

##### Tuple packing and unpacking

Packing is just assignment:

```
t = ('jan', 'feb', 'mar')
```

Unpacking is assigning the values of a tuple to individual variables:

```
>>> (m1, m2, m3) = t
>>> m1
'jan'
>>> m1 = 'JAN'
>>> t
('jan', 'feb', 'mar')
```

Left and right must have the same number of elements.

Can omit parenthesis:

```
m1, m2, m3 = 'jan', 'feb', 'mar'
```

Nifty way to swap two vars without the use of temp var:

`a,b = b,a`

Cam unpack multiple items into a var as a list

```
>>> start, *end = 1,2,3,4,5
>>> end
[2, 3, 4, 5]
>>> start, *mid, end = 1,2,3,4,5
>>> mid
[2, 3, 4]
>>> type(mid)
<class 'list'>
```

### Dictionaries

* keys must be immutable
* values can be anything


Defining:

```
d = {}  # empty dictionary
d = { 'key1': 'val1', 'key2': 'val2' }
d = dict( [ ('key1','val1'), ('key2', 'val2') ] )
d = dict(foo=100, bar=200, baz=300)

```

Retrievel uses `[]`:

* `d['key2']`  -Non-existent key would result in Exception.
* `d.get(key)` - retrieval without exception, returns `None` if not exists



Remove a entry:

`del ['key1']`
`d.pop('key1')` - removes and returns removed value


#### Operators and methods

Check key existance:

```
'key1' in d
True
```

Common idiom:

```
"blah" in d or d["blah"] = 1
```

* `d.items()` - returns list of kv tuples* 
* `list(d.keys())` - returns list of keys (by itself it returns `dict_keys` type
* `list(d.values())` - same as keys()
* `d.update(d1)` - merge d1 into d, overriding any elements with same keys


### Sets

* Unordered
* Set elements are unique
* Mutable 
* Elements must be hashable 
* They're fast compared to lists and tuples



Defining:

* `set()` - empty set (because `{}` is empty dictionary)
* `x = { 1, 2 }` - similar to dictionaries but no :
* `set(Iterable)`  e.g. `set([1,2])`


For dictionaries, it will take the keys



#### Hashing

* All immutable objects are hashable (but not all hashable objects are immutable)
* E..g can hash a tuple but not a list

Test for hashing:

`hash(x)`

#### Set operations

* `len(x)` - size
* `<elem> in s`  - check membership
* `<elem> not in s`  - imverse membership
* `not <elem> not in s`  - another way for imverse membership
* `for elem in x:`  - iteration

* `s1.union(s2)` - union - returns new set
	* `s1 | s2` - also union
* `s1.update(s2)` - also uniom but modifies the existing set
	* `s1 |= s2` - same as update
* `s1.intersection(s2)` - set intersection
	* `s1 & s2` - also set intersection
* `s1.intersection_update(s2)` - same as intersection but modifes s1 in place
	* `s1 &= s2`
* `s1.difference(s2)`
	* `s1 - s2`
* `s1.difference_update(s2)`
	* `s1 -= s2`
* `s1.symmetric_difference(s2)` - elements that exist only in a single set (inverse of intersection)
	* `s1 ^ s2` - also synmetric difference
* `s1.isdisjoint(s2)` - checks if the two sets have any elements in common
* `s1.issubset(s2)`
	* `s1 <= s2` - also subset
	* `s1 < s2` - proper subset (the sets are not identical)
* `s1.issuperset(s2` - check if s1 is superset of s2
	* `s1 >= s2`
	* `s1 > s2` - proper superset (not identical)
	
* `s1.add(elem)`	
* `s1.remove(elem)` - can also use set difference. Will generate exception if elem not exists
* `s1.discard(elem)` - same as remove except no error raised if elem not in set
* `s1.clear()` - remove all elements


NOTE: the augmented assignment operators / `update_xxx()` methods modify the first set in place whereas the expanded counterparts (e.g. x = x | y) returns new sets.

NOTE: there's no indexing operator `[]` because it doesn't make sense on sets.

#### Frozen sets

They're immutable:
```
x = frozenset(Iterable)`
```

This allows you to create nested sets (normally you can't because sets are mutable and sets can only contain hashable objects)

Unlike with regular setes, Augmented assignment operators return new frozensets.

## Program lexical structure

### Line continuation

**Implicit continuation** is where brackets or parenthesis are used. Python will assume a single statement until all matchines parenthesis are parsed:

```
a = [
    [ 1, 2, 3 ],
    [ 10, 20, 30 ]
    ]
```

PEP-8 (style guide) actually recommends parenthesis:

```
val = (
    a > b &&
    a < 10
    )
```

Curl braces also work.


**Explicit continuation**

Can use backslash as last char on line to continue as well but it's not recommended in general.
Multiple statements separated by `;` also works but is Not Pythonic.


### Docstrings

Triple quoted strings within a function are treated as docstrings:


```
def func1(): 
"""This functions does blah"""
   return blah
    
```




## Functions

```
def f():
  s = "blah"
  return s
```

function stub:

```
def f():
   pass
```

Different ways to provide arguments:
### positional args (reguar way)
* order matters


### keyword arguments
* caller provides arg names e.g. f(price=5)
* order no longer matters
* assign default values in function declaration to make argument optional, e.g. `def f(price=0):`
  

WARNING: mutable default arguments

Default arguments are defined once when the function is declared. When you call the function, the reference to the exisitng object is re-used. So if default argument is a mutable object, the *mutations will persist* across function calls.

To get around it, use immutable default values.

### Pass-by-assignment aka pass-by-object reference

Basically everything is an object in Python and variables are just references to those objects.

So assginment and paramaers are just ways of assigning names to objects. THe variables and argument names have their own values and it points to object references.


### Argument tuple packing (aka varargs) *

Argument name can be preceded by a `*`, any corresponding args in calls will be packed into a tuple. The convention is to name the tuple `*args`:

E.g.:
```
fun f(*args):
   for arg in args:
      do_something(arg)
```

Unpacking - passing tuples in as arguments:

```
t  = (1, 2, 3)
f(*t)
```

### Argument dictionary packing and unpacking **

Prepending `**` before argument name in the declaration specifies keyword args will be packed into the named dictionary. Convention: `**kwargs`

```
def f(**kwargs):
   for key, val in kwargs.items():
      print(key, '->', val)
f(foo=1, bar=2, baz=3)

```

Unpacking:

```
d = { 'foo': 1, 'bar':2, 'baz':3 }
f(**d)
```
### Using argument packing with keyword ares

The packed argument must come first, the keyword args must come last and have a default value.

### Bare variable argument parameter

Specifies end to tuple packing

```
def f(a, b, *, op="+")
```

### Function annotations (3.x)

* Attach metadata to a functions' paramaters and return value
* Used by adding a `:` after the parameter name.
* For return values, add a `->` between the `)` and the ending ':'
* annotations can be expressions or objects

e.g.

```
def f(a:'metadata1', b:'metadata2') -> 'ret_meta':
  return "bla"
  
```

Annotations are stored as a dictionary in

```
fn.__annotations__
```

E.g.:

```
n [1]: def f(a: int, b: str) -> float:
   ...:     return 1.0
   ...: 

In [2]: f.__annotations__
Out[2]: {'a': int, 'b': str, 'return': float}

```


#### Type hinting

A common use is to do type hinting:

```
def f(a: int, b: str) -> float:
```
Functionally they don't do any actual validation, just shows up in IDEs.

### Profiling

You can store data in the `__annotations__` dictionary and update with each function call, so can collecting timings or counters.



## Iteration

## IO

### Console
```
name = input("What is your name?")
```
Note that the type returns is a string, there's no magic conversation if you input a int.
Needs to be converted to the type you're expecting.


## Classes

### Calling a parent's constructor

```
class Player(pygame.sprite.Sprite):
    def __init__(self):
        super(Player, self).__init__()
```



