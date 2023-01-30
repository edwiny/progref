# Kotlin idioms


https://kotlinlang.org/docs/idioms.html#what-s-next

## using safe call operator to access nullable objects

```
val figure: Figure? = createFigure()
val otherFigure = figure?.copy(color = Color.YELLOW)
```

## Avoid using if-null checks - instead use elvis operator to deal with nulls

```
val figure: Figure = createFigure() ?: throw IllegalStateException("figure is null")
```

By using the nullable types in Kotlin, you help the compiler to validate your programs, and so make the code safer.

## Avoid using not-null assertions (!!)

Sounds like you're yelling at the compiler.

## Using let() for doing non-null checks

Old skool:
```
val order: Order? = findOrder()
if (order != null){
    dun(order.customer)
}
```

New skool:

```
findOrder()?.let { dun(it.customer) }
//or
findOrder()?.customer?.let(::dun)
```

## Value objects

it's a bit like C type-defs. Helps to be more expressive:

```
// Don't
fun send(target: String){}

// Do
fun send(target: EmailAddress){}
// expressive, readable, type-safe

data class EmailAddress(val value: String)
// Even better (Kotlin 1.3):
inline class EmailAddress(val value: String)
```

## Using extension functions

In stead of using facades to extend std library classes, can use extension functions in stead


```
fun Person.prettyPrint(): String = "Person{name=$name, age=$age}"
```

Can also use this in stead of declaring utility classes at the top level scope.


## .copy() of data classes

```
val figure = Figure(1, 2, 3, Color.YELLOW)
val (w, h, l, _) = figure.copy(color = Color.RED)
```

.copy() is also one of the main methods in kotlin to maintain immutability.


## using default arguments to prevent overloading of functions


## using named arguments


## if, when, and try are expressions

And can thus return values.

Every time you write an `if` statement consider if you can use `when` to be more concise.

Also works great for `try` clauses:

```
val json = """{"message":"HELLO"}"""
val message = try {
    JSONObject(json).getString("message")
} catch (ex: JSONException) {
    json
}
```



## apply() scope function


Runs codes as if it was a member function. Returns the object. E.g.

```
val obj1 = Car().apply {
model = "ford"
type = "suv"
}
```




## Collection initialisers


In stead of initialising an empty collection, use the initialiser lamda to populate it:

```
val cards = List(n) {
    println("Doing number ${it}")
        val term = readLine()!!
        println("The term is ${term}")
        val definition = readLine()!!
        Card(term, definition)
}
```


