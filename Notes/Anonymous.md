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