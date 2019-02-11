# Hibernate notes


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
