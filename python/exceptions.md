# Exceptions in Python

## Raising Exceptions

```
raise Exception()
```

## Creating new exceptions

* All exceptions must derive from `Exception` (which in turn derives from `BaseException`)

```
class MyError(Exception):
   pass
```

