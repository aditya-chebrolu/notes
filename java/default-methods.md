Here's an in-depth explanation of default methods in Java, formatted in Markdown with an index structure:

# Default Methods in Java

## Table of Contents
1. [Overview](#1-overview)
2. [Key Characteristics](#2-key-characteristics)
3. [Core Methods](#3-core-methods)
4. [Usage](#4-usage)
5. [Edge Cases and Considerations](#5-edge-cases-and-considerations)
6. [Best Practices](#6-best-practices)

## 1. Overview

Default methods were introduced in Java 8 as part of the `java.lang` package. They allow developers to add new methods to interfaces without breaking existing implementations. This feature was primarily introduced to enable the evolution of interfaces in Java libraries, especially the Java Collections Framework.

## 2. Key Characteristics

- Defined in interfaces using the `default` keyword
- Provide a default implementation for interface methods
- Can be overridden by implementing classes
- Allow for multiple inheritance of behavior
- Cannot override methods from `java.lang.Object`

## 3. Core Methods

Default methods don't have specific "core methods" as they are a language feature rather than a class or interface. However, here are some common patterns and examples:

### 3.1 Simple Default Method

```java
interface SimpleInterface {
    default void simpleMethod() {
        System.out.println("Simple default method");
    }
}
```

### 3.2 Default Method with Parameters

```java
interface ParameterizedInterface {
    default int add(int a, int b) {
        return a + b;
    }
}
```

### 3.3 Default Method Calling Abstract Method

```java
interface CombinedInterface {
    void abstractMethod();
    
    default void combinedMethod() {
        System.out.println("Calling abstract method from default method:");
        abstractMethod();
    }
}
```

## 4. Usage

Default methods are used in various scenarios. Here are some examples:

### 4.1 Adding New Functionality to Existing Interfaces

```java
interface List<E> {
    // Existing methods...
    
    default void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        for (int i = 0; i < size(); i++) {
            set(i, operator.apply(get(i)));
        }
    }
}
```

### 4.2 Providing Optional Methods

```java
interface Logger {
    void log(String message);
    
    default void logError(String message) {
        log("ERROR: " + message);
    }
    
    default void logWarning(String message) {
        log("WARNING: " + message);
    }
}
```

### 4.3 Multiple Inheritance of Behavior

```java
interface Flyable {
    default void fly() {
        System.out.println("Flying...");
    }
}

interface Swimmable {
    default void swim() {
        System.out.println("Swimming...");
    }
}

class FlyingFish implements Flyable, Swimmable {
    // Can use both fly() and swim() methods
}
```

## 5. Edge Cases and Considerations

### 5.1 Diamond Problem

When a class implements two interfaces with the same default method, Java requires the implementing class to override the method to resolve the conflict.

```java
interface A {
    default void method() {
        System.out.println("A");
    }
}

interface B {
    default void method() {
        System.out.println("B");
    }
}

class C implements A, B {
    @Override
    public void method() {
        A.super.method(); // Calls A's implementation
    }
}
```

### 5.2 Interaction with Abstract Classes

Default methods in interfaces do not conflict with abstract methods in abstract classes. The concrete method always wins over a default method.

```java
interface I {
    default void method() {
        System.out.println("Interface");
    }
}

abstract class AC {
    public abstract void method();
}

class Concrete extends AC implements I {
    @Override
    public void method() {
        System.out.println("Concrete");
    }
}
```

## 6. Best Practices

1. Use default methods sparingly and only when necessary to evolve interfaces.
2. Provide clear documentation for default methods, explaining their behavior and any assumptions.
3. Avoid complex logic in default methods; keep them simple and focused.
4. Consider using abstract classes for more complex shared behavior.
5. Be cautious when adding default methods to interfaces in libraries, as they may conflict with existing implementations.
6. When facing the diamond problem, explicitly choose which default implementation to use or provide a new implementation.
7. Test thoroughly when adding default methods to ensure they don't introduce unexpected behavior in existing implementations.

Default methods are a powerful feature in Java that allows for more flexible interface design and evolution. By understanding their characteristics, usage patterns, and potential pitfalls, developers can effectively leverage this feature to create more maintainable and extensible Java code.
