# Python Error Handling Cheatsheet

## Index
1. [Basics of Error Handling](#basics-of-error-handling)
2. [Custom Exceptions](#custom-exceptions)
3. [Built-in Exception Hierarchy](#built-in-exception-hierarchy)
4. [Common Exceptions](#common-exceptions)
5. [Raising Exceptions](#raising-exceptions)
6. [Context-Specific Error Handling](#context-specific-error-handling)
7. [Advanced Patterns](#advanced-patterns)
8. [Traceback and Debugging](#traceback-and-debugging)

---

## 1. Basics of Error Handling

### Try-Except Block
```python
try:
    risky_operation()
except ExceptionType:
    print("An error occurred.")
```

### Catching Multiple Exceptions
```python
try:
    risky_operation()
except (TypeError, ValueError):
    print("Caught a TypeError or ValueError.")
```

### Catch-All Exceptions
```python
try:
    risky_operation()
except Exception as e:
    print(f"An error occurred: {e}")
```

### Else Clause
```python
try:
    risky_operation()
except Exception:
    print("An error occurred.")
else:
    print("Operation succeeded.")
```

### Finally Clause
```python
try:
    risky_operation()
except Exception:
    print("An error occurred.")
finally:
    print("Cleanup actions.")
```

## 2. Custom Exceptions

### Defining Custom Exceptions
```python
class CustomError(Exception):
    def __init__(self, message):
        self.message = message
        super().__init__(message)
```

### Raising Custom Exceptions
```python
def risky_operation():
    raise CustomError("Something went wrong!")
```

### Catching Custom Exceptions
```python
try:
    risky_operation()
except CustomError as e:
    print(f"Custom Error: {e.message}")
```


## 3. Built-in Exception Hierarchy

- **BaseException**
  - **Exception**
    - `ArithmeticError` (e.g., `ZeroDivisionError`, `OverflowError`)
    - `LookupError` (e.g., `KeyError`, `IndexError`)
    - `ValueError`, `TypeError`, `IOError`, `OSError`, etc.


## 4. Common Exceptions

### TypeError
```python
try:
    print(5 + "5")
except TypeError:
    print("TypeError: Cannot add int and str.")
```

### ValueError
```python
try:
    int("abc")
except ValueError:
    print("ValueError: Invalid literal for int().")
```

### IndexError
```python
try:
    lst = [1, 2, 3]
    print(lst[5])
except IndexError:
    print("IndexError: List index out of range.")
```

### KeyError
```python
try:
    d = {"a": 1}
    print(d["b"])
except KeyError:
    print("KeyError: Key not found in dictionary.")
```

### ZeroDivisionError
```python
try:
    print(10 / 0)
except ZeroDivisionError:
    print("ZeroDivisionError: Division by zero.")
```

### FileNotFoundError
```python
try:
    with open("nonexistent.txt", "r") as file:
        pass
except FileNotFoundError:
    print("FileNotFoundError: File not found.")
```


## 5. Raising Exceptions

### Manually Raising Exceptions
```python
if some_condition:
    raise ValueError("Invalid condition.")
```

### Rethrowing Exceptions
```python
try:
    risky_operation()
except Exception as e:
    print("Handling exception.")
    raise  # Re-throws the caught exception.
```


## 6. Context-Specific Error Handling

### Ignoring Exceptions
```python
try:
    risky_operation()
except Exception:
    pass
```

### Logging Exceptions
```python
import logging

try:
    risky_operation()
except Exception as e:
    logging.error("An error occurred", exc_info=True)
```

### Assertions
```python
assert condition, "AssertionError message"
```


## 7. Advanced Patterns

### Using `with` for Resource Management
```python
try:
    with open("file.txt", "r") as file:
        data = file.read()
except FileNotFoundError:
    print("FileNotFoundError: File not found.")
```

### Suppressing Exceptions
```python
from contextlib import suppress

with suppress(FileNotFoundError):
    with open("file.txt", "r") as file:
        data = file.read()
```

### Custom Context Manager for Cleanup
```python
class CustomManager:
    def __enter__(self):
        print("Enter context.")
    
    def __exit__(self, exc_type, exc_value, traceback):
        print("Exit context.")
        if exc_type:
            print(f"Exception: {exc_type}, {exc_value}")
            return True  # Suppresses exception

with CustomManager():
    risky_operation()
```


## 8. Traceback and Debugging

### Capturing Exception Details
```python
import traceback

try:
    risky_operation()
except Exception:
    print(traceback.format_exc())
```

### Getting the Exception's Type
```python
try:
    risky_operation()
except Exception as e:
    print(f"Exception type: {type(e).__name__}")
```
