# Java testing notes - Junit

## Conventions

* Test location: `src/test/java`
* Test classes should end with Test - especially for Maven Surefire test plugin that auto discovers test classes
* A test name should explain what it does
* Test names can include Should e.g. `menuShouldGetActive`
* Test names can also follow this convention `Given[ExplainYourInput]When[WhatIsDone]Then[ExpectedResult]`


## Quick example


```
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MyTests {

    @Test
    public void multiplicationOfZeroIntegersShouldReturnZero() {
        MyClass tester = new MyClass(); // MyClass is tested

        // assert statements
        assertEquals(0, tester.multiply(10, 0), "10 x 0 must be 0");
        assertEquals(0, tester.multiply(0, 10), "0 x 10 must be 0");
        assertEquals(0, tester.multiply(0, 0), "0 x 0 must be 0");
    }
}
```

## Test execution order

Tests must never rely on other tests in order to run.
Junit makes the order deterministic but not predictable by default.
Can be controlled with these class annotations:

```
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

```


## Common Annotations

* `@Test` - identifies a test method
* `@Test (expected = Exception.class)` - Fails if the method does not throw the named exception.
* `@Test(timeout=100)` - fail test if not complete in 100ms
* `@Before` - runs before **each test**
* `@After` - runs after each test
* `@BeforeClass` - runs once before any tests. Do time consuming tasks here like db setup. Methods must be declared `static`.
* `@AfterClass`  - runs once after any tests. Methods must be `static`.
* `@Ignore` or `@Ignore("Why disabled")` - ignore e.g. if underlying code has changed but test not updated.


## Assert methods

* `fail([message])` -  Let the method fail. Might be used to check that a certain part of the code is not reached or to have a failing test before the test code is implemented. 
* `assertTrue([message,] boolean condition)`
* `assertFalse([message,] boolean condition)`
* `assertEquals([message,] expected, actual)`
* `assertNull([message,] object)`
* `assertNotNull([message,] object)`
* `assertSame([message,] expected, actual)` - Checks that both variables refer to the same object.


## Test Suites

Combine several test classes into suites. Running a test suite executes all test classes in that suite in the specified order. A test suite can also contain other test suites.

```
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        MyClassTest.class,
        MySecondClassTest.class })

public class AllTests {

}
```
