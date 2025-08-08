> *Topics --> JPA, . . .*\
> *Link to Codes --> [Codes](../Codes/)*


## Java Persistence API
- JPA is the official Java standard for **Object-Relational Mapping (ORM)**
- it's a specification—a set of rules and guidelines for how to manage relational data in a Java application. (like a contract or a blueprint)
- it is a framework for mapping Java objects to database tables. It defines annotations and an API for performing database operations like saving, updating, and querying.
- JPA provides a layer of abstraction between Java objects and the database's SQL
- JPA implementation handles the conversion to and from SQL behind the scenes.
- Hibernate is a library that implements the JPA Specification


### `@Entity`
- this is annotation is the starting point of JPA.
- it tells the JPA implementation that "This Java class represents a table in the database."
- used to mark a Plain Old Java Object (POJO) as a persistent entity. The name of the class will, by default, be the name of the database table, and each property will be a column.

- Requirements for `@Entity` class
  - It must be annotated with `@Entity`.
  - It must have a no-argument constructor. This is required by JPA so it can create new instances of the entity.
  - It must have a **primary key**. You designate a field as the primary key using the `@Id` annotation. This field uniquely identifies each record in the database.



### Spring Data Repositories
- A repository is an interface that acts as the data access layer for the application.
- Instead of writing a concrete class with methods for `save`, `findById`, and `findAll`, we simply define a repository interface, and Spring generates the implementation at runtime.
- use To define a contract for your data access operations without writing the implementation.
- we create an interface and extend one of Spring Data's core repository interfaces, such as `JpaRepository<T, ID>`.
  - `T` --> The type of the entity.
  - `ID` --> The type of the primary key

- By extending JpaRepository, we inherit a rich set of methods for basic CRUD (Create, Read, Update, Delete) operations, including,
  - `save(T entity)`
  - `findById(ID id)`
  - `findAll()`
  - `deleteById(ID id)`
  - . . . 


### Derived Query Methods
- allows us to define custom queries simply by writing a method signature. Spring parses the method name and generates the correct SQL or JPQL query.
- used To define complex queries with no code - just a method name.
- *Naming Conventions*
  - It starts with `find`, `read`, `get`, `query`, or `stream`.
  - This is followed by `By`.
  - After `By`, names of the entity properties to filter by.


### Fine-Grained Entity Mapping
- used to define how the java objects are mapped to database
- `@Table` --> This annotation is placed on the `@Entity` class to explicitly name the database table.
  - used when the table name doesn't match the class name.
  - *Example*
    ```
    @Entity
    @Table(name = "vehicle_inventory") // Maps to a table named 'vehicle_inventory'
    public class Vehicle {
        // ...
    }
    ```

- `@Column` --> This annotation is used on a field to specify the column name, data type, or other constraints. 
  - used when the column name in the database doesn't match the field name in the Java class.
  - *Example*
    ```
    @Id
    @Column(name = "vehicle_id") // Maps the 'id' field to a column named 'vehicle_id'
    private Long id;
    ```


### Entity Relationships
- JPA provides annotations to define these relationships directly in the entity classes, abstracting away the foreign keys.

- `@OneToOne` --> each record in one table is linked to exactly one record in another table.
- `@OneToMany` and `@ManyToOne` --> One record in the first table is linked to many records in the second, but each record in the second table is linked to only one in the first. The `@ManyToOne` side is typically the "owning" side of the relationship and contains the foreign key.
- `@ManyToMany` --> This relationship links many records in one table to many in another. This usually requires a separate join table in the database to manage the relationship.
  - *Example*
    ```
    // Vehicle.java
    @ManyToOne
    @JoinColumn(name = "location_id") // 'location_id' is the foreign key column
    private Location location;

    // Location.java
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private Set<Vehicle> vehicles;
    ```

    - `@JoinColumn` --> specifies the foreign key column.
    - `mappedBy` --> This tells JPA that the relationship is managed by the location field in the Vehicle entity.



### Transaction Management (`@Transactional`)
- ensures data integrity
- Transaction management ensures that a group of database operations either all succeed or are all rolled back if any one of them fails.

- `@Transactional` --> typically placed on a service method. When a method is annotated with `@Transactional`, Spring wraps the method in a database transaction.
  - *Working*
    - When the method is called, Spring starts a transaction.
    - All database operations within that method (e.g., multiple `save()` calls) are executed within the same transaction.
    - If the method completes successfully, Spring commits the transaction, making all changes permanent.
    - If the method throws an exception, Spring rolls back the transaction, undoing all changes and ensuring data remains consistent.


### Custom Queries with `@Query`
- `@Query` is used where the usage of drived queries is different.
- `@Query` annotation gives us full control by allowing us to write our own queries.
- used to define a custom query using either JPQL (Java Persistence Query Language) or native SQL.
- **JPQL** --> A query language similar to SQL but operates on JPA entities and their fields, not database tables and columns.

- *Example*
    ```
    public interface VehicleRepository extends JpaRepository<Vehicle, String> {

        @Query("SELECT v FROM Vehicle v WHERE v.location = :location AND v.status = :status")
        List<Vehicle> findVehiclesByLocationAndStatus(@Param("location") String location, @Param("status") String status);
    }
    ```
- `@Query` --> The annotation itself contains the query string.
- `@Param` --> This annotation maps the method parameter to the named parameter (:location) in the query




### Paging and Sorting
- Spring Data JPA provides built-in support for paging and sorting.

- `PagingAndSortingRepository` --> This is a parent interface that `JpaRepository` extends. It provides methods like `findAll(Pageable pageable)`and `findAll(Sort sort)`.

- `Pageable` --> An object that contains information about the page number, page size, and sorting directives. can pass this to repository methods to retrieve data in chunks.

- `Page<T>` --> The return type for paged queries. It contains the list of entities for the current page, along with metadata like the total number of pages and the total number of records.