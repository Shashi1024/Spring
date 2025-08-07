> *Topics --> Spring Boot, . . .*\
> *Link to Codes --> [Codes](../Codes/)*

## Fundamentals

### Principles of Spring Boot

- **Convention over Configuration**
  - In Spring Boot, the framework observes what dependencies you've included in your project and automatically configures the application based on those conventions.
  - there is no need to write boilerplate code in XML or Java configuration files.


- **Auto Configuration**
  - Auto-configuration is the technical implementation of "Convention over Configuration.
  - Spring Boot's auto-configuration looks at the dependencies on your classpath.
  - think of auto-configuration as a set of conditional bean declarations. It says, "If this class is on the classpath and a DataSource bean doesn't already exist, then create and register one for me."


- **Embedded web Servers**
  - When you create a Spring Boot application, it's packaged as a single, executable JAR (Java aRchive) file. This JAR file contains all your application code, all its dependencies (including Spring and other libraries), and an embedded web server (Tomcat by default).



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

### Injecting Properties into Code with `@Value`
- To access these properties in Java code, we use the `@Value` annotation. 
- This annotation is a core part of Spring and works in Spring Boot just as you'd expect. 
- It automatically injects the value of a configuration property into a field or method parameter.

- *Syntax*
  ```
  @Value("${app.welcome.message}")
  private String welcomeMessage;
  ```