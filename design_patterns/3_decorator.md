
# The Decorator Pattern

Attaches additional responsibilities to a object dynamically. Decorators provide a
flexible alternative to subclassing for extending functionality.

Works by base component and decorators all inheriting from the same super class. 
Inheritance is done here to achieve *type matching* not to extend behaviour.

Decorators extend a Component and can stand in as a Component. Decorators have a "HAS-A"
relationship with Components, and as such has a instance variable of the Component to extend.

We say the Decorators are *composed* together with the Components.

Class hierarchy:

* Component    (base class)
  * methodA()
  * methodB()
* ConcreteComponent inherits from Component
  * methodA()
  * methodB()
* Decorator inherits from Component
  * Component wrappedObj     //composition
  * methodA()
  * methodB()
* ConcreteDecoratorA inherits from Decorator
  * methodA()     //typically extend Component by adding new functionality here
  * methodB()
  * newMethodC()  //but you can also add new behaviour explicitly


  The constructors for the Decorators would need to accept a argument of type Component.


  # Real-world usage of Decorator Pattern

  The `java.io.*` packages are all based on the Decorator pattern.

  * InputStream is the abstract Component.
  * FileInputStream, StringBufferInputStream, ByteArrayInputStream are the concrete Components
  * FilterInputStream is a abstract Decorator
  * PushBackInputStream, BufferedInputStream, DataInputStream, ZipInputStream are all concrete Decorators

  # Drawbacks of the Decorator pattern

  Can create a lot of classes that can overwhelm users of the API.
  