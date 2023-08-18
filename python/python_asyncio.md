# Python asyncio

asyncio is concurrency for IO bound problems. It's not concurrency for CPU bound problems.

It's basically a event loop with select() calls to multiplex io.



The io-bound operation must be 'awaitable' - typically means it must be one of the asyncio methods.

Must be called within a co-routine.


A co-routine is one of:
- a async generator func
- an awaitable (anothing run with the `await` keyword)
- a future


# usage


Call the function that will block on IO using the `await` keyword:

``` 
async def randn():
    await asyncio.sleep(3)
    return randint(1, 10)

```

It must be wrapped in a `async` function - together with `await` this makes it a co-routine.

NOTE: you can only `await` inside a `async def` function.


Then you might want to create another async function to compose the calls to your io-bound calls:


```
async def async_code():
    now = time()
    val = await asyncio.gather( *[randn() for _ in range(30)] )
    print(f"{val=}")
    elapsed = time() - now
    print(f"{elapsed=:.2} seconds")

```
Note the * in front of the list comprehension unpacks it.

Finally, blend it with the synchronous code:

```
def main():


  # normal program stuff

  asyncio.run(async_code())
```