# Spring boot notes

## About Spring

* Application framework
* Design goal is to reduce complexity and remove unneccasary boilerplate code.
* Not all or nothing, plays nice with other frameworks or core libraries.

Major components:
* Spring Core
  * Dependency Injection, Events, Validation, data binding, AOP
  * Support for Testing
  * Spring Web
  * Can be used with Kotlin and Groovy
* Spring MVC
  * Web MVC
  * Spring MVC based on Servlet-stack web apps)
  * Spring WebFlux for React-based applications)
* Spring Persistance
  * Also part of core
  * allows Data Access: transactions, DAO, JDBC, ORM, Marshalling XML
  * Spring Data project which aims to reduce boilerplate code related to Data Access
* Spring Security
  * Moot popular security framework for JAva
  * Can be used without the rest of Spring
* Spring Cloud
  * opnionated vie of micros services framework
* Spring Boot
  * Spring Boot covers all the components and provides a simplified model via defaults and opinionated builtin config.
  * Spring boot will intelligently back up when you are overriding defaults.
  * Spring Boot does hide Spring internals and this can be dangerous, need understanding of Spring Core to go along with Boot.
  * Has Boot starters which encapsulate very specific functionality e.g. email, logging, jpa, testing. Can be added to a project
  * Actuators - production ready functionality e.g. instrumention, health checks.


## Getting Started with Spring Boot

* Can use the Spring Initliazr project to build project files: (https://start.spring.io)

## Dependency Injection

When reusing components, pass already-created objects into the constructors of classes in stead of creating them inside class.

**Spring is the thing that instantiates the classes** aka it is the IOC Container.
It has the responsibility of creating and configuring the dependencies and injecting them where needed.

Depedencies can be passed via:
* a raw field
* constructor
* setter

Dependencies == Bean

## Spring Configuration

### Configuration Classes

Create a class annotated with `@Configuration` and add methods that return
instances of the class you want to beanify. Annote each of these methods with `@Bean`.

When to use:
* when you don't want to create a dependency on Spring in your domain classes. All the Spring-specific config goes into the Spring Configration classes.
* when you want more control over bean startup logic.


### Component Scanning

Spring Boot can scan for beans to wire if the class is annotated by the `@Component` annotation.

The scanning is configured in the main class (entry point):

```
package com.bla.ls

import org.springframework.boot.SpringApplication;

@SpringBootApplication()
public class LsApp {
	public static void main(final String... args) {
		SpringApplication.run(LsApp.class, args);
	}
}
```

Note: this will scan the `com.bla.ls` package and any sub packages under that namespace e.g. `com.bla.ls.persistence`.

If you want to scan only certain classes, try:

```
@SpringBootApplication(scanBasePackages = "com.bla.ls.persistence")
```

When to use:
* This is somewhat easier method than the Configuration classes.
* Adds dependency on Spring to your domain classes, though.

#### Stereotype annotations

`@Component is a base annotation, there are others flavours that do slightly more specific things:

* `@Component` - the basic type
* `@Repository` - "Encapsulation of storage, retrieval and search which emulates a collection of objects"
* `@Service` - "Interface to operation that stands alone in the model, with no encapsulated state."


## Bean Lifecycle


* Phase 1: Initialisation
* Phase 2: General use
* Phase 3: Termination

### Initialization


First method is to add the `@PostConstruct` annotation to a method in the bean class:


```
public class BeanA {
	@PostConstruct
        public void initialise() {
	}
}
```

Second method is to use the InitMethod parameter to the @Bean constructor.
Note this is configured in the Configuration class. The Bean (domain) class
doesn't know anything about Spring.

```
   @Bean(initMethod = "initialise")
    public BeanB beanB() {
        return new BeanB();
       
    }
```

### Destroy Phase

Starts when ApplicationContext becomes eligible for garbage collection.


Very similar to startup phase, we have two methods to hook into the event:
* `@PreDestroy` annotation on a method in the bean class.
* `destroyMethod="<name_of_destroy_method>"` parameter to the `@Bean` annotation in the Configuration class.


## Setting up dependencies between beans aka wiring

So Spring will create beans from the classes you've indicated (or contributed to the application context) 
but how do they call each other?

Spring will instnatiate one copy of each bean and inject it into the specified classes via
one of 3 ways:

### Constructor based DI

Simply pass beans via constructors. 

In this example, the projectRepository bean is passed to the ProjectService bean via constructor.


```
@Service
public class ProjectServiceImpl implements IProjectService {

    private IProjectRepository projectRepository;

    public ProjectServiceImpl(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    // ...
}

```

### Setter based DI

Use the `@Autowired` annotation to indicate a setter method to inject via:

```
public class ProjectServiceImpl implements IProjectService {
  @Autowired
  public void setProjectRepository(IProjectRepository projectRepository) {
      this.projectRepository = projectRepository;
  }
```


### Field based DI


Use `@Autowire` annotation on the field declaration in the class you're trying to inject into:

```
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private IProjectRepository projectRepository;

```

### Resolving beans if multiple classes implement the interface

Injection is always done via interfaces, so if multiple classes implement the same interface, how
would Spring resolve the correct one to inject as depedency?

*Method 1*

Use the `@Qualifier` annotation to arguments where the interface is passed to indicate the implementatin class:

```

  public void setProjectRepository(@Qualifier("ProjectRepositoryImpl2") IProjectRepository projectRepository) {
```

*Method 2*

Use the `@Primary` annotation to indicate a primary class.


## Project Configuration

Spring uses a property file to:
* store simple key value pairs
* externalise configuration
* Inject config at runtime not compile time.

Spring by convention uses the file `src/java/main/resources/application.properties` for configuration.

Format of the file is typically:

```
project.prefix = BLAH
project.suffix = 123
```

To inject the values, simply annotate class variables with the `@Value` annotation:

```
@Value("${project.prefix}")
private String prefix;

@Value("${project.suffix}")
private Integer suffix;

```

## Logging

Common log levels:
* TRACE
* DEBUG
* INFO
* WARN
* ERROR

### Dependencies

For dependencies, include `spring-starter-logging` in the pom (generally already included in `spring-web-starter`).


### Configuration


NOTE that your IDE might confuse the filename patterns used to package up resources into the target jar.
To explicitly remove any excludes in the Maven pom.xml, add the following to the `<build>` section:

```
<resources>
    <resource>
        <directory>YOUR_PROJECT_HOME/src/main/resources</directory>
        <excludes>
        </excludes>
    </resource>
<resources>
  
```  


By default the loglevel is set to `INFO`. To configure customer level levels, add config in the `application.properties`.

Default for all packages:

```
logging.level.root = DEBUG
```

Pattern of packages:

### Using it in code


```
//there are several implementations, but Simple Logging Facade for Java will do
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

...

// define as member inside class
Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);


```

To use:

```
LOG.debug("Project Service >> Finding projet by id {}", id);
```


