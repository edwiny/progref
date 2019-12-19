# Java snippets


## Parsing cmd-line arguments


```
import org.apache.commons.cli.*;
...

CommandLineParser parser = new DefaultParser();

Options options = new Options();

/*
     * @param opt Short single-character name of the option.
     * @param longOpt Long multi-character name of the option.
     * @param hasArg flag signally if an argument is required after this option
     * @param description Self-documenting description
     * @return the resulting Options instance

 */

options.addOption( "s", "service", true, "service location" );

try {
    // parse the command line arguments
    CommandLine line = parser.parse( options, args );


    if(!line.hasOption("user")) {
        throw new IllegalArgumentException("user");
    }
    params.put("user", line.getOptionValue("user"));
```

pom.xml:

```
 <dependency>
    <groupId>commons-cli</groupId>
    <artifactId>commons-cli</artifactId>
    <version>1.4</version>
 </dependency>
```

## Sorting

Use aggregation functions with lambdas:

```        
sortedProjects.sort(Comparator.comparing(Project::getName));
```