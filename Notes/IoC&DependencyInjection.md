> *Topics: Inverison of Control, Dependency Injection, ...*\
> *Link to Codes --> [Codes](../Codes/)*


## Inversion of Control & Dependency Injection
- Spring basically is an Ioc Container

### Inversion of Control - The Principle
- *Concept* --> IoC is a design principle where the control of object creation and lifecycle management is inverted or transferred from our application code to a framework (in this case, Spring).
  - traditionally, we our code is responsible to create the objects it needs.
  - we don't create the objects; we describe how they should be created and configured, and the framework (Spring's IoC container) takes care of the actual instantiation, configuration, and assembly of our objects. 
  - The framework calls our code, rather than our code calling the framework.

  -  The "**inversion**" refers to the shift in responsibility. Instead of your code actively "**pulling**" dependencies, the framework "**pushes**" them into your code.

---

### Dependency Injection - The Pattern
- *Concept* --> DI is a specific implementation of the IoC principle. It's the mechanism by which the IoC container "injects" (provides) the dependencies of an object into it, rather than the object creating or looking up its own dependencies.

- **Dependencies** --> These are simply objects that another object needs to function. 
- we declare the dependencies an object needs (e.g., via a constructor, setter method, or field), and the Spring container automatically finds and provides those dependencies when it creates the object.

---

### IoC Containers (`BeanFactory` & `ApplicationContext`)
- spring provides two types of IoC Containers

#### `BeanFactory` (The Low-Level Container)
- *Concept* --> The `org.springframework.beans.factory.BeanFactory` interface represents the simplest IoC container. It provides basic dependency injection capabilities.
- *Characteristics*
  - Lazy Initializations
  - Primarily focuses on managing bean definitions and providing instances.

- `BeanFactory` is the foundational interface. It defines methods like `getBean()`, `containsBean()`, etc. It's the bare minimum for an IoC container.


#### `ApplicationContext` (The Feature-Rich Container)
- *Concept* --> The `org.springframework.context.ApplicationContext` is a sub-interface of `BeanFactory` and most commonly used IoC container in Spring. It extends `BeanFactory` with enterprise-specific features.
- *Characteristics*
  - **Eager Initialization (Default)** --> All singleton beans are instantiated and configured when the `ApplicationContext` starts up. This ensures that any configuration issues are detected early.
  - **Message Source Handling** --> Provides internationalization (i18n) capabilities.
  - **Event Publishing** --> Supports event-driven programming (Observer pattern), allowing beans to communicate via events.
  - **Resource Loading** --> Can load resources from various locations (classpath, file system, URL).
  - **AOP Integration** --> Seamless integration with Spring AOP.
  - **Web Application Support** --> Specific implementations for web environments (e.g., `WebApplicationContext`).
  - **Auto-detection of `BeanPostProcessors` and `BeanFactoryPostProcessors`** --> Automatically detects and registers special beans that can modify other bean definitions or bean instances.

---

### Bean Configuration Styles

#### Manual Instantiation (The Problem Spring Solves)
- Before Spring, we used to manually create objects and manage their dependencies.
- Directly using the `new` keyword to create objects and then manually passing their dependencies.


#### XML-based Configuration
- Bean definitions and their dependencies are declared in XML files. The Spring container reads these XML files to build the application context.
- You use `<bean>` tags to define beans, and `<property>` or `<constructor-arg>` tags to inject dependencies.

- provides centralized configuration
- No need to recompile Java code to change bean wiring.

- *Cons*
  - lack of type safety (XML parsing errors are runtime errors)


#### Java-based Configuration (`@Configuration`, `@Bean`)
- preferred way to configure Spring applications (provides type safety)
- Bean definitions are declared using Java classes annotated with `@Configuration` and methods annotated with `@Bean`.
- `@Configuration` classes are treated as sources of bean definitions. Methods annotated with `@Bean` within these classes are responsible for instantiating, configuring, and returning bean instances.

- *Cons*
  - Requires recompilation for changes.

---

### Dependency Injection Types
- three ways to inject dependencies into beans

#### Constructor Injection
- Dependencies are provided as arguments to the bean's constructor.
- Spring calls the appropriate constructor (often the one annotated with `@Autowired` if there are multiple, or the only one if there's just one) and passes the required dependencies.
- *Pros*
  - Immutability (if `final` is used)
  - Exlicit dependencies --> clearly states what an object need to function
  - Guaranteed State --> ensures that an object is created with all its mandatory dependencies, preventing `NullPointerExceptions` later.

- *Cons*
  - can lead to "Constructor Hell" (Too many arguments) if a class has too many dependencies


#### Setter Injection
- Dependencies are provided by calling setter methods on the bean after it has been instantiated.
- Spring creates an instance of the bean using its default constructor, then calls the setter methods annotated with `@Autowired` (or configured in XML) to inject the dependencies
- *Pros*
  - Optional Dependencies --> suitable for dependencies that are not strictly required (or that can be changed).

- *Cons*
  - Mutable state
  - Partial Initialization (may lead to `NullPointerException`)


#### Field Injection
- Dependencies are injected directly into private fields using the `@Autowired` annotation.
- Spring uses reflection to set the value of the annotated field directly, bypassing constructors and setters.
- *Cons*
  - hides dependencies
  - No Immutability --> Fields cannot be `final`

---

### Bean Scopes
- defines the lifecycle and visibility of a bean instance within the Spring IoC container.
- we Use the `@Scope` annotation or the `scope` attribute in XML configuration to define scope
- **Default Scope** --> `singleton`

#### `singleton` (Default)
- *Concept* --> Only one single instance of the bean is created per Spring IoC container. All requests for that bean will return the exact same instance.
- *LifeCycle* --> Created when the `ApplicationContext` starts (eagerly by default) and destroyed when the context closes.


#### `prototype`
- *Concept* --> A new instance of the bean is created every time it is requested from the Spring IoC container.
- *Lifecycle* --> Created on demand. Spring manages the creation but does not manage the complete lifecycle (e.g., destruction callbacks are not guaranteed unless you manually register a `BeanPostProcessor`). You are responsible for cleaning up prototype-scoped objects.


#### Web-Aware Scopes (Only applicable in a web environment)
- avaiable only while using a `WebApplicationContext`
- `request` --> A new instance of the bean is created for each HTTP request.
- `session` --> A new instance of the bean is created for each HTTP session.
- `application` --> A single instance of the bean is created for the entire web application's lifecycle (similar to a servlet `ServletContext` attribute).
- `websocket` --> A single instance of the bean is created for the lifecycle of a WebSocket session.

---


### Bean Lifecycle
- *Sequence of Events*,
  - **Instantiation** --> The bean is instantiated
  - **Dependency Injection** --> (via constructor, setter, or field injection).
  - **Bean Name Awareness (Optional)** --> giving access to its own ID/name in the container
  - **Bean Factory Awareness (Optional)** --> giving it a reference to the BeanFactory that created it.
  - **Application Context Awareness (Optional)** --> giving it a reference to the `ApplicationContext` that created it.
  - **Bean Post Processors (Before Initialization)**
  - **Initialization**
    - If the bean implements the `InitializingBean` interface, Spring calls its `afterPropertiesSet()` method.
    - If a custom `init-method` is specified in the bean definition (XML) or a method is annotated with `@PostConstruct` (Java config), that method is called.
  - **Bean Usage**
  - **Destruction**
    - `ApplicationContext` Shutdown --> When the `ApplicationContext` is closed (or the JVM shuts down gracefully).
    - `@PreDestroy` --> If a method is annotated with `@PreDestroy`, it's called.
    - `DisposableBean` Callback --> If the bean implements `org.springframework.beans.factory.DisposableBean`, its `destroy()` method is called.
    - Custom `destroy-method`: If a custom `destroy-method` is specified in configuration, that method is called.