# Python modules and packaging

## Modules vs Packages

Module is just a single .py file that is imported directly without a prefix. Eg.:

`import mymod` vs `import mymods.mod`

Package is a collection of modules in a directory.





### Modules

3 types:
* written in python
* written in C
* internal module like itertools

### Module search path aka what happens with import statement

Search order:

* Script dir
* Current working dir if running interactively
* `PYTHONPATH` env var
* Compiled in list of vars

Resulting list of paths are kept in `sys.path`.


`import foo` means python will look for a file called `foo.py` in the list of import directories.

Each module has its own symbol table which serves as global table for everything in that module. This symbol table gets added to the caller's table via the module name. 

Any symbols in the module thus need to be accessed via `foo.a`.

Alternative, use 'from foo import a'
This will overwrite any existing symbols in the caller's namespace with the same name.

```
>>> dir()
['__annotations__', '__builtins__', '__doc__', '__loader__', '__name__', '__package__', '__spec__', 're', 'sys']
>>> from re import *	
>>> dir()   
['A', 'ASCII', 'DOTALL', 'I', 'IGNORECASE', 'L', 'LOCALE', 'M', 'MULTILINE', 'Match', 'Pattern', 'S', 'U', 'UNICODE', 'VERBOSE', 'X', '__annotations__', '__builtins__', '__doc__', '__loader__', '__name__', '__package__', '__spec__', 'compile', 'error', 'escape', 'findall', 'finditer', 'fullmatch', 'match', 'purge', 're', 'search', 'split', 'sub', 'subn', 'sys', 'template']

```


#### Executing a module as a script

Whenever the module is imported, it is fully executed and then added to your current namespace.
To ensure executing code is executed only within the context of a script, use the __main__ pattern:



```
if (__name__ == '__main__'):
    print('Executing as standalone script')
```

A common pattern is to move it to a function:

```
def main():
    print("Hello,", name)

if __name__ == "__main__":
    main()
```


Adding this enables the module to be run as a script, which might be useful to test it from the command line.





#### Initialising a module

Any code at the global level will be executed at module import time.


You can restrict the list of symbols that gets imported when the called does a wildcard import by adding the a allowlist in `__all__`

### Packages

Packages allow for a hierarchical structuring of the module namespace using dot notation. In the same way that modules help avoid collisions between global variable names, packages help avoid collisions between module names.

Modules for a package is colocated in a package directory, and imported using directory name . module name:

```
import pkg1.mod1
```
Importing just `pkg1` has no meaningful effect.

#### Package initialisation

Add the file `__init__.py`. Note: this also tells python the directory is to be treated as a package.



Any variables declared in here is made available as

`pkg1.varname`

Can be imported by modules in the package by:

`from pkg1 import varname`

A list `__all__` in `__init__.py` is taken to be a list of modules that should be imported when the statement from <package_name> import * is encountered.

NOTE: before Python 3.3, the prescence of `__init__.py` was neccessary to indicate a directory is a package, but in later versions Implicit Namespace Packages were introducted.




## when to use `__init__.py`

When creating a package (not module). Tells python the directory is to be treated as a package.
If there are sub packages, each sub folder needs its own `__init__.py`

When doing wildcard imports, you can also constrain the list of modules to import by adding this to the `__init__.py`:

```
__all__ = ["submodule1", "submodule10"]
```


## Relative imports

```
from . import artificial    # one dot means addressing to a current package/subpackage

from .. import subpackage2  # two dots mean addressing to a parent package/subpackage

from ..subpackage2 import amazing
```