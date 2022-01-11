# Software Engineering General Guidance


# Design Principals

Also see the principals in the design patterns folder.


## SOLID

* (S)ingle Responsibility Principle
* (O)pen-Closed Principle
* (L)iskov Substitution Principle
* (I)nterface Segregation Principle
* (D)ependency Inversion Principle


## Other common principles

* DRY - don't repeat yourself (i.e. reuse)
* YAGNI - you ain't gonna need it (only write what is absolutely neccessary)
* KISS - keep it simple, stupid



# Design Methods

## Functional Decompisition

The process of decomposing a problem into several functions.

Advantages:
* Structure the code, make it more readable and understandable;
* Modify the code easily;
* Reuse the functions and make the code terser;
* Make the testing process more convenient by testing components separately.


# Avoiding bugs

### Understand the requirements

As a programmer, you need to understand the requirements of a program that you are going to work on. If you have doubts, you can always find some help on the Internet or among fellow developers.

### Decomposition

Decompose a program into small units that are easy to look through and understand. A good architecture reduces software complexity, and, as a consequence, the number of errors.


### Easy to read

Write easy-to-read-code and follow all the standards of the language. It will also allow you to make fewer errors.

### Automated Testing

Run the program with boundary input values. Do not forget to consider different cases: 0 or a huge number as an input value, 0 or 1 element as an input sequence. Such cases often reveal bugs.
Write automated tests that will check the program at the build time.

## Self-documenting code

* Code should be self explanatory
* Well named variables, classes, methods, etc should take the place of comments
* Shorten where possible but not at the expense of readability
* Well written code serves as a example for future projects.
o
