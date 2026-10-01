# Java Coding Exam: SimpleAdventure

An item and character system built around a provided main program. `Person`
interacts with `Item`s: a `Weapon` deals damage, a `Food` restores health, and a
plain `Item` does neither.

## Class layout

| Class | Adds | Purpose |
|---|---|---|
| `Item` | name, weight | Base item. `use()` prints `Not usable` and returns false. |
| `Food` | health | `use()` heals a `Person`, capped at 100. |
| `Weapon` | damage | `use()` damages a `Person`, floored at 0. |
| `Person` | name, health | Health starts at 100, clamped to 0-100. |

`Food` and `Weapon` extend `Item` and add one field each; name and weight come
from the parent. Fields are private, accessed through getters and setters.
`use(Object target)` checks `instanceof Person` at runtime and returns false
silently if the target is not a Person.

## Project structure

```
ics2606/mp2/       the five deliverable files
  Person.java  Item.java  Food.java  Weapon.java
  SimpleAdventure.java      (provided; not modified)
ics2606/tests/     the JUnit 5 suite
```

The `ics2606/mp2` folder name is required: it matches the `package ics2606.mp2;`
declaration in `SimpleAdventure.java`.

## Running the program

```bash
javac -d /tmp/b ics2606/mp2/*.java
java -cp /tmp/b ics2606.mp2.SimpleAdventure
```

Using `-d /tmp/b` keeps compiled `.class` files out of the source tree.

## Running the tests

50 JUnit 5 tests. No `pom.xml`, so the JUnit jar is referenced directly. Run
both from the project root; `SimpleAdventureTest` resolves its expected output
relative to it.

```bash
javac -d /tmp/b -cp ~/.cache/java-coding-exam/junit-platform-console-standalone-6.1.3.jar ics2606/mp2/*.java ics2606/tests/*.java
java -jar ~/.cache/java-coding-exam/junit-platform-console-standalone-6.1.3.jar execute --class-path /tmp/b --scan-class-path /tmp/b --details=summary
```

The jar is not committed. Download it if missing:

```bash
mkdir -p ~/.cache/java-coding-exam
curl -sSL -o ~/.cache/java-coding-exam/junit-platform-console-standalone-6.1.3.jar \
  https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/6.1.3/junit-platform-console-standalone-6.1.3.jar
```
