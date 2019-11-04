# Hibernate notes



## Class Mappings

Example domain model from Java Enterprise in a Nutshell, 3rd Edition:

* Department - Will contain multiple classes and professors but no students
* UniversityClass Will have a single professor and multiple students, will belong to a single department, and will have a single syllabus
* Syllabus - Will belong to a UniversityClass; every Syllabus will be unique to a specific UniversityClass
* Professor - Will teach one or more classes but have a single primary department
* Students - Will attend multiple classes but have no formal association with either a Professor or a Department


This is where you map your domain model to the database schema.

Hibernate is java / class-centric. All data entities must be represented as classes.

* Each persistable class must be mapped
* Each class must map to one table. If your class is represented by multiple tables, you have to either refacter the class into multiple, smaller classes or create a single view that spans across the tables.

Mappings consist of:
* id
	* Each class must have one or more fields that uniquely identify it. This can be controlled explicitly by you or by Hibernate.
* properties
	* you don't need to persist all properties e.g. there may be calculated fields in the class that don't need persistence
	* hibernate should be able to figure the type out automatically via reflection but you can choose to explicitly define it.

**First-class objects and collections**

These as the primary actors in the model. E.g. for a Professor class that can have one or more Email addresses, the former is a first-class object.
Any instance of a first class object is unique.

The second-class entities can be represented as a collection property on the first-class object:

```
Public class Professor {
    private Long id;
    private String firstName;
    private String lastName;
    private Set emails;
    // getters/setters elided for brevity...
}
```

Defining the emails property as a Set in the class mapping would result in tables like this:

```
CREATE TABLE 'professors' (
    'professor_id' int(11) NOT NULL auto_increment,
    'first_name' varchar(25) NOT NULL,
    'last_name' varchar(50) NOT NULL,
    PRIMARY KEY ('professor_id')
)
CREATE TABLE 'emails' (
    'professor_id' int(11) NOT NULL,
    'email' varchar(255) NOT NULL
)
```

Professors might have 0, 1, or many email addresses in the corresponding table.




**One-to-One relationships**

The UniversityClass and Syllabus entities have a one-to-one relationship:

```
public class UniversityClass {
    private Long id;
    private String name;
    private Syllabus syllabus;
    //getters/setters elided for clarity...
}

public class Syllabus {
    private Long id;
    private String abstract;
    private String fulltext;
    private UniversityClass uclass;
    //getters/setters elided for clarity...
}
```

Here UniversityClass can be considered the primary class or parent. Syllabus is the dependent object or child. These entities reference each other so it's a bi-directinoal releationship.

The mappings are slightly different between these. For the parent:
```
...
<many-to-one name="syllabus" column="syllabus_id"
      unique="true"/>
...
```
Says 'many-to-many' because the physical representation on this side looks the same as one-to-one. Note the `unique` attribute.

For the child:

```
...
<one-to-one name="uClass" property-ref="syllabus"/>
...

```
Says the entity is in a one-to-one relationship and that `uClass` is establishes one end of the relationship and that the other end is of type `uClass` and that it will contain a fields called `syllabus` that refers to this class.


**Bidirectional one-to-many**

This is the most common type of relationship.

One object is the parent and has multiple children. The children are bound to their parent and only to their parent.

Using the university example, a Professor can teach many classes but one class can only be taught by a single professor.

The class definitions:

```
public class UniversityClass {
    private Long id;
    private String name;
    private Syllabus syllabus;
    private Professor professor;
}
```
The mapping:

```
<many-to-one name="professor" column="professor_id" not-null="true"/>
```

And:
```
public class Professor {
    private Long id;
    private String firstName;
    private String lastName;
    private Set emails;
    private Set classes;
}
```

The xml mapping:

```
<set name="classes">
    <key column="professor_id"/>
    <one-to-many class="UniversityClass"/>
</set>
```

The resulting tables:

```
CREATE TABLE 'professors' (
    'professor_id' int(11) NOT NULL auto_increment,
    'first_name' varchar(25) NOT NULL,
    'last_name' varchar(50) NOT NULL,
    PRIMARY KEY ('professor_id')
)
CREATE TABLE 'university_classes' (
    'university_class_id' int(11) NOT NULL auto-increment,
    'name' varchar(255) NOT NULL,
    'syllabus_id' int(11),
    'professor_id' int(11) NOT NULL,
    PRIMARY KEY ('university_class_id')
)
```

**Many-to-many**

Requires a join table with foreign keys into the two tables being joined.

Can do the class definitions in one of two ways:
* Class for join table with one-to-many relationships for both of the classes being joined (suggested by Hibernate group)
* or encode the many-to-many relationship directly into the two objects being joined (preferred by most people.) We'll use this for the example below.

Each end of the domain relationship now has to have a property that is a collection of the other end of the relationship:

```
public class UniversityClass {
    private Long id;
    private String name;
    private Syllabus syllabus;
    private Professor professor;
    private Set students;
}

<set name="students" table="students_classes" inverse="true">
    <key column="university_class_id"/>
    <many-to-many column="student_id" class="Student"/>
</set>

```
student is marked as inverse - This makes the Student the “parent” in the relationship, and Hibernate will not try to insert or modify the properties of the relationship if the changes are made only from the UniversityClass side.

```
public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private Set classes;
}

<set name="classes" table="students_classes">
    <key column="student_id"/>
    <many-to-many column="university_class_id" class="UniversityClass"/>
</set>

```

The SQL definitions:

```
CREATE TABLE 'university_classes' (
    'university_class_id' int(11) NOT NULL auto-increment,
    'name' varchar(255) NOT NULL,
    'syllabus_id' int(11),
    'professor_id' int(11) NOT NULL,
    PRIMARY KEY ('university_class_id')
)
CREATE TABLE 'students' (
    'student_id' int(11) NOT NULL auto-increment,
    'firstName' varchar(25) NOT NULL,
    'lastName' varchar(50) NOT NULL,
    PRIMARY KEY ('student_id')
)
CREATE TABLE 'students_classes' (
    'student_id' int(11) NOT NULL,
    'university_class_id' int(11) NOT NULL
)
```




### Entity Types

* Rows in a table
* Uniquely identifiable
* Has it's own life cycle
* Corresponds to the doman model classes

### Value types

Does not define its own lifecycle.

* Basic types
	* int or string, etc. Search internet for Hibernate Basic Types to see how they map to JDBC and Java types
	* can use the `@Basic` annotation to flag a variable as basic but it's kinda redundant because it's the default. It has these attributes:
		* `optional` - boolean - defaults to true - makes it NULLABLE
		* `fetch` - FetchType - EAGER or LAZY


```
Java primitive types (boolean, int, etc)
wrappers for the primitive types (java.lang.Boolean, java.lang.Integer, etc)
java.lang.String
java.math.BigInteger
java.math.BigDecimal
java.util.Date
java.util.Calendar
java.sql.Date
java.sql.Time
java.sql.Timestamp
byte[] or Byte[]
char[] or Character[]
enums
any other type that implements Serializable (JPA’s "support" for Serializable types is to directly serialize their state to the database).
```
* Embeddable types - composite sub type possibly FK?
* Collection types - ???


### Relationships

#### Many-to-One (unidirectional)

#### One-To-Many ( + ManyToOne)


#### Uni-directional vs bi-directinal

If each endpoint of the relationship has a public property referencing the other entity, then it's bi-directional.

#### Owning side vs inverse side

The owning side is the entity that has the reference to the other one.






## Configuration


* `hibernate.dialect`	classname of org.hibernate.dialect.Dialect subclass
* `hibernate.connection.provider_class`	classname of ConnectionProvider subclass (if not specified hueristics are used)
* `hibernate.connection.username`	database username
* `hibernate.connection.password`	database password
* `hibernate.connection.url`	JDBC URL (when using java.sql.DriverManager)
* `hibernate.connection.driver_class`	classname of JDBC driver
* `hibernate.connection.isolation`	JDBC transaction isolation level (only when using java.sql.DriverManager)
* `hibernate.connection.pool_size`	the maximum size of the connection pool (only when using java.sql.DriverManager)
* `hibernate.connection.datasource`	databasource JNDI name (when using javax.sql.Datasource)

Postgres example:

```
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="connection.driver_class">org.postgresql.Driver</property>
        <property name="connection.url">jdbc:postgresql://localhost/db_name</property>
        <property name="connection.username">username</property>
        <property name="connection.password"></property>
        <property name="connection.pool_size">1</property>
        <!-- Disable the second-level cache  -->
        <property name="cache.provider_class">org.hibernate.cache.internal.NoCachingRegionFactory</property>
        <!-- Echo all executed SQL to stdout -->
        <property name="show_sql">true</property>
        <!-- Drop and re-create the database schema on startup -->
        <property name="hbm2ddl.auto">create</property>
        <!-- Names the annotated entity class -->
        <mapping class="org.hibernate.tutorial.annotations.Event"/>
    </session-factory>
</hibernate-configuration>

```


## initialisaition


```
    public static SessionFactory initDb() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            return(new MetadataSources(registry).buildMetadata().buildSessionFactory());

        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            throw new Error("Failed to init db");
        }
    }

```

**NB:** you must manually close the session factory at end of program or your program will keep on running.

## Using Annotations to define the models

Each entity class must:
* be annoted by `@Entity`
* The class must have a public or protected, no-argument constructor. The class may have other constructors.
* Persistent instance variables must be declared private, protected, or package-private and can be accessed directly only by the entity class’s methods.

Entities may extend both entity and non-entity classes, and non-entity classes may extend entity classes.

All fields in the class will be persisted unless annotated by `@javax.persistence.Transient`.




```
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "table1" )

public class Table1 {
    Long id;
    String resourceId;


    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }


}
```



## Annotations Quick Ref

* `@Column` - set the name of the column in the db
* `@Table` - set the name of the table in the db
* `@ManyToOne` - in a Entity/Table that represents the Many side of Many-to-One relationship, this indicates an attribute to be the One key.
