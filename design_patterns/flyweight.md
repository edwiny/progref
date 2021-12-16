# Flyweight

Use when you need to create many instances of a class.

# Class list

The idea is to maintain the state of the many objects as an array, then use class methods to perform operations on elements of the array.

E.g.

* TreeManager
  * int[] treeArrayDataX
  * int[] treeArrayDataY
* Tree
  * public static void display(x, y)
  * public static void display(TreeManager mgr, int index)   //I made this up
  


