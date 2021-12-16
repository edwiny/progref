# Kotlin and JPA


## The allopen plugin

In order to make lazy fetching possible, entities should be `open` by default.
A shortcut to hook this into the `@Entity` annotation is to use the allopen plugin in Gradle:

```
plugins {
      ...
            kotlin("plugin.allopen") version "1.3.61"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}
```

## Declaring entities

```
@Entity
class Article(
    var title: String,
    var headline: String,
    var content: String,
    @ManyToOne var author: User,
    var slug: String = title.toSlug(),
    var addedAt: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue var id: Long? = null)

@Entity
class User(
    var login: String,
    var firstname: String,
    var lastname: String,
    var description: String? = null,
    @Id @GeneratedValue var id: Long? = null)
```

In Kotlin it is not unusual to group concise class declarations in the same file.

## Data classes with JPA


We don’t use `data` classes with `val` properties because JPA is not designed to work with immutable classes or the methods generated automatically by data classes.



