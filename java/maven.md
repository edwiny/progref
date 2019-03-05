# Maven


## Oveview

POMs describe WHAT NEEDS TO BE BUILT, not HOW TO BUILD it.
The 'Maven Build Life Cycles, Phases and Goals' determines how to build.

### Data structure

* Build Life Cycle 1
  * Phase 1
     * Goal 1
     * Goal 2
  * Phase 2
     * Goal 1
     * Goal 2
* Build Life Cycle 2


- When you run maven, you pass it a command, which can be a Build Life Cycle, Phase, or Goal.
- If Cycle requested, all Phases in the Cycle is executed.
- If Build Phase requested, all other build phases preceding it in a predefined order are also executed.


### A minimal pom.xml

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>babel</groupId>
  <artifactId>maven1</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>maven1</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>
```


### Dependencies and Repos
- If dependency cannot be found in local Maven repo, it will be downloaded from any repos configured.

### Build Plugins

Used to insert extra Goals into a Build Phase. 
There's a bunch of existing Plugins you can use, or you can write your own.

```
  <build>
  <plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <configuration>
            <archive>
                <manifest>
                    <addClasspath>true</addClasspath>
                    <mainClass>hello.Application</mainClass>
                </manifest>
            </archive>
        </configuration>
    </plugin>

  </plugins>
  </build>
```

### Build Profiles

You can build differently depending on the environment you're building for, e.g. dev or prod.

## POM files

* The POM file describes what to build, but most often not how to build it.
* Each project has a POM file. The POM file is named pom.xml and should be located in the root directory of your project. 
* A project divided into subprojects will typically have one POM file for the parent project, and one POM file for each subproject.

### Minimal POM

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                      http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.jenkov</groupId>
    <artifactId>java-web-crawler</artifactId>
    <version>1.0.0</version>
</project>
```


### GAVs
* Group - name of org
  * does not need to be package name but if it is, then will follow directory structure implied in the dots.
* Artefact - name of project
* Version  - version name


### Super POMs

You can make a POM inherit from another POM file:

```xml
<parent>
<groupId>org.codehaus.mojo</groupId>
<artifactId>my-parent</artifactId>
<version>2.0</version>
<relativePath>../my-parent</relativePath>
</parent>
```
                                               
This way you can manage default settings for a large number of POMs.

### Effective POM

This is the result from merging the pom in current dir with any parent poms:

```
mvn help:effective-pom
```

## Maven Settings file

The Maven installation directory:
``` $M2_HOME/conf/settings.xml```
The user's home directory:
```${user.home}/.m2/settings.xml```



## Running Maven

### Default directory structure

```
- src
  - main
    - java
    - resources
    - webapp
  - test
    - java
    - resources

- target
```

See [http://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html]

You can setup a default pom and directory structure with
```
mvn archetype:generate -DarchetypeArtifactId=maven-archetype-quickstart -DgroupId=xxx -DartifactId=xxx
```


## Dependencies

### Regular Dependencies

Declare like this

```xml

  <dependencies>

        <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>4.8.1</version>
          <scope>test</scope>
        </dependency>

      </dependencies>
```

Sometimes a given dependency is not available in the central Maven repository. You can then download the dependency yourself and put it into your local Maven repository. Remember to put it into a subdirectory structure matching the groupId, artifactId and version. Replace all dots (.) with / and separate the groupId, artifactId and version with / too. Then you have your subdirectory structure.




### External Dependencies

"External" means external to the maven local/central repo system.
E.g. the jar is in a location on the filesystem outside of local repo. 

Declare like this:
```xml
<dependency>
  <groupId>mydependency</groupId>
  <artifactId>mydependency</artifactId>
  <scope>system</scope>
  <version>1.0</version>
  <systemPath>${basedir}\war\WEB-INF\lib\mydependency.jar</systemPath>
</dependency>
```


### Snapshot dependencies

```xml
<dependency>
    <groupId>com.jenkov</groupId>
    <artifactId>java-web-crawler</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```


## Repositories

Maven repositories are directories of packaged JAR files with extra meta data. The meta data are POM files describing the projects each packaged JAR file belongs to, including what external dependencies each packaged JAR has. 

Maven has three types of repository:

* Local repository
* Central repository
* Remote repository

Maven searches for JARs in this ^ order.


### Local repo

* Specified in settings.xml file, defaults to ${HOME}/.m2/repository
* The same Maven repository is typically used for several different projects. 
* Your own projects can also be built and installed in your local repository, using the mvn install command.
* Location can be changed in your .m2/settings.xml file:
```xml
<settings>
    <localRepository>
        d:\data\java\products\maven\repository
    </localRepository>
</settings>
```



### Central repo

By default Maven looks in this central repository for any dependencies needed but not found in your local repository. Maven then downloads these dependencies into your local repository. You need no special configuration to access the central repository.

### Remote repo

Used like Central repo but is internal to your org and not public. 

Dependencies downloaded from remote repos are also downloaded into local repo.
It's declared after the 'dependencies' tag in the pom.xml:

```xml
<repositories>
   <repository>
       <id>jenkov.code</id>
       <url>http://maven.jenkov.com/maven2/lib</url>
   </repository>
</repositories>
```

## Build Life Cycles and Phases
### Build Life Cycles

* default  - compiling and packaging
* clean    - remove temp files and artefacts
* site     - generates docs



## Build Phases


* The default life cycle cannot be expliticly invoked, only one of it's phases or goals.
* When you execute a build phase, all build phases before that build phase in this standard phase sequence are executed.


The default life cycle is of most interest since that is what builds the code. Since you cannot execute the default life cycle directly, you need to execute a build phase or goal from the default life cycle.

Build Phase | Description
------------|------------
validate    | download dependencies
compile     | build
test        | test
package     | Packs the compiled code in its distributable format, such as a JAR.
install     | Install the package into the local repository, for use as a dependency in other projects locally.
deploy      | Copies the final package to the remote repository for sharing with other developers and projects.

All previous phases are executed for a given phase.

### Goals

Build goals are the finest steps in the Maven build process. A goal can be bound to one or more build phases, or to none at all. If a goal is not bound to any build phase, you can only execute it by passing the goals name to the mvn command. If a goal is bound to multiple build phases, that goal will get executed during each of the build phases it is bound to.

## Build Profiles

For building prod or dev.

Specified like this:

```xml
<profiles>
      <profile>
          <id>test</id>
          <activation>...</activation>
          <build>...</build>
          <modules>...</modules>
          <repositories>...</repositories>
          <pluginRepositories>...</pluginRepositories>
          <dependencies>...</dependencies>
          <reporting>...</reporting>
          <dependencyManagement>...</dependencyManagement>
          <distributionManagement>...</distributionManagement>
      </profile>
</profiles>
```

* A build profile describes what changes should be made to the POM file when executing under that build profile. 
* The elements inside the profile element will override the values of the elements with the same name further up in the POM.


Inside the profile element you can see a activation element. This element describes the condition that triggers this build profile to be used. 
Another way to choose what profile is being executed is in the settings.xml file. There you can set the active profile. 
Another way is to add -P profile-name to the Maven command line.


## Maven Archetypes

Maven archetypes are project templates which can be generated for your by Maven. In other words, when you are starting a new project you can generate a template for that project with Maven.

To see what archetypes are available:
```mvn archetype:generate```


### Quickly initialising pom and directory structure

```
mvn archetype:generate -DarchetypeArtifactId=maven-archetype-quickstart -DgroupId=<groupid> -DartifactId=<artifact>
```



### Editor "named" archetypes

You can generate project files for Eclipse or Intellij:

```
mvn eclipse:eclipse
mvn idea:idea
```

## Standard Directory Layout

```
pom.xml     project file
target          Output of the builds

src/main/java   Application/Library sources
src/main/resources  Application/Library resources
src/main/config   Configuration files
src/main/scripts  Application/Library scripts

src/test/java   Test sources
src/test/resources  Test resources

src/it      Integration Tests (primarily for plugins)
src/assembly    Assembly descriptors
src/site    Site
```



## Common plugins


### Using surefire testing plugin

Generate a testing report:

```mvn surefire-report:report```




### build fat runnable jars

```
<build>
 <plugins>
   <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-assembly-plugin</artifactId>
        <executions>
          <execution>
             <goals>
               <goal>attached</goal>
             </goals>
             <phase>package</phase>
             <configuration>
               <descriptorRefs>
                 <descriptorRef>jar-with-dependencies</descriptorRef>
              </descriptorRefs>
              <archive>
                <manifest>
                  <mainClass>babel.App</mainClass>
                </manifest>
              </archive>
            </configuration>
         </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

Then run:

```
$ mvn package
$ jar tf target/dateutils-jar-with-dependencies.jar 
```

### create javadocs

```

  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-javadoc-plugin</artifactId>
      <executions>
        <execution>
          <id>attach-javadocs</id>
          <goals>
            <goal>jar</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
```

### set java language level

```
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.5.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## Common dependencies


## log4j

```
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

### junit
```
<!-- https://mvnrepository.com/artifact/junit/junit -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.11</version>
    <scope>test</scope>
</dependency>
```

