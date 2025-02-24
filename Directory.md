### What is a Directory?
A **directory** is a special type of file that acts as a container for other files and directories. Think of it as a folder on your computer. It helps organize files into a structured hierarchy.

For example:
```
/home/user/documents/
├── file1.txt
├── file2.txt
└── projects/
    ├── project1/
    │   ├── code.java
    │   ├── readme.txt
    │   └── images/
    │       ├── logo.png
    │       └── banner.jpg
    └── project2/
        ├── main.py
        └── notes.md
```
In this example,
- `/home/user/documents/` is a **directory** containing files (`file1.txt`, `file2.txt`) and subdirectories (`projects/`).
- `projects/` itself contains more directories (`project1/`, `project2/`), creating a **nested structure**.

### The `Directory` Class in Java
Java **does not have a built-in `Directory` class** specifically for handling directories. Instead, directories in Java are managed using the **`java.io.File`** and **`java.nio.file`** packages.

#### 1. Using `java.io.File` for Directories
The `File` class represents **both** files and directories. You can use it to:
- Check if a directory exists
- Create a new directory
- List files inside a directory
- Delete a directory

##### Example: Checking if a Directory Exists
```java
import java.io.File;

public class DirectoryExample {
    public static void main(String[] args) {
        File dir = new File("my_folder");

        if (dir.exists() && dir.isDirectory()) {
            System.out.println("Directory exists: " + dir.getAbsolutePath());
        } else {
            System.out.println("Directory does not exist.");
        }
    }
}
```

##### Example: Creating a Directory
```java
import java.io.File;

public class CreateDirectory {
    public static void main(String[] args) {
        File dir = new File("new_folder");

        if (!dir.exists()) {
            if (dir.mkdir()) {  // mkdir() creates a single directory
                System.out.println("Directory created successfully!");
            } else {
                System.out.println("Failed to create directory.");
            }
        } else {
            System.out.println("Directory already exists.");
        }
    }
}
```

##### Example: Creating Nested Directories
```java
import java.io.File;

public class CreateNestedDirectories {
    public static void main(String[] args) {
        File dir = new File("parent_folder/child_folder");

        if (!dir.exists()) {
            if (dir.mkdirs()) {  // mkdirs() creates nested directories
                System.out.println("Directories created successfully!");
            } else {
                System.out.println("Failed to create directories.");
            }
        } else {
            System.out.println("Directories already exist.");
        }
    }
}
```

##### Example: Listing Files and Folders in a Directory
```java
import java.io.File;

public class ListFiles {
    public static void main(String[] args) {
        File dir = new File("my_folder");

        if (dir.exists() && dir.isDirectory()) {
            String[] files = dir.list(); // Get names of files and directories
            System.out.println("Files in " + dir.getAbsolutePath() + ":");
            for (String file : files) {
                System.out.println(file);
            }
        } else {
            System.out.println("Directory does not exist.");
        }
    }
}
```

##### Example: Deleting a Directory
A directory must be **empty** before deleting it using `delete()`.
```java
import java.io.File;

public class DeleteDirectory {
    public static void main(String[] args) {
        File dir = new File("old_folder");

        if (dir.exists()) {
            if (dir.delete()) {  // Deletes only if empty
                System.out.println("Directory deleted.");
            } else {
                System.out.println("Failed to delete directory (might not be empty).");
            }
        } else {
            System.out.println("Directory does not exist.");
        }
    }
}
```

---

#### 2. Using `java.nio.file` for Directories (Modern Approach)
Since Java 7, the **`java.nio.file`** package provides a more flexible way to handle directories. The `Files` class is used for directory operations.

##### Example: Creating a Directory
```java
import java.nio.file.*;

public class CreateDirectoryNIO {
    public static void main(String[] args) {
        Path path = Paths.get("nio_folder");

        try {
            Files.createDirectory(path);
            System.out.println("Directory created: " + path.toAbsolutePath());
        } catch (Exception e) {
            System.out.println("Error creating directory: " + e.getMessage());
        }
    }
}
```

##### Example: Deleting a Directory
```java
import java.nio.file.*;

public class DeleteDirectoryNIO {
    public static void main(String[] args) {
        Path path = Paths.get("nio_folder");

        try {
            Files.delete(path);  // Deletes only if empty
            System.out.println("Directory deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting directory: " + e.getMessage());
        }
    }
}
```

##### Example: Listing Files in a Directory
```java
import java.nio.file.*;
import java.io.IOException;
import java.util.stream.Stream;

public class ListFilesNIO {
    public static void main(String[] args) {
        Path dir = Paths.get("my_folder");

        try (Stream<Path> files = Files.list(dir)) {
            files.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Error listing files: " + e.getMessage());
        }
    }
}
```

---

### Summary
| Feature        | `java.io.File` | `java.nio.file.Files` |
|---------------|---------------|-----------------------|
| Exists check  | ✅ `.exists()` | ✅ `Files.exists()`  |
| Create directory | ✅ `.mkdir()` | ✅ `Files.createDirectory()` |
| Create nested directories | ✅ `.mkdirs()` | ✅ `Files.createDirectories()` |
| List files    | ✅ `.list()` | ✅ `Files.list()` (more powerful) |
| Delete directory | ✅ `.delete()` (only empty) | ✅ `Files.delete()` (only empty) |
| Modern and flexible | ❌ No | ✅ Yes |

For **beginner-level** projects, `java.io.File` is easier to start with. For **advanced applications**, `java.nio.file.Files` is the recommended approach because it provides better error handling and supports modern features.

Let me know if you need further explanation! 🚀