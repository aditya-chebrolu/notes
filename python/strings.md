# Python Strings Cheatsheet

## **Index**

1. [String Creation](#1-string-creation)
2. [String Indexing and Slicing](#2-string-indexing-and-slicing)
3. [String Concatenation and Repetition](#3-string-concatenation-and-repetition)
4. [String Methods](#4-string-methods)
   - [Case Manipulation](#41-case-manipulation)
   - [Check Properties](#42-check-properties)
   - [Search and Replace](#43-search-and-replace)
   - [Formatting](#44-formatting)
   - [Splitting and Joining](#45-splitting-and-joining)
   - [Strip Characters](#46-strip-characters)
5. [String Formatting](#5-string-formatting)
   - [f-strings (Python 3.6+)](#51-f-strings-python-36)
   - [str.format()](#52-strformat)
   - [% Formatting](#53--formatting)
6. [Escape Sequences](#6-escape-sequences)
7. [Famous Use Cases](#7-famous-use-cases)
   - [Reverse a String](#71-reverse-a-string)
   - [Check Palindrome](#72-check-palindrome)
   - [Count Vowels in a String](#73-count-vowels-in-a-string)
   - [Remove Duplicates](#74-remove-duplicates)
   - [Replace Multiple Spaces with Single](#75-replace-multiple-spaces-with-single)
8. [Advanced String Manipulation](#8-advanced-string-manipulation)
   - [Raw Strings](#81-raw-strings)
   - [String Translation](#82-string-translation)
9. [Accessing Unicode and Encoding](#9-accessing-unicode-and-encoding)
10. [String Comparison](#10-string-comparison)

---

## **1. String Creation**
```python
# Single and double quotes
s1 = 'Hello'
s2 = "World"

# Triple quotes (for multi-line strings)
s3 = """This is a 
multi-line string."""

# String with escape sequences
s4 = 'I\'m learning Python'
s5 = "Line1\nLine2"

# Raw string (ignores escape sequences)
s6 = r"C:\new_folder"
```

---

## **2. String Indexing and Slicing**
```python
# Access characters by index
s = "Python"
print(s[0])  # 'P'
print(s[-1]) # 'n'

# Slicing
print(s[0:3])  # 'Pyt' (start inclusive, end exclusive)
print(s[::2])  # 'Pto' (step slicing)
print(s[::-1]) # 'nohtyP' (reversing a string)
```

---

## **3. String Concatenation and Repetition**
```python
# Concatenation
s1 = "Hello"
s2 = "World"
print(s1 + " " + s2)  # 'Hello World'

# Repetition
print(s1 * 3)  # 'HelloHelloHello'
```

---

## **4. String Methods**

### **4.1 Case Manipulation**
```python
s = "Python Programming"
print(s.lower())    # 'python programming'
print(s.upper())    # 'PYTHON PROGRAMMING'
print(s.title())    # 'Python Programming'
print(s.capitalize()) # 'Python programming'
print(s.swapcase()) # 'pYTHON pROGRAMMING'
```

### **4.2 Check Properties**
```python
s = "12345"
print(s.isdigit())   # True
print(s.isalpha())   # False
print(s.isalnum())   # True (alphanumeric)
print(s.islower())   # False
print(s.isupper())   # False
print(s.isspace())   # False
```

### **4.3 Search and Replace**
```python
s = "hello world"
print(s.find('world'))   # 6 (returns -1 if not found)
print(s.index('world'))  # 6 (raises ValueError if not found)
print(s.rfind('o'))      # 7 (rightmost occurrence)
print(s.count('l'))      # 3
print(s.replace('world', 'Python'))  # 'hello Python'
```

### **4.4 Formatting**
```python
s = "Python"
print(s.center(20, '-'))    # '-------Python-------'
print(s.ljust(20, '*'))     # 'Python*************'
print(s.rjust(20, '*'))     # '*************Python'
print(s.zfill(10))          # '0000Python'
```

### **4.5 Splitting and Joining**
```python
s = "apple,banana,cherry"
print(s.split(','))  # ['apple', 'banana', 'cherry']
print(s.splitlines()) # Split by newline

words = ['Python', 'is', 'awesome']
print(' '.join(words))  # 'Python is awesome'
```

### **4.6 Strip Characters**
```python
s = "   hello world   "
print(s.strip())      # 'hello world'
print(s.lstrip())     # 'hello world   '
print(s.rstrip())     # '   hello world'
print("$$hello$$".strip('$'))  # 'hello'
```

---

## **5. String Formatting**

### **5.1 f-strings (Python 3.6+)**
```python
name = "Alice"
age = 25
print(f"My name is {name} and I am {age} years old.")
```

### **5.2 str.format()**
```python
print("My name is {} and I am {} years old.".format(name, age))
print("I have {1} apples and {0} oranges.".format(5, 10))
```

### **5.3 % Formatting**
```python
print("My name is %s and I am %d years old." % (name, age))
```

---

## **6. Escape Sequences**

| Escape Sequence | Description          |
|------------------|----------------------|
| `\n`            | New line            |
| `\t`            | Tab                 |
| `\\`            | Backslash           |
| `\'`            | Single quote        |
| `\"`            | Double quote        |

---

## **7. Famous Use Cases**

### **7.1 Reverse a String**
```python
s = "Python"
print(s[::-1])  # 'nohtyP'
```

### **7.2 Check Palindrome**
```python
s = "madam"
print(s == s[::-1])  # True
```

### **7.3 Count Vowels in a String**
```python
s = "hello world"
vowels = "aeiou"
count = sum(1 for char in s if char in vowels)
print(count)  # 3
```

### **7.4 Remove Duplicates**
```python
s = "banana"
unique_chars = "".join(set(s))
print(unique_chars)  # Output order may vary
```

### **7.5 Replace Multiple Spaces with Single**
```python
import re
s = "This  is    Python"
print(re.sub(' +', ' ', s))  # 'This is Python'
```

---

## **8. Advanced String Manipulation**

### **8.1 Raw Strings**
```python
regex = r"\d{3}-\d{2}-\d{4}"
print(regex)  # Output: \d{3}-\d{2}-\d{4}
```

### **8.2 String Translation**
```python
table = str.maketrans("abc", "123")
print("apple".translate(table))  # '1pple'
```

---

## **9. Accessing Unicode and Encoding**
```python
# Unicode to char and vice versa
print(ord('A'))  # 65
print(chr(65))   # 'A'

# Encoding and Decoding
s = "hello"
encoded = s.encode('utf-8')
print(encoded)  # b'hello'
print(encoded.decode('utf-8'))  # 'hello'
```

---

## **10. String Comparison**
```python
s1 = "abc"
s2 = "ABC"
print(s1 == s2)         # False
print(s1.lower() == s2.lower())  # True
print(s1 > s2)          # True (lexicographical order)
```
