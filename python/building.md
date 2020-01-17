## setuptools (setup.py)


### Dev Loop
A typical dev loop with setuptools looks like this:

* create a virtualenv (one time only)
* make code changes
* `tox -e py37`
* `python setup.py install`
* run program to verify your changes

