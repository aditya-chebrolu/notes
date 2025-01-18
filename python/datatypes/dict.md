# Python Dictionaries Cheatsheet

## Basics
| **Operation**                  | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Create a dictionary            | `d = {"key": "value"}`             | Creates a dictionary.                            |
| Access value by key            | `d["key"]`                         | Retrieves the value associated with `"key"`.     |
| Add or update key-value pair   | `d["new_key"] = "new_value"`        | Adds or updates the key-value pair.              |
| Delete key-value pair          | `del d["key"]`                     | Removes the specified key from the dictionary.   |
| Check if key exists            | `"key" in d`                       | Returns `True` if the key exists.                |
| Dictionary length              | `len(d)`                           | Returns the number of key-value pairs.           |

---

## Common Methods
| **Method**                     | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Get value with default         | `d.get("key", default_value)`       | Returns value or default if key is missing.       |
| Remove and return value         | `d.pop("key", default_value)`       | Removes key and returns its value.                |
| Remove and return last item    | `d.popitem()`                      | Removes and returns the last inserted key-value.  |
| Get all keys                   | `d.keys()`                         | Returns a view of all keys.                       |
| Get all values                 | `d.values()`                       | Returns a view of all values.                     |
| Get all key-value pairs        | `d.items()`                        | Returns a view of `(key, value)` pairs.           |
| Clear dictionary               | `d.clear()`                        | Removes all key-value pairs.                      |
| Copy dictionary                | `d.copy()`                         | Returns a shallow copy of the dictionary.         |
| Update dictionary              | `d.update(other_dict)`             | Updates `d` with key-value pairs from `other_dict`.|

---

## Dictionary Comprehensions
| **Operation**                  | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Basic comprehension            | `{k: v for k, v in iterable}`      | Creates a dictionary from an iterable.            |
| With condition                 | `{k: v for k, v in iterable if cond}` | Filters items based on a condition.              |

---

## Iteration
| **Operation**                  | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Iterate over keys              | `for key in d:`                    | Iterates through all keys.                        |
| Iterate over values            | `for value in d.values():`         | Iterates through all values.                      |
| Iterate over key-value pairs   | `for k, v in d.items():`           | Iterates through `(key, value)` pairs.            |

---

## Advanced Operations
| **Operation**                  | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Merge dictionaries (Python ≥3.9)| `d1 \| d2` or `{**d1, **d2}`                        | Merges `d1` and `d2` into a new dictionary.       |
| Merge in-place (Python ≥3.9)   | `d1 \|= d2`                         | Updates `d1` with `d2`.                           |
| Default dictionary for missing keys | `from collections import defaultdict; d = defaultdict(default_factory)` | Provides default values for missing keys.        |
| Count items                    | `from collections import Counter; Counter(iterable)` | Counts occurrences of elements.                 |

---

## Use Cases
| **Use Case**                   | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Reverse key-value pairs        | `{v: k for k, v in d.items()}`     | Creates a dictionary with swapped keys/values.    |
| Group by key                   | `{k: [v for v in iterable if cond]}`| Groups values by key.                             |
| Sort by keys                   | `dict(sorted(d.items()))`          | Returns a dictionary sorted by keys.              |
| Sort by values                 | `dict(sorted(d.items(), key=lambda x: x[1]))` | Sorts dictionary by values.                     |

---

## Useful Tips
- **Constructor shortcut**: Use `dict(a=1, b=2)` to create `{"a": 1, "b": 2}`.
- **Access nested dictionaries**: Use `d["key1"]["key2"]` for nested access.
- **Set default if key doesn’t exist**: `d.setdefault("key", default_value)` adds the key with `default_value` if it doesn’t exist.
- **Combine keys and values from two lists**: `dict(zip(keys, values))` creates a dictionary.

