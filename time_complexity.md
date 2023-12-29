# Time complexity and Big-O classifications

The running time of an algorithm depends on the input size. The efficiency is expressed in Big-O notation and describes the upper bound of the function's growth rate. This means we can predict the maximum time a algorithm will take given its input size.

Put it another way: an algorithm's performance is measured by its **worst case**.

However, 2 algorithms may have the same worst-case, but different average cases.

## Common Growth Rates

From fastest to slowest.

### O(1) Constant time

Duration does not depend on input size.

Typical algorithms:
* using a formulae to arrive at an answer

Data structures:
 * Hash table (search, insert, delete) (avg case)

### O(log n) logarithmic time

Algorithms that typically divide inputs into halves.

### O(n) linear time

Time is proportional to the input size.
Typically all elements are iterated once.

### O(n²) Quadratic time

Go through all pairs of input elements.
These algorithms typically have two nested loops.

### O(2ⁿ) Exponntial time

Algorithms that scan all subsets of elements.

## Calculating time complexity of a algorithm

* Ignore constants. Iterating all elements 5 times does not yield O(5n), it's still just O(n)
* Need to go over the n elements n times? n . O(n) = O(n²)
* If algo is doing several things in sequence with different Big-O classes, then only take the slowest/largest  of them. 

