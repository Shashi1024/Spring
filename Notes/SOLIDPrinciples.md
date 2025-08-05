> *Topics: SOLID Principles, . . .*

## SOLID Principles
- SOLID is an acronym for five design principles intended to make software designs more understandable, flexible, and maintainable.

### Single Responsibility Principle (SRP)
- *Concept* --> A class should have only one reason to change.
  - Identify distinct responsibilities and encapsulate each in a separate class or module.
- *Usage in Spring*
  - **Layered Architecture**
    - has different layers, - Controller, Service, Repository, each adhering to SRP

---

### Open/Closed Principle (OCP)
- *Concept* --> Software entities (classes, modules, functions, etc.) should be open for extension, but closed for modification.
  - Achieved through abstraction (interfaces, abstract classes) and polymorphism. New behavior is added by creating new implementations or extending existing ones, rather than modifying the original.
  - Prevents changes to existing, tested code when new functionality is added, reducing the risk of introducing bugs.
- *Usage in Spring*
  - Extensive Use of Interfaces
  - Strategy Pattern
  - Custom Auto-configurations

---

### Liskov Substitution Principle (LSP)
- *Concept* --> Objects in a program should be replaceable with instances of their subtypes without altering the correctness of that program.
  - Subtypes must adhere to the contracts (preconditions, postconditions, invariants) established by their supertypes.
  - Ensures that inheritance hierarchies are correctly designed and that subclasses don't break the expected behavior of their supertypes.

- *Usage in Spring*
  - Polymorphism in DI
  - Spring Data JPA

---

### Interface Segregation Principle (ISP)
- *Concept* --> Clients should not be forced to depend on interfaces they do not use. Rather than one large interface, many small, client-specific interfaces are better.
  - Break down large interfaces into smaller, more focused ones, each serving a specific set of clients.
  Prevents "fat interfaces" that force implementing classes to provide empty or irrelevant implementations for methods they don't need, leading to unnecessary coupling.

- *Usage in Spring*
  - Granular Interfaces
  - Spring Data JPA

---

### Dependency Inversion Principle (DIP)
- *Concept*
  1. High-level modules should not depend on low-level modules. Both should depend on abstractions.
  2. Abstractions should not depend on details. Details should depend on abstractions.
- Dependencies are introduced through interfaces or abstract classes (abstractions) rather than concrete implementations (details). 
- The "inversion" refers to the control of creating and managing dependencies shifting from the high-level module to an external entity (like the Spring IoC container).

- *Usage in Spring*
  - IoC/DI is DIP in Action