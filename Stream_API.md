## Java Streams API: A Beginner's Guide

The **Streams API** in Java was introduced in **Java 8** to provide a functional and efficient way of processing data collections. It allows you to perform operations such as **filtering, mapping, sorting, and reducing** in a **declarative style** without modifying the underlying data structure.

---

## 🔹 **What is a Stream?**
A **Stream** is a sequence of elements supporting **sequential and parallel** operations. It **does not store data** but rather processes elements from a source (like a `List`, `Set`, or `Array`).

Streams operate in a **pipeline structure**, consisting of:
1. **A source** → Collection, Array, or I/O channel
2. **Intermediate operations** → Transformations like `filter()` or `map()`
3. **A terminal operation** → A final operation like `collect()` or `forEach()`

---

## 🔹 **Creating a Stream**
Streams can be created from different sources:

### 1️⃣ **From a List**
```java
List<String> names = List.of("Alice", "Bob", "Charlie");
Stream<String> stream = names.stream();
```

### 2️⃣ **From an Array**
```java
String[] array = {"A", "B", "C"};
Stream<String> stream = Arrays.stream(array);
```

### 3️⃣ **Using `Stream.of()`**
```java
Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
```

### 4️⃣ **Using `Stream.generate()`**
Creates an **infinite stream**:
```java
Stream<Double> randomNumbers = Stream.generate(Math::random).limit(5);
```

### 5️⃣ **Using `Stream.iterate()`**
Creates an **infinite** sequence:
```java
Stream<Integer> numbers = Stream.iterate(1, n -> n + 1).limit(5);
```

---

## 🔹 **Intermediate Operations (Transformations)**
These operations **do not modify** the original collection but produce a new stream.

### 1️⃣ **`filter(Predicate<T>)`**
Filters elements based on a condition.
```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
numbers.stream()
       .filter(n -> n % 2 == 0) // Keeps only even numbers
       .forEach(System.out::println);
```

### 2️⃣ **`map(Function<T, R>)`**
Transforms each element into another value.
```java
List<String> names = List.of("alice", "bob", "charlie");
names.stream()
     .map(String::toUpperCase)
     .forEach(System.out::println);
```

### 3️⃣ **`sorted()`**
Sorts elements **naturally** (for `Comparable` objects) or using a **custom comparator**.
```java
List<String> names = List.of("Charlie", "Alice", "Bob");
names.stream()
     .sorted()
     .forEach(System.out::println); // Alice, Bob, Charlie
```

### 4️⃣ **`distinct()`**
Removes duplicates.
```java
List<Integer> numbers = List.of(1, 2, 2, 3, 4, 4, 5);
numbers.stream()
       .distinct()
       .forEach(System.out::println);
```

### 5️⃣ **`limit(n)` and `skip(n)`**
- `limit(n)`: Takes only the first `n` elements.
- `skip(n)`: Skips the first `n` elements.
```java
Stream.of(1, 2, 3, 4, 5)
      .limit(3)
      .forEach(System.out::println); // Output: 1, 2, 3

Stream.of(1, 2, 3, 4, 5)
      .skip(2)
      .forEach(System.out::println); // Output: 3, 4, 5
```

---

## 🔹 **Terminal Operations (Final Processing)**
These operations **consume** the stream and return a value or produce a side effect.

### 1️⃣ **`forEach(Consumer<T>)`**
Performs an action for each element.
```java
List<String> names = List.of("Alice", "Bob", "Charlie");
names.stream()
     .forEach(System.out::println);
```

### 2️⃣ **`collect(Collector)`**
Converts a stream into a collection.
```java
List<String> upperNames = names.stream()
                               .map(String::toUpperCase)
                               .collect(Collectors.toList());
```

### 3️⃣ **`count()`**
Counts the number of elements in the stream.
```java
long count = Stream.of(1, 2, 3, 4, 5).count();
```

### 4️⃣ **`findFirst()` and `findAny()`**
Finds the **first** or **any** element.
```java
Optional<Integer> first = Stream.of(10, 20, 30).findFirst();
Optional<Integer> any = Stream.of(10, 20, 30).findAny();
```

### 5️⃣ **`allMatch()`, `anyMatch()`, `noneMatch()`**
Checks conditions on elements.
```java
boolean allEven = Stream.of(2, 4, 6).allMatch(n -> n % 2 == 0); // true
boolean anyEven = Stream.of(1, 3, 5, 8).anyMatch(n -> n % 2 == 0); // true
boolean noneNegative = Stream.of(1, 2, 3).noneMatch(n -> n < 0); // true
```

### 6️⃣ **`reduce()` (Aggregation)**
Performs **accumulation**.
```java
int sum = Stream.of(1, 2, 3, 4, 5)
                .reduce(0, Integer::sum); // 15
```

---

## 🔹 **Parallel Streams (Performance Optimization)**
Streams can be executed in **parallel** to leverage **multicore processors**.

### 🔹 **Convert to Parallel Stream**
```java
List<String> names = List.of("Alice", "Bob", "Charlie");
names.parallelStream()
     .map(String::toUpperCase)
     .forEach(System.out::println);
```
> ⚠️ **Note:** Parallel streams should be used carefully since they introduce non-deterministic execution.

---

## 🔹 **Example: Processing a List with Streams**
```java
import java.util.List;
import java.util.stream.Collectors;

public class StreamExample {
    public static void main(String[] args) {
        List<String> names = List.of("Alice", "Bob", "Charlie", "David");

        List<String> filteredNames = names.stream()
                                          .filter(name -> name.startsWith("A") || name.startsWith("C"))
                                          .map(String::toUpperCase)
                                          .sorted()
                                          .collect(Collectors.toList());

        System.out.println(filteredNames); // Output: [ALICE, CHARLIE]
    }
}
```

---

## 🔹 **Key Takeaways**
- Streams process **data collections efficiently**.
- They provide **declarative operations** (filtering, mapping, reducing).
- Intermediate operations **return new streams** (lazy evaluation).
- Terminal operations **consume** the stream.
- **Parallel streams** improve performance but require careful use.

---

### 🔥 **Why Use Streams?**
✅ **More readable & concise**  
✅ **Less boilerplate code**  
✅ **Better performance (parallel processing)**  
✅ **Functional programming style**
