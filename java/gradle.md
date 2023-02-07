# gradle notes

Gradle build script describes one or more Projects. Each project is made up of different tasks. A task is a piece of work which a build performs.


## Quickshart / bootstrap new project


```
gradle init
```


## Projects
Grade scripts represent Projects as Project Objects. See [Project](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html)

A project typically is something to be built or deployed.

A project is essentially a collection of Task objects. Each task performs some basic piece of work, such as compiling classes, or running unit tests, or zipping up a WAR file. You add tasks to a project using one of the create() methods.

Each task is available as a property of the project, using the task name as the property name.

A project generally has a number of dependencies it needs in order to do its work. Also, a project generally produces a number of artifacts, which other projects can use. Those dependencies are grouped in configurations, and can be retrieved and uploaded from repositories. 

 You use the 
   -  `ConfigurationContainer` returned by `Project.getConfigurations()` method to manage the configurations
   -  `DependencyHandler` returned by `Project.getDependencies()` method to manage the dependencies. 
   -  `ArtifactHandler` returned by `Project.getArtifacts()` method to manage the artifacts. 
   -  `RepositoryHandler` returned by `Project.getRepositories()` method to manage the repositories.

Projects are arranged into a hierarchy of projects. A project has a name, and a fully qualified path which uniquely identifies it in the hierarchy.

You can also access the Project instance using the `project` property. This can make the script clearer in some cases. For example, you could use `project.name` rather than name to access the project's name.

All extra properties must be defined through the "ext" namespace. Once an extra property has been defined, it is available directly on the owning object (in the below case the Project, Task, and sub-projects respectively) and can be read and updated. Only the initial declaration that needs to be done via the namespace.

## Build Phases

A Gradle build has three distinct phases.

### Initialization
Gradle supports single and multi-project builds. During the initialization phase, Gradle determines which projects are going to take part in the build, and creates a Project instance for each of these projects.

### Configuration
During this phase the project objects are configured. The build scripts of all projects which are part of the build are executed.

### Execution
Gradle determines the subset of the tasks, created and configured during the configuration phase, to be executed. The subset is determined by the task name arguments passed to the gradle command and the current directory. Gradle then executes each of the selected tasks.


## Settings file
Beside the build script files, Gradle defines a settings file. The settings file is determined by Gradle via a naming convention. The default name for this file is settings.gradle. Later in this chapter we explain how Gradle looks for a settings file.

**The settings file is executed during the initialization phase**. A multi-project build must have a settings.gradle file in the root project of the multi-project hierarchy. It is required because the settings file defines which projects are taking part in the multi-project build (see Authoring Multi-Project Builds). For a single-project build, a settings file is optional. Besides defining the included projects, you might need it to add libraries to your build script classpath (

## Tasks


Gradle has different phases, when working with tasks. First of all, there is a configuration phase, where the code, which is specified directly in a task's closure, is executed. The configuration block is executed for every available task and not only for those tasks which are later actually executed.

After the configuration phase, the execution phase runs the code inside the doFirst or doLast closures of those tasks, which are actually executed.


### Creating tasks

 Task is a keyword which is used to define a task into build script

 E.g.:

```
 task hello {
   doLast {
      println 'tutorialspoint'
   }
}
```

can also be:

```
task('hello') << {
   println "tutorialspoint"
}
```
this defines a task called hello with a single closure to execute. The << operator is simply an alias for doLast.




or

```
tasks.create(name: 'hello') << {
   println "tutorialspoint"
}
```

### Task Dependencies

Execute on task before another

```
task taskX << {
   println 'taskX'
}
task taskY(dependsOn: 'taskX') << {
   println "taskY"
}
```

another way to define it is:

```
task taskX << {
   println 'taskX'
}
taskX.dependsOn {
   tasks.findAll { 
      task → task.name.startsWith('lib') 
   }
}
```
### Skipping tasks

```
task eclipse << {
   println 'Hello Eclipse'
}

// #1st approach - closure returning true, if the task should be executed, false if not.
eclipse.onlyIf {
   project.hasProperty('usingEclipse')
}

// #2nd approach - alternatively throw an StopExecutionException() like this
eclipse.doFirst {
   if(!usingEclipse) {
      throw new StopExecutionException()
   }
}
```

## Project Dependencies

Gradle takes care of building and publishing the outcomes somewhere. Publishing is based on the task that you define. You might want to copy the files to the local directory, or upload them to a remote Maven or lvy repository, or you might use the files from another project in the same multi-project build. The process of publishing is called as publication.

### Declaring Java dependencies


```
apply plugin: 'java'

repositories {
   mavenCentral()
}

dependencies {
   compile group: 'org.hibernate', name: 'hibernate-core', version: '3.6.7.Final'
   testCompile group: 'junit', name: 'junit', version: '4.+'
}
```

### Dependency configurations

  - Compile − The dependencies required to compile the production source of the project.
  - Runtime − The dependencies required by the production classes at runtime. By default, also includes the compile time dependencies.
  - Test Compile − The dependencies required to compile the test source of the project. By default, it includes compiled production classes and the compile time dependencies.
  - Test Runtime − The dependencies required to run the tests. By default, it includes runtime and test compile dependencies.

### External dependencies

```
dependencies {
    // This dependency is used by the application.
    implementation group: 'com.google.guava', name: 'guava', version: '28.0-jre'

    // Use JUnit test framework only for testing
    testImplementation 'junit:junit:4.12'

    // It is only needed to compile the application
    compileOnly 'org.projectlombok:lombok:1.18.4'
}

```

There are various ways to specify versions:

```
implementation("com.google.guava:guava:28.0-jre")

implementation "com.google.guava:guava:28.0-jre"

implementation 'com.google.guava:guava:28.0-jre'

def guava_version = "28.0-jre"
implementation "com.google.guava:guava:$guava_version"
```

### Repositories

By default, Gradle does not define any repositories. We have to define at least one repository explicitly.

```
repositories {
   mavenCentral()
}
```
or
```
repositories {
   maven {
      url "http://repo.mycompany.com/maven2"
   }
}
```


### Publishing Artefacts

```
apply plugin: 'maven'

uploadArchives {
   repositories {
      mavenDeployer {
         repository(url: "file://localhost/tmp/myRepo/")
      }
   }
}
```

## Plugins

A plugin is nothing but a set of tasks, almost all useful tasks such as compiling tasks, setting domain objects, setting up source files, etc. are handled by plugins.


Two types of plugins:

### Script

Is an additional build script that gives a declarative approach to manipulating the build. This is typically used within a build. 

Can be applied from a script on the local filesystem or at a remote location. Filesystem locations are relative to the project directory

```
apply from: 'other.gradle'
```


### Binary

The classes that implement the plugin interface and adopt a programmatic approach to manipulating the build. Binary plugins can reside with a build script, with the project hierarchy or externally in a plugin JAR.


Each plugin is identified by a plugin id. Some core plugins use short names to apply it and some community plugins use fully qualified name for plugin id. Sometimes it allows to specify a class of plugin.

```
# apply by type

apply plugin: JavaPlugin

# apply core plugin by id
plugins {
   id 'java'
}

# apply community plugin

plugins {
   id "com.jfrog.bintray" version "0.4.1"
}
```

### Standard plugins

   - java
   - groovy
   - scala
   - antir

### Creating custom plugin


```
apply plugin: GreetingPlugin

class GreetingPlugin implements Plugin<Project> {
   void apply(Project project) {
      project.task('hello') << {
         println "Hello from the GreetingPlugin"
      }
   }
}
```

## Runnning gradle

Build file is build.gradle.

Don't use "gradle". That will call the gradle you have installed on your machine.

Use ./gradlew wrapper in the project folder instead.
It will cause it to download and run specific version configured for this particular project.

### Excluding tests

gradle task4 -x test

### Seeing all tasks

gradle tasks --all


### Discovering more info about a task 

./gradlew bootRun --info

### See task dependency tree

Add this plugin:

```
plugins {
  id "com.dorongold.task-tree" version "1.3.1"
}
```
Then run

```
./gradlew build taskTree
```




* creating gradle build files

gradle init

