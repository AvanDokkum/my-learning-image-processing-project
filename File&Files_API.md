### File/Files API
- Question: "What is the difference between File/Files API?"

The difference between `File` and `Files` in Java lies in their **design, purpose, and functionality**.

---

## **1. `File` (Class)**
- `File` is an **old class from `java.io` (since Java 1.0)**.
- It represents **a file or directory path**, but it **does not provide methods for reading or writing file contents**.
- It is used primarily for:
    - Checking if a file or directory exists (`exists()`)
    - Creating new files or directories (`createNewFile()`, `mkdir()`, `mkdirs()`)
    - Deleting files (`delete()`)
    - Getting metadata like file name, size, and last modified date.

### **Example**
```java
import java.io.File;
import java.io.IOException;

public class FileExample {
    public static void main(String[] args) throws IOException {
        File file = new File("example.txt");

        if (!file.exists()) {
            file.createNewFile();  // Creates a new empty file
            System.out.println("File created: " + file.getAbsolutePath());
        } else {
            System.out.println("File already exists!");
        }

        System.out.println("File name: " + file.getName());
        System.out.println("Is directory? " + file.isDirectory());
        System.out.println("File size: " + file.length() + " bytes");
    }
}
```

### **Limitations of `File`**
- `File` **only represents** a file or directory but **cannot read or write** data. You need extra classes like `FileReader`, `FileWriter`, `BufferedReader`, etc., for actual file content operations.
- It does **not support advanced file operations** like atomic file copying, moving, or symbolic links.

---

## **2. `Files` (Utility Class)**
- `Files` is a **utility class from `java.nio.file` (since Java 7)**.
- It provides **static methods** to perform advanced file I/O operations.
- It works with `Path` objects (not `File` objects).
- It allows:
    - Reading and writing files easily (`readAllBytes()`, `write()`)
    - Copying, moving, and deleting files (`copy()`, `move()`, `delete()`)
    - Creating directories (`createDirectories()`)
    - Checking file attributes (`isReadable()`, `size()`, `getLastModifiedTime()`)

### **Example**
```java
import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class FilesExample {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("example.txt");

        // Writing to a file
        Files.write(path, "Hello, Java NIO!".getBytes());

        // Reading from a file
        List<String> lines = Files.readAllLines(path);
        lines.forEach(System.out::println);

        // Checking file attributes
        System.out.println("File exists? " + Files.exists(path));
        System.out.println("File size: " + Files.size(path) + " bytes");
    }
}
```

---

## **Key Differences**
| Feature | `File` | `Files` |
|---------|-------|--------|
| **Type** | Class | Utility class |
| **Package** | `java.io` | `java.nio.file` |
| **Works With** | `File` objects (file paths as `String`) | `Path` objects (`java.nio.file.Path`) |
| **File Operations** | Only represents files & directories (creation, deletion, metadata) | Advanced file operations (copy, move, delete, read/write) |
| **Reading/Writing** | Needs `FileReader`, `BufferedReader`, etc. | Has built-in methods (`readAllBytes()`, `write()`) |
| **Exception Handling** | Uses `IOException` but lacks detailed exceptions | Provides better exception handling with `IOException` and `NoSuchFileException` |

---

## **Conclusion**
- Use `File` **if you only need to check file existence, metadata, or create empty files**.
- Use `Files` **for actual file I/O operations like reading, writing, copying, or moving files**.
- `Files` is **more powerful and preferred** in modern Java applications.