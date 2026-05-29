## Command-Line Compilation Guide

### Compiling a single module

```
javac -d <output-dir> <source-files>
```

```
javac -d mods2\com.skillstorm.status
      notification-status\src\main\java\module-info.java
      notification-status\src\main\java\com\skillstorm\status\Priority.java
      notification-status\src\main\java\com\skillstorm\status\NotificationStatus.java
```

`-d` — destination directory for compiled `.class` files.
The compiler creates the package subdirectory structure inside it automatically.

### Compiling a module that depends on other modules

```
javac --module-path mods2 -d mods2\com.skillstorm.api ...
```

`--module-path` (short: `-p`) — tells the compiler where to find already-compiled
modules. This replaces `-classpath` in a module world.

Order matters: compile dependencies before dependents. In this demo:
1. `com.skillstorm.status` (no deps)
2. `com.skillstorm.api` (needs status)
3. `com.skillstorm.app` (needs api, which transitively needs status)

### Running a module

```
java --module-path mods2 -m com.skillstorm.app/com.skillstorm.app.Main
```

| Flag | Meaning |
|------|---------|
| `--module-path mods2` | where to find all modules |
| `-m` / `--module` | `moduleName/fully.qualified.MainClass` |

This replaces the old `java -cp classes com.skillstorm.app.Main` classpath style.

### Other useful runtime flags

| Flag | When to use |
|------|------------|
| `--add-modules X` | Load module X even though no other module requires it (needed for ServiceLoader demos where the consumer has no direct requires on the impl) |
| `--list-modules` | Print all modules the JVM can see, then exit |
| `--describe-module X` | Print the full descriptor of module X |
| `--add-opens X/pkg=Y` | Temporarily open a package for reflection at runtime (migration escape hatch) |
| `--add-exports X/pkg=Y` | Temporarily export a package at runtime (migration escape hatch) |

`--add-opens` and `--add-exports` are "escape hatches" for migrating legacy code that relies on
deep reflection into JDK internals. You will see them in Spring Boot / Hibernate
startup scripts. They are a sign that the library hasn't fully adopted JPMS yet.

Example:
```
java --module-path mods2
     --add-opens com.skillstorm.impl/com.skillstorm.impl=com.skillstorm.app
     -m com.skillstorm.app/com.skillstorm.app.Main
```

### Compiling with --release

```
javac --release 17 -d mods2\com.skillstorm.status ...
```

`--release 17` enforces source and API compatibility with Java 17.
Useful when your build machine has Java 21 but your target is Java 17.

---

## jdeps — Java Dependency Analysis Tool

`jdeps` ships with the JDK (same `bin/` folder as `javac` and `java`).
It analyzes `.class` files and reports what each module or package depends on.

### Basic module dependency report

```
jdeps --module-path mods2 --module com.skillstorm.app
```

Output shows:
```
com.skillstorm.app -> com.skillstorm.api
com.skillstorm.app -> com.skillstorm.status   (via transitive from api)
com.skillstorm.app -> java.base
```

### Why use jdeps?

| Use case | How jdeps helps |
|----------|----------------|
| Migrating a JAR to JPMS | Shows which JDK modules the JAR needs — tells you what to put in `requires` |
| Auditing dependencies | Finds surprise dependencies your code has that you didn't intend |
| Removing unused dependencies | Shows if a `requires` is declared but never actually used |
| Understanding a codebase | Generates a module graph without reading all the code manually |

### Generate a module-info stub for an existing JAR (migration)

```
jdeps --generate-module-info out/ mylibrary.jar
```

`jdeps` reads `mylibrary.jar` and writes a suggested `module-info.java` to
the `out/` directory. You then edit and compile it. This is the recommended
first step when adding JPMS support to an existing library.

### Other useful flags

| Flag | Effect |
|------|--------|
| `--recursive` | Follow all transitive dependencies |
| `--list-deps X` | List only the JDK module names X depends on |
| `--dot-output dir/` | Write a Graphviz `.dot` file for visualizing the graph |
| `-verbose:package` | Show package-level instead of module-level detail |
| `-verbose:class` | Show class-level detail |

### jdeps is not on your PATH?

`jdeps` lives in the same `bin/` directory as `java` and `javac`. If it's not
found, run it with the full path:

```
"C:\Program Files\Java\jdk-21\bin\jdeps" --module-path mods2 --module com.skillstorm.app
```

Or add the JDK `bin/` directory to your system PATH.