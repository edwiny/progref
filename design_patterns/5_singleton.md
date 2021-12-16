# Singleton

Ensures a class has only one instance, and provides a global point of access to it.

## Example

```
public class Singleton {
    private static Singleton uniqueInstance;

    private Singleton() {}

    public static synchronised Singleton getInstance() {
        if(uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }

}
```

* Constructor is private so cannot be instantiated via traditional means
* Under multithreading the getInstance() method can result in multiple instantiations,
therefor we need to ensure it's `synchronised`.
* Another approach is initialise `uniqueInstance` with an object at class load time e.g.
`private static Singleton uniqueInstance = new Singleton()`

## Singletons as ENUMs

The best way to create a singleton that avoids all synchronisatin problems is to use Enum:

```
public enum SingletonEnum {
    INSTANCE;

    int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

public class EnumDemo {

    public static void main(String[] args) {
        SingletonEnum singleton = SingletonEnum.INSTANCE;

        System.out.println(singleton.getValue());
        singleton.setValue(2);
        
        System.out.println(singleton.getValue());
    }
}
```

