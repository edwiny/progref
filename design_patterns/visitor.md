# Visitor

When you want to add capabilities to a composite of objects and encapsulation is not important.

Works with a Traverser, who knows how to traverse a tree or list of objects.
Each objected being visit must implement a `getState()` method.
The Visitor collects this state and exposes new operation on them, without needing to add the methods to the
existing classes.

The client then works via the Visitor to "call" the new methods on objects in the tree.
