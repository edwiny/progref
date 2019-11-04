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


