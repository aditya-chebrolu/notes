## namedtuple()

### Methods:
| **Method**             | **Description**                                                   | **Usage**                                                                 |
|------------------------|-------------------------------------------------------------------|---------------------------------------------------------------------------|
| `_make(iterable)`      | Create a namedtuple instance from an iterable.                   | `Point._make([10, 20])`                                                   |
| `_asdict()`            | Return a new dictionary mapping field names to values.           | `p._asdict()`                                                             |
| `_replace(**kwargs)`   | Return a new instance with specified fields replaced.            | `p._replace(x=30)`                                                        |
| `_fields`              | Tuple of field names.                                            | `Point._fields`                                                           |
| `_field_defaults`      | Dictionary of field default values.                              | `Point._field_defaults`                                                   |
| `_source`              | String representation of the namedtuple class.                  | `Point._source`                                                           |
| `__doc__`              | Documentation string of the namedtuple.                         | `Point.__doc__`                                                           |
| `__name__`             | Name of the namedtuple class.                                    | `Point.__name__`                                                          |

### Use Cases:
- Creating lightweight object-like structures.
- Accessing elements by name instead of index.
- Easy conversion to dictionary for serialization.

### Example:
```python
from collections import namedtuple
Point = namedtuple('Point', ['x', 'y'])
p = Point(10, 20)
print(p.x, p.y)  # Output: 10 20
p2 = Point._make([30, 40])
print(p2._asdict())  # Output: {'x': 30, 'y': 40}
```

## deque

### Methods:
| **Method**            | **Description**                                                                 | **Usage**                                            |
|-----------------------|---------------------------------------------------------------------------------|------------------------------------------------------|
| `append(x)`           | Add `x` to the right end of the deque.                                          | `d.append(4)`                                        |
| `appendleft(x)`       | Add `x` to the left end of the deque.                                           | `d.appendleft(0)`                                   |
| `clear()`             | Remove all elements from the deque.                                             | `d.clear()`                                         |
| `copy()`              | Create a shallow copy of the deque.                                             | `d2 = d.copy()`                                     |
| `count(x)`            | Count the number of occurrences of `x` in the deque.                            | `d.count(2)`                                        |
| `extend(iterable)`    | Extend the right side by appending elements from the iterable.                  | `d.extend([5, 6])`                                  |
| `extendleft(iterable)`| Extend the left side by appending elements from the iterable in reverse order.   | `d.extendleft([7, 8])`                              |
| `index(x, start, stop)`| Return the position of `x` between `start` and `stop`.                          | `d.index(2)`                                        |
| `insert(i, x)`        | Insert `x` into position `i`.                                                   | `d.insert(2, 9)`                                    |
| `pop()`               | Remove and return an element from the right end.                                | `d.pop()`                                           |
| `popleft()`           | Remove and return an element from the left end.                                 | `d.popleft()`                                       |
| `remove(value)`       | Remove the first occurrence of `value`.                                         | `d.remove(3)`                                       |
| `reverse()`           | Reverse the elements of the deque in place.                                     | `d.reverse()`                                       |
| `rotate(n)`           | Rotate the deque `n` steps to the right (negative values rotate left).          | `d.rotate(2)`                                       |

### Use Cases:
- Implementing efficient queues and stacks.
- Managing sliding windows for algorithms.
- Maintaining ordered collections with fast insertions/removals at both ends.

### Example:
```python
from collections import deque
d = deque([1, 2, 3])
d.append(4)
d.appendleft(0)
d.rotate(1)
print(d)  # Output: deque([4, 0, 1, 2, 3])
```

## ChainMap

### Methods:
| **Method**              | **Description**                                                               | **Usage**                                  |
|-------------------------|-------------------------------------------------------------------------------|--------------------------------------------|
| `maps`                  | List of mappings in the ChainMap.                                             | `config.maps`                              |
| `new_child(m=None)`     | Add a new child mapping to the ChainMap.                                       | `config.new_child({'debug': True})`       |
| `parents`               | Return a new ChainMap without the first mapping.                              | `config.parents`                           |
| `reversed(chainmap)`    | Reverse the order of maps in the ChainMap.                                    | `reversed(config)`                         |
| `items()`               | Return a view of the ChainMap's items.                                        | `config.items()`                           |
| `keys()`                | Return a view of the ChainMap's keys.                                         | `config.keys()`                            |
| `values()`              | Return a view of the ChainMap's values.                                       | `config.values()`                          |
| `get(key[, default])`   | Get the value for `key`, defaulting if not found.                             | `config.get('theme')`                      |
| `pop(key[, default])`   | Remove and return the value for `key`, defaulting if not found.               | `config.pop('theme')`                      |
| `popitem()`             | Remove and return the last key-value pair as a tuple.                         | `config.popitem()`                         |
| `clear()`               | Clear all mappings in the ChainMap.                                           | `config.clear()`                           |
| `update(m[, **kwargs])` | Update the ChainMap with the key-value pairs from another mapping.            | `config.update({'theme': 'blue'})`         |

### Use Cases:
- Combining multiple dictionaries for a unified lookup.
- Managing dynamic scopes of variables (e.g., configuration settings).

### Example:
```python
from collections import ChainMap
defaults = {'theme': 'light', 'version': 1.0}
overrides = {'theme': 'dark'}
config = ChainMap(overrides, defaults)
print(config['theme'])  # Output: dark
config = config.new_child({'debug': True})
print(config['debug'])  # Output: True
```

## Counter

### Methods:
| **Method**                    | **Description**                                                                 | **Usage**                                      |
|-------------------------------|---------------------------------------------------------------------------------|------------------------------------------------|
| `elements()`                  | Return an iterator over elements, repeating each as many times as its count.    | `list(counts.elements())`                     |
| `most_common([n])`            | Return a list of the `n` most common elements and their counts.                 | `counts.most_common(2)`                       |
| `subtract(iterable_or_mapping)`| Subtract counts using another iterable or mapping.                              | `counts.subtract({'a': 1})`                   |
| `update([iterable-or-mapping])`| Add counts from another iterable or mapping.                                    | `counts.update('banana')`                     |
| `total()`                     | Return the sum of all counts.                                                   | `counts.total()`                              |
| `fromkeys(iterable, v=0)`     | Create a Counter with specified keys and values initialized to `v`.             | `Counter.fromkeys('abc', 2)`                  |
| `clear()`                     | Remove all elements from the Counter.                                           | `counts.clear()`                              |
| `copy()`                      | Create a shallow copy of the Counter.                                           | `counts.copy()`                               |
| `dict()`                      | Convert the Counter to a regular dictionary.                                    | `counts.dict()`                               |
| `get(key[, default])`         | Get the value for `key`, defaulting if not found.                               | `counts.get('a')`                             |
| `pop(key[, default])`         | Remove and return the value for `key`, defaulting if not found.                 | `counts.pop('a')`                             |

### Use Cases:
- Tallying and counting elements efficiently.
- Finding the most common elements in a dataset.
- Managing histograms or frequency distributions.

### Example:
```python
from collections import Counter
counts = Counter('abracadabra')
print(counts.most_common(2))  # Output: [('a', 5), ('b', 2)]
counts.update('banana')
print(counts)  # Updated counts
```
