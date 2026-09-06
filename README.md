# AssertJ JSON

[![Release](https://jitpack.io/v/archyoshi/assertj-json.svg)](https://jitpack.io/#archyoshi/assertj-json)
[![Build and Test](https://github.com/archyoshi/assertj-json/actions/workflows/ci.yml/badge.svg)](https://github.com/archyoshi/assertj-json/actions/workflows/ci.yml)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

AssertJ JSON adds fluent assertions tailored for JSON payloads, JSON files, and JSON tree values.

## Goals

- Assert JSON structure and values using the fluent AssertJ style
- Work seamlessly with `JsonNode` from Jackson
- Support JSON files as inputs
- Remain easy to consume from Maven and Gradle

---

## Installation (JitPack)

AssertJ JSON is currently available via [JitPack](https://jitpack.io/#archyoshi/assertj-json) during its beta period.

### Maven

1. Add the JitPack repository to your `pom.xml`:

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

2. Add the dependency:

```xml
<dependency>
  <groupId>com.github.archyoshi</groupId>
  <artifactId>assertj-json</artifactId>
  <version>v0.1.0</version>
  <scope>test</scope>
</dependency>
```

> **Note:** To consume the latest development snapshots, you can use `<version>develop-SNAPSHOT</version>` or a specific commit hash.

### Gradle

#### Groovy DSL (`build.gradle`)

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    testImplementation 'com.github.archyoshi:assertj-json:v0.1.0'
}
```

#### Kotlin DSL (`build.gradle.kts`)

```kotlin
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    testImplementation("com.github.archyoshi:assertj-json:v0.1.0")
}
```

---

## Usage

```java
import static com.archyoshi.assertj.json.JsonAssertions.assertThat;

String json = "{ \"name\": \"Vegeta\", \"age\": 30, \"active\": true }";

assertThat(json)
  .hasField("name")
  .hasValueForField("Vegeta", "name")
  .hasField("age")
  .hasValueForField(30, "age");
```

```java
import static com.archyoshi.assertj.json.JsonAssertions.assertThat;

Path file = Path.of("src/test/resources/example.json");

assertThat(file).hasSameContentAs("""
    {
      "name": "Vegeta"
    }
    """);

assertThat(file).hasSameFieldsAs(Path.of("src/test/resources/expected.json"));

assertThat(file).partiallyContains("""
    {
      "active": true
    }
    """);
```

---

## Building

```bash
./mvnw clean verify
```

---

## Author

- [archyoshi](https://github.com/archyoshi)

## License

This project is licensed under the Apache License 2.0.
