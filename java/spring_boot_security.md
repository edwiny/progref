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



## HTTP Basic auth


Server responds with 401 and header 'WWW-Authenticate`.
Browser resends request after prompting use for username and password, adds the Authorization header.

```
Authorization: <type> <credentials>
```

Type is 'Basic' and creds are base64 encoded 'username:password'.


## Implementing a custom user store (post Spring Security 5.7)


First create a User class:




```
class User {
    private String username;
    private String password;
    private String role; // should be prefixed with ROLE_
    //constructor, getters and setters...
}
```



Then create a repo class (Be sure to use threadsafe Map)

```
@Component
public class UserRepository {
    final private Map<String, User> users = new ConcurrentHashMap<>();

    public User findUserByUsername(String username) {
        return users.get(username);
    }

    public void save(User user) {
        users.put(user.getUsername(), user);
    }
}
```


Create a registration controller:
```
@RestController
public class RegistrationController {
    @Autowired
    UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;    //defined later in security config

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        // input validation omitted for brevity

        user.setPassword(encoder.encode(user.getPassword()));

        userRepo.save(user);
    }
}
```

Now we need to implement an interface of `UserDetails` which is what Spring Security knows about users.
We map our internal `User` representation with authorities.


```

public class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String password;
    private final List<GrantedAuthority> rolesAndAuthorities;

    public UserDetailsImpl(User user) {
        username = user.getUsername();
        password = user.getPassword();
        rolesAndAuthorities = List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesAndAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 4 remaining methods that just return true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
```


When now have to implement another Spring interface, the `UserDetailsService`:


```
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("Not found: " + username);
        }

        return new UserDetailsImpl(user);
    }
}
```

Finally, we define the security configuration:


```
@Configuration
public class SecurityConfiguration {

    @Autowired
    UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests( authz -> {
                    authz.requestMatchers(new AntPathRequestMatcher("/register"))
                            .permitAll();
                    authz.requestMatchers(new AntPathRequestMatcher("/test"))
                            .hasAnyRole("USER")
                            .anyRequest()
                            .authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

}

```
