# **Iterators Cheatsheet**

## **Index**
1. [Basics of Iterators](#basics-of-iterators)
2. [Zipping and Unzipping](#zipping-and-unzipping)
3. [Iterators as Function Arguments](#iterators-as-function-arguments)
4. [Comprehensions and Generators](#comprehensions-and-generators)
5. [Useful Iterator Functions](#useful-iterator-functions)
6. [Custom Iterators](#custom-iterators)
7. [Iterator Protocol](#iterator-protocol)
8. [Advanced Iterator Functions](#advanced-iterator-functions)
9. [Iterator Performance Tips](#iterator-performance-tips)
10. [Debugging Iterators](#debugging-iterators)

---

## Basics of Iterators
- **Definition**: An iterator is an object implementing `__iter__()` and `__next__()` methods.
- **Creating an Iterator**:
  ```python
  lst = [1, 2, 3]
  iterator = iter(lst)
  print(next(iterator))  # Outputs: 1
  print(next(iterator))  # Outputs: 2
  ```

---

## Zipping and Unzipping
- **Zipping**:
  ```python
  names = ['Alice', 'Bob']
  ages = [25, 30]
  print(list(zip(names, ages)))  # Outputs: [('Alice', 25), ('Bob', 30)]
  ```
- **Unzipping**:
  ```python
  zipped = [('Alice', 25), ('Bob', 30)]
  names, ages = zip(*zipped)
  print(names)  # Outputs: ('Alice', 'Bob')
  print(ages)   # Outputs: (25, 30)
  ```

---

## Iterators as Function Arguments
- **Using Multiple Iterables**:
  ```python
  nums1 = [1, 2]
  nums2 = [3, 4]
  results = map(lambda x, y: x + y, nums1, nums2)
  print(list(results))  # Outputs: [4, 6]
  ```

---

## Comprehensions and Generators
- **Generator Expression**:
  ```python
  squares = (x**2 for x in range(5))
  print(next(squares))  # Outputs: 0
  print(list(squares))  # Outputs: [1, 4, 9, 16]
  ```
- **List vs Generator**:
  ```python
  lst_comp = [x**2 for x in range(5)]  # List
  gen_comp = (x**2 for x in range(5))  # Generator
  ```

---

## Useful Iterator Functions
- **`map(func, *iterables)`**:
  ```python
  nums = [1, 2, 3]
  print(list(map(lambda x: x * 2, nums)))  # Outputs: [2, 4, 6]
  ```
- **`filter(func, iterable)`**:
  ```python
  nums = [1, 2, 3, 4]
  print(list(filter(lambda x: x % 2 == 0, nums)))  # Outputs: [2, 4]
  ```
- **`reduce(func, iterable)`**:
  ```python
  from functools import reduce
  nums = [1, 2, 3, 4]
  print(reduce(lambda x, y: x * y, nums))  # Outputs: 24
  ```
- **`itertools.chain(*iterables)`**:
  ```python
  from itertools import chain
  print(list(chain([1, 2], [3, 4])))  # Outputs: [1, 2, 3, 4]
  ```
- **`itertools.islice(iterable, start, stop, step)`**:
  ```python
  from itertools import islice
  print(list(islice(range(10), 2, 8, 2)))  # Outputs: [2, 4, 6]
  ```

---

## Custom Iterators
- **Creating Custom Iterator**:
  ```python
  class Counter:
      def __init__(self, start, end):
          self.current = start
          self.end = end
      
      def __iter__(self):
          return self
      
      def __next__(self):
          if self.current > self.end:
              raise StopIteration
          self.current += 1
          return self.current - 1

  for num in Counter(1, 3):
      print(num)  # Outputs: 1, 2, 3
  ```

---

## Iterator Protocol
- **`__iter__`**: Returns the iterator object itself.
- **`__next__`**: Returns the next item; raises `StopIteration` when done.

---

## Advanced Iterator Functions
- **`enumerate(iterable, start=0)`**:
  ```python
  items = ['a', 'b']
  for idx, val in enumerate(items, start=1):
      print(idx, val)  # Outputs: 1 a, 2 b
  ```
- **`sorted(iterable, key, reverse)`**:
  ```python
  nums = [3, 1, 4]
  print(sorted(nums))  # Outputs: [1, 3, 4]
  ```
- **`reversed(iterable)`**:
  ```python
  nums = [1, 2]
  print(list(reversed(nums)))  # Outputs: [2, 1]
  ```
- **`all(iterable)` / `any(iterable)`**:
  ```python
  nums = [1, 2, 3]
  print(all(nums))  # Outputs: True
  print(any(nums))  # Outputs: True
  ```

---

## Iterator Performance Tips
- Use **`itertools`** for memory efficiency.
- Use **generator expressions** for large data when storage isn't needed.

---

## Debugging Iterators
- **Inspect Without Consuming**:
  ```python
  from itertools import tee
  nums = iter([1, 2, 3])
  a, b = tee(nums)
  print(list(a))  # Outputs: [1, 2, 3]
  print(list(b))  # Outputs: [1, 2, 3]
  ```
