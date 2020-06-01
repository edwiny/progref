# Advanced Spring Boot topics

## AOP - Aspect Oriented Programming

One of the ways in which Spring implements "infrastructure" type code
and allow the programmer to focus on application.

Benefits:

* Implement cross cutting concerns
* Can change behaviour of application without modification of code.
* Cleaner business logic - critical features like transactions, tracing / auditing / exception handling and security can be kept seperate from business logic.
* Helps to maintain the principle of *Open/Closed* compoennts  - software entities (classes, modules, functions, etc.) should be open for extension, but closed for modification

### Components of AOP

* Aspect - The cross-cutting logic
* Join Point - the point during execution of a program where the aspect is injected
* Point Cut - a way to match one or more Join Points to see where the cross-cutting logic will be run
* Advice - where our logic runs in relation to the join point (before, after, or around)

### Building own aspects

AOP support in Spring is already included as part of the web starter.

Create a Aspect class, annnotate it as Component and Aspect, and provide a method to implement the logic. Annote the method with the Advice (before, after, or around) and give it a expression to match the method calls you want to inject into:


```
@Aspect
@Component
public class ProjectServiceAspect {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectServiceAspect.class);

    // the Point Cut expression. findbyId is the Join Point
    @Before("execution(* com.baeldung.ls.service.impl.ProjectServiceImpl.findById(Long))")
    public void before(JoinPoint joinPoint) {
        LOG.info("Searching Project with Id {}", joinPoint.getArgs()[0]);
    }
}

```