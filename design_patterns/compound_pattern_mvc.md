# Compound patterns

When multiple patterns are used to create a pattern for solving a typical problem.

## MVC

Model-view-controller is a classic example of a compound pattern:

* Controller:: uses Strategy to decouple interface actions from user interactions. The Controller is the strategy for the View.
* View: typically uses Composite to manage UI elements
* Model: uses Observer to notify the View that something has changed.


See example code.
