# Proxy Pattern

Provides a surrogate or placeholder for another object to control access to it.

Use when the target object:
* is slow expensive to access (remote proxies and virtual proxies)
* needs access control (protection proxy)

## Class listx

* Subject (interface)
  * request()  //abstract
* RealSubject implements Subject (This is the expensive / remote object
  * request()
* Proxy implements Subject
  * RealSubject realSubject;
  * public Proxy() { realSubject = new RealSubject(); }
  * request() //does the caching or access control before acessing RealSubject

The Proxy creates the RealSubject.

Java's built-in support for Proxy can build a dynamic proxy class on demand
and dispatch all calls on it to a handler of your choosing.