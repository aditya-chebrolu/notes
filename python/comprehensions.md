# **Cheatsheet: Comprehensions**

---

## **Index**
1. [List Comprehensions](#list-comprehensions)
   - [Basics](#basics-list-comprehensions)
   - [Adding a Condition](#adding-a-condition-list-comprehensions)
   - [Nested Loops](#nested-loops-list-comprehensions)
   - [With Function Calls](#list-comprehension-with-function-calls)
   - [Flattening a Nested List](#flattening-a-nested-list)
   - [Performance](#performance-list-comprehensions)
2. [Dictionary Comprehensions](#dictionary-comprehensions)
   - [Basics](#basics-dictionary-comprehensions)
   - [Adding a Condition](#adding-a-condition-dictionary-comprehensions)
   - [Reversing a Dictionary](#reversing-a-dictionary)
   - [Combining Two Lists](#combining-two-lists-into-a-dictionary)
   - [Nested Dictionary](#nested-dictionary-comprehension)
3. [Set Comprehensions](#set-comprehensions)

---

## **List Comprehensions**

### **Basics**
```python
[expression for item in iterable]
```
Example:
```python
nums = [1, 2, 3]
squares = [x**2 for x in nums]
print(squares)  # [1, 4, 9]
```

---

### **Adding a Condition**
```python
[expression for item in iterable if condition]
```
Example:
```python
nums = [1, 2, 3, 4]
evens = [x for x in nums if x % 2 == 0]
print(evens)  # [2, 4]
```

---

### **Nested Loops**
```python
[expression for item1 in iterable1 for item2 in iterable2]
```
Example:
```python
pairs = [(x, y) for x in range(2) for y in range(2)]
print(pairs)  # [(0, 0), (0, 1), (1, 0), (1, 1)]
```

---

### **List Comprehension with Function Calls**
```python
result = [func(x) for x in iterable]
```
Example:
```python
def square(x):
    return x * x

nums = [1, 2, 3]
result = [square(x) for x in nums]
print(result)  # [1, 4, 9]
```

---

### **Flattening a Nested List**
```python
flat = [x for sublist in nested for x in sublist]
```
Example:
```python
nested = [[1, 2], [3, 4]]
flat = [x for sublist in nested for x in sublist]
print(flat)  # [1, 2, 3, 4]
```

---

### **Performance**
- **Faster than loops** due to optimized C implementation.
- Use for large computations requiring fast execution.

---

## **Dictionary Comprehensions**

### **Basics**
```python
{key: value for item in iterable}
```
Example:
```python
nums = [1, 2, 3]
squares = {x: x**2 for x in nums}
print(squares)  # {1: 1, 2: 4, 3: 9}
```

---

### **Adding a Condition**
```python
{key: value for item in iterable if condition}
```
Example:
```python
nums = [1, 2, 3, 4]
evens = {x: x**2 for x in nums if x % 2 == 0}
print(evens)  # {2: 4, 4: 16}
```

---

### **Reversing a Dictionary**
```python
reversed_dict = {v: k for k, v in original.items()}
```
Example:
```python
original = {'a': 1, 'b': 2}
reversed_dict = {v: k for k, v in original.items()}
print(reversed_dict)  # {1: 'a', 2: 'b'}
```

---

### **Combining Two Lists into a Dictionary**
```python
{k: v for k, v in zip(keys, values)}
```
Example:
```python
keys = ['name', 'age']
values = ['Alice', 25]
combined = {k: v for k, v in zip(keys, values)}
print(combined)  # {'name': 'Alice', 'age': 25}
```

---

### **Nested Dictionary Comprehension**
```python
{x: {y: y**2 for y in range(n)} for x in range(m)}
```
Example:
```python
nested_dict = {x: {y: y**2 for y in range(2)} for x in range(2)}
print(nested_dict)  # {0: {0: 0, 1: 1}, 1: {0: 0, 1: 1}}
```

---

## **Set Comprehensions**

### **Basics**
```python
{x**2 for x in nums}
```
Example:
```python
nums = [1, 2, 3, 1]
unique_squares = {x**2 for x in nums}
print(unique_squares)  # {1, 4, 9}
```

