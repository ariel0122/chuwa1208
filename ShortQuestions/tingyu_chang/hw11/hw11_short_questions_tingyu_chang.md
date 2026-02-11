### 1. List all of the annotations you learned from this class session.

#### **Core Spring & Configuration**
* `@SpringBootApplication`: This is the main entry point annotation that enables auto-configuration and component scanning.
* `@Component`: This marks a class as a generic Spring-managed bean.
* `@Service`: This specialized version of `@Component` defines the service layer for business logic.
* `@Repository`: This specialized `@Component` marks the DAO layer and enables persistence exception translation.
* `@Configuration`: This indicates that a class declares one or more bean definition methods.
* `@Bean`: This annotation is used on a method to manually define a bean, which is often used for third-party classes.
* `@Primary`: This gives a bean preference when multiple candidates are qualified to autowire a dependency.
* `@Qualifier`: This specifies which unique bean to inject when multiple candidates are available.

#### **Dependency Injection**
* `@Autowired`: This marks a constructor, field, or method to be automatically injected by the Spring container.

#### **Web / REST / MVC**
* `@Controller`: This marks the class as a web controller which is typically used to serve views.
* `@RestController`: This convenience annotation combines `@Controller` and `@ResponseBody` for creating RESTful APIs.
* `@ResponseBody`: This indicates that the return value of a method should be written directly to the HTTP response body.
* `@RequestMapping`: This maps HTTP requests to handler methods in MVC and REST controllers.
* `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: These are shortcut annotations for mapping specific HTTP verbs.
* `@PathVariable`: This extracts values directly from the URI template variables.
* `@RequestParam`: This extracts query parameters from the URL.
* `@RequestBody`: This maps the incoming HTTP request body to a Java object.

#### **JPA / Hibernate (Database)**
* **Entity Setup:**
    * `@Entity`: This marks a class as a JPA entity.
    * `@Table`: This specifies the database table name.
    * `@Id`: This denotes the primary key of the entity.
    * `@GeneratedValue`: This configures the strategy for auto-incrementing the primary key.
    * `@Column`: This specifies details for the database column mapping.
    * `@Temporal`: This configures the mapping format for date and time types.
    * `@Enumerated`: This specifies how an enum should be persisted in the database.
* **Relationships:**
    * `@OneToMany`, `@ManyToOne`, `@ManyToMany`: These annotations define the relationships between different entities.
    * `@JoinColumn`: This specifies the foreign key column.
    * `@JoinTable`: This defines the association table used for many-to-many relationships.
* **Inheritance:**
    * `@MappedSuperclass`: This designates a base class whose mapping information is applied to the entities that inherit from it.
* **Queries:**
    * `@Query`: This allows the definition of custom JPQL or SQL queries.
    * `@NamedQuery`: This defines a static query with a specific name.
    * `@Modifying`: This indicates that a query is an update or delete operation.
* **Transactions & Caching:**
    * `@Transactional`: This defines the scope of a database transaction.
    * `@Cacheable`: This enables caching for the method result.
* **Auditing:**
    * `@CreationTimestamp`: This automatically sets the timestamp when the entity is created.
    * `@UpdateTimestamp`: This automatically updates the timestamp when the entity is modified.

#### **Validation**
* `@Valid`: This triggers validation logic on an argument such as a DTO.
* `@NotEmpty`, `@NotBlank`, `@Size`, `@Email`: These are constraints used to validate specific fields within an object.

#### **Exception Handling**
* `@ExceptionHandler`: This handles specific exceptions thrown within a single controller.
* `@ControllerAdvice`: This marks a class for global exception handling across multiple controllers.
* `@ResponseStatus`: This marks a method or exception class with the specific HTTP status code to return.

#### **Spring Security**
* `@EnableWebSecurity`: This enables the web security support provided by Spring Security.
* `@PreAuthorize`: This evaluates a security expression to check permissions before invoking a method.
