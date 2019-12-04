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

### Controller

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

<html xmlns:th="http://www.Thymeleaf.org">

<thead>
<tr>
    <th>Id</th>
    <th>Name</th>
    <th>Date Created</th>
</tr>
</thead>
<tbody>
<tr>
<tr th:each="project : ${projects}">

    <td th:text="${project.id}"></td>
    <td th:text="${project.name}"></td>
    <td th:text="${project.dateCreated}"></td>
</tr>
</tbody>

</html>
```



