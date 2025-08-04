> *Topics: Design Patterns, ...*\
> *Link to Codes --> [Codes](../Codes/DesignPatterns/)*

## Design Patterns
- Design patterns are reusable solutions to common problems in software design

### Singleton Pattern
> *Link to Codes --> [Codes](../Codes/DesignPatterns/SingletonPattern.java)*

- *Concept* --> Ensures a class has only one instance and provides a global point of access to it.
- *Working* --> Typically involves a private constructor to prevent direct instantiation, a static method to return the single instance, and often lazy initialization.

- *Usage in Spring*
  - **Default Bean Scope** --> By default, all Spring Beans are singletons. 
    - for a given bean definition, the Spring IoC container creates only one shared instance, and all requests for that bean will return the same instance.

---

### Factory Pattern
> *Link to Codes --> [Codes](../Codes/DesignPatterns/FactoryPattern.java)*

- *Concept* --> Provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created.
  - Instead of directly instantiating objects using the `new` keyword, you delegate this responsibility to a "factory" object. This factory then decides which concrete class to instantiate based on certain criteria, and returns an instance of the appropriate class, typically adhering to a common interface or abstract class.

  - rather than directly creating the objects, we delegate that task to some other factory object.

- *Types*
  - **Simple Factory (or Static Factory Method)**
    - A single class (the "Simple Factory") has a static method that takes an argument.
    - Based on this argument, it uses `if-else` or `switch` statements to decide which concrete product to instantiate and return.
    
  - **Factory Method Pattern (Virtual Constructor)**
    - defines an interface for creating an object, but lets subclasses decide which class to instantiate.
    - consider a creator interface or abstract class, it has an abstract method, this must be implemented by the subclasses to create and return the objects of required type.
    - here the creator may contain some logic/methods to operate on the created instances.

  - **Abstract Factory Pattern (Factory of Factories)**
    - It provides an interface for creating families of related or dependent objects without specifying their concrete classes. It's essentially a factory that creates other factories.

- *Usage in Spring*
  - **Implicit Factory** -->Spring's IoC container acts as a sophisticated factory. 
  - **`FactoryBean` Interface** --> Spring provides the `FactoryBean` interface, which allows you to plug in custom factory logic. If a bean implements `FactoryBean`, Spring will use its `getObject()` method to create the actual bean instance.

---


### Proxy Pattern
> *Link to Codes --> [Codes](../Codes/DesignPatterns/ProxyPattern.java)*

- *Concept* --> Provides a surrogate or placeholder for another object to control access to it.
  - The proxy object has the same interface as the real object. When a method is called on the proxy, the proxy performs its additional logic and then delegates the call to the real object.

- *Usage in Spring*
  - **Spring AOP** heavily relies on the Proxy pattern. 
  - **Runtime Weaving** --> When a method is called on the proxied bean, the proxy intercepts the call, executes the advice defined in the aspect, and then proceeds to call the actual method on the target object.
  - **Types of Proxies** --> Spring uses JDK dynamic proxies (for interfaces) and CGLIB proxies (for classes without interfaces).

---

### Observer Pattern
> *Link to Codes --> [Codes](../Codes/DesignPatterns/ObserverPattern.java)*

- *Concept* --> Defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
  - A subject maintains a list of its observers and notifies them of state changes, usually by calling one of their methods.

- *Usage in Spring*
  - **Spring Event Handling**

---

### Strategy Pattern
> *Link to Codes --> [Codes](../Codes/DesignPatterns/StrategyPattern.java)*

- *Concept* --> Defines a family of algorithms, encapsulates each one, and makes them interchangeable. The strategy lets the algorithm vary independently from clients that use it.
  - Allows an object to change its behavior dynamically at runtime by swapping out different algorithms. Avoids large conditional statements.
  - An interface defines the common algorithm. Concrete strategy classes implement this interface. The client holds a reference to the strategy interface and uses it to execute the algorithm.

- *Usage in Spring*
  - **Dependency Injection for Strategies** --> we define an interface for our strategy, multiple implementations, and then inject the desired implementation into our client.

---

### Template Method Pattern
> *Link to Codes --> [Codes](../Codes/DesignPatterns/TemplatePattern.java)*

- *Concept* --> Defines the skeleton of an algorithm in the superclass but lets subclasses override specific steps of the algorithm without changing its structure.
  - An abstract class defines the template method (the algorithm's skeleton) and abstract methods for the steps that subclasses must implement. It can also include concrete methods for common steps.

- *Usage in Spring*
  - `JdbcTemplate`, `RestTemplate`, `JmsTemplate`, `HibernateTemplate` --> these are examples of template classes in Spring



