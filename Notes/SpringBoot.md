> *Topics --> Spring Boot, . . .*\
> *Link to Codes --> [Codes](../Codes/)*

## Fundamentals

### Spring Modules
- **Spring Core Container** (contains `spring-core`, `spring-beans`, `spring-context`, `spring-expression`)
- **Spring AOP**
- **Spring Data**
- **Spring Web**
- **Spring Security**

---

### Principles of Spring Boot

- **Convention over Configuration**
  - In Spring Boot, the framework observes what dependencies you've included in your project and automatically configures the application based on those conventions.
  - there is no need to write boilerplate code in XML or Java configuration files.


- **Auto Configuration**
  - Auto-configuration is the technical implementation of "Convention over Configuration.
  - Spring Boot's auto-configuration looks at the dependencies on your classpath.
  - think of auto-configuration as a set of conditional bean declarations. It says, "If this class is on the classpath and a DataSource bean doesn't already exist, then create and register one for me."
  - *Working*
    - works using `@Conditional` annotations (allows spring to make decisions at runtime) (*annotations start with `@Conditional`)
      - `@ConditionalOnClass` --> "Only auto-configure this bean if a specific class is present on the classpath."
      - `@ConditionalOnMissingBean` --> "Only configure this bean if a bean of this type has not already been created by the user."


- **Embedded web Servers**
  - When you create a Spring Boot application, it's packaged as a single, executable JAR (Java aRchive) file. This JAR file contains all your application code, all its dependencies (including Spring and other libraries), and an embedded web server (Tomcat by default).

---

### Spring Context
- Spring Context, represented by the `org.springframework.context.ApplicationContext` interface, is the heart of every Spring application. 
- It is the central container that manages the entire lifecycle of the application's objects (beans).

- *Responsibilities*
  - Inversion of Control Container
  - Dependency Injection
  - Bean Lifecycle Management
  - Resource Loading
  - Event Publishing

- `AnnotationConfigApplicationContext` takes one or more `@Configuration` classes as input and uses them to configure itself

- *Context Hierarchy*
  - **Root WebApplicationContext** --> it is the top level context, created by `ContextLoaderListener`
    - not aware of web layer.
    - holds beans that are shared across all the application.
  - **Servler WebApplicationContext** --> Each `DispatcherServlet` creates its own child context
    - This context is specific to the servlet and contains beans for the web layer, contains beans for the web layer, such as controllers, view resolvers, and interceptors. 
    - These beans can access beans from the root context but cannot see beans in other child contexts.


---

### Spring Boot Starter
- a pre-packaged dependency that aggregates a collection of other, related dependencies into a single, convenient unit. 
- When you add a starter to your pom.xml file, Maven or Gradle will automatically pull in all the necessary libraries for that particular feature (e.g., a web application, database access, security).
- The naming convention for all official starters is spring-boot-starter-*, where the asterisk (*) represents the specific technology or feature.

- *Example*
  ```
  <dependencies>
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
      </dependency>
  </dependencies>
  ```

- *Some Spring Starters* --> `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-security`, `spring-boot-starter-test`, `spring-boot-starter-actuator`

- **Parent Project**
  - `<parent>` tag in `pom.xml` is responsible for ensuring compatibilty of versions in Starters
  - The `spring-boot-starter-parent` defines a set of default dependencies and their versions. This is what provides the centralized version management.
  - when we include `spring-boot-starter-web` without a version, Maven knows to use the version specified by the parent project, ensuring all your starter dependencies are compatible with each other and with Spring Boot itself.

  ```
  <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>2.6.4</version>
  </parent>
  ```

---

### `spring.factories`
- `META-INF/spring.factories`
- contains all the auto-configuration classes to check
-  When the application starts, Spring Boot looks at this file, finds the auto-configuration classes, and applies the conditional logic to decide which ones to activate.

---

### Spring Boot App file Structure

    ```
    springboot-app/
    ├── .mvn/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/
    │   │   │       └── example/
    │   │   │           └── springbootapp/
    │   │   │               └── SpringbootApp.java  <-- The main entry point
    │   │   └── resources/
    │   │       ├── application.properties  <-- For configuration settings
    │   │       └── static/
    │   │       └── templates/
    │   └── test/
    │       └── java/
    │           └── com/
    │               └── example/
    │                   └── myfirstspringbootapp/
    │                       └── SpringbootAppTests.java
    ├── .gitignore
    ├── pom.xml                                           <-- Maven build file
    └── README.md

    ```

---

### Application Properties and YAML
- These files, located in the `src/main/resources` directory of your project, serve as the central hub for all your application's configuration settings.
- *they allow us to*,
  - Override defaults
  - Define Custom Properties
  - Configure Libraries

- You only need one of these files, and while `application.properties` is the default, `application.yml` (YAML) is often preferred for its clean, hierarchical syntax.

- **`application.properties` File**
  - This is the traditional, key-value format. Each property is on a new line.
  - *Syntax* --> `key=value`
  - *Example*
    ```
    # A comment
    server.port=8081
    app.name=My First Spring Boot App
    app.version=1.0.0
    app.welcome.message=Welcome to my application!
    ```

- **`application.yml` File**
  - YAML (YAML Ain't Markup Language) uses indentation to represent hierarchy, which makes it much cleaner and easier to read, especially for complex configurations.
  - *Syntax* --> `key: value`
    ```
    # A comment
    server:
        port: 8081
    app:
        name: My First Spring Boot App
        version: 1.0.0
        welcome:
            message: Welcome to my application!
    ```

---

### Injecting Properties into Code with `@Value`
- To access these properties in Java code, we use the `@Value` annotation. 
- This annotation is a core part of Spring and works in Spring Boot just as you'd expect. 
- It automatically injects the value of a configuration property into a field or method parameter.

- *Syntax*
  ```
  @Value("${app.welcome.message}")
  private String welcomeMessage;
  ```

---

### Spring MVC
- Spring MVC is a mature framework for building web applications and APIs. 
- It is based on the Model-View-Controller (MVC) design pattern and uses a central front controller, the `DispatcherServlet`.

- `DispatcherServlet` --> a special kind of servlet that acts as a central handler for all incoming web requests.
  - *request-response workflow*
    - **Request Reception** --> `DispatcherServlet` recieves an incoming request
    - **Handler Mapping** -->  It consults a `HandlerMapping` to determine which controller method (the "handler") is responsible for the request based on the URL and HTTP method.
    - **Handler Execution** --> It then uses a `HandlerAdapter` to invoke the selected controller method.
    - **Result Processing** --> After the method returns, the `DispatcherServlet` processes the return value. 
      - For a traditional web app, it uses a `ViewResolver` to find and render a view. 
      - For a REST API, it uses a `HttpMessageConverter` to serialize the return object (e.g., to JSON) directly into the response body.

---

### Web Controllers
- these components handle the interactions between the web framework and the applications's business logic.
- `@Controller` --> A stereotype annotation used for traditional MVC applications. Methods in this class typically return a String representing the name of a view template to be rendered.
- `@RestController` --> A specialized, more common annotation for building REST APIs. It's a convenience annotation that combines `@Controller` and `@ResponseBody`. This means that every method in a `@RestController` automatically serializes its return object directly to the response body, eliminating the need for view rendering.

---

### Overall Flow

- **Spring Boot Start** (spring context is initialized)
- **Auto-Configuration** (Spring MVC is configured)
- **Component Scanning** (Scans for beans)
- **Dependency Injection**
- **Request Arrives**
- **Handler Found**
- **Method Invocation**
- **Business Logic**
- **Response**


---

### Spring Expression Language (SpEL)
- SpEL is a powerful expression language that supports querying and manipulating an object graph at runtime

- *Syntax & Features*
  - An expression is enclosed in `#{...}`.

  - **Literal Expressions**
    - Strings: `#{ 'Hello World' }`
    - Numbers: `#{ 100 }, #{ 100.50 }`
    - Booleans: `#{ true }`
  
  - **Property Access** --> can access the properties of objects
    - `#{vehicleService.findAll()}` --> Calls the `findAll()` method on the `vehicleService` bean.
    - `#{vehicle.make}` --> Accesses the make property of a `vehicle` object.
  
  - **Method Invocation** --> can invoke methods on objects
    - `#{T(java.lang.Math).random()}` --> Calls a static method on a class. The `T()` syntax is used for static methods.
  
  - **Operators** --> supports common operators.
    - Arithmetic: `#{ 1 + 2 }, #{ 10 * 2 }`
    - Relational: `#{ 10 > 5 }, #{ vehicle.year < 2020 }`
    - Logical: `#{ true and false }, #{ not (10 > 5) }`