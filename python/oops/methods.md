# 🚀 Python Methods Cheat Sheet

## 📌 Quick Overview

| Feature         | `@staticmethod` | `@classmethod` | **Instance Method** |
|---------------|---------------|---------------|----------------|
| Uses `self`?  | ❌ No         | ❌ No         | ✅ Yes        |
| Uses `cls`?   | ❌ No         | ✅ Yes        | ❌ No        |
| Access Class Vars? | ❌ No  | ✅ Yes | ✅ Yes (via `self.__class__.var`) |
| Access Instance Vars? | ❌ No | ❌ No | ✅ Yes |
| Call via Class? | ✅ Yes | ✅ Yes | ❌ No |
| Call via Instance? | ✅ Yes | ✅ Yes | ✅ Yes |
| Best for? | Utility functions | Factory methods, class state | Object-specific actions |

---

## 🏷 **Static Methods (`@staticmethod`)**

| Feature | Details |
|---------|---------|
| ✅ **No access to `self` or `cls`** | Functions inside a class that act like normal functions |
| ✅ **Can be called via class or instance** | `Class.method()` or `instance.method()` |
| ✅ **Best for utility functions** | E.g., math operations, formatters |
| ❌ **Cannot access class or instance variables** | Independent of instance & class data |

### **Example:**
```python
class MathUtils:
    @staticmethod
    def add(x, y):
        return x + y

print(MathUtils.add(5, 3))  # ✅ 8
obj = MathUtils()
print(obj.add(10, 2))  # ✅ 12
```

---

## 🏷 **Class Methods (`@classmethod`)**

| Feature | Details |
|---------|---------|
| ✅ **Uses `cls` instead of `self`** | Works with class-level data |
| ✅ **Can modify class variables** | `cls.var = value` |
| ✅ **Can be called via class or instance** | `Class.method()` or `instance.method()` |
| ✅ **Best for factory methods & shared state** | Example: creating instances with different defaults |
| ❌ **Cannot access instance variables (`self.var`)** | Works on class level, not object level |

### **Example:**
```python
class Example:
    count = 0  # ✅ Class variable

    @classmethod
    def increment(cls):
        cls.count += 1  # ✅ Modifies class variable
        return cls.count

print(Example.increment())  # ✅ 1
obj = Example()
print(obj.increment())  # ✅ 2 (shared state)
```

---

## 🏷 **Instance Methods (`self`)**

| Feature | Details |
|---------|---------|
| ✅ **Uses `self` to access instance variables** | Works on object-specific data |
| ✅ **Can access both class and instance variables** | `self.var`, `self.__class__.var` |
| ✅ **Can only be called via an instance** | `instance.method()` |
| ✅ **Best for modifying individual objects** | Object-level modifications |

### **Example:**
```python
class User:
    def __init__(self, name):
        self.name = name  # ✅ Instance variable

    def greet(self):
        return f"Hello, {self.name}!"

obj = User("Alice")
print(obj.greet())  # ✅ "Hello, Alice!"
```

---

## 🏷 **Singleton Pattern**

| Feature | Details |
|---------|---------|
| ✅ **Ensures only one instance exists** | Prevents multiple object creations |
| ✅ **Uses class variable to store instance** | `cls.__instance` |
| ✅ **Useful for database connections, caches, AWS clients** | Shared resources |
| ❌ **Not needed if instance-specific behavior is required** | Avoid if objects should have unique states |

### **Example:**
```python
class Singleton:
    __instance = None  # ✅ Private class variable

    def __new__(cls):
        if cls.__instance is None:
            cls.__instance = super(Singleton, cls).__new__(cls)
        return cls.__instance

a = Singleton()
b = Singleton()
print(a is b)  # ✅ True (same object)
```

---

## 🏷 **Class Variables (`cls.var`) vs Instance Variables (`self.var`)**

| Feature | **Class Variable (`cls.var`)** | **Instance Variable (`self.var`)** |
|---------|----------------|----------------|
| Scope | Shared across all instances | Unique for each object |
| Memory Usage | Efficient (one copy) | Uses more memory (multiple copies) |
| Inheritance | Shared with subclasses | Unique to each instance |
| Example Use | AWS Clients, Config Data | User Info, Object-Specific Data |

### **Example:**
```python
class Example:
    class_var = 0  # ✅ Shared across all instances

    def __init__(self):
        self.instance_var = 0  # ✅ Unique for each instance
```

---

## 🏷 **Best Practices Summary**

| Use Case | Best Method Type |
|----------|----------------|
| **Helper functions (math, logs, utils)** | `@staticmethod` |
| **Factory methods (creating instances differently)** | `@classmethod` |
| **Modifying class-level state (shared settings)** | `@classmethod` |
| **Working with instance data (user, product, etc.)** | **Instance Method (`self`)** |
| **Ensuring a single shared instance** | **Singleton Pattern** |

---

🚀 **Follow these rules to write cleaner, efficient Python classes!**  
