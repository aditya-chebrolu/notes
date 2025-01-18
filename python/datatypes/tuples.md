# Python Tuples Cheatsheet

## Basics
| **Operation**                  | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Create a tuple                 | `tpl = (1, 2, 3)`                  | Creates a tuple with initial values.               |
| Single-element tuple           | `tpl = (1,)`                       | Requires a comma to distinguish from parentheses.  |
| Access element by index        | `tpl[0]`                           | Accesses the first element.                        |
| Negative indexing              | `tpl[-1]`                          | Accesses the last element.                         |
| Length of a tuple              | `len(tpl)`                         | Returns the number of elements.                    |
| Check existence of value       | `value in tpl`                     | Returns `True` if `value` exists in the tuple.     |

---

## Tuple Methods
| **Method**                     | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Count occurrences              | `tpl.count("value")`               | Counts occurrences of a value in the tuple.        |
| Find index of value            | `tpl.index("value")`               | Returns the index of the first occurrence.         |

---

## Tuple Slicing
| **Operation**                  | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Basic slicing                  | `tpl[start:end]`                   | Returns elements from `start` to `end` (exclusive).|
| Full slicing                   | `tpl[:]`                           | Returns a shallow copy of the entire tuple.        |
| Start index only               | `tpl[start:]`                      | Returns elements from `start` to the end.          |
| End index only                 | `tpl[:end]`                        | Returns elements from the start to `end` (exclusive).|
| With step                      | `tpl[start:end:step]`              | Skips `step` elements between indices.             |
| Negative step (reverse slicing)| `tpl[::-1]`                        | Returns the tuple in reverse order.                |
| Advanced slicing example       | `tpl[1:1:2]`                       | Returns an empty tuple (start equals end).         |

---

## Tuple Operations
| **Operation**                  | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Concatenate tuples             | `tpl1 + tpl2`                      | Combines two tuples into a new one.                |
| Repeat elements                | `tpl * 3`                          | Repeats elements of the tuple 3 times.             |
| Iterate through elements       | `for x in tpl:`                    | Loops through each element in the tuple.           |
| Convert to list                | `list(tpl)`                        | Converts the tuple to a list.                      |
| Convert from list              | `tuple(lst)`                       | Converts a list to a tuple.                        |
| Unpack tuple into variables    | `a, b, c = tpl`                    | Assigns elements to corresponding variables.       |

---

## Advanced Operations
| **Operation**                  | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Nested tuples                  | `tpl = (1, (2, 3))`                | Tuples can contain other tuples.                  |
| Access nested tuple            | `tpl[1][0]`                        | Accesses elements of a nested tuple.              |
| Find max value                 | `max(tpl)`                         | Returns the maximum value in the tuple.           |
| Find min value                 | `min(tpl)`                         | Returns the minimum value in the tuple.           |
| Sum of elements                | `sum(tpl)`                         | Returns the sum of all elements.                  |
| Zip multiple tuples            | `tuple(zip(tpl1, tpl2))`           | Combines elements of tuples into pairs.           |

---

## Key Points
1. **Immutable**: Tuples cannot be changed after creation.
2. **Ordered**: Tuples maintain the order of elements.
3. **Supports Heterogeneous Data**: Tuples can contain mixed data types.
4. **Hashable**: Tuples can be used as keys in dictionaries if all elements are hashable.
5. **Memory Efficient**: Tuples are more memory-efficient than lists.
