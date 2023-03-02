# Build REST apis with Spring Boot


## Difference between @Controller and @RestController

@Controller is more generic. If you wanted to return raw JSON data with @Controller you would need to marshall objects into the ResponseBody

@RestController automatically marshalls objects into ResponseBody.


Note that Controllers are not multi-thread safe.


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
        // can also return a custome response:
        return new ResponseEntity<>(convertToDto(entity), HttpStatus.ACCEPTED);G
    }


    @PostMapping("/addresses")
    public void postAddress(@RequestParam String name, @RequestParam String address) {
        addressBook.put(name, address);
    }

    @DeleteMapping("/addresses")
    public String removeAddress(@RequestParam String name) {
        addressBook.remove(name);
        return name + " removed from address book!";
    }
    
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

### @RequestBody 

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


To accept other formats in the body:

```
@PostMapping(value = "/user", consumes = MediaType.APPLICATION_XML_VALUE)
public String userStatus(@RequestBody UserInfo user) {
    return String.format("Added User %s", user);
}
```


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


### @RequestParam

Map query params to controller method args.

Example usage:

```
public Collection<ProjectDto> findProjects(@RequestParam("name") String name) {
  // ...
}
```

Additional attributes that can be passed to the annotation:
* required (boolean) - will generate a exception if no value specified  -default is true
* defaultValue (String) - good practice to set it to "" so we don't need to null check the value




## Exception Handling


### Manul method - not really recommended

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

1. Use the `@ControllerAdvice` annotation to create a global exception handler class:

```
@ControllerAdvice
public class GlobalExceptionHandler {

}

```

2. Define methods on this class to handle specific exception classes. It has to be annotated with the class of the exception:

```
@ExceptionHandler(EmptyResultDataAccessException.class)
public ResponseEntity<String> handleDataRetrievalException(EmptyResultDataAccessException ex) {
    return new ResponseEntity<String>("Exception retrieving data: " + ex.getMessage(), HttpStatus.NOT_FOUND);
}
```

**Note**: you can hamdle "families" of exceptions this way by specifying a parent exception class in the annotation and method parameters.

3. You can also extend the Spring class `ResponseEntityExceptionHandler` which already maps most of the internal Spring exception classes and offers protected methods to customise behaviour.


## Testing a REST api via Spring RestTemplate


RestTemplate is a REST client provided with Spring Web. It makes it trivial to make a HTTP call and marshall the response into the expect class.
Make sure the client has access to the REST DTO.


```

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

// your DTO

import com.baeldung.ls.web.dto.ProjectDto;


@SpringBootTest
public class ProjectRestApiLiveTest {
    private static String BASE_URL = "http://localhost:8080/projects/";
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void givenProjectExists_whenGet_thenSuccess() {
        ResponseEntity<ProjectDto> response = restTemplate.getForEntity(BASE_URL + "1", ProjectDto.class);
        assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        assertNotNull(response.getBody());
    }


}
```


To POST:


```
@Test
public void givenNewProject_whenCreated_thenSuccess() {
    ProjectDto newProject = new ProjectDto(1L, "First Project", LocalDate.now());
    ResponseEntity<Void> response = restTemplate.postForEntity(BASE_URL, newProject, Void.class);
    
    // assertions...
}

```



## The REST Resource Naming Guide

Good to review if building a new REST service:

https://restfulapi.net/resource-naming/



# Using model mapper with DTOs


```
dependencies {
    implementation 'org.modelmapper:modelmapper:3.1.0'
}
```

Inject into service:

```
public UserService(UserRepository peopleRepository, ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
}
```


Write the conversion functions.


```
UserDTO convertUserToDTO(User user) {
    UserDTO dto = modelMapper.map(user, UserDTO.class);
    return dto;
}

User convertDTOToUser(UserDTO dto) {
    User user = modelMapper.map(dto, User.class);
    user.setAccountCreatedAt(LocalDate.now());
    return user;
}
```


With Lombok, some complications can arise. Here is a config that should work

```
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private int id;
    private String username;
    private LocalDate dateOfBirth;
}
```


Can also use **records** (JDK 16)

```
public record UserDTO(int id, String username, LocalDate dateOfBirth) {
}
```
