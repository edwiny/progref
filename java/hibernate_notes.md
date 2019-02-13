# Hibernate notes



## Model

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


## Annotations

* `@Column` - set the name of the column in the db
* `@Table` - set the name of the table in the db
* `@ManyToOne` - in a Entity/Table that represents the Many side of Many-to-One relationship, this indicates an attribute to be the One key.

## Relationships


### Unidirectional Many-to-One

Imagine 'Order' and 'OrderItem' tables/entities. One order can have many items but one item can only belong to one order.

Order:
* the One side


OrderItem:
* the Many side
* hibernate definition:
```
@Entity
public class OrderItem {
 
    @ManyToOne
    private Order order;
 
    …
} 
```
* Hibernate would use a column with the name order_id to store the foreign key to the Order entity. To specify a different column name:

```
@Entity
public class OrderItem {
 
    @ManyToOne
    @JoinColumn(name = “fk_order”)
    private Order order;
 
    …
}

```









### OneToMany ( + ManyToOne)

one row in a table is mapped to multiple rows in another table.

E.g.  One cart can have many items, so here we have a one-to-many mapping.

At the db level:

```
CREATE TABLE `Cart` (
  `cart_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
 
CREATE TABLE `Items` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `cart_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cart_id` (`cart_id`),
  CONSTRAINT `items_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `Cart` (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

```

The corresponding models:

```
@Entity
@Table(name = "CART")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private long id;


    @OneToMany(mappedBy = "cart")
    private Set<Items> items;

    //getters and setters

}

```
Items class:
```
@Entity
@Table(name = "ITEMS")
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    // Hibernate requires no-args constructor
    public Items() {
    }

    //getters and setters

}
```

#### Uni-directional vs bi-directinal

If cart referenced items, but items did not reference cart back, then the relationship would be uni-directional.


#### Owning side vs inverse side

The owning side is the entity that has the reference to the other one.

### ManyToMany



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

## defining the model


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
