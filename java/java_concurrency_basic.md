
## Concurrency
### Problems of concurrency

#### Inteference

Interference happens when two operations, running in different threads, but acting on the same data, interleave. This means that the two operations consist of multiple steps, and the sequences of steps overlap.

Most common example is the ++ operator: it involves a read, increment and write sequence.

#### Memory inconsistency

Memory consistency errors occur when different threads have inconsistent views of what should be the same data. 

The key to avoiding memory consistency errors is understanding the happens-before relationship. This relationship is simply a guarantee that memory writes by one specific statement are visible to another specific statement. 

#### Liveness

A concurrent application's ability to execute in a timely manner is known as its liveness. 
Deadlocks, starvation and livelocks are types of liveness problems.

Deadlock:
 * Deadlock describes a situation where two or more threads are blocked forever, waiting for each other

Starvation:
 * Starvation describes a situation where a thread is unable to gain regular access to shared resources and is unable to make progress. This happens when shared resources are made unavailable for long periods by "greedy" threads.

 Livelock:
  * A thread often acts in response to the action of another thread. If the other thread's action is also a response to the action of another thread, then livelock may result. As with deadlock, livelocked threads are unable to make further progress. However, the threads are not blocked — they are simply too busy responding to each other to resume work. This is comparable to two people attempting to pass each other in a corridor

### Basic Threads


There are 2 ways to start a thread:
* Implement the `Runnable` interface
    * More flexible way, recommened
* Subclass `Thread`
    * Kinda old skool

#### Implement Runnable

`Runnable` is a functional interface.

```
public class HelloRunnable implements Runnable {

    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new Thread(new HelloRunnable())).start();
    }

}
```

#### Subclassing Thread

The Thread class itself implements Runnable, though its run method does nothing. An application can subclass Thread, providing its own implementation of run.
```
public class HelloThread extends Thread {
    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new HelloThread()).start();
    }
}
```

### Thread.sleep

Suspends the current thread's execution. Two forms
* milliseconds
* nanoseconds
Sleep time is not precise.

### Handling interrupts

It's up to the thread to respond to interrupts and exit it's `run()` method.

**Catching InterruptedException**

Exampple:

```
// in the run() method....
try {
        Thread.sleep(4000);
    } catch (InterruptedException e) {
        // We've been interrupted: no more messages.
        return;
    }
```

**Checking the interrupted flag**

If the thread is involved in non interruptible work, you can periodically check the interrupted flag on the thread object to see if a interrupt has been invoked:

```
if (Thread.interrupted()) {
        // We've been interrupted: no more crunching.
        return;
        //also makes sense to throw new InterruptedException()
    }
```


### Join

The instance method 'join()' suspends running in the current thread until the thread whose join was called returns.

Can be interrupted.

Can also specify a timeout in milliseconds to wait for the other thread.


### Synchronisation

Threads communicate by reading and writing the same instance state. However this causes several problems:
* interference - two or more threads mutate the same state at the same time and result in unexpectedness. Even seemingly atomic actions may result in multiple instructions in the VM that can be interefered with.
* memory consistency -  when different threads have inconsistent views of what should be the same data

It's useful to think of groups of statements to have (or not have) a **happens-before** relationship with each other. That is, one statement (or group of statements) always run before another. Some of the thread operations imply a happens-before relationship:

* `Thread.start()` - any statements before this one is guaranteed to occur before the new thread starts
* `Thread.join()` - the statements in the executing thread following the join is guaranteed to follow after the thread's execution is done.

#### Intrinsic locks / aka monitors

* Every object has an intrinsic lock associated with it. 
* By convention, a thread that needs exclusive and consistent access to an object's fields has to acquire the object's intrinsic lock before accessing them, and then release the intrinsic lock when it's done with them. 
* A thread is said to own the intrinsic lock between the time it has acquired the lock and released the lock. 
* As long as a thread owns an intrinsic lock, no other thread can acquire the same lock. The other thread will block when it attempts to acquire the lock.
* When a thread releases an intrinsic lock, a **happens-before relationship** is established between that action and any subsequent acquisition of the same lock.

A thread becomes the owner of the object's monitor in one of three ways:

* By executing a synchronized instance method of that object.
* By executing the body of a synchronized statement that synchronizes on the object.
* For objects of type Class, by executing a synchronized static method of that class.

#### synchronised methods

Add `synchronized` to method declaration.

Synchronization is built around an internal entity known as the intrinsic lock or monitor lock. There is a monitor per Class and per object of that class (so instances methods are locked separately from the class methods.)

example:
```
public synchronized void increment() {
        c++;
    }
```

* it is not possible for two invocations of synchronized methods on the same object to interleave. When one thread is executing a synchronized method for an object, all other threads that invoke synchronized methods for the same object block (suspend execution) until the first thread is done with the object.
* When a synchronized method exits, it automatically establishes a happens-before relationship with any subsequent invocation of a synchronized method for the same object. This guarantees that changes to the state of the object are visible to all threads.
* Note that constructors cannot be synchronized — using the synchronized keyword with a constructor is a syntax error.
* Synchronisation can cause **liveness** problems.


#### synchronised statements

Unlike synchronized methods, synchronized statements must specify the object that provides the intrinsic lock:

```
public void addName(String name) {
    synchronized(this) {
        lastName = name;
        nameCount++;
    }
    nameList.add(name);
}
```

Useful for improving concurrency with fine-grained synchronization.

#### Reentrant Synchronisation

Allowing a thread to acquire the same lock more than once enables reentrant synchronization. This describes a situation where synchronized code, directly or indirectly, invokes a method that also contains synchronized code, and both sets of code use the same lock. This is to prevent a thread from blocking itself.

#### volatile variables and atomic access

When a variable is declared with the `volatile` keyword it means reads and writes to that variable is atomic.
Btw, reading and writing to variables of basic types except double and long are also atomic.

* Changes to a volatile variable are always visible to other threads. 
* What's more, it also means that when a thread reads a volatile variable, it sees not just the latest change to the volatile, but also the side effects of the code that led up the change.
* Using simple atomic variable access is more efficient than accessing these variables through synchronized code, but requires more care by the programmer to avoid memory consistency errors.


#### Guarded Blocks / wait() + notifyAll() pairs

* The `wait()` function releases the object's intrinsic lock and suspends the current thread
* Must already own the intrinsic lock (easiest is via synchronised method call).
* Another thread, after obtainin the intrinsic lock can call `notify()` (wakes up a random waiting thread) or `notifyAll()` to wake up all waiting threads.
* The `wait()` call can be interrupted so best to always call it in a loop.


An example of a Producer-Consumer:

```
public class Drop {
 
    private String message;
    // True if consumer should wait for producer to send message, false if producer should wait for consumer to retrieve message.
    private boolean empty = true;

    public synchronized String take() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        empty = true;
        notifyAll();
        return message;
    }

    public synchronized void put(String message) {
        while (!empty) {
            try { 
                wait();
            } catch (InterruptedException e) {}
        }
        empty = false;
        this.message = message;
        notifyAll();
    }
}

######

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        String importantInfo[] = {
            "Mares eat oats",
            "Does eat oats",
            "Little lambs eat ivy",
            "A kid will eat ivy too"
        };
        Random random = new Random();

        for (int i = 0;
             i < importantInfo.length;
             i++) {
            drop.put(importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }
        drop.put("DONE");
    }
}

#####

import java.util.Random;

public class Consumer implements Runnable {
    private Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        for (String message = drop.take();
             ! message.equals("DONE");
             message = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %s%n", message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }
    }
}

####

public class ProducerConsumerExample {
    public static void main(String[] args) {
        Drop drop = new Drop();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}
```

### Immutable Objects

An object is considered immutable if its state cannot change after it is constructed. Maximum reliance on immutable objects is widely accepted as a sound strategy for creating simple, reliable code. They are particularly useful in concurrent applications. Since they cannot change state, they cannot be corrupted by thread interference or observed in an inconsistent state.

Some tips for making classes immutable:
* Don't provide "setter" methods — methods that modify fields or objects referred to by fields.
* Make all fields final and private.
* Don't allow subclasses to override methods. The simplest way to do this is to declare the class as final. A more sophisticated approach is to make the constructor private and construct instances in factory methods.
* If the instance fields include references to mutable objects, don't allow those objects to be changed:
   * Don't provide methods that modify the mutable objects.
   * Don't share references to the mutable objects. Never store references to external, mutable objects passed to the constructor; if necessary, create copies, and store references to the copies. Similarly, create copies of your internal mutable objects when necessary to avoid returning the originals in your methods.

### High level concurrency objects

#### Lock Objects

The `java.util.concurrent.locks` package provides a `Lock` and `Condition` objects that can be used over basic synchronisation or intrinsic locks.
Advantage:
* you can back out of a lock attempt - the `tryLock` method backs out if the lock is not available immediately or before a timeout expires 

```
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

...

private final Lock lock = new ReentrantLock();

try {
        myLock = lock.tryLock();
    } finally {
        if (! myLock)  {
           //lock attempt failed
        }
    }


}
lock.unlock();
```

#### Executers

Executers - objects that encapsulate thread managment and creaton.

3 Interfaces defined in the `java.util.concurrent` package:
* `Executor` - for launching new tasks
* `ExecutorService` - subinterface of Executor, which adds features that help manage the lifecycle, both of the individual tasks and of the executor itself.
* `ScheduledExecutorService` - subinterface of ExecutorService, supports future and/or periodic execution of tasks

**Executor**:

Provides a single method, execute, designed to be a drop-in replacement for a common thread-creation idiom. If r is a Runnable object, and e is an Executor object you can replace:

```
# launch new thread immediately
(new Thread(r)).start();
```
with
```
e.execute(r);
```
Depending on the interface implmentation this will:
* likely to reuse an existing worker thread
* or place r in a queue of worker threads

**ExecutorService**:

Add a `submit` method to Executor that:
* takes `Runnable` as well as `Callable` objects
* returns a `Future` which is a construct to obtain the return value of a `Callable`

Furthermore the interface prescribes methods for managing the state of tasks.

**ScheduledExecutorService**:

Adds the `schedule` method that:
* executes a Runnable or Callable task after a specified delay.

Also adds:
* `scheduleAtFixedRate` - execute tasks repeatedly
* `scheduleWithFixedDelay` - execute tasks at defined intervals

#### Thread pools

Thrreads are actually memory intensive and their creation is resource intense.

Java offers thread pool capability which are pools of generic threads that are constantly running
and replaced with new threads should old ones die.

Tasks are submitted to the pool via an internal queue, which holds extra tasks whenever there are more active tasks than threads..

Thread pools allow applications to degrade gracefully because tasks that it cannot handle yet are queued up in stead of overwhelming system resources.

Can be created via factory methods of the `java.util.concurrent.Executors` class:

* `newFixedThreadPool` - fixed size thread pool
* `newCachedThreadPool` -  expandable queue, suitable for many short-lived tasks
* `newSingleThreadExecutor` - executes a single task at a time
* Several factory methods that provide implementations of the `ScheduledExecutorService` interface

These classes also provide thread pools:

* `java.util.concurrent.ThreadPoolExecutor` - more generic with options to fine tune `Executers`
* `java.util.concurrent.ScheduledThreadPoolExecutor` 

#### Fork/Join

Designed for work that can be broken into smaller pieces recursively. The goal is to use all the available processing power.
Implementation of the `ExecutorService` interface, so it creates a thread pool. However it uses a work-stealing algorithm. Worker threads that run out of things to do can steal tasks from other threads that are still busy.

Use the `ForkJoinPool` class to create the pool and exectute `ForkJoinTask` processes.

The tasks should follow this pattern and be wrapped in sublcasses of either `RecursiveTask` (which can return a result) or `RecursiveAction`:

```
if (my portion of the work is small enough)
  do the work directly
else
  split my work into two pieces
  invoke the two pieces and wait for the results
```

Then create the object that represents all the work to be done and pass it to the `invoke()` method of a `ForkJoinPool` instance.


Example: blurring an image by going through an array of pixels and producing a new array of pixels averaged by it's neighbours:

```
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import javax.imageio.ImageIO;
 

public class ForkBlur extends RecursiveAction {
 
    private int[] mSource;
    private int mStart;
    private int mLength;
    private int[] mDestination;
    private int mBlurWidth = 15; // Processing window size, should be odd.
 
    public ForkBlur(int[] src, int start, int length, int[] dst) {
        mSource = src;
        mStart = start;
        mLength = length;
        mDestination = dst;
    }
 
    // Average pixels from source, write results into destination.
    protected void computeDirectly() {
        int sidePixels = (mBlurWidth - 1) / 2;
        for (int index = mStart; index < mStart + mLength; index++) {
            // Calculate average.
            ...
            mDestination[index] = dpixel;
        }
    }
    protected static int sThreshold = 10000;
 
    @Override
    protected void compute() {
        if (mLength < sThreshold) {
            computeDirectly();
            return;
        }
 
        int split = mLength / 2;
 
        invokeAll(new ForkBlur(mSource, mStart, split, mDestination),
                new ForkBlur(mSource, mStart + split, mLength - split, 
                mDestination));
    }
 
    // Plumbing follows.
    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(srcFile);
                  
        BufferedImage blurredImage = blur(image);
        ImageIO.write(blurredImage, "jpg", dstFile);
         
         
    }
 
    public static BufferedImage blur(BufferedImage srcImage) {
        int[] src = srcImage.getRGB(0, 0, w, h, null, 0, w);
        int[] dst = new int[src.length];
 
        int processors = Runtime.getRuntime().availableProcessors();
 
        ForkBlur fb = new ForkBlur(src, 0, src.length, dst);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(fb);
 
        BufferedImage dstImage =
                new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        dstImage.setRGB(0, 0, w, h, dst, 0, w);
 
        return dstImage;
    }
}
```

ForkJoins are also used in:
* Arrays.parallelSort()
* Streams 

#### Concurrent Collections

`java.util.concurrent` provides these thread safe collection interfaces:

* `BlockingQueue` -  blocks or times out when you attempt to add to a full queue, or retrieve from an empty queue.
* `ConcurrentMap` - defines atomic functions to remove or replace a key-value pair only if the key is present, or add a key-value pair only if the key is absent.
* `ConcurrentNavigableMap` - sub interface of `ConcurrentMap`, upports approximate matches

#### Atomic Variables

The ` ava.util.concurrent.atomic` defines classes that support atomic operations on single variables. They all have `get` and `set` methods. E.g

* `AtomicBoolean`
* `AtomicInteger`
* `AtomicIntegerArray`
* `AtomicReference<V>`

Classes in this package extend the notion of `volatile` values, fields, and array elements to those that also provide an atomic conditional update operation of the form:
```
  boolean compareAndSet(expectedValue, updateValue);
```

This method (which varies in argument types across different classes) atomically sets a variable to the updateValue if it currently holds the expectedValue, reporting true on success. 


Exampple:

```
import java.util.concurrent.atomic.AtomicInteger;

class AtomicCounter {
    private AtomicInteger c = new AtomicInteger(0);

    public void increment() {
        c.incrementAndGet();
    }

    public void decrement() {
        c.decrementAndGet();
    }

    public int value() {
        return c.get();
    }

}
```