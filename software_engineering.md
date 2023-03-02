# Software Engineering General Guidance


## Avoiding bugs

### Understand the requirements

As a programmer, you need to understand the requirements of a program that you are going to work on. If you have doubts, you can always find some help on the Internet or among fellow developers.

### Decomposition

Decompose a program into small units that are easy to look through and understand. A good architecture reduces software complexity, and, as a consequence, the number of errors.


### Easy to read

Write easy-to-read-code and follow all the standards of the language. It will also allow you to make fewer errors.

### Automated Testing

Run the program with boundary input values. Do not forget to consider different cases: 0 or a huge number as an input value, 0 or 1 element as an input sequence. Such cases often reveal bugs.
Write automated tests that will check the program at the build time.



## Layered Architecture

* Presentation
* Business Logic / Domain
** Service classes act as the glue between Domain and Persistence layers. The idea is that the service will apply business rules, then forward the request to the persistence layer to manipulate the database as required.
* Persistence
* Database



## Coupling and Cohesion


Tight coupling is when classes have hard dependencies on each other (like instantiating one class inside another class).

Achieve loose coupling with DI and using interfaces.



Low cohesion is when a class does too many things.


Ideally, your objects should have low coupling and high cohesion.


Situations that may arise:


Making a god object (high cohesion, high coupling) — one object performs all functions.
Poorly selected boundaries (low cohesion, high coupling) — fragments of code have boundaries, but they also contain classes that shouldn't be included in them.
Destructive decoupling (low cohesion, low coupling) — parts of code have the lowest coupling possible, but that leads to a lack of focus on your code.
