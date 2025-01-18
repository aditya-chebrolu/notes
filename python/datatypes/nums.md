# Python Numeric Data Types Cheatsheet

## Overview
| **Numeric Type**   | **Description**                          | **Example**               |
|---------------------|------------------------------------------|---------------------------|
| `int`              | Integer values, unlimited precision.    | `x = 42`                 |
| `float`            | Floating-point numbers.                 | `x = 3.14`               |
| `complex`          | Complex numbers with real and imaginary parts. | `x = 2 + 3j`       |

---

## Common Operations
| **Operation**                  | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Addition                       | `a + b`                            | Adds `a` and `b`.                                  |
| Subtraction                    | `a - b`                            | Subtracts `b` from `a`.                            |
| Multiplication                 | `a * b`                            | Multiplies `a` and `b`.                            |
| Division                       | `a / b`                            | Divides `a` by `b` (float result).                 |
| Floor division                 | `a // b`                           | Divides `a` by `b`, rounds down to nearest int.    |
| Modulus (remainder)            | `a % b`                            | Returns the remainder of `a / b`.                  |
| Exponentiation                 | `a ** b`                           | Raises `a` to the power of `b`.                    |
| Negation                       | `-a`                               | Negates `a`.                                       |

---

## Type Conversion
| **Operation**                  | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Convert to integer             | `int(x)`                           | Converts `x` to an integer.                        |
| Convert to float               | `float(x)`                         | Converts `x` to a floating-point number.           |
| Convert to complex             | `complex(x, y)`                    | Creates a complex number with real `x` and imaginary `y`. |
| Absolute value                 | `abs(x)`                           | Returns the absolute value of `x`.                 |
| Round to n decimals            | `round(x, n)`                      | Rounds `x` to `n` decimal places.                  |

---

## Math Functions (using `math` module)
| **Function**                   | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Square root                    | `math.sqrt(x)`                     | Returns the square root of `x`.                    |
| Exponential (e^x)              | `math.exp(x)`                      | Returns `e` raised to the power of `x`.            |
| Natural logarithm              | `math.log(x)`                      | Returns the natural logarithm of `x` (base `e`).   |
| Logarithm with base            | `math.log(x, base)`                | Returns the logarithm of `x` to the given `base`.  |
| Power                          | `math.pow(x, y)`                   | Raises `x` to the power of `y`.                    |
| Trigonometric functions        | `math.sin(x)`, `math.cos(x)`       | Computes sine, cosine of `x` (in radians).         |
| Radians to degrees             | `math.degrees(x)`                  | Converts radians to degrees.                       |
| Degrees to radians             | `math.radians(x)`                  | Converts degrees to radians.                       |
| Factorial                      | `math.factorial(x)`                | Returns factorial of `x`.                          |
| Greatest common divisor (GCD)  | `math.gcd(a, b)`                   | Returns GCD of `a` and `b`.                        |

---

## Random Numbers (using `random` module)
| **Function**                   | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Generate random float          | `random.random()`                  | Returns a random float in `[0.0, 1.0)`.            |
| Random integer                 | `random.randint(a, b)`             | Returns a random integer in `[a, b]`.              |
| Random float in range          | `random.uniform(a, b)`             | Returns a random float in `[a, b]`.                |
| Shuffle list                   | `random.shuffle(lst)`              | Shuffles the elements of the list `lst`.           |
| Choose random element          | `random.choice(lst)`               | Chooses a random element from `lst`.               |
| Sample without replacement     | `random.sample(lst, k)`            | Returns `k` unique random elements from `lst`.     |

---

## Complex Numbers
| **Operation**                  | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Create a complex number        | `z = 3 + 4j`                       | Complex number with real part `3` and imaginary part `4`. |
| Access real part               | `z.real`                           | Returns the real part of `z`.                      |
| Access imaginary part          | `z.imag`                           | Returns the imaginary part of `z`.                 |
| Magnitude (absolute value)     | `abs(z)`                           | Returns the magnitude of `z`.                      |
| Conjugate                     | `z.conjugate()`                     | Returns the conjugate of `z`.                      |

---

## Constants
| **Constant**                   | **Code Example**                   | **Description**                                      |
|--------------------------------|-------------------------------------|----------------------------------------------------|
| Pi                             | `math.pi`                          | Approximation of π (3.14159).                      |
| Euler's number                 | `math.e`                           | Approximation of `e` (2.71828).                    |
| Infinity                       | `math.inf`                         | Represents positive infinity.                      |
| Not a Number (NaN)             | `math.nan`                         | Represents a "Not a Number" value.                 |
