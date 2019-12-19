# Spring Boot Views, View Resolvers, and Templating Engines

The *View* presents the *Model* in a specific format.

Can have multiple Views.

Spring View Resolvers resolves a View by name.

Template engines typically provides 
* a View Resolver for each specific templating technology if supports
    * ViewResolver provides a mapping between view names and actual views.
* a Template Resolver which  :shrug:




## Thymeleaf

* Modern, Server-side templating engine
* Has good support in Spring MVC

### Dependencies

Use the Spring boot-starter for Thymeleaf:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

```

### Handling GETs

Create a controller class and annotate with `@Controller`:

```
@Controller
@RequestMapping(value = "/projects")
public class ProjectController {
...
}
```

Create a GetMapping method that accepts a Model arguement and returns a String template name:

```
    @GetMapping
    public String getProjects(Model model) {
        Iterable<Project> projects =  projectService.findAll();
        List<ProjectDto> projectDtos = new ArrayList<>();
        projects.forEach(p -> projectDtos.add(convertToDto(p)));

        model.addAttribute("project", projectDtos);
        return "projects";
    }
```

Create folder `src/main/resources`.

Create the "projects" template in that folder:

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
<title>Projects</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Date Created</th>
			</tr>
		</thead>
		<tbody>
			<tr th:each="project : ${projects}">
				<td th:text="${project.id}"></td>
				<td th:text="${project.name}"></td>
				<td th:text="${project.dateCreated}"></td>
			</tr>
		</tbody>
	</table>

    <p>
	<a th:href="@{/projects/new}">New Project</a>


</body>
</html>
```

### Handling POSTs

* Create a template with a form, and bind it to a GET request.
* In the HTML form, reference the name of a model attribute containing the DTO of the model being passed to the 
view, using the `th:object` HTML attribute in the form definition (See below)

E.g. template `new-project.html`:

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <title>Add Project</title>
</head>
<body>

<form method="POST" th:action="@{/projects}" th:object="${project} ">

    <label for="name"> Project name: </label>
    <input type="text" id="name" th:field="*{name}"/>
    <input type="submit" value="Save project">


</form>

</body>
</html>
```

`#th:object` indicates the model attribute to which the submitted data will be bound.

In the Controller, create a method to return this template for GET requests:

```
   @GetMapping("/new")
    public String newProject(Model model) {
        // "project" is the attribute that got referenced in the HTML form
        model.addAttribute("project", new ProjectDto());

        return "new-project";
    }

```


Now create the POST handler in the same Controller class:

```
    @PostMapping
    public String addProject(ProjectDto project) {
        projectService.save(convertToEntity(project));
        return "redirect:/projects";
    }
```

Notice the "redirect" syntax.

### Referencing text in the template from a properties file

Create a file: `main/resources/messages.properties`:

Add properties like this:

```
new.project.title=Add a new Project
```

To referece in the template, try:

Note the 'blah' will be replaced by the parameter.
```
<h2 th:text="#{new.project.title}">blah</h2>

```
### Input validation

From the `javax.validation.constraints` package, add the 
`@NotBlank` or other annotation to the DTO fields, e.g.:

```
public class ProjectDto {

    private Long id;

    @NotBlank
    private String name;
    ...
}
```

Now, in the Controller method where the POST is mapped, add the `@Valid` annotation to the model argument:

```
   @PostMapping
    public String addProject(@Valid ProjectDto project) {
        projectService.save(convertToEntity(project));
        return "redirect:/projects";
    }
```

This will yield a basic error to the user if validation fails:


```
There was an unexpected error (type=Bad Request, status=400).
Validation failed for object='projectDto'. Error count: 1
```


### User Friendly Validation


Add a `BindingResult` argument to the post handling in the controller, and return
back to the same template. Note that you also probably need to make sure the model attributes
are provisioned into the template. You can do this with the `@ModelAttribute("attr-name")` annotation in the method declaration:

```
    @PostMapping
    public String addProject(@Valid @ModelAttribute("project") ProjectDto project, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "new-project";
        }
        projectService.save(convertToEntity(project));
        return "redirect:/projects";
    }
```

This will return the user back to the form they came from, but there won't be any visual feedback about the validation failure. To do that, use the thymeleaf directives like this in the template:


```
    <p th:if="${#fields.hasErrors('name')}" th:errors="*{name}" />
```

This funky magic will display a 'must not be blank' message below the input field.



## FreeMarker

### Dependency

```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
            <version>2.1.8.RELEASE</version>
        </dependency>
```

### Displaying data

The Controller GetMapping method is exactly the same as in the Thymeleaf example above:

```
   @GetMapping
    public String getProjects(Model model) {
        Iterable<Project> projects =  projectService.findAll();
        List<ProjectDto> projectDtos = new ArrayList<>();
        projects.forEach(p -> projectDtos.add(convertToDto(p)));

        model.addAttribute("projects", projectDtos);
        return "projects";
    }
```

The template `projects.ftl` needs to be create in `main/resources/templates` and can look like this:

```
<#list projects as project>

    <h1>${project.name}</h1>

    Tasks:
    <ul>
        <#list project.tasks as task>
            <li>${task.name} : ${task.description}</li>
        </#list>
    </ul>
</#list>

```

### Changing the template filename suffix 

By default, FreeMarker templates have a ".ftl" extension. To change it to ".html" (to get IDE support), set the application.properties property:

```spring.freemarker.suffix=.html```
