# Python Booleans Cheatsheet

## Overview of Boolean Data Type
| **Data Type**     | **Description**                          | **Example**         |
|--------------------|------------------------------------------|---------------------|
| `bool`            | Represents truth values: `True` or `False`. | `x = True`         |

---

## Boolean Values
| **Value**          | **Description**                          | **Example**                         |
|--------------------|------------------------------------------|-------------------------------------|
| `True`             | Represents truth.                       | `x = True`                          |
| `False`            | Represents falsehood.                   | `y = False`                         |

---

## Boolean Operations
| **Operation**      | **Code Example**                        | **Description**                          | **Result**                 |
|--------------------|------------------------------------------|------------------------------------------|---------------------------|
| Logical AND        | `a and b`                               | Returns `True` if both `a` and `b` are `True`. | `True and False → False` |
| Logical OR         | `a or b`                                | Returns `True` if either `a` or `b` is `True`. | `True or False → True`   |
| Logical NOT        | `not a`                                 | Returns the opposite of `a`.                  | `not True → False`       |

---

## Boolean Comparisons
| **Comparison**     | **Code Example**                        | **Description**                          | **Result**                 |
|--------------------|------------------------------------------|------------------------------------------|---------------------------|
| Equals             | `a == b`                                | Checks if `a` is equal to `b`.           | `3 == 3 → True`          |
| Not equals         | `a != b`                                | Checks if `a` is not equal to `b`.       | `3 != 4 → True`          |
| Greater than       | `a > b`                                 | Checks if `a` is greater than `b`.       | `4 > 3 → True`           |
| Less than          | `a < b`                                 | Checks if `a` is less than `b`.          | `2 < 5 → True`           |
| Greater than or equal | `a >= b`                             | Checks if `a` is greater than or equal to `b`. | `3 >= 3 → True`   |
| Less than or equal | `a <= b`                                | Checks if `a` is less than or equal to `b`. | `2 <= 3 → True`   |

---

## Boolean Conversions
| **Operation**      | **Code Example**                        | **Description**                          | **Result**                  |
|--------------------|------------------------------------------|------------------------------------------|----------------------------|
| Convert to boolean | `bool(value)`                           | Converts `value` to a boolean.           | `bool(0) → False`          |
| Truthy values      | `bool(value)`                           | Non-zero numbers, non-empty collections. | `bool(1) → True`           |
| Falsy values       | `bool(value)`                           | `0`, `None`, `""`, `[]`, `{}`.          | `bool("") → False`         |

---

## Truthy and Falsy Examples
| **Value**          | **Type**               | **Bool Conversion** |
|--------------------|------------------------|---------------------|
| `0`                | Integer               | `False`             |
| `1`                | Integer               | `True`              |
| `""`               | String (empty)        | `False`             |
| `"Hello"`          | String (non-empty)    | `True`              |
| `[]`               | List (empty)          | `False`             |
| `[1, 2, 3]`        | List (non-empty)      | `True`              |
| `{}`               | Dictionary (empty)    | `False`             |
| `{key: value}`     | Dictionary (non-empty)| `True`              |
| `None`             | Special object        | `False`             |

---

## Boolean Operations with Short-Circuiting
| **Operation**      | **Code Example**                        | **Description**                          | **Notes**                  |
|--------------------|------------------------------------------|------------------------------------------|---------------------------|
| AND short-circuit  | `a and b`                               | If `a` is `False`, `b` is not evaluated. | Saves computation time.   |
| OR short-circuit   | `a or b`                                | If `a` is `True`, `b` is not evaluated.  | Saves computation time.   |

---

## Examples

```python
# Boolean values
x = True
y = False
print(x and y)         # False
print(x or y)          # True
print(not x)           # False

# Comparisons
print(3 > 2)           # True
print(4 <= 4)          # True
print(5 == 5.0)        # True (int and float comparison)

# Boolean conversions
print(bool(0))         # False
print(bool("Python"))  # True
print(bool([]))        # False

# Short-circuiting
a = False
b = print("Hello")     # `b` won't be executed because `a` is False
print(a and b)         # False
```
