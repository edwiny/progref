# Spring Data 

Spring Data JPA is the most common way to work with RDBMS with Spring Data.


## Getting Started Config (H2)

1. Choose database (postgres, mysql, etc)

2. Add the JDBC driver for the chosen DB to build dependencies

3. Configure in `application.properties`:

```
# (Optional) Set the class e.g. MySQL, PostgreSQL
spring.datasource.driverClassName=org.h2.Driver

# (Not always required) JPA Dialect
# Only use this is you are using JPA - there are other ways to work with db's
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# jdbc url
spring.datasource.url=jdbc:h2:file:~/testdb
# for in-memory only db:
# spring.datasource.url=jdbc:h2:mem:testdb


# login 
spring.datasource.username=sa
spring.datasource.password=sa

# possible db specific config
...
```

4. Add Spring JPA build dependency

```
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
```


H2 also offers a web based console:

Build dependencies:

```
implementation 'org.springframework.boot:spring-boot-starter-web'
```

Applicatin properties:

```
spring.h2.console.enabled=true
spring.h2.console.settings.trace=true
```

Access console on http://localhost:8080/h2-console (can be changed with application properties `spring.h2.console.path`)


## MySQL

Build dependencies:

```
implementation 'mysql:mysql-connector-java'
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
```

Application properties:

```
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/testdb
spring.datasource.username=testuser
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect
```


## PostgreSQL

Build dependencies:

```
runtimeOnly 'org.postgresql:postgresql'
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
```

Application properties:

```
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/testdb
spring.datasource.username=testuser
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL10Dialect
```


