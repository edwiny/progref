# Python standard library

## pathlib (3.5+)

```
import pathlib

cwd = pathlib.Path.cwd()
path = pathlib.Path(r'/tmp/blah')
anotherpath = pathlib.Path.home() / 'python' / 'test.py'
path2 = pathlib.Path.home().joinpath('python', 'scripts', 'test.py')


```

Show the absolute path name:

* `path.resolve()`


Path components:
,
* `.name`: the file name without any directory
* `.parent`: the directory containing the file, or the parent directory if path is a directory
* `.stem`: the file name without the suffix
* `.suffix`: the file extension
* `.anchor`: the part of the path before the directories

Other things you can do with a path:

* `.exists()`


## Reading from stdin

`input([<prompt>])`

Does not include a newline.

Note: behaves differently between v2 and v3

Using `sys.stdin`:


```
import sys
# this is not the python way
lines = sys.stdin.readlines()
```


```
import sys

for line in sys.stdin:
    print(line)
```




## Reading a file

```
with open(path, mode='r') as fid:
    lines = [line.strip() for line in fid ]
```

Alternatively, esp if file is large, use a generator which is more memory efficient:

```
lines = (line for line in open(filename))
for line in lines:

```
Other functions:

* `read_text()`: open the path in text mode and return the contents as a string.
* `.read_bytes()`: open the path in binary/bytes mode and return the contents as a bytestring.
* `.write_text()`: open the path and write string data to it.
* `.write_bytes()`: open the path in binary/bytes mode and write data to it.


## Finding files in a directory (aka globbing)

Returns a Iterable:

```
pathlib.Path.cwd().glob('*.p*')
```

## Finding last modified file

```
from datetime import datetime
time, file_path = max((f.stat().st_mtime, f) for f in directory.iterdir())
```


## Regex


```
import re

# returns a match object
re.search('123', s)
```


Match objects are Truthy:

```
if re.search('123', s):
...     print('Found a match.')
... else:
...     print('No match.')
```

Group matching:


```
m = re.search(...)

m.groups()      # returns tuple of groups
m.group(0)      # returns tuple of groups
m.group(n]      # returns the n-th group, starting at 1
```


Flags
```
re.search('123', s, re.I|re.M)

```

Find all matches (equiv to `/g`):

```
m = re.findall('(\w+?)=\[(\S+?)]', text, re.M)
```

