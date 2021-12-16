# Java Persistence API

Part of the Java EE JDK.

```javax.persistence.*```

## Mapping Entities

Can either use annotations of XML files. The XML files override any annotations.


### @Entity

Maps a class to a table.

Example:

```
@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "app_name", nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;
    private String owner;
}
```
    
* Class needs a public no args constructor
* At minimum needs a @Id to denote the key.


### @Id

### @Table

### @Column

### @GeneratedValue




## Entity Manager


Allows you to use the entity mappings to perform CRUD operations on the database.

Example (no Spring dependencies):


```
@Transactional
@Repository
public class ApplicationDAO implements IApplicationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addApplication(Application application) {
        entityManager.persist(application);
    }

    @Override
    public Application getApplicationById(int applicationId) {
        return entityManager.find(Application.class, applicationId);
    }
}
```
### merge() or persist() ?

See Entity Lifecycle below.

The difference between merge() and persist() is that merge() adds the entity to the persistence context, but your reference to the object is not "managed." If you make changes to your object after invoking merge(), those changes will not be sent to the database. The persist() method marks your object as "managed." Any changes you make to your object after invoking persist() will be sent to the database.


### Transactional Managers

Aka "Shared" manager.

Allows a thread-safe shared Entity Manager to be injected into the Data Access Objects without getting a new one from the factory for every transaction.

Configure by passing the transactional type into the @PersistenceContext annotion.


## JPQL

JPA-specific query language to find objects in the database.

Does not support NoSQL or schemaless databases.

E.g:

```
public boolean applicationExists(String name, String owner) {
    //note application is the class name; not the table name; class name is case sensitive; use class field names - column names
    String jpql = "from Application as a WHERE a.name = ? and a.owner = ?";
    int count = entityManager.createQuery(jpql).setParameter(0, name).setParameter(1, owner).getResultList().size();
    return count > 0;
}
```

Will return collection of entities.




## Entity Life cycle


Loading -> Persisting -> Updating -> Removing


Entities can be Managed (under control of the Entity Manager) or Detached (final state).

When a entity is created via `new` keyword, it's seen as a regular java object.
Before it's persisted, it is in a Transient or New state.
Once persisted, it enters the Managed state. Or if it's loaded from the database.

Once a entity is in a Managed state, any updates to it via setter methods are auto synchronised to the database.
This is where there is no update() method in the Entity Manager API.

An entity will enter the Removed state when marked for deletion with the Entity Manager's remove() method.

An entity enters the Detached state after it has been removed, flushed, or comitted. After this it will stay around until the Garbage collector destroys it.




## Transactions



When the DAO class is annotated with @Transactional, the Entity Manager will seamlessly interact with the Java Transaction API to create transactions
and roll them back if an exception occurs. 


## Relationships

### @OneToOne

could keep it together in one table?

### @OneToMany and @ManyToOne


The @OneToMany and @ManyToOne annotations facilitate both sides of the same relationship. 
Consider an example where a Book can have only one Author, but an Author may have many books. 
The Book entity would define a @ManyToOne relationship with Author and the Author entity would define a @OneToMany relationship with Book.


```

@Entity
public class Book {
    @Id
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name="AUTHOR_ID")
    private Author author;
    ...
}
```

```
@Entity
public class Author {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();
    ...
}
```

### @ManyTo@Many

Where on book can have multiple authors, and authors can write multiple books.


```

@Entity
public class Book {
    @Id
    private Integer id;
    private String name;
    @ManyToMany
    @JoinTable(name="BOOK_AUTHORS",
    		   joinColumns=@JoinColumn(name="BOOK_ID"),
    		   inverseJoinColumns=@JoinColumn(name="AUTHOR_ID"))
    private Set<Author> authors = new HashSet<>();
    ...
}

```


```
@Entity
public class Author {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "author")
    private Set<Book> books = new HashSet<>();
    ...
}
```





## Hibernate Dependencies


```
  <dependencies>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>${hibernate.version}</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>${hibernate.version}</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate.javax.persistence</groupId>
            <artifactId>hibernate-jpa-2.1-api</artifactId>
            <version>1.0.2.Final</version>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.197</version>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
```


## References

* https://thorben-janssen.com/ultimate-guide-association-mappings-jpa-hibernate/
* https://www.infoworld.com/article/3373652/java-persistence-with-jpa-and-hibernate-part-1-entities-and-relationships.html
* https://stackoverflow.com/questions/5478328/in-which-case-do-you-use-the-jpa-jointable-annotation
