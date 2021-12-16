# Chain of Responsibility

Use when you want to give more than one object a chance to handle a request.

## Class list

* Handler (class)
   * private Handler successor;
   * handleRequest()
* Handler1 extends Handler
* Handler2 extends Handler
* Handler3 extends Handler

Each object in the chain is a handler. It's initialised with a successor which is also a handler.
If it can handle the requeset it does so and the chain can end there. Otherwise it calls the next
successor's `handleRequest()` method.

