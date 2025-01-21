### **Python Context Managers Cheatsheet**

| **Category**          | **Operation**                    | **Code Example**                                                | **Explanation**                                                                                 |
|------------------------|-----------------------------------|------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| **File Handling**      | Open a file for reading          | ```python with open('example.txt', 'r') as file: print(file.read())``` | Automatically closes the file after reading.                                                  |
|                        | Open a file for appending        | ```python with open('example.txt', 'a') as file: file.write('More text')``` | Appends data to the file and ensures closure.                                                 |
| **Threading**          | RLock (reentrant lock)           | ```python from threading import RLock lock = RLock() with lock: print('Reentrant lock acquired')``` | Allows the same thread to acquire the lock multiple times.                                    |
| **File Compression**   | Manage ZIP files                | ```python import zipfile with zipfile.ZipFile('example.zip', 'r') as zip: print(zip.namelist())``` | Ensures the ZIP file is properly closed after operations.                                     |
| **Temporary Files**    | Temporary directory              | ```python import tempfile with tempfile.TemporaryDirectory() as tmpdir: print(f'Temp dir: {tmpdir}')``` | Automatically deletes the temporary directory when done.                                      |
| **Custom Context Managers** | Timing operations           | ```python import time class Timer: def __enter__(self): self.start = time.time() return self def __exit__(self, *args): print(f'Time elapsed: {time.time() - self.start}') with Timer(): sum(range(1000000))``` | Times the block of code and prints the elapsed time.                                          |
| **Networking**         | Manage server sockets           | ```python import socket with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s: s.bind(('localhost', 8080)); s.listen()``` | Ensures the socket is closed when the server stops.                                           |
|                        | HTTP request context             | ```python import requests with requests.get('http://example.com') as response: print(response.text)``` | Simplifies HTTP requests by automatically closing the connection.                             |
| **Resource Cleanup**   | Ignore multiple exceptions       | ```python from contextlib import suppress with suppress(FileNotFoundError, ZeroDivisionError): 1 / 0; open('nonexistent.txt')``` | Suppresses both `FileNotFoundError` and `ZeroDivisionError`.                                   |
| **Chaining Contexts**  | Combine file handling with locks | ```python import threading with threading.Lock() as lock, open('example.txt') as file: print(file.read())``` | Combines multiple context managers in a single `with` statement.                              |
| **Database Connections** | Commit transactions on success | ```python import sqlite3 with sqlite3.connect('example.db') as conn: conn.execute('CREATE TABLE IF NOT EXISTS test (id INT)')``` | Automatically commits the transaction unless an exception occurs.                             |
| **Logging**            | Add logging context             | ```python import logging from contextlib import redirect_stdout with open('log.txt', 'w') as log_file, redirect_stdout(log_file): print('Logging output')``` | Redirects `stdout` (e.g., print statements) to a log file.                                    |

---

### **Advanced Context Manager Examples**

#### **Custom Context Managers with `contextlib`**
```python
from contextlib import contextmanager

@contextmanager
def open_file(file_name, mode):
    f = open(file_name, mode)
    try:
        yield f
    finally:
        f.close()

with open_file('example.txt', 'w') as file:
    file.write('Custom context manager!')
```
**Explanation:** A simple custom context manager using `@contextmanager` to manage file operations.

---

#### **Nested Context Managers**
```python
with open('file1.txt', 'r') as file1, open('file2.txt', 'w') as file2:
    data = file1.read()
    file2.write(data)
```
**Explanation:** Combines two file operations in a single `with` statement, ensuring both files are properly closed.

---

#### **Context Management with Resource Pools**
```python
from multiprocessing import Pool

with Pool(4) as pool:
    results = pool.map(lambda x: x * x, range(10))
    print(results)
```
**Explanation:** Manages a process pool, ensuring it is properly terminated after execution.

---

#### **Timer as a Context Manager**
```python
import time

class Timer:
    def __enter__(self):
        self.start = time.time()
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        print(f"Elapsed time: {time.time() - self.start:.2f}s")

with Timer() as timer:
    sum(range(1000000))
```
**Explanation:** Measures the time taken for code execution within the `with` block.

---

#### **Suppress Multiple Exceptions**
```python
from contextlib import suppress

with suppress(ZeroDivisionError, FileNotFoundError):
    1 / 0  # Suppressed
    open('nonexistent.txt')  # Suppressed
```
**Explanation:** Ignores multiple exceptions without interrupting program execution.

---

#### **Managing Configurations**
```python
import os
from contextlib import contextmanager

@contextmanager
def temporary_environment_variable(key, value):
    old_value = os.environ.get(key)
    os.environ[key] = value
    try:
        yield
    finally:
        if old_value is None:
            del os.environ[key]
        else:
            os.environ[key] = old_value

with temporary_environment_variable('TEST_VAR', '123'):
    print(os.environ.get('TEST_VAR'))  # Output: 123
```
**Explanation:** Temporarily modifies an environment variable, restoring it afterward.
