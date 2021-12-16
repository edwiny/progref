# Command Pattern

Encapsulates a request as an object, thereby letting you parameterise other
objects with different requests, queue or log requests, and support undoable actions.

Decouples an object making a request from the one who knows how to perform it.

An Invoker makes a request of a Command object by calling its `execute()` which proxies the request to the Receiver (the thing doin the actual work)

Invoker can be the Client or some other part of the system (like a thread listening on a queue)

Can also trivially implement:
* undo functionality - each Command implmenentation knows what it is doing and can undo it.
* macro functionality by creating a Command with a list of other Commands.

Commands can also be used for implementing logging and/or transactional systems.

## Example use case

Imaginge a generic remote with buttons configurable to perform any action.

Similar to a restaurant:
* customer is Client
* waitress is Invoker
* order slip is Command
* cook is Receiver
* customer gives order to waitress, who gives it to cook who works on it when he's ready.

## Class list

* Client (Creates the Command)
* Invoker
  * setCommand(Command cmd)
* Command (interface)
  * execute()
  * undo()
* Receiver
  * someAction()
* ConcreteCommand1 implements Command
  * execute() -> receiver.someAction()
  
See `coding_examples`.
## 