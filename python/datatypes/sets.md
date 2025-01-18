# Python Sets Cheatsheet

## Overview of Sets
| **Type**        | **Description**                                   | **Example**                |
|------------------|---------------------------------------------------|----------------------------|
| `set`           | Unordered, mutable collection of unique elements. | `s = {1, 2, 3}`            |
| `frozenset`     | Immutable version of a set.                      | `fs = frozenset([1, 2, 3])`|

---

## Set Creation
| **Operation**                  | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Create a set                   | `s = {1, 2, 3}`                    | Creates a set with elements 1, 2, and 3.         |
| Create an empty set            | `s = set()`                        | Creates an empty set (note: `{}` is a dictionary).|
| Convert iterable to set        | `s = set([1, 2, 2, 3])`            | Converts iterable to a set (duplicates removed). |

---

## Set Methods
| **Method**                     | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Add element                    | `s.add(4)`                         | Adds 4 to the set `s`.                            |
| Remove element                 | `s.remove(4)`                      | Removes 4 from `s` (raises `KeyError` if not found).|
| Discard element                | `s.discard(4)`                     | Removes 4 from `s` (no error if not found).       |
| Remove and return random item  | `s.pop()`                          | Removes and returns a random element.            |
| Clear set                      | `s.clear()`                        | Removes all elements from `s`.                   |
| Copy set                       | `s.copy()`                         | Returns a shallow copy of `s`.                   |

---

## Set Operations
| **Operation**                  | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Union                          | `s1 | s2` or `s1.union(s2)`        | Returns a set with elements from both sets.       |
| Intersection                   | `s1 & s2` or `s1.intersection(s2)` | Returns a set with elements common to both sets.  |
| Difference                     | `s1 - s2` or `s1.difference(s2)`   | Returns a set with elements in `s1` but not `s2`. |
| Symmetric difference           | `s1 ^ s2` or `s1.symmetric_difference(s2)` | Returns a set with elements in either set, but not both. |
| Subset check                   | `s1 <= s2` or `s1.issubset(s2)`    | Checks if `s1` is a subset of `s2`.               |
| Superset check                 | `s1 >= s2` or `s1.issuperset(s2)`  | Checks if `s1` is a superset of `s2`.             |
| Disjoint check                 | `s1.isdisjoint(s2)`                | Returns `True` if sets have no common elements.   |

---

## Iteration
| **Operation**                  | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Iterate through elements       | `for elem in s:`                   | Loops through each element in the set.            |

---

## Set Comparisons
| **Operation**                  | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Equal sets                     | `s1 == s2`                         | Returns `True` if both sets have the same elements.|
| Not equal sets                 | `s1 != s2`                         | Returns `True` if sets have different elements.   |

---

## Frozen Sets
| **Operation**                  | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Create frozenset               | `fs = frozenset([1, 2, 3])`        | Creates an immutable set.                        |
| Frozenset operations           | `fs1 | fs2`, `fs1 & fs2`           | Supports all standard set operations.            |

---

## Examples of Set Operations

```python
# Basic set operations
s1 = {1, 2, 3}
s2 = {3, 4, 5}

# Union
print(s1 | s2)          # {1, 2, 3, 4, 5}

# Intersection
print(s1 & s2)          # {3}

# Difference
print(s1 - s2)          # {1, 2}
print(s2 - s1)          # {4, 5}

# Symmetric difference
print(s1 ^ s2)          # {1, 2, 4, 5}

# Subset and superset
print(s1 <= {1, 2, 3, 4})   # True (s1 is a subset)
print({1, 2, 3, 4} >= s1)   # True (superset check)

# Disjoint check
print(s1.isdisjoint({4, 5})) # True (no common elements)

# Modifying sets
s1.add(6)
print(s1)               # {1, 2, 3, 6}
s1.remove(3)
print(s1)               # {1, 2, 6}

# Frozen sets
fs = frozenset([1, 2, 3])
# fs.add(4)             # Raises AttributeError (immutable)
print(fs | {4, 5})      # {1, 2, 3, 4, 5}
```
