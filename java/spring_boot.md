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

## Spring Beans

**NOTE**: By default, beans are singletons. This means that there is only one object for the whole application. 
(can be changed.)


By default, the name of a bean is the same as the name of the method that produces it. 

### Configuration Classes

Create a class annotated with `@Configuration` and add methods that return
instances of the class you want to beanify. Annote each of these methods with `@Bean`.

Example:

```

//Containing class is marked as containing spring configuration
@Configuration  // or @SpringBootApplication
public class Customers {

    //Customer class has no special config, it's just a pojo

    @Bean
    public Customer customer() {
        return new Customer("joe", "1 Maine Str");
    }
}

```

When to use:
* when you don't want to create a dependency on Spring in your domain classes. All the Spring-specific config goes into the Spring Configration classes.
* when you want more control over bean startup logic.


### Component Scanning

Spring Boot can scan for beans to wire if the class is annotated by the `@Component` annotation.
Contrast wtih configuration class method above, where methods return objects that will become beans.


Here you annotate a class with `@Component` and that class will become the bean.

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

`@Component is a base annotation, there are others flavours that do slightly more specific things:`

* `@Component` - the basic type
* `@Repository` - "Encapsulation of storage, retrieval and search which emulates a collection of objects"
* `@Service` - "Interface to operation that stands alone in the model, with no encapsulated state."


### @Bean vs @Component

* Use @Bean when you need to create a bean from a 3rd party class where you don't have control over the source code.
* Use @Bean for classes the just provide configuration (perhaps some static strings).
* @Component is generally preferred otherwise.


### Auto Instantiation of Spring Platform Beans

It's important to understand that the Spring framework provides the utility of the platform via a bunch of beans that gets instantiated from the platform code.

The exact beans that get instantiated depends on the starter package.

Every bean that Spring provides as a platform, is created conditionally.

Boot relies on the @Conditional annotation and on a number of variations to drive auto-configuration:

* @ConditionalOnClass,
* @ConditionalOnMissingClass,
* @ConditionalOnBean,
* @ConditionalOnMissingBean

Defining another bean of the same interface type will replace the platform bean with our own version. This "back-off" mechanism is a feature of Spring Boot.

To inspect the list of beans that Spring auto configure, add logging to your `application.properties` file:

```
logging.level.org.springframework.boot.autoconfigure=DEBUG
```


## The Spring IoC container

The subsystem in Spring responsible for instantiating beans and injecting them as dependencies.

Two high level types:

* BeanFactory (the root interface)
* ApplicationContext (extends BeanFactory)
  * Contains the AOP stuff and other features. Preferred.
  * Common implementation to use is `AnnotationConfigApplicationContext`


Normally you would just use the `@SpringBootApplication` annotation to do this, but behind the scenes the
IoC can be bootstrapped like this:

Say you have this class that you want to create beans of:


```
public class Person {

   private String name;

   public Person(String name) {
       this.name = name;
   }
}
```

Method 1: Using a Configuration Class


Note that @Configuration contains @Component inside.

```
@Configuration
public class Config {

    @Bean
    public Person personMary() {
        return new Person("Mary");
    }
}
```


Create a new spring application from the config class:

```
public class Application {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(Config.class);
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));

        Person p = context.getBean(Person.class);

        //OR
        context.getBean("personMary"); // returns an Object object
        context.getBean("personMary", Person.class) // returns a Person object
    }
}

```

Method 2: @ComponentScan


Just add `@Component` to all classes you want to beanify, and add `@ComponentScan` to the Config class:

```

@Component
public class Book {
}


//In Configuration class
@ComponentScan
@Configuration
public class ConfigWithComponentScan {
}


//In main Application class:
var context = new AnnotationConfigApplicationContext(ConfigWithComponentScan.class);

```

Method 3: @SpringBootApplication


This is a convenience annotation that invokes @ComponentScan


## Bean Scopes

By default, beans are created as Singletons.


```
@Bean
@Scope("singleton") //OR
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public AtomicInteger createCounter() { /* ... */ }
```

Prototype returns a new instance every time it's injected:

```
@Bean
@Scope("prototype") //OR
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
```



**Request**

 allows a bean to be created and available for the whole lifecycle of an HTTP request.

**Session**


 allows a bean to be created and available for the whole HTTP session that may include a sequence of HTTP requests connected by cookies/session ID into a single session.


**Application**

Bean is shared between multiple ApplicationContexts


**Websocket**

 available during the complete lifecycle of a WebSocket session.


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



### Where to put @Autowire

Several options:


Top of constructor:

```
@Component
public class Runner implements CommandLineRunner {
    private final PasswordGenerator generator;

    @Autowired
    public Runner(PasswordGenerator generator) {
        this.generator = generator;
    }

    // run
}
```

Before constructor arg:

```
@Component
public class Runner implements CommandLineRunner {
    private final PasswordGenerator generator;

    public Runner(@Autowired PasswordGenerator generator) {
        this.generator = generator;
    }

    // run
}
```

Directly on the field:

```
@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private PasswordGenerator generator;

    // run
}
```

Omit it completely!  
It is possible because Spring IoC knows all the components and can inject them by the type when it is needed


```
@Component
public class Runner implements CommandLineRunner {
    private final PasswordGenerator generator;

    public Runner(PasswordGenerator generator) {
        this.generator = generator;
    }

    // run
}
```

## Application Configuration

### Using properties
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

### Using profiles to activate beans selectively

Beans can be restricted to a specific profile.
Add the `@Profile` annotion to any class you want to configure this way, e.g.

```
@Repository
@Profile("dev")
public class ProjectRepositoryImpl implements IProjectRepository {
```

This class will only be instantiated when the "dev" profile is active.

*Inspecting the profile*

Typically the profile gets logged to the console right at the start:

```
2019-11-13 07:57:09.516  INFO 84180 --- [           main] com.baeldung.ls.LsApp                    : No active profile set, falling back to default profiles: default
```



*Activating profiles*:

In the `application.properties` file:

```
spring.profiles.active=dev
```

Multiple profiles can be activated by providing a comma-separated list.
#


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
</resources>
  
```


By default the loglevel is set to `INFO`. To configure customer level levels, add config in the `application.properties`.

Default for all packages:

```
logging.level.root = DEBUG
```

*Pattern of packages*

We can configure different logging levels for packages, using the property: logging.level.packagename.

E.g. to keep the spring framework logging level at INFO:

```
logging.level.org.springframework=INFO
```

However there many be many other packges that emit logging too so most likely you want to keep the default at INFO and only mark your own packages as DEBUG level.




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

### Logging to a file and other config options

In the `application.properties`:

```
logging.file=app.log

logging.pattern.dateformat=yyyy-MM-dd
```

Pro tip: use the IDE autocomplete feature to explore other logging options.

## Running Spring Boot apps

From the IDE:

- On intellij and eclipse, right click on the main application class, then run that class. A run configuration will be automatically created that can subsequently be used.

- from cmdline:

```
java -jar target/myapplication-0.0.1-SNAPSHOT.jar
```

or

```
export MAVEN_OPTS=-Xmx1024m
mvn spring-boot:run
```


## Spring Testing


Spring support for testing is focused on *integration testing* (as opposed to unit testing).


### Dependency

Add these depedencies to the pojrect:
*  `spring-boot-starter-test`
*  `org.junit.jupiter` (Junit 5)

They also need to have the `test` scope:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.5.2</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.5.2</version>
    <scope>test</scope>
</dependency>

```

The following build plugins also need to be configured to enable test discovery:

```
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>


```
### Folder structure

Need to create folders under `src/test/java`:

Create a test with the same package name as the application you're testing.

In IDE you may need to set this directory as a Test Sources Root.


### Config option 1: Spring Test Config class

For integration tests you want to test the beans you've created. You will need to autowire them into your test class. However, for autowiring to work, you first need to create a Configuration class within your tests:

*TestConfig.java*:

```
package com.example.ls.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("com.example.ls")
public class TestConfig {
}
```

You need to reference this class in the test classes using this annotation:

```
@SpringJUnitConfig(classes = TestConfig.class)
```
### Config option 2: Using @SpringBootTest

Simply annotate your test classes with

```
@SpringBootTest
```
This method actually unlocks some Spring Boot magic that allows you to customise how the tests are run.
Specifically, the `webEnvironment` arg controls whether Tomcat is spun up for the tests:
* `@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.NONE)` -  if the tests do not require any web support
* `@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)` - spin up tomcat on random port
* `@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.DEFINED_PORT)` - spin up tomcat on port defined in application.properties (server.port)
* `@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.MOCK)` - spin up tomcat on port defined in application.properties (server.port)

### Config option 2: Using @WebMvcTest

Useful if you don't want to spin up the whole application context, only the web layer.
Works exactly like @SpringBootTest.

Does not instantiate Service beans.

Here is an example test:

```
@WebMvcTest
public class ProjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenProjectExists_thenGetSuccess() throws Exception {

        mockMvc.perform(get("/projects/1"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.name").value("testName"));
    }
}
```








### Test Classes

No convention on naming but good idea to name class something ending with IntegrationTest.




```
package com.example.ls.service;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import com.example.ls.spring.TestConfig;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SpringJUnitConfig(classes = TestConfig.class)
public class ContextIntegrationTest {

    

    // define instances of the beans you want to test
    // you need to instantiate via the interface of the bean.
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private IProjectService projectService;

}
```


### Testing methods and assertions

```

    // inside test class
    @Test
    public void whenContextIsLoaded_ThenNoExceptions() {

    }

    @Test
    public void whenSavingProject_ThenOK() {
        Project savedProject = projectService.save(new Project("project 1", 1L, LocalDate.now()));
        assertThat(savedProject, is(notNullValue()));
    }

```

Note the use of `AssertThat`. This is a generic method in the newer versions of Junit that replaces tbe variants of `AssertXXX` methods.

### Loading test data into a bean before tests are run

Create a seperate bean for loading the data.
Extend the `ApplicationContextAware` interface to hook into the startup process
and set your data on the bean at that point.


```

@Component
public class TestDataLoader implements ApplicationContextAware {
    @Autowired
    private IProjectRepository projectRepository;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        projectRepository.save(new Project(randomAlphabetic(6), LocalDate.now()));
        projectRepository.save(new Project(randomAlphabetic(6), LocalDate.now()));
        projectRepository.save(new Project(randomAlphabetic(6), LocalDate.now()));
        projectRepository.save(new Project(randomAlphabetic(6), LocalDate.now()));
        projectRepository.save(new Project(randomAlphabetic(6), LocalDate.now()));
        
    }
}
```

## Actuators


Drop-in production features such as health checks and monitoring.



### Dependencies

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### Using it

Just pulling in the depedency via the pom activates it.

The starter provides these endpoints:

* http://127.0.0.1:8080/actuator/health
* http://127.0.0.1:8080/actuator/info

To custommise it the data presented:


Changing the `actuators` base path:


```
management.endpoints.web.base-path=/monitoring
```

Display all actuators:

```
management.endpoints.web.exposure.include=*
```


### Extending the HealthInfo actuator

First configre the health actuator to show extended info:

```
management.endpoint.health.show-details=ALWAYS
```

Now create a new bean that implements the `HealthIndicator` interface:


```
package com.baeldung.ls.actuate.health;

@Component
public class DBHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        if (isDbUp()) {
            return Health.up()
                .build();
        } else {
            return Health.down()
                .withDetail("Error Code", 503)
                .build();
        }
    }

    private boolean isDbUp() {
        return false;
    }

}
```


Spring will create a new node called "DB" in the /status output. It gets "DB" from the classname.


## Persistence and JPA

* Heavy use of interfaces for basic operations
* Automatic generation of implementations at runtime. !!!
    * E.g using naming convention of the method it can generate methods



### Dependencies

```
       <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>

```

### Using the data access auto generation capability of Spring Data


Spring Data provides several generic interfaces to encapsulate typical storage scenarios. 


To make use of them, extend the interface and parameterise with your model classes. E.g.

```
import org.springframework.data.repository.CrudRepository;

import com.myexample.ls.persistence.model.Project;

public interface IProjectRepository extends CrudRepository<Project, Long> {

}
```
Note there are no methods defined in the interface because the base interface already defines a bunch of data access methods.

Now annotate your model class with JPA annotations (importing them from `javax.persistence`).

Also make sure it has a *default constructor*.

```
@Entity
public class Project {

    @Id
    private Long id;

    private String name;

    private LocalDate dateCreated;

    //...
    public Project() {
    }

    // also make sure equals method is implemented
}
```

In this case, the interface already provides a method called `findById()` so there's no need to declare it in the child interface. The implementation is actually auto generated.

You can harness the auto generation in new methods, using a simple convention in the method name:

`findBy<Attribute><Operator>`. 

E.g. if you declare the following method in your interface:

```
List<Project> findByDateCreatedBetween(LocalDate start, LocalDate end);
```

Spring Data will automatically generate the appropriate implemention for you.

You can also specify your own *custom SQL* in the interface:

```
@Query("select t from Task t where t.name like %?1%")
List<Task> findByNameMatches(String name);
}
```

### Relationships

More notes tbd but example for now:

```
@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private LocalDate dateCreated;

    private LocalDate dueDate;

    private TaskStatus status;
    
    // ...
}

...

@Entity
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private LocalDate dateCreated;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Set<Task> tasks;
    
    // ...
}
```
### Pagination and Sorting

Simply extend the `PagingAndSortingRepository` which itself is extended from the `CrudRepository` interface:

```
public interface IProjectRepository extends PagingAndSortingRepository<Project, Long> {
```



Example how to use it:

Paging:

```
Page<Project> page = projectRepository.findAll(PageRequest.of(0, 1));

assertThat(page.getContent(), hasSize(2));
```

Sorting:

```
Iterable<Project> retrievedProjects = projectRepository.findAll(PageRequest.of(0, 2, Sort.by(Order.asc("name"))));

List<Project> projectList = new ArrayList<>();
retrievedProjects.forEach(projectList::add);

```


## Using Spring diretly with Hibernate

You don't need to use the Spring Data boot starter.

Pom dependencies:

```
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-orm</artifactId>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>
```

### Configuratiuon

Out of the box, hibernate will create a default h2 test db, you can see it in the startup logs like this:

```
2019-11-25 06:06:22.603  INFO 1963 --- [           main] o.s.j.d.e.EmbeddedDatabaseFactory        : Starting embedded database: url='jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false', username='sa'
```

To specify your own config, create a Spring Configuration factory class that returns a `DataSource` bean:

```
package com.example.ls.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfig
{
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .setName("my-spring-db")
                .build();
    }
}


```

To have a bit more control over how the db is defined, use the Spring `DriverManagerDataSource` class:

```
   @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        // these should be obtained via application properties
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:my-spring-db;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("username");
        dataSource.setPassword("password");
        return dataSource;
    }
```

### The Entity Manager

The `EntityManager` is a core JPA API for persisting entities via JPA.

Spring has deep integration into JPA. But injecting a bean from JPA requires a bit different logic to the usual Autowire mechanism. You have to use the annotations from `javax.persistence`:

```
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
...

    @PersistenceContext
    private EntityManager entityManager;
```

Once you have the entity manager bean injected in your class, you can use it like thus
in your repository implementation:

```
    @Override
    @Transactional
    public Optional<Project> findById(Long id) {
        Project entity = entityManager.find(Project.class, id);
        return Optional.ofNullable(entity);
    }

    @Override
    @Transactional
    public Project save(Project project) {

        entityManager.persist(project);
        return(project);

    }
```
## Web 

### Dependencies

```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
```


### Basic usage

Create a controller class and annotate with `@RestController`:
Typically you create one for each entity (TBD).


```
package com.exmaple.ls.web.controller;

import com.baeldung.ls.persistence.model.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController

// this annotation configures the base path
@RequestMapping(path = "/projects")
public class ProjectController {

    // binds to a HTTP GET method at the specified path
    @GetMapping("/1")
    public Project findOne() {
        // this will return a JSON representation of Project
        return(new Project("test project 1", LocalDate.now()));
    }

}

```

Remember to autowire or otherwise inject via constructor the service.


### Reading input via HTTP


In the controller class;
Pass in a `value = "/{varname}"` argument to the `@GetMapping` anotation,
then use the `@PathVariable` annotation on the arg to indicate the method
argument that should receive the value:

```


    @GetMapping(value = "/{id}")
    public Project findOne(@PathVariable Long id) {
        return projectService.findById(id).get();
    }

}

```

### Handling NOT FOUND errors:

By default, a 500 error will be generated if a non-existent entity is requested. To fix,
use the `Optional.orElseThrow()` method:

```
    @GetMapping(value = "/{id}")
    public Project findOne(@PathVariable Long id) {
        return projectService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
```


### Performing POSTS

Lets say you want to have a POST handler at the root of the controller path (`/projects`):

```
@PostMapping
public void create(@RequestBody Project project) {
        projectService.save(project);
    }
```

The `@RequestBody` annotation will place the object in the request body into the method arg.

## MVC vs REST

* MVC - Model View Controller 
  * has concept of a model and view
  * returns representation and presentation
* REST - Representational State Transfer
  * Only returns representation, presentation entirely up to client
  * Becoming more popular than MVC due to advent of SPA and JS.


  MVC example:

```
  @GetMapping("/viewProjectPage")
public ModelAndView projectPage() {
    ModelAndView modelAndView = new ModelAndView("projectPage");
    modelAndView.addObject("message", "Baeldung");
    return modelAndView;
}
```

REST example:

```
@GetMapping("/project")
public Project project() {
    Project project = projectService.find...;
    return project;
}
```

### Decoupling Controllers from Entities

It's generally not a good idea to expose Entities in your data model directly to the client because:

* *Entities* are not *Resources* (aka Data Transfer Objects)
* can lead to sensitive data being exposed unintentionally

As such, it's good practice to maintain a 2nd model (the Data Transfer Objects) and convert between this and the model when retrieving or saving data.

The DTO class is typically copied and pasted from the Entitiy class, but without any Persistence annotations.

Conversion can look like this:

```
protected ProjectDto convertToDto(Project entity) {
    ProjectDto dto = new ProjectDto(entity.getId(), entity.getName(), entity.getDateCreated());
    dto.setTasks(entity.getTasks().stream().map(t -> convertTaskToDto(t)).collect(Collectors.toSet()));

    return dto;
}

protected Project convertToEntity(ProjectDto dto) {
    Project project = new Project(dto.getName(), dto.getDateCreated());
    if (!StringUtils.isEmpty(dto.getId())) {
        project.setId(dto.getId());
    }
    return project;
}
```
The Controller methods can then be changed to look something like this:

```
@GetMapping(value = "/{id}")
public ProjectDto findOne(@PathVariable Long id) {
    Project entity = projectService.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    return convertToDto(entity);
}

@PostMapping
public void create(@RequestBody ProjectDto newProject) {
    Project entity = convertToEntity(newProject);
    this.projectService.save(entity);
}

```

### Testing MVC

#### Dependencies

```
       <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <scope>test</scope>
        </dependency>
```



## H2 Database



Dependencies

```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-jdbc'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'com.h2database:h2'
    testImplementation('org.springframework.boot:spring-boot-starter-test') {
        exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    }
}
```
Configuration is done via the application.properties file:


```
# set driver class
spring.datasource.driverClassName=org.h2.Driver

# set location of db (file, in-mem):
spring.datasource.url=jdbc:h2:file:~/sampleDB

# user details
spring.datasource.username=sa
spring.datasource.password=abc123

# db type
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# show statements in log
spring.h2.console.enabled=true  # view at localhost:8080/h2-console, there's another setting that configures the path.
spring.h2.console.settings.trace=true

# auto create tables via hibernate
spring.jpa.hibernate.ddl-auto=update


# show sql statements issued by application
spring.jpa.show-sql=true


## Handling Exceptions


NOTE:

By default, Spring Boot doesn't include the message field in a response. To enable it, add this line in the application.properties file: `server.error.include-message=always`

### Unhandled exceptions

If you just throw a RuntimeException() it will result in 500 INTERNAL ERROR.

### Method1: ResponseStatusException


In the controller, throw an exception of type ResponseStatusException:


```
throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid item selected");
```


Comes in 3 flavours:


ResponseStatusException(HttpStatus status)
ResponseStatusException(HttpStatus status, String reason)
ResponseStatusException(HttpStatus status, String reason, Throwable cause)
)

### Method2: Create custom exceptions

Extend from `RuntimeException` and annotate with `@ResponseStatus`:


E.g.

```
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String cause) {
        super(cause);
    }
}
```



### Customising error returned to user

**WARNING** some of this was outdated by the time I wrote this 2023-02

Create custom class like this:


```
public class CustomErrorMessage {
    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;

    public CustomErrorMessage(
            int statusCode,
            LocalDateTime timestamp,
            String message,
            String description) {

        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }

    //getters....
}
```

Method 1: ExceptionHandler in the Controller closs

Note that you can only handle exceptions from this specific controller class.


In the Controller class, add a method annotated with `@ExceptionHandler`:

```
    @ExceptionHandler(FlightNotFoundException.class) //custom exception
    public ResponseEntity<CustomErrorMessage> handleFlightNotFound(
            FlightNotFoundException exception, WebRequest request) {
        CustomErrorMessage msg = new CustomErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }
```


To reuse the code for exceptions from other controller classes, implement a dedicated exception controller:


```
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleFlightNotFound(
            FlightNotFoundException e, WebRequest request) {

        CustomErrorMessage body = new CustomErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                e.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
```


A more advanced version of this that allows us to map even standard java exceptions to HTTP response codes is to
inherit from abstract class `ResponseEntityExceptionHandler`:

