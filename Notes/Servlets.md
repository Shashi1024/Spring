> *Topics --> Servlets, . . .*\
> *Link to Codes --> [Codes](../Codes/)*


## Servlets
- a Java Servlet is a Java class that extends the capabilities of a server. 
- Servlets are primarily used to handle HTTP requests and generate HTTP responses. 
- Servlet is a Java program that runs inside a Servlet Container

### Working (HTTP Request-Response Model)

- **Client Request** --> a HTTP client sends an HTTP request to a web server
- **Web Server to Servlet Container** --> The web server receives the request and, if it's for a Java web application, forwards it to the configured Servlet Container (e.g., Tomcat).
- **Container Dispatches** --> The Servlet container looks at the request URL and maps it to a specific Servlet.
- **Servlet Processing**
  - the container creates or reuses a Servlet instance
  - It wraps the HTTP request and response into `HttpServletRequest` and `HttpServletResponse` objects, respectively.
  - It invokes the appropriate method on the Servlet instance (like `doGet()`, `doPost()`).
  - the Servlet reads data from the request and processes it
- **Container Sends Response** --> The Servlet container takes the generated response from the Servlet and sends it back to the web server.
- **Web Server to Client** --> The web server sends the HTTP response back to the client.


---

### Servlet Lifecycle
- Servlet lifecycle is managed by the Servlet Container

- **Loading and Instantiation**
  - the Servlet container loads the Servlet class (when the application starts or when the first request for the servlet arrives)
  - It creates an instance of the Servlet class using its no-argument constructor.

- **Initialization (`init()` method)**
  - after instantiation, container calls the `init(ServletConfig config)` exactly once for every Servlet instance
  - this is used for one-time setup tasks, database connections, resurces initializations, ....
  - `ServletConfig` object provides initialization parameters specific to this Servlet and access to the `ServletContext`

- **Request Handling (`service()` method)**
  - For every client request, the container calls the `service(ServletRequest req, ServletResponse res)` method.
  - for HTTP Servlets, the `HttpServlet` class overrides `service()` to dispatch the request to specific `doGet()`, `doPost()`, `doPut()`, `doDelete()`. (`service()` method simply calls these methods rather than processing the request by itself)

- **Destruction (`destroy()` method)**
  - when the Servlet container is shutting down, container calls the `destroy()` method(for cleanup tasks) exactly once for the Servlet instance.
  - after `destroy()` is called the Servlet instance is marked for garbage collection.

---

### Servlet API: Key Interfaces and Classes
- provides the interfaces and classes necessary to write Servlets.

- **`javax.servlet.Servlet` Interface**
  - all Servlets must implement this interface (contains fundamental lifecycle methods like, `init()`, `service()`, `destroy()`) 

- **`javax.servlet.GenericServlet` Abstract Class** (implements `Servlet` interface)
  - Provides default implementations for `init()`, `destroy()`, `getServletConfig()`, and `getServletInfo()`.
  - Leaves the `service()` method abstract, forcing subclasses to implement it.

- **`javax.servlet.http.HttpServlet` Abstract Class** (extends `GenericServlet`)
  - Overrides the `service()` method to dispatch requests to specific `doXxx()` methods based on the HTTP method

- **`javax.servlet.ServletRequest`, `javax.servlet.ServletResponse` Interfaces**
  - Represent the generic request and response objects. and Provide methods for reading input (parameters, headers) and writing output (text, binary data).

- **`javax.servlet.http.HttpServletRequest` Interface** 
  - Extends `ServletRequest` with HTTP-specific methods.

- **`javax.servlet.http.HttpServletResponse` Interface**
  - Extends `ServletResponse` with HTTP-specific methods.

- **`javax.servlet.ServletConfig` Interface**
  - Provides initialization parameters for a specific Servlet.

- **`javax.servlet.ServletContext` Interface**
  - Represents the web application itself. There's one `ServletContext` per web application.

---

### Deployment Descriptor (`web.xml`)
- Servlets are configured using a deployment descriptor file named `web.xml` (located in `WEB-INF/web.xml`).
- Spring Boot applications often avoid explicit web.xml (uses auto-configuration)
- *`web.xml` defines*
  - **Servlet Mappings** --> Which URLs map to which Servlet classes.
  - **Initialization Parameters** --> For Servlets and the entire web application.
  - **Welcome Files** --> Default pages to serve when a directory is requested.
  - **Error Pages** --> Custom pages to display for specific HTTP error codes.
  - **Listeners, Filters** --> Other components of the web application.

---

### Servlet Containers
- A Servlet Container (also known as a web container or web server) is a runtime environment that manages Servlets.
- *Responsible for*,
  - Loading and instantiating Servlets.
  - Managing the Servlet lifecycle (`init`, `service`, `destroy`).
  - Mapping incoming requests to the correct Servlet.
  - Providing the Servlet API objects (`HttpServletRequest`, `HttpServletResponse`).
  - Handling network communication (listening on ports, parsing HTTP).

---

### Spring & Servlet
- key component that bridges the gap between the Servlet world and Spring's IoC container is the `DispatcherServlet`.

- `DispatcherServlet` - Spring MVC's Front Controller
  - The `DispatcherServlet` is itself an `HttpServlet`
  - It's the single entry point for all incoming web requests in a Spring MVC application
  - The `DispatcherServlet` acts as a Front Controller. Instead of having many individual Servlets, all requests go through this one central Servlet.
  - When the `DispatcherServlet` is initialized, it creates its own `WebApplicationContext` (a specialized `ApplicationContext` for web applications).
  - This `WebApplicationContext` is where the Spring MVC controllers, services, repositories, and other web-specific beans are defined and managed.
  - This means the Spring beans are managed by Spring's IoC container, but the entire web application still starts from a standard Servlet.

---

### Listeners
- Java classes that react to specific events occurring within an application's lifecycle.
- *Registration*
  - **Spring Listeners** --> You register them by implementing the `ApplicationListener` interface or by annotating a method with `@EventListener`. Spring automatically detects and registers these.
  - **Servlet Listeners** --> For web applications, you register them in `web.xml` using `<listener>` tags, or in modern Servlet 3.0+ environments, you can use the `@WebListener` annotation on your listener class or register them programmatically.

---

### Filters
- Components that intercept requests and responses in a web application. They form a "chain of responsibility."
- They can inspect, modify, or even block incoming web requests before they reach the target servlet (like Spring's `DispatcherServlet`), and modify outgoing responses after the servlet has processed them but before they're sent back to the client.
- They're good for cross-cutting concerns like authentication, logging, or data compression that apply to many requests.
- *Registration*
  - You register them in `web.xml` using `<filter>` and `<filter-mapping>` tags, or in Servlet 3.0+ environments, using the `@WebFilter` annotation on your filter class or programmatic registration. Spring also provides `FilterRegistrationBean` for registering Servlet filters within a Spring context.

---

### Interceptors
- Spring-specific components that allow you to intercept requests within the Spring MVC framework.
- based on The Spring MVC Framework (specifically the `HandlerInterceptor` interface).
- They provide more granular control than filters. Interceptors operate after the `DispatcherServlet` has received the request but before the actual controller method executes, and also after the controller method but before the view is rendered, and after the response is fully committed. 
- *Registration*
  - You register them in a Spring `@Configuration` class by implementing the `WebMvcConfigurer` interface and overriding its `addInterceptors()` method.