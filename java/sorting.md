# Sorting


## Sorting a hashmap based on a custom field

We can use the `sorted` function to supply a `Comparator` using a lambda expression.

Note that `entrySet()` is a class that contains both key and entry.

```
candidates.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().votes - e1.getValue().votes)
                .map(v -> "candidate: " + v.getKey() + " votes: " +  v.getValue().votes + "\n")
                .collect(Collectors.toList());
```


Another way to do same:

```
candidates.entrySet()
                .stream()
                .sorted((c1, c2) -> Integer.compare(c2.getValue().votes, c1.getValue().votes))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
```
