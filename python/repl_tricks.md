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


