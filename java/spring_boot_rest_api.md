# Build REST apis with Spring Boot

## Difference between @Controller and @RestController

@Controller is more generic. If you wanted to return raw JSON data with @Controller you would need to marshall objects into the ResponseBody

@RestController automatically marshalls objects into ResponseBody.

## @RequestMapping and variants @GetMapping, @PostMapping

This is a generic form of `@GetMapping` et al. 

Best practice is to define a base `@RequestMapping` for each controller class to set the base path. Methods in the class would then refine only the last part of the url that changes for that method. E.g. See how the method is configured with a relative path:



```

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

    private IProjectService projectService;

    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    //

    @GetMapping(value = "/{id}")
    public ProjectDto findOne(@PathVariable Long id) {
        Project entity = projectService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToDto(entity);
    }
    ...
}
```

You can pass in arguments to the `@RequestMapping` annotation (and it's variants):

This one would call the method only if the accept header indicates json:

```
@PostMapping(headers = "accept=application/json")
```

Altenatively:

```
@PostMapping(consumes = "application/json")
```

You can aslo map the method to specific url paramaters:

```
@PostMapping(params = "paramKey=paramValue")
```


## Mapping request and response bodies to java classes

Aka serialistion / deserialisation 
Aka marshalling / demarshalling

 Spring MVC reads and writes the body of the HTTP request using HTTP Message Converters. These modules are enabled and configured automatically based on their availability on the class path.

 For exampe, *spring-boot-starter-web* brings in *Jackson 2* thereby enabling the corresponding HTTP message converter, which is *MappingJackson2HttpMessageConverter*.

 ###  @RequestBody 

 This annotation, provided in the method argument list, indicates that the text in the request body be serialised into the class being annotated:


```
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
// convert body to a ProjectDto object
public ProjectDto create(@RequestBody ProjectDto newProject) {
  Project entity = convertToEntity(newProject);
  return this.convertToDto(this.projectService.save(entity));
}
```

The JSON fields have to match the class attributes but this can be customised with annotations.





### @ResponseBody

This annotation, at the start of a method, tells Spring to return an object of the method's return type to the client.

*But*... don't need to annotate the @RestController-annotated controllers with the @ResponseBody annotation since it's done by default here.


### @RequestParam

Maps the query arguments to method args, e.g.:

```
// http://localhost:8080/projects?name=Project2


public Collection<ProjectDto> findProjects(@RequestParam("name") String name) {
  // ...
}
```

By default, the param will be required on all requests. To make it optional, there are 
two ways:

```
public Collection<ProjectDto> findProjects(@RequestParam(name = "name", required = false) String name) {
  // ...
}
```

OR use Optionals:

```
public Collection<ProjectDto> findProjects(@RequestParam(name = “name”) Optional<String> name) {
  // ...
}

```

OR supply a default value:

```
public Collection<ProjectDto> findProjects(@RequestParam(name = "name", defaultValue = "") String name) {
  // ...
}
```

### @PathVariable

Extract components of the path as arguments for the Controller method.

Can use it in different ways:

```
@GetMapping(value = "/{id}")
public ProjectDto findOne(@PathVariable Long id) {

    Project entity = projectService.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return convertToDto(entity);
}
```

Can also be more explicit with:

```
@PathVariable("id") Long id
```

Using regular expressions to map path components:


```

// http://localhost:8080/projects/categoryA-12/1

@GetMapping(value = "/{category}-{subcategoryId:\\d\\d}/{id}")
public ProjectDto findOne(@PathVariable Long id,
  @PathVariable String category,
  @PathVariable Integer subcategoryId) {
    // ...
}
```

If same path variables are optional, specify with:

```
@PathVariable(required = false) Long id
```

## Exception Handling


### Manul method - not really  recommended

```
    @GetMapping(value = "/{id}")
    public ProjectDto findOne(@PathVariable Long id) {
        Project entity = projectService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToDto(entity);
    }

```

To display custom message:

```
new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found")
```

Scatters exception handling over the codebase.
Spring already does a good job of exception handling.

### Global Exception Handler  - recommneded

Use the `@ControllerAdvice` annotation to create a global exception handler:

```


@ControllerAdvice
public class GlobalExceptionHandler {

}


```