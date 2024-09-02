# Functional Interfaces Examples

In Java, several functional interfaces are part of the `java.util.function` package. These interfaces are used primarily in lambda expressions and method references, and they make it easier to write functional-style code.

### 1. **Supplier<T>**
- **Purpose**: Represents a function that supplies a value of type `T` without taking any arguments.
- **Functional Method**: `T get()`
- **Example**:
    ```java
    Supplier<Double> randomValue = () -> Math.random();
    System.out.println(randomValue.get());  // Generates a random value
    ```

### 2. **Consumer<T>**
- **Purpose**: Represents an operation that accepts a single input argument of type `T` and returns no result.
- **Functional Method**: `void accept(T t)`
- **Example**:
    ```java
    Consumer<String> printer = s -> System.out.println(s);
    printer.accept("Hello, World!");  // Prints "Hello, World!"
    ```

### 3. **Function<T, R>**
- **Purpose**: Represents a function that takes an input of type `T` and returns a result of type `R`.
- **Functional Method**: `R apply(T t)`
- **Example**:
    ```java
    Function<String, Integer> stringLength = s -> s.length();
    System.out.println(stringLength.apply("Hello"));  // Outputs: 5
    ```

### 4. **Predicate<T>**
- **Purpose**: Represents a predicate (boolean-valued function) of one argument.
- **Functional Method**: `boolean test(T t)`
- **Example**:
    ```java
    Predicate<Integer> isEven = num -> num % 2 == 0;
    System.out.println(isEven.test(4));  // Outputs: true
    System.out.println(isEven.test(5));  // Outputs: false
    ```

### 5. **BiConsumer<T, U>**
- **Purpose**: Represents an operation that accepts two input arguments of types `T` and `U`, and returns no result.
- **Functional Method**: `void accept(T t, U u)`
- **Example**:
    ```java
    BiConsumer<String, Integer> printer = (s, i) -> System.out.println(s + " " + i);
    printer.accept("Age:", 30);  // Prints "Age: 30"
    ```

### 6. **BiFunction<T, U, R>**
- **Purpose**: Represents a function that takes two arguments of types `T` and `U`, and returns a result of type `R`.
- **Functional Method**: `R apply(T t, U u)`
- **Example**:
    ```java
    BiFunction<String, String, String> concatenator = (s1, s2) -> s1 + s2;
    System.out.println(concatenator.apply("Hello", " World"));  // Outputs: "Hello World"
    ```

### 7. **UnaryOperator<T>**
- **Purpose**: A special case of `Function` where both the input and output are of the same type.
- **Functional Method**: `T apply(T t)`
- **Example**:
    ```java
    UnaryOperator<Integer> square = x -> x * x;
    System.out.println(square.apply(5));  // Outputs: 25
    ```

### 8. **BinaryOperator<T>**
- **Purpose**: A special case of `BiFunction` where both arguments and the result are of the same type.
- **Functional Method**: `T apply(T t1, T t2)`
- **Example**:
    ```java
    BinaryOperator<Integer> sum = (a, b) -> a + b;
    System.out.println(sum.apply(3, 7));  // Outputs: 10
    ```
