> *Topics --> Spring Application Workflow, . . .*\
> *Link to Codes --> [Codes](../Codes/)*

## Spring Application Flow

### Phase 1: Application Startup
- **Application Entry Point**
  - For standalone applications, a main method (like App.java) is the entry point.
  - For web applications, this is initiated by the web server (e.g., Tomcat) deploying the application.

- **Spring `ApplicationContext` Initialization**
  - to create and initialize the Spring `ApplicationContext` (the IoC Container).
  - This is done by providing Spring with configuration metadata (e.g., XML files like `applicationContext.xml`, Java `@Configuration` classes like `AppConfig`, or component scan directives).
  - If it's a web application, a `ServletContextListener` (like Spring's `ContextLoaderListener`) often triggers this.

- **Component Scanning and Bean Definition Loading**
  - The `ApplicationContext` scans the specified packages (via `@ComponentScan`) for classes annotated with Spring stereotypes (`@Component`, `@Service`, `@Repository`, `@Controller`, `@Configuration`).
  - It also processes any explicit `@Bean` methods in `@Configuration` classes.
  - For each identified component or `@Bean` method, Spring creates a Bean Definition. This definition is a blueprint containing information about the bean's class, scope, dependencies, and lifecycle callbacks.

- **Bean Instantiation and Dependency Injection (DI)**
  - Based on the bean definitions, Spring starts instantiating the beans.
  - When a bean is instantiated, Spring identifies its dependencies (e.g., through `@Autowired` on constructors, setters, or fields).
  - The IoC container then automatically provides (injects) the required dependent beans into the newly created bean. This is the core of Dependency Injection. Spring ensures all dependencies are met before a bean is fully ready for use.
  - This process happens recursively: if `ServiceA` depends on `RepositoryB`, Spring first instantiates and wires `RepositoryB`, then uses that fully initialized `RepositoryB` instance to inject into `ServiceA`.

- **Bean Lifecycle Callbacks (Initialization)**
  - After a bean is instantiated and its dependencies are injected, Spring invokes any initialization callbacks.
  - These can be methods annotated with `@PostConstruct` or methods specified via `init-method` in XML/Java config.
  - This is where beans can perform any necessary setup (e.g., validating properties, populating caches, starting background threads).

- **`ApplicationContext` Ready**
  - Once all singleton beans are instantiated, wired, and initialized, the `ApplicationContext` is fully started and ready to serve requests for beans.


### Phase 2: Application Execution & Request Processing
- **Application Logic Invocation**
  - application code (e.g., from a `main` method, a web controller, or a message listener) requests a bean from the `ApplicationContext`.
  - The retrieved bean is a fully configured and initialized instance, ready to perform its business logic.

- **Interaction with Managed Beans**
  - application code interacts with the retrieved beans, which in turn interact with their injected dependencies (e.g., `customerService.addCustomer(...)` calls `customerRepository.save(...)`)

- **Web Request Flow**
  - If it's a web application, the HTTP request then enters the web-specific pipeline (Servlet Filters -> `DispatcherServlet` -> Spring MVC Interceptors -> Controller method -> Spring MVC Interceptors -> Servlet Filters -> Response).
  - Within the Controller, it interacts with the core service beans managed by the `ApplicationContext`.



### Phase 3: Application Shutdown
- **`ApplicationContext` Closure**
  - For standalone applications, this happens when the `main` method finishes or `context.close()` is explicitly called.
  - For web applications, this happens when the web server is shut down or the application is undeployed.

- **Bean Lifecycle Callbacks (Destruction)**
  - Before destroying beans, Spring invokes any destruction callbacks.
  - These can be methods annotated with `@PreDestroy` or methods specified via `destroy-method` in XML/Java config.
  - This is where beans can perform cleanup (e.g., closing database connections, releasing file handles, stopping threads).

- **Bean Destruction**
  - Spring removes the beans from the container.




## Spring Web Request WorkFlow
- **Client Sends an HTTP Request**
  - The process begins when a client (e.g., a web browser) sends an HTTP request to your server's URL

- **Web Server Receives the Request**
  - The web server (like Tomcat or Jetty) receives the request and forwards it to the correct web application based on the URL. 
  - The request is now a `HttpServletRequest` object and the response is a `HttpServletResponse` object.

- **Servlet Filters Intercept (Pre-processing)**
  - The request first enters the filter chain. This is a sequence of Servlet filters that we or Spring have configured.
  - Each filter's `doFilter()` method is invoked.
  - A filter can inspect, modify, or even block the request.
  - commonly used for Authentication, logging, Request Encoding.
  - Once a filter's logic is complete, it calls `chain.doFilter(request, response)` to pass the request to the next filter in the chain. If this call is skipped, the request processing stops.

- **Request Reaches the `DispatcherServlet`**
  - After passing through the filter chain, the request reaches the `DispatcherServlet`. This is Spring MVC's central front controller. It's a single, powerful servlet that handles all incoming requests for the application and delegates them to the appropriate components.

- **`DispatcherServlet` Delegates to the Handler**
  - it figures out which code should handle this request.
  - It consults its configured `HandlerMapping` to find a matching handler for the request URL and HTTP method 
  - The handler is typically a method in one of the `@Controller` classes.

- **Spring MVC Interceptors Intercept (`preHandle`)**
  - Before the selected handler method is executed, the `DispatcherServlet` runs any configured Spring MVC Interceptors.
  - These are more fine-grained than filters. They're part of the Spring framework and have access to the specific handler method that's about to be invoked
  - The interceptor's `preHandle()` method is called.
  - If `preHandle()` returns `false`, the request processing stops, and the controller method is not executed. This is useful for method-specific authorization.

- **Controller Method Executes**
  - The `DispatcherServlet` finally invokes the identified controller method (e.g., `getCustomerById(@PathVariable("id") int id)`)
  - The method's parameters are automatically populated with data from the request
  - Inside this method, the core business logic is executed. This is where we would call your `@Service` beans, which in turn use your `@Repository` beans to interact with the database.

- **Spring MVC Interceptors Intercept (`postHandle`)**
  - After the controller method successfully executes, the `DispatcherServlet` calls the `postHandle()` method of any configured interceptors.
  - This stage is useful for post-processing the result before the view is rendered, such as adding common data to the model.
  - This method is not called for REST APIs, as there is no view to render.

- **View Resolution and Rendering (for MVC applications)**
  - If your application is returning an HTML page, the `DispatcherServlet` uses a `ViewResolver` to find the correct view template (e.g., Thymeleaf, JSP).
  - The view is then rendered, combining the template with any data returned from the controller.
  - For REST APIs, this step is skipped. The controller's return value is typically converted to JSON or XML directly.

- **Spring MVC Interceptors Intercept (`afterCompletion`)**
  - After the view has been rendered (or the response has been committed for REST APIs), the `afterCompletion()` method of the interceptors is called.
  - This stage is ideal for cleanup tasks and logging final results or any exceptions that occurred.

- **Servlet Filters Intercept (Post-processing)**
  - The response now travels back up the filter chain in reverse order.
  - Filters can perform post-processing tasks on the response, such as compressing the data or adding final headers.

- **Response Sent to Client**
  - Finally, the web server sends the complete HTTP response back to the client.

- **Flow Diagram**
    ```

    +---------------------+
    |     Web Server      |
    |    (e.g., Tomcat)   |
    +----------+----------+
            |
            | HTTP Request (GET /api/customers/123)
            |
            v
    +---------------------+
    |  Servlet Filters    |  
    |  (e.g., Security, Logging)
    |  - Pre-processing
    |  - Calls `chain.doFilter()`
    +----------+----------+
            |
            v
    +---------------------+
    |  DispatcherServlet  |
    |  (Spring Front Controller)
    +----------+----------+
            |
            v
    +---------------------+
    |   HandlerMapping    |
    |  (Finds @Controller method)
    +----------+----------+
            |
            v
    +---------------------+
    | Spring MVC Interceptors |
    |      (preHandle)        |
    |  - Method-specific pre-processing
    +----------+----------+
            |
            v
    +---------------------+
    |    Controller Method   |  
    |  - Your business logic starts here
    |  - Calls @Service, @Repository
    +----------+----------+
            |
            v
    +---------------------+
    | Spring MVC Interceptors |
    |      (postHandle)       |
    |  - Modifies response before rendering
    +----------+----------+
            |
            v
    +---------------------+
    |     View Resolution &   |
    |     Rendering       |
    |  - Converts data to HTML, JSON, etc.
    +----------+----------+
            |
            v
    +---------------------+
    | Spring MVC Interceptors |
    |     (afterCompletion)   |
    |  - Final cleanup, logging
    +----------+----------+
            |
            v
    +---------------------+
    |  Servlet Filters    |
    |  - Post-processing
    |  - Response travels back up the chain
    +----------+----------+
            |
            | HTTP Response (200 OK, JSON body)
            |
            v
    +---------------------+
    |      Client         |
    +---------------------+

    ```