# JVM

## Components


### Class Loader

* Loading class files
  * Bootstrap Class Loader
     * loads standard packages such as `java.lang`, `java.util`
  * Extension class loader
     * load the extension classes
  * Application Class Loader
     * Loads the classes on the class path - `ClassLoader.loadClass()`


* Linking
  * Verification: version, structural correctness, constraints
  * Preparation: initlialise memory for static fields
  * Resolution: match up symbol references to code
* Initialisation
  * Call constructors, initialisation blocks, assign values to static vars


### Runtime Data Area

* Method area
  * Code for methods, runtime constants
  * Only one per JVM
* Heap area
  * Objects and their corresponding instance vars
  * Only one per JVM
* Stack Area
  * Local vars, method calls / call stack, partial results
  * One per thread
* Program Counter Registers
  * Holds the instruction pointer / current instruction
  * On per thread
* Native Method Stacks
  * One per thread

### Execution Engine

* Interpreter
  * Reads and executes the bytecode
  * Slow
* JIT Compiler
  * Intermediate Code Generator - Translates bytecode to intermediate code
  * Code Optimiser - Optimises the code for better performance
  * Target Code Generator - converts intermediate code to native machine code
  * Profiler - finds hotspots (code that is executed repeatedly)
* Garbage Collector
  * Find unreferenced objects. First Marks them, then cleans up later in a Sweep.

### Java Native Interface

Bridge for intefacing with .so's.

### Native Method Libraries

.so's 



## Garbage Collection


Two main approaches:

Reference Counting:
* Each object has a field that gets incremented / decremented each time it's referenced
* Can be slow due to constant memory access

Tracing:
* Find referenced objects and marks the rest as garbage, starting at the GC Roots (local vars, method params,, threads, static vars)
* Used most widely


Memory cleanup:

Method 1: Object generation

Generational Hypothesis: most objects die young so start with the youngest objects

Bucketise the objects into generations based on age. Start cleaning up the youngest bucket first, and only move to the next bucket if insufficient amounts of memory could be freed.


Method 2: Memory regions

Similar to method one but divide memory into regions and clean up next region only if prev didn't yield enough memory. 

Problems:

* Resource consumption (memory, CPU)
* Latency - All implemtations have a "stop the world" mechanism where new object creation is put on hold while GC runs
* Memory fragmentation



## Misc

### Managing multiple versions (Debian Linux)

`sudo update-alternatives --config java`



