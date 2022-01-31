# REPL tricks

Use `dir()`, `globals()` and `local()` to inspect loaded symbols

### Checking the type of a var

`type(n)`

There's also isinstance:

```
isinstance(n, int)
True
```

### Checking object id

```
id(n)
139848771761456
```

### Check reserved keywords

```
help("keywords")
```

OR
```
>>> import keyword
>>> keyword.kwlist
['False', 'None', 'True' ...
```

### Check if something is callable

```
>>> x = "blah"
>>> len(x)
4
>>> callable(x)
False
>>> callable(len)
True
```

### Check var for "truthiness":

```
>>> bool(None)
False
>>> bool("None")
True

```

### Check if object is hashable

```
s = "hello"
>>> hash(s)
8636709500789990133
>>> x = 5
>>> hash(x)
5

```
### ipython speed testing

```
>>> %timeit d['f']
24.9 ns ± 0.176 ns per loop (mean ± std. dev. of 7 runs, 10000000 loops each)
```


### Display docstrings for a function

```
help(fn)

also available as

fn.__doc__
```

# Find symbols in local scope

```
locals()
```

# Inspect import path

```
>>> import sys
>>> sys.path    
['', '/usr/local/lib/python37.zip', '/usr/local/lib/python3.7', '/usr/local/lib/python3.7/lib-dynload', '/home/syoung/.virtualenvs/pygame/lib/python3.7/site-packages']
```

# Find installation path of a imported module

```
>>> import re
>>> re.__file__
'/usr/local/lib/python3.7/re.py'
```

# Inspect symbols in symbol table

To see which variables and symbols are accessible from current namespace, do:

```
dir()

dir(modulename)
```

# listing all the values from a iterable

```
list(range(5))
[0, 1, 2, 3, 4]
```


# Time / profile code


Put all the code in a string and add a import statement in the `setup` parameter 

```
timeit.timeit('vowels = [ i for i in "hello world" if i in "aeiou" ]')
Out[16]: 0.34642113200970925
```

Another way is cProfile:

```
In [5]: import cProfile

In [6]: cProfile.run('[ i for i in "hello world" if i in "aeiou" ]')
4 function calls in 0.000 seconds

Ordered by: standard name

ncalls  tottime  percall  cumtime  percall filename:lineno(function)
1    0.000    0.000    0.000    0.000 <string>:1(<listcomp>)
1    0.000    0.000    0.000    0.000 <string>:1(<module>)
1    0.000    0.000    0.000    0.000 {built-in method builtins.exec}
1    0.000    0.000    0.000    0.000 {method 'disable' of '_lsprof.Profiler' objects}
```






# See how much memory a object is consuming

```
>>> import sys
>>> nums_squared_lc = [ num**2 for num in range(10000) ]
>>> sys.getsizeof(nums_squared_lc)
>>> 87616
```
in bytes



