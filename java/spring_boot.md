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

## Spring Bean Configuration

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


### Auto Instantiona of Spring Platform Beans

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


### Spring Test Config class

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

...
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


## Persistenace and JPA

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

### Using the data access auto generation capability of Spring Dats


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





