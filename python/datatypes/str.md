# Python Strings Cheatsheet

## Basics
| **Operation**                  | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Create a string                | `s = "Hello"`                      | Creates a string.                                 |
| Multiline string               | `s = """Hello\nWorld"""`           | Creates a multiline string.                      |
| Access character by index      | `s[0]`                             | Accesses the first character (`H`).              |
| Negative indexing              | `s[-1]`                            | Accesses the last character (`o`).               |
| String length                  | `len(s)`                           | Returns the length of the string.                |
| Iterate over characters        | `for c in s:`                      | Loops through each character in the string.      |

---

## String Methods
| **Method**                     | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Convert to uppercase           | `s.upper()`                        | Converts all characters to uppercase.            |
| Convert to lowercase           | `s.lower()`                        | Converts all characters to lowercase.            |
| Capitalize                     | `s.capitalize()`                   | Capitalizes the first character.                 |
| Title case                     | `s.title()`                        | Capitalizes the first character of each word.    |
| Strip whitespace               | `s.strip()`                        | Removes leading and trailing whitespace.         |
| Left strip                     | `s.lstrip()`                       | Removes leading whitespace.                      |
| Right strip                    | `s.rstrip()`                       | Removes trailing whitespace.                     |
| Replace                        | `s.replace("old", "new")`          | Replaces occurrences of a substring.             |
| Split string                   | `s.split(" ")`                     | Splits string into a list by delimiter.          |
| Join strings                   | `" ".join(lst)`                    | Joins list elements into a string.               |
| Count substring                | `s.count("sub")`                   | Counts occurrences of a substring.               |
| Find substring                 | `s.find("sub")`                    | Returns index of the first occurrence.           |
| Reverse find substring          | `s.rfind("sub")`                   | Returns index of the last occurrence.            |
| Starts with substring          | `s.startswith("sub")`              | Checks if string starts with `sub`.              |
| Ends with substring            | `s.endswith("sub")`                | Checks if string ends with `sub`.                |
| Center alignment               | `s.center(width)`                  | Centers the string within the specified width.   |
| Left alignment                 | `s.ljust(width)`                   | Left-aligns the string within the width.         |
| Right alignment                | `s.rjust(width)`                   | Right-aligns the string within the width.        |

---

## String Slicing
| **Operation**                  | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Slice string                   | `s[start:end]`                     | Returns substring from `start` to `end` (exclusive).|
| Slice with step                | `s[start:end:step]`                | Returns substring with specified step size.       |
| Reverse string                 | `s[::-1]`                          | Returns the string in reverse order.             |
| Example: advanced slicing      | `s[1:1:2]`                         | Returns an empty string (start equals end).       |

---

## String Formatting
| **Operation**                  | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Old-style formatting           | `"Hello %s" % name`                | Inserts `name` into the string.                  |
| Format method                  | `"Hello {}".format(name)`          | Inserts `name` using `.format()`.                |
| f-string formatting            | `f"Hello {name}"`                  | Inserts `name` using f-strings.                  |

---

## String Testing Methods
| **Method**                     | **Code Example**                   | **Description**                                     |
|--------------------------------|-------------------------------------|---------------------------------------------------|
| Check if alphanumeric          | `s.isalnum()`                      | Returns `True` if all characters are alphanumeric.|
| Check if alphabetic            | `s.isalpha()`                      | Returns `True` if all characters are alphabetic.  |
| Check if numeric               | `s.isdigit()`                      | Returns `True` if all characters are numeric.     |
| Check if lowercase             | `s.islower()`                      | Returns `True` if all characters are lowercase.   |
| Check if uppercase             | `s.isupper()`                      | Returns `True` if all characters are uppercase.   |
| Check if titlecase             | `s.istitle()`                      | Returns `True` if string is in title case.        |
| Check if whitespace            | `s.isspace()`                      | Returns `True` if all characters are whitespace.  |

---

## String Escape Sequences
| **Escape Sequence**            | **Description**                                     | **Example Output**          |
|--------------------------------|---------------------------------------------------|-----------------------------|
| `\n`                           | Newline                                           | `"Hello\nWorld"` → `Hello` <br> `World` |
| `\t`                           | Tab                                               | `"Hello\tWorld"` → `Hello   World` |
| `\\`                           | Backslash                                        | `"C:\\path"` → `C:\path`    |
| `\'`                           | Single quote                                      | `"It\'s"` → `It's`          |
| `\"`                           | Double quote                                      | `"\"Hello\""` → `"Hello"`   |

---

## Examples of String Operations

```python
# Basic string operations
s = "Hello World"
print(s.upper())              # "HELLO WORLD"
print(s.lower())              # "hello world"
print(s.split())              # ["Hello", "World"]
print("-".join(["a", "b", "c"])) # "a-b-c"

# Slicing
print(s[0:5])                 # "Hello"
print(s[::-1])                # "dlroW olleH"

# Searching and replacing
print(s.find("World"))        # 6
print(s.replace("World", "Python"))  # "Hello Python"

# Formatting
name = "Alice"
age = 30
print(f"{name} is {age} years old.")  # "Alice is 30 years old"

# Testing
print("123abc".isalnum())     # True
print("abc".isalpha())        # True
print("   ".isspace())        # True
```
