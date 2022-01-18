# Kotlin idioms


https://kotlinlang.org/docs/idioms.html#what-s-next



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


