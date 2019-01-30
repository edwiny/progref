# Basics of Go

## Packages
```
package main

import (
         "fmt"
         "math/rand"
       )

func main() {
   fmt.Println("My favorite number is", rand.Intn(10))
}

```
Every Go program is made up of packages.
Programs start running in package `main`
By convention, the package name is the same as the last element of the import path. For instance, the "math/rand" package comprises files that begin with the statement package rand.

imports can written as 'factored' i.e. 
```
//better style
import (
        "fmt"
        "math/rand"
        )
```

or 

```
import "fmt"
import "math/rand"
```

In Go, a name is exported if it begins with a capital letter.
When importing a package, you can refer only to its exported names. Any "unexported" names are not accessible from outside the package.

### Building Packages

Go command executables are statically linked; the package objects need not be present to run Go programs.


### Resolving package locations


The path to a package's directory determines its import path.

An import path is a string that uniquely identifies a package. A package's import path corresponds to its location inside a workspace or in a remote repository



## Functions

```
func add(x int, y int) int {
      return x + y
}

func main() {
      fmt.Println(add(42, 13))
}
```

Argument names first, followed by type.

Consecutive arguments of the same type can be abbreviated like this:

```
func add(x, y int) int {
      return x + y
}
```

Functions can return multiple values:

```
func swap(x, y string) (string, string) {
      return y, x
}

func main() {
    a, b := swap("hello", "world")
    fmt.Println(a, b)
}
```

Return values can (and should be) named:

```
func split(sum int) (x, y int) {
      x = sum * 4 / 9
      y = sum - x
      return x, y
}

func main() {
     fmt.Println(split(17))
}


```

## Variable declaration

The var statement declares a list of variables; as in function argument lists, the type is last.
A var statement can be at package or function level. 

```
var c, python, java bool
var i, j int = 1, 2
//If an initializer is present, the type can be omitted; the variable will take the type of the initializer.
var c, python, java = true, false, "no!"
```

A var declaration can include initializers, one per variable.

If an initializer is present, the type can be omitted; the variable will take the type of the initializer.


Inside a function, the := short assignment statement can be used in place of a var declaration with implicit type.
Outside a function, every statement begins with a keyword (var, func, and so on) and so the := construct is not available.

```

# outside, must use var
var myglobal int = 1

## can also used "factored" style

var (
        ToBe   bool       = false
        MaxInt uint64     = 1<<64 - 1
        z      complex128 = cmplx.Sqrt(-5 + 12i)
    )


func main() {
      var i, j int = 1, 2
      k := 3
      c, python, java := true, false, "no!"

      fmt.Println(i, j, k, c, python, java)
}
```


Variables declared without an explicit initial value are given their zero value.

The zero value is:

0 for numeric types,
false for the boolean type, and
"" (the empty string) for strings.

Type inference:
if no type specified in the declaration then type is inferred from statement to the RHS:


```
func main() {
      v := "bla"
      fmt.Printf("v is of type %T\n", v)
}

```




## Types

```
bool

string

int  int8  int16  int32  int64
uint uint8 uint16 uint32 uint64 uintptr

byte // alias for uint8

rune // alias for int32
     // represents a Unicode code point

float32 float64

complex64 complex128
```


The int, uint, and uintptr types are usually 32 bits wide on 32-bit systems and 64 bits wide on 64-bit systems. When you need an integer value you should use int unless you have a specific reason to use a sized or unsigned integer type.



To print out a variables type:
```
fmt.Printf("Type: %T Value: %v\n", MaxInt, MaxInt)
```

Type casting:


```
var i int = 42
var f float64 = float64(i)
var u uint = uint(f)
```

## Constants

Constants are declared like variables, but with the const keyword.

Constants can be untyped or typed. When they are untyped, they behave like literals.

Constants cannot be declared using the := syntax.

```
const Truth = true

# or

const (
      num1 = 1
      )
```

## Conditionals

### if

```
if x < 0 {
    return sqrt(-x) + "i"
}
```

Can intro a if statement with a optional pre statement:

```
if v := math.Pow(x, n); v < lim {
  return v
}
```


## Loops

Go has only 1 loop construct

```
for i := 0; i < 10; i++ {
    sum += i
  }
```


init and post is optional:

```
  for ; sum < 1000; {
    sum += sum
  }
```


# Types

## Arrays

The type `[n]T` is an array of n values of type T.

Declare a variable a as an array of ten integers:

```
var a [10]int
```

Arrays' length is part of it's type, so cannot be resized.

## Slices

The type `[]T` is a slice with elements of type T.

Proides a flexible view into the elements of an array. It is formed by
specifiny two indicies:
* lower: includes element at this position, default is zero
* upper: gets up to this element but not including. default is length of the underlying array.

These slices are all equivalent:

```
var a [10]int
a[0:10]
a[:10]
a[0:]
a[:]
```

Slices are like references to an array. A slice does not store any data, it just describes
a section of an underlying array.  Changing the elements of a slice modifies the corresponding elements of its underlying array.

A slice typically returns a subset of an underlying array. As such, it has a length and a capacity. The length indicates the number of items in the slice's view of the array, while capacity refers to how many elements in the underlying array, offset by the start of the slice.

E.g.

```
slice1 := []int{1, 2, 3, 4, 5}
slice3 := slice1[:2]   # returns two elements from the start of the array, len=2 cap=5 values=[1 2]
slice4 := slice1[3:5]  # returns the last 2 elements and capacity is reduced: len=2 cap=2 values=[4 5]
```


A slice literal is like an array literal without the length.

This creates an array and returns a slice into it:

```
[]bool{true, true, false}
```


Slice has a length and capacity. Capacity is the length of the underlying array.
```
len(s)
cap(s)
```

Slices have a builtin reference (own interpretation) to the underlying array. 
You can create a new slice from an old slice with non overlapping elements. E.g.


```
    s := []int{2, 3, 5, 7, 11, 13}

    // Slice the slice to give it zero length.
    s = s[:0]

    // Extend its length.
    s = s[:4]

    // Drop its first two values.
    s = s[2:]
```


The zero value of a slice is nil.

A nil slice has a length and capacity of 0 and has no underlying array:

```
    var s []int
    fmt.Println(s, len(s), cap(s))
    if s == nil {
        fmt.Println("nil!")
    }
```

### Dynamically sized arrays aka slices

Create slices with builtin function `make`:

```
a := make([]int, 5)  // len(a)=5
```

It creates a zeroed array and returns a slice of the specified length.
Optionally you can provide a cap argument:

```
b := make([]int, 0, 5) // len(b)=0, cap(b)=5
```

### Multi dimensional slices / arrays

```
board := [][]string{
        []string{"_", "_", "_"},
        []string{"_", "_", "_"},
        []string{"_", "_", "_"},
    }
```

### nil slices

`nil` is the zero value for a slice. It has len 0 and cap 0, and no underlying array.

### Allocating memory for dynamic arrays

Use the builtin function `make` to allocate memory for a new array. It returns a slice into the new array:

```
b := make([]int, 0, 5) // len(b)=0, cap(b)=5
```

To grow a slice, use the builting `appand` function:


The first parameter s of append is a slice of type T, and the rest are T values to append to the slice.

The resulting value of append is a slice containing all the elements of the original slice plus the provided values.

If the backing array of s is too small to fit all the given values a bigger array will be allocated. The returned slice will point to the newly allocated array.


```
    var s []int
    printSlice(s)

    // append works on nil slices.
    s = append(s, 0)

    // The slice grows as needed.
    s = append(s, 1)

    // We can add more than one element at a time.
    s = append(s, 2, 3, 4)
```


### Looping over slices


Use the `range` form of the for loop:

```
for i, v := range pow {
            fmt.Printf("2**%d = %d\n", i, v)
}
```

To discard the index value, assign it to `_` :

```
for _, value := range pow {
    fmt.Printf("%d\n", value)
}
```

**NOTE** the slice element is passed by value so changes to it is not reflected in the slice/array.

## Structs

```
package main

import "fmt"

type Vertex struct {
    X int
    Y int
}

func main() {
    fmt.Println(Vertex{1, 2})
    v.X = 4
    fmt.Println(v.X)
}

```

To access the field X of a struct when we have the struct pointer p we could write `(*p).X`. However, that notation is cumbersome, so the language permits us instead to write just p.X, without the explicit dereference.


```
    v := Vertex{1, 2}
    p := &v
    p.X = 1e9
    fmt.Println(v)
```

Notice how structs can be created with literals: `v := Vertex{1,2}`. This is like a class constructor.
Field can be named in the initiliaser list and are optional:

```
    v2 = Vertex{X: 1}  // Y:0 is implicit
```


Initialising array of structs:

```

    s := []struct {
        i int
        b bool
    }{
        {2, true},
        {3, false}
    }

```

## Maps

You create maps with `make` function:


```

type Vertex struct {
    Lat, Long float64
}

var m map[string]Vertex

func main() {
    m = make(map[string]Vertex)
    m["Bell Labs"] = Vertex{
        40.68433, -74.39967,
    }
    fmt.Println(m["Bell Labs"])
}
```


Referencing a undefined key returns the zero value for the map's element type.

### Map literals

Example:

```
type Vertex struct {
    Lat, Long float64
}

var m = map[string]Vertex{
    "Bell Labs": Vertex{
        40.68433, -74.39967,
    },
    "Google": Vertex{
        37.42202, -122.08408,
    },
}

```

If the toplevel type is just a type then you can omit it:

```

type Vertex struct {
    Lat, Long float64
}

var m = map[string]Vertex{
    "Bell Labs": {40.68433, -74.39967},
    "Google":    {37.42202, -122.08408},
}

```


### get, set, and delete, and key test
 
```
m[key] = elem
elem = m[key]
delete(m, key)
```

Test key exists by:

```
elem, ok = m[key]
# or if not declared yet:
elem, ok := m[key]
```

If not exists, then elem will be zero value for that type, and `ok` will be false.





## Functions


Functions are types to and values of which can be passed around or returned from other functions, or arguments.


```

import (
    "fmt"
    "math"
)

func compute(fn func(float64, float64) float64) float64 {
    return fn(3, 4)
}

func main() {
    hypot := func(x, y float64) float64 {
        return math.Sqrt(x*x + y*y)
    }
    fmt.Println(hypot(5, 12))

    fmt.Println(compute(hypot))
    fmt.Println(compute(math.Pow))
}

```


### Clojures


A closure is a function value that references variables from outside its body. The function may access and assign to the referenced variables; in this sense the function is "bound" to the variables.

```
import "fmt"

func adder() func(int) int {
    sum := 0
    return func(x int) int {
        sum += x
        return sum
    }
}

func main() {
    pos, neg := adder(), adder()
    for i := 0; i < 10; i++ {
        fmt.Println(
            pos(i),
            neg(-2*i),
        )
    }
}
```

## Classes

Go does not have classes. In stead, you can define methods on types.


## Methods

A method is a function with a special *reciever* argument, delcared between the `func` keyword and the method name.
Any instance of that type will then get that method.



```

type Vertex struct {
    X, Y float64
}

// declare with 'value' receiver make recieve readonly
func (v Vertex) Abs() float64 {
    return math.Sqrt(v.X*v.X + v.Y*v.Y)
}

// declare with 'pointer' receiver to enable method to change receiver.
func (v *Vertex) Scale(f float64) {
    v.X = v.X * f
    v.Y = v.Y * f
}

func main() {
    v := Vertex{3, 4}
		v.Scale(10)
    fmt.Println(v.Abs())
}

```

You can only declare methods on types that are defined in the same package.
You can declare methods on non-struct typedefs too, e.g. if you had typedef for your own float.
Pointer receivers are more common'

Go automatically interprets method expressoins as pointer receivers where appropriate:


```
func (v *Vertex) Scale(f float64) {
    v.X = v.X * f
    v.Y = v.Y * f
}

..
v := Vertex{3, 4}
v.Scale(2)             //works fine
p := &Vertex{4, 3}
p.Scale(3)             //works fine

```

In general, all methods on a given type should have either value or pointer receivers, but not a mixture of both. 

## Interfaces

A value of interface type can hold any value that implements those methods.

To delcare:

```
type Abser interface {
    Abs() float64
}
```

To use:

```
var a Abser
    f := MyFloat(-math.Sqrt2)
    v := Vertex{3, 4}

    a = f  // a MyFloat implements Abser
    a = &v // a *Vertex implements Abser

```

Types implement interfaces implicitly.  There is no explicit declaration of intent, no "implements" keyword.

Under the covers, interface values can be thought of as a tuple of a value and a concrete type:

```
(value, type)
```

An interface value holds a value of a specific underlying concrete type.
Calling a method on an interface value executes the method of the same name on its underlying type.

It's okay for a interface variable to have a `nil` value. It is common for methods to check for a `nil` receiver.

But you can't call a method on a `nil` interface value.

### The empty interface

```
interface{}
```

An empty interface may hold values of any type. Empty interfaces are used by code that handles values of unknown type. For example, fmt.Print takes any number of arguments of type interface{}

### Type assertions aka how to implement functions taking any type of argument

A type assertion provides access to an interface value's underlying concrete value.
If you try to access it with the wrong type, a runtime error will occur.
A tuple is returned with an okay code that you can us to prevent the panic.

```
var i interface{} = "hello"

s := i.(string)
fmt.Println(s)

s, ok := i.(string)
fmt.Println(s, ok)

f, ok := i.(float64)
fmt.Println(f, ok)

f = i.(float64) // panic
fmt.Println(f)
```

A **type switch** is a special form of a switch statement that allows you to evaluate a interface value against a series of types. Here is an example that shows how to implement a multi type function printf style:

 
```
func voila(x interface{}) {
    switch v := x.(type) {

        case string:
            fmt.Printf("string - value is %s\n", v)
        case int:
            fmt.Printf("int - value is %d\n", v)
        default:
            fmt.Printf("I don't know about type %T!\n", v)
    }
}

func main() {

    fmt.Printf("test")

    voila("hello")
    voila(5)
    voila(true)
}


```

### Stringers

Popular interface to help print custom types via Printf family. It's declared in the "fmt" package

```
type Person struct {
    Name string
    Age  int
}

func (p Person) String() string {
    return fmt.Sprintf("%v (%v years)", p.Name, p.Age)
}

func main() {
    a := Person{"Arthur Dent", 42}
    z := Person{"Zaphod Beeblebrox", 9001}
    fmt.Println(a, z)
}
```


## Errors


Functions / methods express error state with `error` values.

It's a builtin inteface:

```
type error interface {
    Error() string
}
```

Functions often return an error value, and calling code should handle errors by testing whether the error equals `nil`.

```
i, err := strconv.Atoi("42")
if err != nil {
    fmt.Printf("couldn't convert number: %v\n", err)
    return
}
fmt.Println("Converted integer:", i)
```

Typically you define your own error types:

```

type MyError struct {
    When time.Time
    What string
}

func (e *MyError) Error() string {
    return fmt.Sprintf("at %v, %s",
        e.When, e.What)
}

func Sqrt() float64, error {

    return 0, &MyError{
        time.Now(),
        "it didn't work",
    }
}

```
NOTE I did't test the above program section.


OR you can reuse the errors.New() function:

```
func Sqrt(f float64) (float64, error) {
    if f < 0 {
        return 0, errors.New("math: square root of negative number")
    }
    // implementation
}
```

Errors should show contextualised info. E.g. instead of `file not found` do `/some/path: file not found`


Function returning the error should look something like this:

```
func Sqrt(x float64) (float64, error) {
    return 0, nil
}
```

# Building Go 

## Code Organisation

Go source, package dependencies and build artefacts are store in a single 'workspace' directory at path $GOPATH.

Typically a workspace consists of multiple source repos (different to other tools where the repo is often the base
unit of code organisation). Each repo typically contains one or more packages.

Most Go programmers keep all their Go source code and dependencies in a single workspace.

Each package consists of one or more Go source files, in a single directory.

The path to a package's directory determines the import path.

Workspaces have 3 directories at the root:

* `src` contains Go source files,
* `pkg` contains package objects, and
* `bin` contains executable commands.

The go tool builds source packages and installs the resulting binaries to the pkg and bin directories.

The src subdirectory typically contains multiple version control repositories

Example layout:


```
bin/
    hello                          # command executable
    outyet                         # command executable
pkg/
    linux_amd64/
        github.com/golang/example/
            stringutil.a           # package object
src/
    github.com/golang/example/
        .git/                      # Git repository metadata
	hello/
	    hello.go               # command source
	stringutil/
	    reverse.go             # package source
	    reverse_test.go        # test source
    golang.org/x/image/
        .git/                      # Git repository metadata
	bmp/
	    reader.go              # package source
	    writer.go              # package source

```

The naming of the locations under the src dir is significant, as it will determine the import path used by any go program using your package.

Typically, the path should reflect the location of the repo, e.g. like this:
```github.com/reponame```


## ENV vars

Set these in your shell's .profile:

```
GOPATH="location to your workspace"
# if you want to easily execute go commands that you've built:
PATH="$PAtH:$GOPATH/bin"
```


## Build cycle

```go build``` compiles the code

```go install``` compiles and installs to pkg or bin top level directories in the workspace
Go command executables are statically linked; the package objects need not be present to run Go programs.


# Concurrency


## Goroutines


A goroutine is a lightweight thread managed by the Go runtime.


```
go f(x, y, z)
```

starts a new goroutine running


```
f(x, y, z)
```

The evaluation of f, x, y, and z happens in the current goroutine and the execution of f happens in the new goroutine.

Goroutines run in the same address space, so access to shared memory must be synchronized.

Example:

```
package main

import (
	"fmt"
	"time"
)

func say(s string) {
	for i := 0; i < 5; i++ {
		time.Sleep(100 * time.Millisecond)
		fmt.Println(s)
	}
}

func main() {
	go say("world")
	say("hello")
}
```


## Channels

Like a FIFO, but typed. 

Create like:

```
c := make(chan int)
```

Use like:

```
ch <- v    // Send v to channel ch.
v := <-ch  // Receive from ch, and assign value to v.
```

Pass it around like
```
func sum(s []int, c chan int) {
```
By default, sends and receives block until the other side is ready. This allows goroutines to synchronize without explicit locks or condition variables. NOTE: the go runtme will complain fatally if readers and writers are not balanced, i.e. if trying to write to a channel but there's no read operation waiting.

Can make channels buffered, so they will only block when sending to a full channel. Reading from it will automatically reduce the buffer.

```
ch := make(chan int, 100)
```
### Closing channels

The writing end of a channel can be closed. The receiver can test for closure by capturing a status variable with read operations like this:

```
//sender closes:
close(c)

//receiver checks for closed channel
v, ok := <-ch
if(!ok) {
	//channel closed
}
```

### range


You can use the `range` function to consume all the values from a channel:

```
	for i := range c {
		fmt.Println(i)
	}
```

## Select

Lets a goroutine wait on multiple communication operations.

A select blocks until one of its cases can run, then it executes that case. It chooses one at random if multiple are ready.

E.g.

```
select {
		case c <- x:
			x, y = y, x+y
		case <-quit:
			fmt.Println("quit")
			return
}
```

The `default` case in a select is run if no other case is ready.

## Mutex

Go's standard library provides mutual exclusion with sync.Mutex and its two methods:

```
Lock
Unlock
```

We can also use `defer` to ensure the mutex will be unlocked.


# IO

## Readers

Most standard Go library IO functions implement a bunch of interfaces defined in `io` package. (Reader, Writer?)

The io.Reader interface has a Read function:

```
func (T) Read(b []byte) (n int, err error)
```

 It populates the given byte slice with data and returns the number of bytes populated and an error value. It returns an io.EOF error when the stream ends.


## Simple File Reading

### Slurping

`ioutil.ReadFile` is the simplest form:

```
    body, err := ioutil.ReadFile(filename)
    if err != nil {
        return nil, err
    }

```

Alternatively, to manage your own memory, need to use stat to get size of file first and allocate sufficient memory.


```
package main

import (
        "fmt"
        "os"
       )

func main () {
    file, err := os.Open("testfile1.txt")
    if(err != nil) {
        fmt.Println(err)
        return
    }

    defer file.Close()

    fileinfo, err := file.Stat()
    if(err != nil) {
        fmt.Println(err)
        return
    }

    filesize := fileinfo.Size()
    buffer := make([]byte, filesize)

    bytesread, err := file.Read(buffer)
    if (err != nil) {
        fmt.Println(err)
    }


    fmt.Println("bytes read:", bytesread)
    fmt.Println("contents as string: ", string(buffer))
}
```

### Scanning line by line

```
package main


import (
        "fmt"
        "bufio"
        "os"
       )


func main() {

    file, err := os.Open("testfile1.txt")
    if(err != nil) {
        fmt.Println(err)
        return
    }

    defer file.Close()
    scanner := bufio.NewScanner(file)
    scanner.Split(bufio.ScanLines)

    var lines []string

    for scanner.Scan() {
        lines = append(lines, scanner.Text())
    }

    for _, line := range lines {
        fmt.Println(line)
    }
}
```
