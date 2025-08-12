> *Topics --> Aspect Oriented Programming, . . .*\
> *Link to Codes --> [Codes](../Codes/)*


## Aspect Oriented Programming
- the functionalities (other than business logic) that are needed across different parts of the code, such as logging, security, and transaction management are called as **Cross-Cutting Concerns**(as they "cut across" multiple layers and modules of the application).
- AOP allows us to write this logic in one place (an aspect) and then declaratively apply it to all the methods that need it.

- *Core Concepts*
  - **Aspect**
    - it is an modular unit of a cross-cutting concern
    - it is a class that encapsulates a specific functionality.
    - a class is designated as an aspect using `@Aspect` annotation.

  - **Join Point**
    - a join point is a point in the execution of the a program where we can "join" an aspect.
    - in spring AOP, a join point is almost always a method execution.

  - **Advice**
    - it is an action taken by an aspect at a specific join point.
    - it is the actual code taht runs
    - it can be applied at different stages of method execution.
    - *Types*
      - `@Before` --> runs before join point method executes
      - `@After` --> runs after the join point method finishes, regardless of whether it succeeded or threw an exception
      - `@AfterReturning` --> runs after the join point method successfully returns (without throwing an exception)
      - `@AfterThrowing` --> runs after the join point method throws an exception.
      - `@Around` --> it surrounds the join point method, allowing us to perform actions both before and after the method call.
        - we can also choose to prevent the original method from running.

  - **Pointcut**
    - it is a predicate or expression that defines which hoin points should be targeted by a particular piece of advice. (defines "where" AOP logic)
    - these are written using a specific expression language.
    - often defined using `@Pointcut` annotation

    - *Pointcut expression syntax*
      - `execution(modifiers-pattern? return-type-pattern declaring-type-pattern? method-name-pattern(param-pattern) throws-pattern?)`
        - `*` --> A wildcard that matches any return type, any method name, or any class.
        - `..` --> A wildcard for matching any number of parameters or any sub-packages.
    
      - `@annotation(com.copart.MyAnnotation)` --> Matches any method that is annotated with `@MyAnnotation`. This is a powerful way to create custom, declarative behaviors.
      - `within(com.copart..*)` --> Matches any method within the specified package and its sub-packages. This is a simpler alternative to `execution`.
      - `args(String, ..)` --> Matches any method that accepts a `String` as its first argument, followed by any other arguments.


    - *examples*,
      - `execution(* com.copart.service.*.*(..))` --> This reads as: "Execute the advice on any method (`*`) in any class (`*`) within the `com.copart.service` package, with any number of parameters (`..`)."
      - `within(com.copart.service.*)` --> Applies to any method within the specified package.
      - `@annotation(org.springframework.transaction.annotation.Transactional)` --> Applies to any method that is annotated with `@Transactional`.


  - **Weaving**
    - it is the process of linking aspects to the application code.
    - Spring AOP performs this weaving at runtime using dynamic proxies
    - when a method is called on a bean that has an aspect applied to it, spring intercepts that call with a proxy object. the proxy executes the advice and then delegates the call to the original method.

    - *Types*
      - **Compile-time weaving** --> Aspects are woven into bytecode during compilation.
      - **Load-time weaving (LTW)** --> Aspects are woven when the class is loaded by the JVM.
      - **Runtime weaving** --> (***Spring AOP uses this**) it creates dynamic proxy for each bean that has an aspect applied to it. the proxy intercepts method calls and applies the advice at runtime.

---

### Use cases of AOP in Spring
 - Declarative Transaction Management (`@Transactional`)
 - Security (`@PreAuthorize`, `@PostAuthorize`)
 - Logging and Auditing
 - Caching(`@Cacheable`)


---

### The `@AspectJ` Weaving Mechanism
- Spring AOP framework uses `@AspectJ` style, it relies on annotaions like `@Aspect`, `@Before`, to enable this mechanism, we must explicitly tell speing to create dynamic proxies for beans that have aspects applied to them.
- `@EnableAspectJAutoProxy` --> it is typically placed on a spring `@Configuration` class, to activate spring's AOP capabilities.
  - it tells "Scan the app of `@Aspect` classes and for any bean that has a method matching an aspect's pointcut, create a proxy"
  - in spring boot app, this annotation is automatically included as part of the `@SpringBootApplication` annotation.


---

### Accesing the `JointPoint` Context
- Spring provides two key objects to get the information about the mehtod an advice is intercepting.

- `JointPoint`
  - the `JointPoint` object is available for all the advice types (`@Before`, `@After`, `@AfterReturning`, `@AfterThrowing`)
  - it provides basic metadata about the method execution

  - *Methods*
    - `getSignature()` --> return the method signature, including the name and parameters.
    - `getTarget()` --> returns the target object that the method is being executed on
    - `getArgs()` --> return the arguments that were passed to the intercepted method.

- `ProceedingJointPoint`
  - this object is a sub-interface of `JointPoint` and is only availabe for `@Around` advice.
  - it gives us control whether the original method should be executed.

  - *Methods*
    - `proceed()` --> this method executes the original, underlying method (can be called with or without arguments)
      - without `proceed()` the original method is never called.

---

### Controlling Advice Execution Order
- when multiple aspects are configured to run at the same hoin point, the order of execution can be controlled using `@Order` annotation.

- `@Order` --> it is placed on the aspect class itself. the lower the number, the higher the precedence