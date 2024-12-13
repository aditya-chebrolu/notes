# **Cheatsheet: Generators**

---

## **Index**
1. [Generator Expressions](#generator-expressions)
   - [Basics](#basics-generator-expressions)
   - [Difference from List Comprehensions](#difference-from-list-comprehension)
   - [Using Generators with Functions](#using-generators-with-functions)
   - [Chaining Generators](#chaining-generators)
   - [Infinite Generators](#infinite-generators)
   - [Performance Tips](#performance-tip-generator-expressions)

---

## **Generator Expressions**

### **Basics**
```python
(expression for item in iterable if condition)
```
Example:
```python
nums = [1, 2, 3]
squares = (x**2 for x in nums)
print(next(squares))  # 1
```

---

### **Difference from List Comprehensions** 
- **Memory-efficient**: Generators use lazy evaluation.
```python
nums_gen = (x for x in range(10**6))  # Efficient
nums_list = [x for x in range(10**6)]  # High memory usage
```

---

### **Using Generators with Functions**
Example:
```python
nums = (x for x in range(5))
print(sum(nums))  # 10
```

---

### **Chaining Generators**
```python
gen2 = (y + 1 for y in gen1)
```
Example:
```python
gen1 = (x**2 for x in range(5))
gen2 = (y + 1 for y in gen1)
print(list(gen2))  # [1, 2, 5, 10, 17]
```

---

### **Infinite Generators**
Example with `itertools`:
```python
from itertools import count
infinite_gen = (x**2 for x in count(1))
print(next(infinite_gen))  # 1
```

---

### **Performance Tips** 
- Use for **streaming large datasets** or infinite sequences.
- Generators avoid **memory overflow** issues.
