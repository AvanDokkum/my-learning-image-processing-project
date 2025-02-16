### Path/Paths API
- Question: "What is the difference between the Path/Paths API"

The difference between `Path` and `Paths` in Java is primarily in their roles and usage:

### **1. `Path` (Interface)**
- `Path` is an **interface** in the `java.nio.file` package.
- It represents a hierarchical file system path and provides methods to manipulate and retrieve path information.
- Since it is an interface, you **cannot instantiate it directly**. Instead, you obtain a `Path` instance through the `Paths` class or the `FileSystems` class.
- `Path` objects allow operations like:
    - Normalizing (`normalize()`)
    - Resolving (`resolve()`)
    - Getting parent directories (`getParent()`)
    - Converting to `File` (`toFile()`)
    - Checking if a path is absolute (`isAbsolute()`)
    - Converting to `URI` (`toUri()`)

**Example:**
```java
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathExample {
    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\Desktop\\file.txt");

        System.out.println("File Name: " + path.getFileName());
        System.out.println("Parent Directory: " + path.getParent());
        System.out.println("Is Absolute? " + path.isAbsolute());
    }
}
```

---

### **2. `Paths` (Utility Class)**
- `Paths` is a **final class** that provides **static factory methods** for obtaining `Path` instances.
- The main method in `Paths` is:
  ```java
  static Path get(String first, String... more)
  ```
    - This method creates a `Path` object based on the given string representation of a file path.

**Example:**
```java
Path path = Paths.get("C:\\Users\\Desktop\\file.txt");
```
or using multiple arguments:
```java
Path path = Paths.get("C:", "Users", "Desktop", "file.txt");
```
- This approach makes it more platform-independent by handling the correct path separators automatically.

---

### **Key Differences**
| Feature  | `Path` | `Paths` |
|----------|-------|--------|
| **Type** | Interface | Final Utility Class |
| **Instantiation** | Cannot be instantiated directly | Provides a static `get()` method to create `Path` objects |
| **Purpose** | Represents a file system path | Factory class for creating `Path` instances |
| **Methods** | Has methods for path manipulation (e.g., `resolve()`, `normalize()`) | Only provides static methods to obtain `Path` objects |

---

### **Conclusion**
- Use `Paths.get(...)` to create a `Path` object.
- Once you have a `Path` object, you can perform operations like resolving, normalizing, and converting paths.
- `Path` provides the actual functionality for file paths, while `Paths` is just a helper class for creating `Path` objects.
