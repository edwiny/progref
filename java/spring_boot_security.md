# Spring Security

Sits between client and the application.
Performs both authentication and authorisation.


## Dependency

```
implementation("org.springframework.boot:spring-boot-starter-security")
```

includes autoconfiguration and by default enables security-related features
* adds default login and logout screen
* by default restricts access to all content and endpoints
* creates a default user called 'user' and generates a new password every time on startup (look at startup logs)

## Authentication

### Configuration: application.properties

In `application.properties`


```
# default user
spring.security.user.name=someone
spring.security.user.password=123
```

### Configuration: AuthenticationManagerBuilder

NOTE: outdated. See https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter


Obtain a AuthenticationManagerBuilder:

```
@EnableWebSecurity   //instructs spring to consider this config
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
    // note there are multiple versions of this method. Choose one where the builder is passed as arg.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        //example of in memory config of hardcoded users

        auth.inMemoryAuthentication()
            .withUser("user1")
            .password("pass1")
            .roles("ADMIN")
            .and()
            .withUser("user2")
            .password("pass2")
            .roles()


            //roles("ADMIN") is equivalent to authorities("ROLE_ADMIN")


    }
}
```




## Authorisation


* **Authorities** are actions users can take, e.g. `READ`, `WRITE`
* **Roles** are groups of authorities


Under the hood, a role is String prefixed with ROLE_.

### Configuration: AuthenticationManagerBuilder

NOTE: outdated. See https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter


Obtain a AuthenticationManagerBuilder:

```
@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .mvcMatchers("/admin").hasRole("ADMIN")
            .mvcMatchers("/user").hasAnyRole("ADMIN", "USER")
            .mvcMatchers("/", "/public").permitAll()
            .mvcMatchers("/**").authenticated() // or .anyRequest().authenticated()
            and().httpBasic();


        //first, append a call to a method that allows for selecting endpoints, 
        //then call to bind to a role/authortity


        //NOTE: order of methods are important, the permissive rules must be at the end.


        //NOTE: avoid the Ant matchers as they match only 1 string.
    }
}
```


NOTE: calls to POST are auto protected against CSRF, in development you need to add the
`.csrf().disable()` calls to the HttpSecurity object.



