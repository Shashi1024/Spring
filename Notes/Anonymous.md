> *Topics --> Statefull, Stateless, . . .*\
> *Link to Codes --> [Codes](../Codes/)*


### Stateless (Thread-safe by default)
- stateless object or system does not hold onto any information from a previous interaction. 
- Each request or interaction is treated as a completely new one, with no memory of past requests.

- *In Spring*, Most Spring beans, especially `@Service` and `@Repository` beans, are designed to be stateless. This is because, by default, Spring beans are singletons. If a singleton bean held state, it could lead to concurrency issues.
- Stateless is the default and preferred approach for most components. This is why `@Service` and `@Repository` beans are singletons and designed without instance-level fields that hold user-specific data.

---

### Statefull (Not thread-safe by default)
- statefull object or system retains information from a previous interaction and uses it for future interactions. This state is maintained over time.

- *In Spring*, the beans with `prototype`, `request`, `session` scopes are the best examples of statefull beans
- Statefull is used when a component's lifecycle is tied to a specific context, like a user's HTTP session (`@Scope("session")`) or a single request (`@Scope("request")`), and you need to store temporary, contextual data.


---

### RESTful APIs
- **REST (Representational State Transfer)** is an architectural style for designing networked applications. 
- It's not a protocol or a standard but a set of guiding principles for building services that are scalable and loosely coupled.

- *Principles*
  - **Resources**
    - Everyting is a resource
    - a resource is an object that can be identified by a URI (uniform resource identifier)
    - Ex. `http://api.copart.com/vehicles/`, `http://api.copart.com/vehicles/12345`

  - **Uniform Interface**
    - relies on a uniform, consistent interface for interacting with resources, defined by standard HTTP methods,
      - `GET` --> Read
      - `POST` --> Create
      - `PUT` --> Update
      - `DELETE` --> Delete

  - **Statelessness**
    - sever must not store any client state between requests
    - each request from the client must contain all the information required to process it.

  - **Client-Server**
    - client and server are distinct and separate.

---

### URI vs. URL