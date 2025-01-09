# Python Tuples

## **Table of Contents**
1. [Creating a Tuple](#creating-a-tuple)
2. [Accessing Elements](#accessing-elements)
3. [Tuple Slicing](#tuple-slicing)
4. [Basic Operations with Tuples](#basic-operations-with-tuples)
5. [Tuple Methods](#tuple-methods)
6. [Unpacking Tuples](#unpacking-tuples)
7. [Common Use Cases of Tuples](#common-use-cases-of-tuples)
8. [Key Points](#key-points)

---

## Creating a Tuple
```python
# Empty Tuple
my_tuple = ()

# Tuple with elements
my_tuple = (1, 2, 3, 'hello', True)

# Without parentheses (parentheses are optional)
my_tuple = 1, 2, 3

# Single-element tuple (comma required)
single_element_tuple = (1,)  # Note the comma

# Nested tuple
nested_tuple = ((1, 2), (3, 4), (5, 6))

# From an iterable
my_tuple = tuple(range(5))  # (0, 1, 2, 3, 4)
```

---

## Accessing Elements
```python
# Indexing
my_tuple[0]      # First element
my_tuple[-1]     # Last element

# Checking existence
if 3 in my_tuple:
    print("3 is in the tuple")
```

---

## Tuple Slicing
```python
# Basic Slicing
my_tuple[start:stop:step]

# Examples
my_tuple[1:4]    # Elements from index 1 to 3
my_tuple[:3]     # First 3 elements
my_tuple[3:]     # From index 3 to the end
my_tuple[::2]    # Every second element
my_tuple[::-1]   # Reversed tuple
my_tuple[-3:]    # Last 3 elements
```

---

## Basic Operations with Tuples
```python
# Length of a tuple
len(my_tuple)

# Concatenating tuples
new_tuple = my_tuple + (4, 5)

# Repeating elements
repeated_tuple = my_tuple * 3  # (1, 2, 3, 1, 2, 3, 1, 2, 3)

# Checking membership
if 'hello' in my_tuple:
    print("Found!")
```

---

## Tuple Methods
| **Method**       | **Description**                              | **Example**                 |
|------------------|---------------------------------------------|-----------------------------|
| `count(item)`    | Returns the count of the item in the tuple  | `my_tuple.count(2)`         |
| `index(item)`    | Returns the index of the first occurrence   | `my_tuple.index(3)`         |

---

## Unpacking Tuples
```python
# Basic unpacking
a, b, c = (1, 2, 3)
print(a, b, c)  # 1, 2, 3

# Ignoring specific values using `_`
a, _, c = (1, 2, 3)
print(a, c)  # 1, 3

# Variable-length unpacking
a, *rest = (1, 2, 3, 4)
print(a, rest)  # 1, [2, 3, 4]

# Nested tuple unpacking
nested_tuple = (1, (2, 3))
a, (b, c) = nested_tuple
print(a, b, c)  # 1, 2, 3
```

---

## Common Use Cases of Tuples
### Immutable Data
Tuples are used when data should not change:
```python
coordinates = (10.5, 20.3)
```

### Returning Multiple Values
```python
def get_values():
    return (1, 2, 3)

a, b, c = get_values()
```

### Dictionary Keys
Tuples can be used as keys in dictionaries:
```python
dict_with_tuple_key = { (1, 2): "value" }
```

### Iterating with `enumerate`
```python
for index, value in enumerate(['a', 'b', 'c']):
    print(index, value)
```

---

## Key Points
1. **Immutable**: Tuples cannot be changed after creation.
2. **Ordered**: Tuples maintain the order of elements.
3. **Supports Heterogeneous Data**: Tuples can contain mixed data types.
4. **Hashable**: Tuples can be used as keys in dictionaries if all elements are hashable.
5. **Memory Efficient**: Tuples are more memory-efficient than lists.
