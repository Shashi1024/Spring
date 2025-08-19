> *Topics --> Spring Security, . . .*\
> *Link to Codes --> [Codes](../Codes/)*


## Spring Security
- this framework provides Authentication, Authorization, protection against common web vulnerabilities

### Authentication Vs. Authorization
- **Authentication** --> verifying who the user is.
- **Authorization** --> process of determining what is an authenticated user is allowed to do.

- *for more details refer --> [Authentication Vs. Authorization](AuthenticateAuthorize.md)*

---

### Key Players

- `SecurityContextHolder` --> a static class that holds the `SecurityContext` for the current thread of execution. It's how Spring Security knows who the currently authenticated user is at any point in the application.

- `SecurityContext` --> This object holds the `Authentication` object, which represents the currently authenticated principal (user).

- `Authentication` --> object that holds the principal (the user), their credentials (e.g., password), and their GrantedAuthoritys (permissions/roles).

- `AuthenticationManager` --> The top-level interface for authentication. It receives an `Authentication` request and returns a fully populated `Authentication` object if the user is valid.

- `AuthenticationProvider` --> The actual worker of the authentication process. An `AuthenticationManager` can have multiple `AuthenticationProvider`s, each of which can handle a different authentication method (e.g., one for username/password, one for OAuth2).

- `UserDetailsService` --> it is a service interface we implement to load a user's details from the application's data store (database). It returns a `UserDetails` object.

- `UserDetails` --> A simple data model that contains the user's username, password, and `GrantedAuthority`s. It's a standard interface used by Spring Security for all user details.

- `GrantedAuthority` --> Represents a permission or a role. Roles are typically prefixed with `ROLE_` (e.g., `ROLE_ADMIN`).

---

### Spring Security Request Flow
Spring Security is integrated with Servlet API to intercept every web request. This interception is a series of `Filters` that form a "security filter chain."

- **Request enter web server** --> request from client enters the servlet container
- **`FilterChainProxy`** --> The first Spring Security component to be invoked is a `Filter` called `FilterChainProxy`. Its job is to manage the security filter chain.

- **`SecurityFilterChain`** --> The `FilterChainProxy` delegates the request to a specific `SecurityFilterChain`. 
  - A chain is a collection of security filters that handle different aspects of security. Common filters include:
    - `UsernamePasswordAuthenticationFilter` --> Handles authentication for form-based login. It extracts a username and password from the request and creates an Authentication object.
    - `BasicAuthenticationFilter` --> Handles HTTP Basic authentication.
    - `AuthorizationFilter` --> Checks if the authenticated user has the required permissions to access the requested resource.
    - `ExceptionTranslationFilter` --> Catches security-related exceptions and handles them gracefully, such as redirecting an unauthenticated user to a login page.

- **Authentication Process**
  - a filter (like `UsernamePasswordAuthenticationFilter`) creates an unauthenticated `Authentication` object and this object is passed to `AuthenticationManager`
  - `AuthenticationManager` finds the correct `AuthenticationProvider` to handle the request.
  - `AuthenticationProvider` uses custom `UserDetailsService` to load the user's details from the database.
  - the provider compares the provided credentials with the stored credentials.
  - if the credentials matches, it returns a fully authenticated `Authentication` object.
  - this object is then placed in the `SecurityContextHolder`, making the user's identity available to the rest of the application.

- **Authorization Process**
  - a later filter (like `AuthorizationFilter`) checks the URL and HTTP method of the incoming request.
  - it retrieves the authenticated `Authentication` object from the `SecurityContextHolder`
  - it compares the user's `GrantedAuthority`s (roles) against the configured access rules for that URL.
  - if the user is authorized, the request proceeds to the `DispatcherServlet` and is handled by the controller.
  - if not, an `AccessDeniedException` is thrown.

- **Controller Execution**
  - if all security checks pass, the request finally reaches the Spring MVC `DispatcherServlet` and is handled by the controller.


---

### Web Security Configuration
- security rules are configured using a `SecurityFilterChain` bean (this bean defines the entire security policy for the application)
- `@EnableWebSecurity`
  - this annotation, placed on the configuration class, activates Spring Security's web support. 
  - it is often used with a class that provides a `SecurityFilterChain` bean


---

### Practical Components
- `PasswordEncoder` --> it is an interface used to securely hash and store the passwords. most common implementation is `BCryptPasswordEncoder` (`PasswordEncoder` bean must be provided in the configuration)

- **Method-Level Security** --> in addition to URL-based security, individual methods can be secured using annotations like `@PreAuthorize` and `@PostAuthorize` (a form of Spring AOP)
  - `@PreAuthorize("hasRole('ADMIN')")` --> Checks the user's roles before the method is executed.
  - `@PostAuthorize("returnObject.owner == authentication.name")` --> Checks the user's permissions after the method has executed, based on the returned object.