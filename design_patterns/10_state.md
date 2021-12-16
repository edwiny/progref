# The State pattern

The State pattern allows an object to alter its behaviour when its internal state changes.
The object will appear to change its class.


The idea is to keep instances of each state object in the Context object, and let them do the required work for each state change. The state objects reference each other via the Context object which is passed to each State object as part of their initialisation.

Typically each state object has only one or two transitions that make sense. For the ones that don't make sense, the method simply does not advance the context object to a new state.

# Class list

The concrete state classes all implement the State interface so that they can be interchangable.

* State (interface)
  * handle1()  //state transition
  * handle2() ... etc
* ConcreteStateA implements State
  * Context context;
  * public ConcreteStateA(Context context) { this.context = context; }
  * handle1() { context.setState(context.getState2()) }
  * handle2() //similar. Does something then advance Context object to next state.
* ConcreteStateB implements State
  * handle1() //similar
  * handle2() //similar
* Context (the class that can be in different states)
  * State stateA;
  * State stateB;
  * State state;
  * public Context() { stateA = ConcreteStateA(this); ...}
  * requestFromClient() { state.handle1() }
  * setState(State state) { this.state = state; }

