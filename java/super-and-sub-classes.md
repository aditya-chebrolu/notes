# Superclass and Subclass 🤯

```java
class Super {
    int superValue;

    Super() {
        this.superValue = 10;
    }

    void superMethod() {
        System.out.println("Method from Super class: superValue = " + superValue);
    }

    void commonMethod() {
        System.out.println("Common method from Super class");
    }
}

class Sub extends Super {
    int subValue;

    Sub() {
        this.subValue = 20;
    }

    @Override
    void commonMethod() {
        System.out.println("Overridden method in Sub class: subValue = " + subValue);
    }

    void subMethod() {
        System.out.println("Method from Sub class: subValue = " + subValue);
    }
}
```

### Super a = new Sub();
- Creates an instance of `Sub` but references it with a `Super` type.
- Only properties and methods defined in `Super` can be accessed directly.
- Overridden methods in `Sub` will be called if they exist.

```java
Super a = new Sub();
a.superMethod();     // Output: Method from Super class: superValue = 10
a.commonMethod();    // Output: Overridden method in Sub class: subValue = 20

// a.subMethod();    // Compilation error: subMethod() is not accessible
// System.out.println(a.subValue); // Compilation error: subValue is not accessible
```

### Super b = new Super();
- Creates an instance of `Super` and references it with a `Super` type.
- Only properties and methods defined in `Super` can be accessed.

```java
Super b = new Super();
b.superMethod();     // Output: Method from Super class: superValue = 10
b.commonMethod();    // Output: Common method from Super class

// b.subMethod();    // Compilation error: subMethod() is not accessible
// System.out.println(b.subValue); // Compilation error: subValue is not accessible
```

### Sub c = new Sub();
- Creates an instance of `Sub` and references it with a `Sub` type.
- All properties and methods defined in both `Super` and `Sub` can be accessed.

```java
Sub c = new Sub();
c.superMethod();     // Output: Method from Super class: superValue = 10
c.commonMethod();    // Output: Overridden method in Sub class: subValue = 20
c.subMethod();       // Output: Method from Sub class: subValue = 20

System.out.println(c.superValue); // Output: 10
System.out.println(c.subValue);   // Output: 20
```

### Sub d = new Super();
- Compilation error. You cannot reference a `Super` instance with a `Sub` type.

```java
// Sub d = new Super(); // Compilation error: incompatible types
```

## Summary
- **Super a = new Sub();**
  - Can access: `superMethod`, `commonMethod` (overridden in `Sub`)
  - Cannot access: `subMethod`, `subValue`
  - The `commonMethod` in `Sub` will print the `subValue` of `Sub`.

- **Super b = new Super();**
  - Can access: `superMethod`, `commonMethod`
  - Cannot access: `subMethod`, `subValue`
  - The `commonMethod` in `Super` does not print `subValue`.

- **Sub c = new Sub();**
  - Can access: `superMethod`, `commonMethod` (overridden in `Sub`), `subMethod`, `superValue`, `subValue`
  - The `commonMethod` in `Sub` will print the `subValue` of `Sub`.

- **Sub d = new Super();**
  - Compilation error: Cannot assign `Super` to `Sub`
