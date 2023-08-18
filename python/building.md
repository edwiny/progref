## setuptools (setup.py)


### Dev Loop
A typical dev loop with setuptools looks like this:

* create a virtualenv (one time only)
  * `mkvirtualenv -p $(which python3) the_name`
* make code changes
* run tests: `tox -e py37`
* `python setup.py install`
* run program to verify your changes

### the src layout

```
.
├── README.md
├── noxfile.py
├── pyproject.toml
├── setup.py
├── src/
│    └── awesome_package/
│       ├── __init__.py
│       └── module.py
└── tools/
    ├── generate_awesomeness.py
    └── decrease_world_suck.py
```


### Alternative

pyproject.toml:

```
[build-system]
requires = [ "setuptools>64.0.0", "wheel"]
build-backend = "setuptools.build_meta"

[project]
name = "maze-solver"
version = "1.0.0"
```

```
# create and load virtualenv with python -m venv
python -m pip install --editable .
```
 
 The `-e` or `--editable` is so that you don't need to reinstall the package each time you change the code.



