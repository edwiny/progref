# Jackson JSON Serialisation / Deserialisation


## Dependencies

```
com.fasterxml.jackson.core:jackson-databind
```

## Default Serialisation


```
public class Post {
    private int id;
    private Date createdDate;
    private String content;
    private int likes;
    private List<String> comments;

//  constructor, getters, setters
}

...

// Step 1
Post post = new Post(
        1,
        new Date(),
        "I learned how to use jackson!",
        10,
        Arrays.asList("Well done!", "Great job!")
);

// Step 2
ObjectMapper objectMapper = new ObjectMapper();

// Step 3
String postAsString = objectMapper.writeValueAsString(post);

// OR pretty print

String postAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(post);

System.out.println(postAsString);

```

NOTE:
Jackson supports `Date` from `java.util` by default, and Jackson will serialize the Date to the timestamp format (number of milliseconds since January 1st, 1970, UTC).


To change the name of the key using in the JSON output, use `@JsonProperty`

```
@JsonProperty("postedAt")
private Date createdDate;
```


Can also use it to denote a getter/setter

```
@JsonProperty("createdDate")
public String getReadableCreatedDate() {
    return (new SimpleDateFormat("dd-MM-yyyy")).format(createdDate);
}
```



To ignore fields, use `@JsonIgnore`


## Deserialisation

```
String inputJson = "{\"id\":1,\"createdDate\":1654027200000,\"content\":\"I learned how to use jackson!\",\"likes\":10,\"comments\":[\"Well done!\",\"Great job!\"]}\n";

ObjectMapper objectMapper = new ObjectMapper();
Post post = objectMapper.readValue(inputJson, Post.class);
```

### Conditions of the class

* Class must have an empty constructor
* Fields must not be `final` (i.e. can be assigned to more than once)
* The class must have a constructor with the `@JsonCreator` annotation, and all its parameters must have the  `@JsonProperty` annotation, which must necessarily contain the name from JSON.


E.g.:
```
public class Post {
    private final int id;
    private final Date createdDate;
    private String content;
    private int likes;
    private List<String> comments;

    @JsonCreator
    public Post(
            @JsonProperty("id") int id,
            @JsonProperty("createdDate") Date createdDate,
            @JsonProperty("content") String content,
            @JsonProperty("likes") int likes,
            @JsonProperty("comments") List<String> comments) {
        this.id = id;
        this.createdDate = createdDate;
        this.content = content;
        this.likes = likes;
        this.comments = comments;
    }

// getters
}
```
