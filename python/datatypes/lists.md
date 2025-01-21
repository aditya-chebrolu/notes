# Python Lists Cheatsheet

## Basics
| **Operation**                  | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Create a list                  | `lst = [1, 2, 3]`                  | Creates a list with initial values.               |
| Access element by index        | `lst[0]`                           | Accesses the first element.                       |
| Negative indexing              | `lst[-1]`                          | Accesses the last element.                        |
| Slicing                        | `lst[start:end:step]`              | Returns a slice of the list.                      |
| Length of a list               | `len(lst)`                         | Returns the number of elements in the list.       |
| Check existence of value       | `value in lst`                     | Returns `True` if `value` exists in the list.     |

---

## Common Methods
| **Method**                     | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Append element                 | `lst.append(4)`                    | Adds an element to the end of the list.           |
| Extend list                    | `lst.extend([4, 5])`               | Adds multiple elements to the end.                |
| Insert at index                | `lst.insert(1, "value")`           | Inserts value at the specified index.             |
| Remove element by value        | `lst.remove("value")`              | Removes the first occurrence of the value.        |
| Remove and return by index     | `lst.pop(1)`                       | Removes and returns the element at index.         |
| Remove last element            | `lst.pop()`                        | Removes and returns the last element.             |
| Clear all elements             | `lst.clear()`                      | Removes all elements from the list.               |
| Find index of value            | `lst.index("value")`               | Returns the index of the first occurrence.        |
| Count occurrences              | `lst.count("value")`               | Counts the occurrences of the value.              |
| Reverse the list               | `lst.reverse()`                    | Reverses the list in place.                       |
| Sort the list                  | `lst.sort()`                       | Sorts the list in ascending order.                |
| Sort the list by key           | `lst.sort(key=len)`                | Sorts the list in ascending order.                |
| Sort in descending order       | `lst.sort(reverse=True)`           | Sorts the list in descending order.               |
| Copy the list                  | `lst.copy()`                       | Returns a shallow copy of the list.               |

---

## List Comprehensions
| **Operation**                  | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Basic comprehension            | `[x**2 for x in range(5)]`         | Creates `[0, 1, 4, 9, 16]`.                       |
| With condition                 | `[x for x in lst if x > 2]`        | Filters elements greater than 2.                  |
| Nested comprehension           | `[[x, y] for x in range(2) for y in range(2)]` | Creates nested lists.                             |

---

## Iteration
| **Operation**                  | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Iterate through elements       | `for x in lst:`                    | Loops through each element in the list.           |
| Iterate with index             | `for i, x in enumerate(lst):`      | Loops with both index and element.                |
| Enumerate elements             | `list(enumerate(lst))`             | Returns a list of `(index, value)` pairs.         |

---

## Advanced Operations
| **Operation**                  | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Concatenate lists              | `lst1 + lst2`                      | Combines two lists into a new one.                |
| Repeat elements                | `lst * 3`                          | Repeats elements of the list 3 times.             |
| Flatten nested list            | `[y for x in nested for y in x]`   | Flattens a nested list.                           |
| Zip multiple lists             | `list(zip(lst1, lst2))`            | Combines elements of lists into pairs.            |
| Unzip lists                    | `zip(*zipped)`                     | Splits paired elements into individual lists.     |
| Find max value                 | `max(lst)`                         | Returns the maximum value in the list.            |
| Find min value                 | `min(lst)`                         | Returns the minimum value in the list.            |
| Sum of elements                | `sum(lst)`                         | Returns the sum of all elements.                  |

---

## List Slicing
| **Operation**                  | **Code Example**       | **Description**                                     |
|--------------------------------|-------------------------|----------------------------------------------------|
| Basic slicing                  | `lst[start:end]`       | Returns elements from `start` (inclusive) to `end` (exclusive). |
| Full slicing                   | `lst[:]`               | Returns a shallow copy of the entire list.         |
| Start index only               | `lst[start:]`          | Returns elements from `start` to the end of the list. |
| End index only                 | `lst[:end]`            | Returns elements from the start to `end` (exclusive). |
| With step                      | `lst[start:end:step]`  | Skips `step` elements between indices.             |
| Negative step (reverse slicing)| `lst[::-1]`            | Returns the list in reverse order.                 |
| Advanced slicing example       | `lst[1:1:2]`           | Returns an empty list (`start` and `end` are the same). |

---

## Tips and Tricks
| **Tip**                        | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Remove duplicates              | `list(set(lst))`                   | Converts list to set and back to remove duplicates.|
| Reverse without modifying      | `lst[::-1]`                        | Creates a reversed copy of the list.              |
| Sort without modifying         | `sorted(lst)`                      | Returns a sorted copy of the list.                |
| Initialize list of size n      | `[0] * n`                          | Creates a list with `n` zeroes.                   |
| Find common elements           | `set(lst1) & set(lst2)`            | Finds common elements between two lists.          |
