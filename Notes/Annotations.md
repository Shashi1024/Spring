> *Topics --> Annotations, . . .*\
> *Link to Codes --> [Codes](../Codes/Annotations/)*


## Annotations
- an annotation is a form of metadata that you can add to Java source code. (Data about Data)
- they dont directly effect the flow of execution, but provide info that can be used by tools, compiler, or runtime env.
- they start with `@` symbol.

- *Characteristics*
  - Metadata
  - Non-intrusive
  - Processed by tools/frameworks

- *Use of Annotations in Spring* (**Convention Over Configuration**)
  - **Component Scanning** --> Automatically discovers and registers Spring-managed components (beans).
  - **Dependency Injection** --> Automatically wires dependencies between beans.
  - **Configuration** --> Defines configuration classes and bean definitions.
  - **Aspect-Oriented Programming** --> Marks methods for transactional behavior, security, logging, etc.
  - **Web Development** --> Maps web requests to controller methods, handles request parameters, etc.
  - **Data Access** --> Defines repositories and handles database interactions.


### Registration
- registration is the process of telling the underlying environment (like a web server) or the Spring container about your custom classes (listeners, filters, interceptors, or even your own Spring beans)
- It's how you "plug in" your custom logic so that the framework knows to instantiate your class and invoke its methods at the appropriate times during the application's lifecycle or request processing.

### Directives
- what are directives?
- Annotation is an example of directives
- a way to describe instructions or commands given to the framework.


### Types of Spring Annotations
- usually categorized by their purpose

#### Core Spring Annotations (Stereotype Annotations)
- These annotations mark a class as a Spring-managed component (a "bean"). 
- They are specializations of `@Component` and indicate a specific role in the application's architecture.

- `@Component`
  - A generic stereotype for any Spring-managed component. It's the most basic annotation for marking a class as a bean.
  - If a class doesn't fit into any other specific stereotype (like service, repository, or controller), we can use `@Component`.

- `@Service`
  - Marks a class as a service layer component. Services typically encapsulate business logic and often coordinate calls to multiple repositories.
  - it is similar to `@Component` but adds a semantic meaning

- `@Repository`
  - Marks a class as a data access layer component. Repositories handle interactions with a database or other data sources.
  - along with semantic meaning, it provides Exception Translation (databases specific exceptions are converted to Spring consistent exceptions)

- `@Controller`
  - Marks a class as a web layer component, typically used in Spring MVC applications to handle incoming web requests
  - Applied to classes that serve as request handlers for web endpoints.

- `@Configuration`
  - Marks a class that declares one or more `@Bean` methods.

- `@Bean`
  - Annotates a method within a `@Configuration` class. The method's return value will be registered as a bean in the `ApplicationContext`. The bean's name defaults to the method name.

- `@ComponentScan`
  - Used to configure component scanning. Spring will look for classes annotated with stereotypes (`@Component`, `@Service`, etc.) in the specified packages and register them as beans.


#### Dependency Injection Annotations
- used to automatically inject dependencies into beans

- `@Autowired`
  - most common annotation for automatic dependency injection
  - Spring automatically finds a matching bean in the application context and injects it.
  - Can be applied to constructors, setters, fields
  - *Working*
    - Spring performs "autowiring by type" by default. It looks for a bean of the type required by the field, constructor parameter, or setter method. 
    - If multiple beans of the same type exist, it might throw an error or require further disambiguation.

- `@Qualifier("beanName")`
  - Used in conjunction with `@Autowired` to resolve ambiguity when multiple beans of the same type are available. It specifies the exact bean by its ID.

- `@Value("${property.name}")`
  - Injects values from properties files (`.properties`), environment variables, or other configuration sources directly into fields or method parameters.


#### Lifecycle Annotations
- These allow us to define methods that should be called at specific points in a bean's lifecycle (after initialization, before destruction).

- `@PostConstruct` (from `javax.annotation`)
  - Marks a method to be executed after the bean has been constructed and all dependencies have been injected. Useful for initialization logic.

- `@PreDestroy` (from `javax.annotation`)
  - Marks a method to be executed before the bean is destroyed by the Spring container. Useful for cleanup logic


#### Other Annotations
- `@Scope("prototype")` or `@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)`
  - Defines the scope of a Spring bean. By default, beans are `singleton` (one instance per container). `prototype` means a new instance is created every time it's requested.
  - `request`, `session`, `application`, these can be used for web applications.

- `@Primary`
  -  Used when multiple beans of the same type exist, but you want to designate one as the primary candidate for autowiring when no specific qualifier is given.




### Working
- Spring employs reflection at runtime.
- **Component Scanning** --> When you configure Spring to scan a package, it iterates through the classes in that package.
- **Annotation Detection** --> For each class, Spring uses reflection to check if it's annotated with `@Component` or one of its specializations (`@Service`, `@Repository`, `@Controller`, `@Configuration`).
- **Bean Definition** --> If an annotation is found, Spring creates a bean definition for that class, effectively telling the IoC container, "Hey, I need to manage an instance of this class."
- **Dependency Resolution (`@Autowired`)** --> When Spring creates an instance of a bean, it then looks for `@Autowired` annotations on its constructors, fields, or setters. Using reflection, it identifies the required type and then searches its application context for a matching bean to inject.



### `BeanPostProcessor`
- it is a special bean that can intercept the creation of other beans and perform additional logic on them, both before and after they are initialized. 
- Spring's core functionality is built upon several internal `BeanPostProcessor` implementations.

- *Annotation Processing*
  - **Component Scanning** --> When we use `@ComponentScan`, Spring uses an internal `BeanPostProcessor` to scan the classpath for classes with stereotype annotations (`@Component`, `@Service`, etc.). It then registers a `BeanDefinition` for each of them.

  - **Dependency Injection** --> When a bean is instantiated, another internal `BeanPostProcessor` called `AutowiredAnnotationBeanPostProcessor` looks at all of its fields, constructors, and setters. It finds those annotated with `@Autowired` and, using reflection, injects the correct bean instances into them. This is how the "wiring" happens.

  - **Lifecycle Hooks** --> A `BeanPostProcessor` named `CommonAnnotationBeanPostProcessor` looks for methods annotated with `@PostConstruct` and `@PreDestroy`. It executes the `@PostConstruct` methods after a bean's properties have been set, and it executes the `@PreDestroy` methods just before the bean is destroyed.



### Custom Annotations
- **Define the Annotation** --> Use the `@interface` keyword to define a new annotation.

- **Add Meta-Annotations** --> Use meta-annotations to define how your annotation will be used:
  - `@Retention(RetentionPolicy.RUNTIME)` --> This is crucial. It tells the JVM to make the annotation available at runtime so that Spring's processors can read it.
  - `@Target({ElementType.TYPE, ElementType.METHOD, ...})` --> Specifies where your annotation can be applied (e.g., on a class, method, field).
  - `@Documented` --> (Optional) Indicates that the annotation should be included in Javadoc.

- **Add Attributes** (Optional) --> You can define attributes in your annotation, similar to an interface's methods. These can be used to pass metadata to your processor.

- To make custom annotation do something, we need to write a `BeanPostProcessor` that looks for it and performs a specific action. This is a direct application of the Proxy design pattern.