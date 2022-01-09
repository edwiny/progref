# Python basics



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

```
>>> 2+3j
(2+3j)
>>> type(2+3j)
<class 'complex'>

```

### Strings

Literals can use either double quotes or single quotes.

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

Triple quoted strings (either single quote or double) 

Escape sequences still work in triple-quoted strings, but single quotes, double quotes, and newlines can be included without escaping them. This provides a convenient way to create a string with both single and double quotes in it.

```
>>> """Is this a
... triple
... quoted
... string?"""
'Is this a \ntriple\nquoted\nstring?'

```

### Boolean

`True` or `False` (has to be capitalised)

To convert a string to boolean expression, use `bool(theString)`
(non empty string will be true, empty string false)


## Iteration

## Lists



## Dictionaries

### Checking if key exists


```
if 'key' in 'thedict':
   print("found")
```

