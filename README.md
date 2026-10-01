# Java Coding Exam: SimpleAdventure

An item and character system built around a provided main program. Items are
things a `Person` can interact with: a `Weapon` that deals damage, a `Food`
that restores health, and a plain `Item` that does neither.

## Class layout

Four classes plus the given main program. `Food` and `Weapon` both extend
`Item` and add exactly one field each. Neither subclass stores its own copy of
name or weight; both come from the parent.

| Class | Adds | Purpose |
|---|---|---|
| `Item` | name, weight | Base item. `use()` prints `Not usable` and returns false. |
| `Food` | health | `use()` heals a `Person`, capped at 100. |
| `Weapon` | damage | `use()` damages a `Person`, floored at 0. |
| `Person` | name, health | Health starts at 100. `heal()` and `defends()` clamp both ends of the 0-100 range. |

All fields are `private`, except `MAX_HEALTH`, which is a constant. Access is
through getters and setters.

`use(Object target)` checks `instanceof Person` at runtime. If the target is not
a Person, it returns false without printing anything.

## Project structure

```
ics2606/mp2/       the five deliverable files
  Person.java  Item.java  Food.java  Weapon.java
  SimpleAdventure.java      (provided; not modified)
ics2606/tests/     the JUnit 5 suite
```

The `ics2606/mp2` folder name is required, not stylistic. It matches the
`package ics2606.mp2;` declaration in `SimpleAdventure.java`, and Java will not
compile that file from any other directory.

## Running the program

```bash
javac -d /tmp/b ics2606/mp2/*.java
java -cp /tmp/b ics2606.mp2.SimpleAdventure
```

Using `-d /tmp/b` keeps compiled `.class` files out of the source tree.

## Running the tests

50 JUnit 5 tests. No `pom.xml`, so the JUnit jar is referenced directly.

```bash
javac -d /tmp/b -cp ~/.cache/java-coding-exam/junit-platform-console-standalone-6.1.3.jar ics2606/mp2/*.java ics2606/tests/*.java
java -jar ~/.cache/java-coding-exam/junit-platform-console-standalone-6.1.3.jar execute --class-path /tmp/b --scan-class-path /tmp/b --details=summary
```

Run both from the project root. `SimpleAdventureTest` reads its expected output
from a path relative to that root, so it fails if launched from anywhere else.

The jar is not committed. Download it from Maven Central if it is missing:

```bash
mkdir -p ~/.cache/java-coding-exam
curl -sSL -o ~/.cache/java-coding-exam/junit-platform-console-standalone-6.1.3.jar \
  https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/6.1.3/junit-platform-console-standalone-6.1.3.jar
```

In VS Code, tests also run from the **Run Test** link above each test class. The
extension bundles its own JUnit jars. If the test explorer shows stale errors
after files move, run **Java: Clean Java Language Server Workspace** from the
command palette.

## Two bugs the tests caught

Neither is reachable from `SimpleAdventure`, which only ever passes positive
numbers. Both were found by writing assertions from the requirements rather than
from the implementation.

`heal(-10)` on a Person at 5 health produced **-5**, and `isAlive()` still
returned `true` because it only checked `!= 0`.

`defends(-50)` produced **150**, above the 100 maximum.

Both fixed by clamping both ends of the range:

```java
this.health = Math.max(0, Math.min(this.health + boost, MAX_HEALTH));
this.health = Math.max(0, Math.min(this.health - damage, MAX_HEALTH));
```

## Notes on behaviour the spec leaves open

Three points are not covered by the assignment. The tests pin current
behaviour, so any change becomes a deliberate decision.

**Negative inputs.** The spec describes the 100 cap and the zero floor but never
mentions negative boosts or damage. These clamp to the valid range rather than
being rejected.

**`use()` on a non-Person.** Returns false and prints nothing. The base
`Item.use()` is the exception: it prints `Not usable` before returning false.

**Lethal attacks.** `Weapon.use()` returns true even when the target dies. Only
the printed message differs.

## Known limitation

`ics2606/tests/simple-adventure-expected-output.txt` was generated from this
implementation's own output, not from the assignment's expected-output section.
`SimpleAdventureTest` therefore locks in current behaviour rather than
independently confirming it matches the spec. Replacing that file with the
assignment's transcript makes the test authoritative.