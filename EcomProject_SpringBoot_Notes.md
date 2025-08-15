# EcomProject: Spring Boot Learning Notes

# EcomProject: Spring Boot Learning Notes

---

## Quick Interview Summary & Cheat Sheet

**What I Built:**
Built a Spring Boot e-commerce backend to learn CRUD, file upload, REST API structure.

**Flow Diagram:**
```
Request → Controller → Service → Repo → DB
```
- **Controller:** (Receptionist 🧑‍💼) Receives requests, directs them.
- **Service:** (Manager 🧑‍💼) Handles business logic.
- **Repo:** (Clerk 🧑‍💼) Fetches/saves data.
- **DB:** Stores everything.

**Key Concepts I Used (with why):**
- `@RestController` → skips `@ResponseBody`, cleaner REST APIs.
- `@Autowired` → no need to manually create service objects.
- `@Lob` → store images as byte array in DB for quick prototyping (but in real life, use file storage).
- `@Query` → custom JPQL for flexible search.
- `@Entity`, `@Id`, `@GeneratedValue` → map Java class to DB table with auto-generated primary key.

**Mistake Log:**
- Forgot to check `imageFile.isEmpty()` → caused null pointer.
- Wrong JPQL syntax in custom query → fixed by adding correct alias.

**My Own “Cheat” Snippets:**

_Add product + image in one call:_
```java
@PostMapping("/api/product")
public Product addProduct(@RequestPart("product") Product prod,
             @RequestPart(value = "imageFile", required = false) MultipartFile imageFile)
```

_Custom search query:_
```java
@Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List<Product> searchProducts(String keyword);
```

_Mapping entity to table with auto-ID:_
```java
@Entity
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  // other fields...
}
```

---

## Table of Contents
1. [Project Overview](#project-overview)
2. [Tech Stack](#tech-stack)
3. [Folder Structure & File Roles](#folder-structure--file-roles)
4. [Spring Concepts Covered](#spring-concepts-covered)
5. [Key Classes & Methods](#key-classes--methods)
6. [Data Flow](#data-flow)
7. [Database Schema](#database-schema)
8. [Common Patterns Used](#common-patterns-used)
9. [Error Handling](#error-handling)
10. [Lessons Learned & Best Practices](#lessons-learned--best-practices)
11. [Glossary: Small Definitions](#glossary-small-definitions)


## Project Overview

**Purpose:**  
A simple e-commerce backend for managing products (cars), supporting CRUD operations, image upload, and search.

**Main Features:**
- Add, update, delete, and search products.
- Upload and retrieve product images.
- RESTful API for frontend integration.

**Architecture Diagram:**  
```
[Frontend] <--> [Controller] <--> [Service] <--> [Repository] <--> [Database]
```
- Controller: Handles HTTP requests.
- Service: Business logic.
- Repository: Data access.
- Database: Stores products and images.

---

## Tech Stack

- **Spring Boot:** Main framework for building REST APIs.
- **Spring Data JPA:** Simplifies database access using repositories and entities.
- **H2 Database:** In-memory database for development/testing.
- **Lombok:** Reduces boilerplate code (getters/setters, constructors).
- **Maven:** Dependency management and build tool.
- **Spring DevTools:** Hot reload for faster development.

---

## Folder Structure & File Roles

```
src/
  main/
    java/
      com/spring/EcomProject/
        EcomProjectApplication.java   # Main entry point
        controller/                   # REST controllers
        model/                        # Entity classes (Product)
        repo/                         # Repository interfaces
        service/                      # Business logic
    resources/
      application.properties          # App configuration
      data.sql                        # Initial data
      static/                         # Static web resources
      templates/                      # (for Thymeleaf, not used here)
test/
  java/
    ...                              # Unit tests
pom.xml                              # Maven build file
HELP.md                              # Project help/reference
```

---

## Spring Concepts Covered

- **@SpringBootApplication:** Marks the main class for Spring Boot.
- **@RestController:** Defines REST endpoints.
- **@RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping:** Maps HTTP requests to methods.
- **@Autowired:** Injects dependencies (e.g., service into controller).
- **@Service:** Marks business logic classes.
- **@Entity:** Maps Java class to database table.
- **@Id, @GeneratedValue:** Primary key and auto-generation.
- **@Lob:** Large object (used for image data).
- **@Repository:** Marks data access layer.
- **@Query:** Custom database queries.
- **@CrossOrigin:** Enables CORS for frontend access.

---

## Key Classes & Methods

### 1. EcomProjectApplication.java
```java
@SpringBootApplication
public class EcomProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcomProjectApplication.class, args);
    }
}
```
- **Role:** Starts the Spring Boot application.

### 2. ProductController.java
- Handles all product-related HTTP requests.
- Example:
  ```java
  @PostMapping("/api/product")
  public Product addProduct(@RequestPart("product") Product prod,
                            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile)
  ```
  - Accepts product data and image file in a single request.

### 3. ProductService.java
- Contains business logic for products.
- Example:
  ```java
  public Product addProduct(Product prod, MultipartFile imageFile) throws IOException {
      if (imageFile != null && !imageFile.isEmpty()) {
          prod.setImageName(imageFile.getOriginalFilename());
          prod.setImageType(imageFile.getContentType());
          prod.setImageDate(imageFile.getBytes());
      }
      return repo.save(prod);
  }
  ```
  - Saves product and image to the database.

### 4. ProductRepo.java
- Data access layer using Spring Data JPA.
- Example:
  ```java
  @Query("SELECT p from Product p WHERE ...")
  List<Product> searchProducts(String keyword);
  ```
  - Custom search query.

### 5. Product.java (Entity)
- Represents a product in the database.
- Example fields:
  ```java
  private String name;
  private String desc;
  private String brand;
  private BigDecimal price;
  private String category;
  @Lob
  private byte[] imageDate;
  ```

---

## Data Flow

1. **Request:** Frontend sends HTTP request (e.g., add product with image).
2. **Controller:** Receives request, extracts data.
3. **Service:** Processes business logic, handles image file.
4. **Repository:** Saves product and image to database.
5. **Response:** Returns result to frontend.

---

## Database Schema

**Product Table:**
- `id` (PK, auto-generated)
- `name`, `desc`, `brand`, `price`, `category`
- `releaseDate`, `available`, `quantity`
- `imageName`, `imageType`, `imageDate` (for image storage)

**Relationships:**  
- Single table for products; no foreign keys in this simple project.

---

## Common Patterns Used

- **MVC (Model-View-Controller):** Controller, Service, Repository, Entity.
- **Service Layer:** Business logic separated from controllers.
- **Repository Pattern:** Data access abstracted via interfaces.
- **DTOs:** Not explicitly used, but Product acts as a data transfer object.

---

## Error Handling

- **Try-catch blocks** in service/controller for image upload and database operations.
- **ResponseEntity** used to return proper HTTP status codes (e.g., 404 for not found, 500 for server error).
- **Best Practice:** Always validate input and handle exceptions gracefully.

---

## Lessons Learned & Best Practices

- **Multipart Requests:** Use `@RequestPart` for handling files and data together.
- **Entity Design:** Store images as byte arrays with `@Lob` for simplicity.
- **Service Layer:** Keeps business logic out of controllers.
- **Repository Queries:** Use custom queries for flexible search.
- **Configuration:** Use `application.properties` for easy DB and app setup.
- **Testing:** Use H2 for fast, in-memory testing.
- **Pitfalls:**  
  - Always check for null/empty files before processing.
  - Use correct field names in queries and entities.
  - Keep error handling user-friendly.

---

## Example Code Snippets

**Add Product with Image:**
```java
@PostMapping("/api/product")
public Product addProduct(@RequestPart("product") Product prod,
                         @RequestPart(value = "imageFile", required = false) MultipartFile imageFile)
```

**Search Products:**
```java
@Query("SELECT p from Product p WHERE ...")
List<Product> searchProducts(String keyword);
```

**Entity with Image:**
```java
@Lob
private byte[] imageDate;
```

---

## Real-World Analogies

- **Controller:** Like a receptionist, receives requests and directs them.
- **Service:** Like a manager, decides what needs to be done.
- **Repository:** Like a clerk, fetches and stores data.
- **Entity:** Like a record in a filing cabinet.

---

## Glossary: Small Definitions

- **Spring Boot:** Framework for building stand-alone, production-grade Spring applications.
- **REST API:** Web service that uses HTTP methods (GET, POST, etc.) for communication.
- **Entity:** Java class mapped to a database table.
- **Repository:** Interface for data access operations.
- **Service:** Class for business logic.
- **Controller:** Class that handles HTTP requests.
- **@Autowired:** Automatically injects dependencies.
- **@RestController:** Combines @Controller and @ResponseBody for REST APIs.
- **@RequestMapping:** Maps HTTP requests to handler methods.
- **@GetMapping, @PostMapping, etc.:** Specialized request mappings for HTTP methods.
- **@Lob:** Marks a field as a large object (e.g., image data).
- **@Entity:** Marks a class as a JPA entity.
- **@Id:** Marks the primary key field.
- **@GeneratedValue:** Auto-generates primary key values.
- **@Query:** Custom database query.
- **@CrossOrigin:** Allows cross-origin requests (for frontend-backend communication).
- **H2 Database:** Lightweight, in-memory database for development/testing.
- **Lombok:** Library to reduce boilerplate code in Java.
- **Maven:** Build and dependency management tool.
- **DevTools:** Spring Boot tool for hot reloading during development.
- **DTO:** Data Transfer Object, used to transfer data between layers.
- **CRUD:** Create, Read, Update, Delete operations.
- **ResponseEntity:** Spring class for building HTTP responses with status codes.

---

> Use this document as your personal Spring Boot reference for interviews and future projects!
