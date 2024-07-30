# Handling JSON Property Mapping for Boolean Fields in Spring Boot

## Issue

When using a DTO class in Spring Boot, the Boolean field `isActive` is not correctly mapped in the JSON response. Instead, it appears as `active` with a `null` value.

**DTO Class Example:**

```java
package com.example.week2.dtos;

public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Boolean isActive;

    public EmployeeDTO() {}

    public EmployeeDTO(Long id, String name, String email, Integer age, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.isActive = isActive;
    }

    // Getters and setters...
}
```

**JSON Response Example:**

```json
{
    "id": 1,
    "name": "aditya",
    "email": "abc@gmail.com",
    "age": 24,
    "active": null
}
```

## Cause

Jackson, the JSON library used by Spring Boot, follows JavaBeans naming conventions. For Boolean fields, Jackson expects a getter method named `is<FieldName>()` or `get<FieldName>()`. In the provided DTO, the getter is named `getActive()`, causing Jackson to look for a field named `active`.

```java
// either (only for boolean properties)
public Boolean isIsActive() { // awkward naming
    return isActive;
}

// or
public Boolean getIsActive() {
    return isActive;
}
```

## Solutions

### 1. Rename Getter Method

Rename the getter method to follow the JavaBeans naming convention for Boolean fields.

**Updated DTO Class:**

```java
public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Boolean isActive;

    // Constructors...

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // Other getters and setters...
}
```

### 2. Use `@JsonProperty` Annotation

Use the `@JsonProperty` annotation to explicitly specify the JSON property name.

**Updated DTO Class:**

```java
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    
    @JsonProperty("isActive")
    private Boolean isActive;

    // Constructors...

    @JsonProperty("isActive")
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // Other getters and setters...
}
```

### 3. Use Lombok Annotations

Simplify your code using Lombok annotations, which generate the correct getters and setters.

**Updated DTO Class:**

```java
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Boolean isActive;
}
```

**Dependencies:**

For Maven:

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.20</version>
    <scope>provided</scope>
</dependency>
```

For Gradle:

```groovy
dependencies {
    compileOnly 'org.projectlombok:lombok:1.18.20'
    annotationProcessor 'org.projectlombok:lombok:1.18.20'
}
```

## Conclusion

To ensure correct JSON property mapping for Boolean fields in Spring Boot, follow JavaBeans naming conventions, use `@JsonProperty` annotations, or leverage Lombok annotations. Each approach helps avoid common pitfalls and ensures the correct serialization and deserialization of JSON properties.

This version of the markdown file explicitly includes `@Getter` and `@Setter` annotations in the Lombok-based solution.
