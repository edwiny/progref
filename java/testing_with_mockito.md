# Testing with Mockito


## Mock Objects

A mock object is a dummy implementation for an interface or a class in which you define the output of certain method calls. Mock objects are configured to perform a certain behavior during a test. They typically record the interaction with the system and tests can validate that.


*Mockito* is a popular mock framework which can be used in conjunction with JUnit. Mockito allows you to create and configure mock objects.


## Creating Mock objects

Either via static `mock()` method or `@Mock` annotation.


### Annotation method


```
import static org.mockito.Mockito.*;

public class MockitoTest  {

    @Mock
    MyDatabase databaseMock;   //this is the class being mocked

    // you have to instruct Mockito to use the annotation 
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testQuery()  {

        //here we pass the mock to the class we're trying to test
        ClassToTest t  = new ClassToTest(databaseMock);

        boolean check = t.query("* from t");
        assertTrue(check);
        
        // verify the the 'query' method was indeed called
        verify(databaseMock).query("* from t");
    }
}
```


### Static method

Note: By adding the `org.mockito.Mockito.*;` static import, you can use methods like `mock()` directly in your tests. St

Unspecified method calls return "empty" values:

* null for objects
* 0 for numbers
* false for boolean
* empty collections for collections

## Defining methods on the mock

Mocks can return different values depending on arguments passed into a method.

### when ... thenReturn


```
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@Test
public void test1()  {
        //  create mock
        MyClass test = mock(MyClass.class);

        // define return value for method getUniqueId()
        when(test.getUniqueId()).thenReturn(43);

        // use mock in test....
        assertEquals(test.getUniqueId(), 43);
}


// this test demonstrates how to return values based on the input
@Test
public void testReturnValueDependentOnMethodParameter()  {
        Comparable<String> c= mock(Comparable.class);
        when(c.compareTo("Mockito")).thenReturn(1);
        when(c.compareTo("Eclipse")).thenReturn(2);
        //assert
        assertEquals(1, c.compareTo("Mockito"));
}
```

### doReturn ... when



### doThrow ... when

For when exceptions are expected

## Wrapping objects with spy()

You can mock out certain methods on a real object with spy():

```
Properties properties = new Properties();

Properties spyProperties = spy(properties);

doReturn(“42”).when(spyProperties).get(”shoeSize”);

String value = spyProperties.get(”shoeSize”);

assertEquals(”42”, value);
```


## Behavour testing with verify()

Mockito keeps track of all the method calls and their parameters to the mock object. You can use the verify() method on the mock object to verify that the specified conditions are met. For example, you can verify that a method has been called with certain parameters. This kind of testing is sometimes called behavior testing. Behavior testing does not check the result of a method call, but it checks that a method is called with the right parameters.



```
import static org.mockito.Mockito.*;

@Test
public void testVerify()  {
    // create and configure mock
    MyClass test = Mockito.mock(MyClass.class);
    when(test.getUniqueId()).thenReturn(43);


    // call method testing on the mock with parameter 12
    test.testing(12);
    test.getUniqueId();
    test.getUniqueId();


    // now check if method testing was called with the parameter 12
    verify(test).testing(ArgumentMatchers.eq(12));

    // was the method called twice?
    verify(test, times(2)).getUniqueId();
}

```


## Capturing arguments



The `ArgumentCaptor` class allows to access the arguments of method calls during the verification. This allows to capture these arguments of method calls and to use them for tests.


```
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


public class MockitoTests {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Captor
    private ArgumentCaptor<List<String>> captor;


    @Test
    public final void shouldContainCertainListItem() {
        List<String> asList = Arrays.asList("someElement_test", "someElement");
        final List<String> mockedList = mock(List.class);
        mockedList.addAll(asList);

        verify(mockedList).addAll(captor.capture());
        final List<String> capturedArgument = captor.getValue();
        assertThat(capturedArgument, hasItem("someElement"));
    }
}
```
