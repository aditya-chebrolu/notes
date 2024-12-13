# Python Functions Cheatsheet

## Index
1. [Defining Functions](#defining-functions)
2. [Calling Functions](#calling-functions)
3. [Function Parameters](#function-parameters)
   - [Positional Parameters](#positional-parameters)
   - [Default Parameters](#default-parameters)
   - [Keyword Arguments](#keyword-arguments)
   - [Arbitrary Arguments (`*args`)](#arbitrary-arguments-args)
   - [Arbitrary Keyword Arguments (`**kwargs`)](#arbitrary-keyword-arguments-kwargs)
4. [Return Statement](#return-statement)
5. [Anonymous Functions (Lambdas)](#anonymous-functions-lambdas)
6. [Scope and Closures](#scope-and-closures)
7. [Higher-Order Functions](#higher-order-functions)
8. [Decorators](#decorators)
9. [Docstrings](#docstrings)
10. [Function Annotations](#function-annotations)
11. [Recursive Functions](#recursive-functions)
12. [Best Practices](#best-practices)

---

## 1. Defining Functions
```python
def function_name(parameters):
    """Docstring (optional): Describe the function."""
    # Function body
    return result
```

Example:
```python
def greet(name):
    return f"Hello, {name}!"
```

## 2. Calling Functions
```python
result = function_name(arguments)
```

Example:
```python
print(greet("Alice"))  # Output: Hello, Alice!
```

## 3. Function Parameters

### a. Positional Parameters
Arguments passed in order.
```python
def add(a, b):
    return a + b

print(add(2, 3))  # Output: 5
```

### b. Default Parameters
Provide a default value.
```python
def greet(name="Guest"):
    return f"Hello, {name}!"

print(greet())         # Output: Hello, Guest!
print(greet("Alice"))  # Output: Hello, Alice!
```

### c. Keyword Arguments
Pass arguments using parameter names.
```python
def introduce(name, age):
    return f"{name} is {age} years old."

print(introduce(age=30, name="Alice"))  # Output: Alice is 30 years old.
```

### d. Arbitrary Arguments (`*args`)
Collect multiple positional arguments into a tuple.
```python
def sum_all(*args):
    return sum(args)

print(sum_all(1, 2, 3, 4))  # Output: 10
```

### e. Arbitrary Keyword Arguments (`**kwargs`)
Collect multiple keyword arguments into a dictionary.
```python
def show_info(**kwargs):
    return kwargs

print(show_info(name="Alice", age=30))  
# Output: {'name': 'Alice', 'age': 30}
```

## 4. Return Statement
Return a value from the function.
```python
def square(x):
    return x * x

print(square(4))  # Output: 16
```

## 5. Anonymous Functions (Lambdas)
Compact, single-expression functions.
```python
lambda arguments: expression
```

Example:
```python
square = lambda x: x * x
print(square(4))  # Output: 16

# Lambda with multiple arguments
add = lambda a, b: a + b
print(add(2, 3))  # Output: 5
```

Use with `map`, `filter`, `sorted`:
```python
nums = [1, 2, 3, 4]
squared = map(lambda x: x ** 2, nums)
print(list(squared))  # Output: [1, 4, 9, 16]
```

## 6. Scope and Closures
- **Local Scope**: Variables defined inside a function.
- **Global Scope**: Variables defined outside all functions.

Example:
```python
x = 10  # Global

def example():
    x = 5  # Local
    print(x)

example()  # Output: 5
print(x)   # Output: 10
```

**Closures**: Functions that capture variables from their enclosing scope.
```python
def outer_function(x):
    def inner_function(y):
        return x + y
    return inner_function

closure = outer_function(10)
print(closure(5))  # Output: 15
```

## 7. Higher-Order Functions
Functions that accept other functions as arguments or return them.

Example:
```python
def apply_function(func, value):
    return func(value)

print(apply_function(lambda x: x ** 2, 5))  # Output: 25
```

## 8. Decorators
Modify the behavior of a function.
```python
def decorator(func):
    def wrapper():
        print("Before function call")
        func()
        print("After function call")
    return wrapper

@decorator
def say_hello():
    print("Hello!")

say_hello()
# Output:
# Before function call
# Hello!
# After function call
```

## 9. Docstrings
Document your function.
```python
def greet(name):
    """
    Greets a person with the given name.
    
    Args:
        name (str): The person's name.
    
    Returns:
        str: Greeting message.
    """
    return f"Hello, {name}!"
```

## 10. Function Annotations
Annotate arguments and return types.
```python
def add(a: int, b: int) -> int:
    return a + b
```

## 11. Recursive Functions
A function that calls itself.
```python
def factorial(n):
    if n == 0:
        return 1
    return n * factorial(n - 1)

print(factorial(5))  # Output: 120
```
## 12. Best Practices
1. Use meaningful function names.
2. Keep functions small and focused.
3. Use default arguments carefully.
4. Write docstrings.
5. Avoid global variables.
6. Use type hints for readability.
