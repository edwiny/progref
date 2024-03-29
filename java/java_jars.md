# JAR usage


## Structure of a jar

```
example.jar
├── META-INF
│   └── MANIFEST.MF
├── second
│   ├── Main.class
│   └── MyIcon.png
└── third
    └── another
        └── OneMore.class
```

This jar contains 2 packages:
* `second`
* `third.another`


## Creating a jar

with custom manifest:
```
jar -cfm jars/Main.jar META-INF/MANIFEST.MF -C classes .
```

with default manifest:

```
jar cf jars/myjarfile.jar classesdir/
```

Will recursively package all classes

WARNING: will use the full path of classes in manifest e.g if class files under
class/*, the jar will also contain the class/* path. Use -C arg to chdir into target dir


## Viewing jar contents

```
jar tf jarfile.jar
```

## Manifests

 - Each jar can have only 1 manifest. 
 - Located in: `META-INF/MANIFEST.MF`
 - Contents:

```
Manifest-Version: 1.0
Created-By: 1.7.0_06 (Oracle Corporation)
Main-Class: Main
```


**Warning**: The text file from which you are creating the manifest must end with a new line or carriage return. The last line will not be parsed properly if it does not end with a new line or carriage return.

## Setting entry point

Specify class with main function in this header. The class name needs be be fully qualified (i.e. contain path / package)

Main-Class: Main


If this field is present, you can just start the program with `java -jar my.jar`. 
Otherwise you need to provide the class path with `java -cp my.jar Main`


## Load classes from another jar into your jars classpath:

Class-Path: MyUtils.jar



