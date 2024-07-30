# Spring Boot Database Mappings Cheat Sheet

## 1. `@OneToOne`

Defines a one-to-one relationship between two entities.

### Example
```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Profile profile;

    // getters and setters
}

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bio;

    // getters and setters
}
```

## 2. `@OneToMany`

Defines a one-to-many relationship, where one entity instance is associated with multiple instances of another entity.

### Example
```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    // getters and setters
}

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // getters and setters
}
```

### `mappedBy` Explanation

- The `mappedBy` attribute is used in bidirectional relationships to indicate that the field it refers to is the owner of the relationship. In the example above, `mappedBy = "department"` in the `Department` class indicates that the `department` field in the `Employee` class owns the relationship.

## 3. `@ManyToOne`

Defines a many-to-one relationship, typically used in conjunction with `@OneToMany`.

### Example
Refer to the `Employee` entity in the `@OneToMany` example above.

## 4. `@ManyToMany`

Defines a many-to-many relationship where multiple instances of one entity are associated with multiple instances of another entity.

### Example
```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    // getters and setters
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    // getters and setters
}
```

### `mappedBy` Explanation

- In a `@ManyToMany` relationship, the `mappedBy` attribute is used to indicate the inverse side of the relationship. In the example above, `mappedBy = "courses"` in the `Course` class indicates that the `courses` field in the `Student` class owns the relationship.

## 5. `@JsonIgnore`

Used to prevent a field or property from being serialized into JSON. Useful for avoiding circular references in bidirectional relationships.

### Example
Avoiding circular references:

```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Employee> employees;

    // getters and setters
}

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // getters and setters
}
```

In this example, the `@JsonIgnore` annotation prevents the `employees` list in the `Department` entity from being included in JSON serialization, thus avoiding infinite recursion.

---

You can use this markdown document as a cheat sheet in your GitHub repository for reference.
