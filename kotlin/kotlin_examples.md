# Examples of my attempts vs pros

## Search

### My attempt


```
package search

class Search {
    var lines = mutableListOf<String>()

    fun loadDataFromConsole() {
        println("Enter the number of people:")
        val numExpected = readLine()!!.toInt()
        println("Enter all people:")
        repeat(numExpected) {
            lines.add(readLine()!!)
         }
    }

    fun searchLines(search: String) : List<Int> {
        val matches = mutableListOf<Int>()

        val lcSeardh = search.lowercase()

        for ( i in 0..lines.lastIndex) {
            if(lines[i].lowercase().indexOf(lcSeardh) != -1) matches.add(i)
        }
        return (matches.toList())
    }

    fun search() {
        println("Enter data to search people:")
        val search = readLine()!!
        val matches = searchLines(search)
        if(matches.isEmpty()) {
            println("No matching people found.")
        } else {
            matches.forEach { println(lines[it]) }
        }
    }
}


fun main() {
    val s1 = Search()
    s1.loadDataFromConsole()
    println("Enter the number of search queries:")
    val numSearches = readLine()!!.toInt()
    repeat(numSearches) {
        s1.search()
    }
}
```

### Pro


```
fun main_pro() {
    val scanner = Scanner(System.`in`)
    println("Enter the number of people:")
    val dataLines = Array(scanner.nextLine().toInt()) {
        scanner.nextLine().trim()
    }
    println("Enter the number of search queries:")
    repeat(scanner.nextLine()!!.toInt()) {
        val q = scanner.nextLine().trim()
        dataLines.filter { it.contains(q, true) }.apply {
            if(isEmpty())
                println("Not Found")
            else
                forEach(::println)
        }
    }
}
```

