Here's the updated markdown file with indexing:

# Index
1. [Spring Beans](#spring-beans)
   1. [What is a Bean?](#what-is-a-bean)
   2. [Bean Definition](#bean-definition)
   3. [Component Scanning](#component-scanning)
   4. [Bean Scopes](#bean-scopes)
   5. [Bean Lifecycle](#bean-lifecycle)
   6. [Lazy Initialization](#lazy-initialization)
   7. [Conditional Bean Creation](#conditional-bean-creation)
   8. [Important Annotations](#important-annotations)
2. [Dependency Injection in Spring](#dependency-injection-in-spring)
   1. [Types of Dependency Injection](#types-of-dependency-injection)
   2. [Primary and Qualifiers](#primary-and-qualifiers)
   3. [Handling Non-Bean Dependencies](#handling-non-bean-dependencies)
   4. [Constructor Injection without `@Autowired`](#constructor-injection-without-autowired)
   5. [Non-Bean Dependency in Constructor](#non-bean-dependency-in-constructor)
3. [Handling Prototype Beans in Spring](#handling-prototype-beans-in-spring)
   1. [Prototype Bean Lifecycle](#prototype-bean-lifecycle)
   2. [Manual Destruction](#manual-destruction)
   3. [Example](#example)
      1. [Prototype Bean](#prototype-bean)
      2. [Usage](#usage)
4. [Points to remember](#points-to-remember)

# Spring Beans

## What is a Bean?
- An object instantiated, assembled, and managed by the Spring IoC container.
- Backbone of a Spring application.

## Bean Definition
- Defined using:
  - `@Component` and its specializations (`@Service`, `@Repository`, `@Controller`).
  - `@Bean` in a configuration class.
  - XML configuration (less common).

## Component Scanning
- Automatically detects and instantiates beans using component scanning.
- Annotate classes with `@Component` (or specializations).
- Use `@ComponentScan` in configuration to specify packages to scan.

## Bean Scopes
- **Singleton**: (Default) Single shared instance per Spring IoC container.
- **Prototype**: New instance each time the bean is requested.
- **Request**: Single instance per HTTP request (web applications).
- **Session**: Single instance per HTTP session (web applications).
- **Global Session**: Single instance per global HTTP session (portlet-based web applications).

## Bean Lifecycle
- **Instantiation**: Bean is created.
- **Dependency Injection**: Dependencies are injected.
- **Initialization**: Custom initialization logic (`@PostConstruct`).
- **Destruction**: Custom cleanup logic (`@PreDestroy`).

## Lazy Initialization
- **@Lazy**: Delay bean instantiation until needed.
  ```java
  @Lazy
  @Component
  public class LazyBean { ... }
  ```

## Conditional Bean Creation
- **@Conditional**: Create beans conditionally.
  ```java
  @Conditional(MyCondition.class)
  @Bean
  public MyBean myBean() { ... }
  ```

## Important Annotations
- **@Component**: Marks a class as a Spring component.
- **@Service**: Specialization of `@Component` for service layer beans.
- **@Repository**: Specialization of `@Component` for data access layer beans.
- **@Controller**: Specialization of `@Component` for presentation layer beans.
- **@Bean**: Marks a method as a bean definition in a configuration class.
- **@Primary**: Marks a bean as the primary candidate for autowiring.
- **@Qualifier**: Specifies the exact bean to inject.
- **@Autowired**: Marks a constructor, field, or setter method for dependency injection.
- **@PostConstruct**: Marks a method to be called after the bean is initialized.
- **@PreDestroy**: Marks a method to be called before the bean is destroyed.

# Dependency Injection in Spring

## Types of Dependency Injection
- **Field Injection**:
  ```java
  @Autowired
  private MyBean myBean;
  ```
- **Constructor Injection**:
  ```java
  public MyClass(MyBean myBean) {
      this.myBean = myBean;
  }
  ```
- **Setter Injection**:
  ```java
  @Autowired
  public void setMyBean(MyBean myBean) {
      this.myBean = myBean;
  }
  ```

## Primary and Qualifiers
- **@Primary**: Indicate a primary bean when multiple beans of the same type exist.
  ```java
  @Primary
  @Component
  public class PrimaryBean { ... }
  ```
- **@Qualifier**: Specify which bean to inject when multiple beans of the same type exist.
  ```java
  @Autowired
  @Qualifier("beanName")
  private MyBean myBean;
  ```

## Handling Non-Bean Dependencies
1. **Manual Instantiation in Constructor**:
   ```java
   @Component
   public class ClassA {
       private final NonBeanClass nonBeanClass;

       public ClassA() {
           this.nonBeanClass = new NonBeanClass();
       }
   }
   ```
2. **Define as a Bean in Configuration Class**:
   ```java
   @Configuration
   public class AppConfig {
       @Bean
       public NonBeanClass nonBeanClass() {
           return new NonBeanClass();
       }
   }

   @Component
   public class ClassA {
       private final NonBeanClass nonBeanClass;

       @Autowired
       public ClassA(NonBeanClass nonBeanClass) {
           this.nonBeanClass = nonBeanClass;
       }
   }
   ```

## Constructor Injection without `@Autowired`
- **Spring Version 4.3 and Above**:
  - If a class has only one constructor, `@Autowired` is optional.
  ```java
  @Component
  public class ClassB {
      // ClassB implementation
  }

  @Component
  public class ClassA {
      private final ClassB classB;

      public ClassA(ClassB classB) {
          this.classB = classB;
      }
  }
  ```

## Non-Bean Dependency in Constructor
- Spring cannot manage and inject non-Bean classes directly.
  ```java
  public class NonBeanClass {
      // NonBeanClass implementation
  }

  @Component
  public class ClassA {
      private final NonBeanClass nonBeanClass;

      public ClassA(NonBeanClass nonBeanClass) {
          this.nonBeanClass = nonBeanClass;
      }
  }
  ```

## Summary
- **Constructor Injection**:
  - Spring handles injection automatically if there’s a single constructor from version 4.3.
  - `@Autowired` is not needed in such cases.
- **Non-Bean Dependency**:
  - Options to handle non-Bean dependencies:
    1. Manual Instantiation
    2. Define as a Bean in Configuration Class
    3. Setter Injection or Field Injection (less preferred)

# Handling Prototype Beans in Spring

## Prototype Bean Lifecycle
- **Prototype Scope**: A new instance each time the bean is requested.
- **Lifecycle Management**:
  - Spring only manages the creation (initialization) of the bean.
  - Client code is responsible for the destruction of the prototype bean.
  - The container does not call the `@PreDestroy` method for prototype beans.

## Manual Destruction
- Manual cleanup is needed for prototype beans:
  ```java
  import org.springframework.context.annotation.AnnotationConfigApplicationContext;

  public class Main {
      public static void main(String[] args) {
          try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
              PrototypeBean bean = context.getBean(PrototypeBean.class);
              // Use the bean...
              context.getBeanFactory().destroyBean(bean);
          }
      }
  }
  ```

## Example

### Prototype Bean
```java
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PrototypeBean {
    @PostConstruct
    public void init() {
        System.out.println("PrototypeBean is created");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("PrototypeBean is destroyed");
    }
}
```

### Usage
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class SomeService {
    private final ApplicationContext context;

    @Autowired
    public SomeService(ApplicationContext context) {
        this.context = context;
    }

    public void usePrototypeBean() {
        PrototypeBean bean = context.getBean(PrototypeBean.class);
        // Use the bean...
    }
}
```

## Summary
- **Prototype Beans**: Spring manages the initialization but not the destruction.
- **@PreDestroy**: Not called automatically for prototype beans.
- **Manual Cleanup**: You need to manually destroy prototype beans if cleanup is required.

# Points To Remember
1. If a bean name is not specified explicitly, the name defaults to the class name in camel case or the name of the method annotated with `@Bean`.

2. If there are two beans of the same class, one declared with `@Component` named "abc" and the other declared using `@Bean` in a configuration class named "abc" as well, the bean declared in the configuration class takes priority during dependency injection.

3. If there are two beans of the same class, one declared with `@Component` named "abc" and the other declared using `@Bean` in a configuration class named "def", and the variable name for the dependency injection is neither "abc" nor "def", Spring will be confused about which bean to use for instantiation. In such cases, you need to use either the `@Primary` or `@Qualifier` annotations to resolve the conflict.

4. Beans are instantiated irrespective of their use, except for prototype-scoped beans, which are instantiated only when used. A new instance is created every time a prototype bean is injected.
