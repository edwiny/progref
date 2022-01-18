# Commonly used Lambda expressions



## Scope functions

Executes a block of code within the context of an object.

The lambda forms a temporary scope such that you can access the object without its name.


The difference between the scope functions are:
* the way they refer to the context object
   * `this`: `run`, `with`, `apply` basically as if the lamda is a class member
   * `it`: `let`, `also` - context object is a lambda argument (`it`)
* the return value
   * return the context object - `apply`, `also`
   * return lambda result - `let`, `run`, `with`


**this example**

```
val adam = Person("Adam").apply { 
    age = 20                       // same as this.age = 20 or adam.age = 20
    city = "London"
}

```


### let

Context object: `it`
Return val: lambda result
When to use: Executing a code block only with non-null values.


### with

Context object: `this`
Return value: lambda result
When to use:


```
val service = MultiportService("https://example.kotlinlang.org", 80)


// the same code written with let() function:
val letResult = service.let {
    it.port = 8080
    it.query(it.prepareRequest() + " to port ${it.port}")
}
```

### run

Context object: `it`
Return value: lamda result
When to use: when your lambda contains both the object initialization and the computation of the return value.


```
val service = MultiportService("https://example.kotlinlang.org", 80)


val result = service.run {
    port = 8080
    query(prepareRequest() + " to port $port")
}
```


### apply

Context object: `this`
Return value: Context object
When to use: Use apply for code blocks that don't return a value and mainly operate on the members of the receiver object. The common case for apply is the object configuration.

```
val adam = Person("Adam").apply {
    age = 32
    city = "London"        
}
println(adam)
```

### also

Context object: `it`
Return value: Context object
When to use: It returns the original object which means the return data has always the same type



