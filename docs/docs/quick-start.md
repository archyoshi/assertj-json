---
title: Quick Start
sidebar_position: 1
---

# Quick Start

Get up and running with AssertJ-JSON in minutes.

## Installation

Add AssertJ-JSON to your project using your preferred build tool. AssertJ-JSON is an extension of AssertJ-core, but you don't need to have AssertJ Core in your dependencies to use it.

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
  <version>v0.1.2</version>
  <scope>test</scope>
</dependency>
```

### Gradle

Add the following to your `build.gradle`:

```groovy
testImplementation 'com.github.archyoshi:assertj-json:v0.1.2'
```

## Basic Usage

To use AssertJ-JSON, you need to use the static `assertThat` method provided by `com.github.archyoshi.assertj.json.JsonAssertions`.

```java
import static com.github.archyoshi.assertj.json.JsonAssertions.assertThat;
import org.junit.jupiter.api.Test;

public class JsonTest {

    @Test
    public void testJsonContent() {
        String json = "{\"name\": \"John\", \"age\": 30, \"active\": true}";

        assertThat(json)
            .isJsonObject()
            .hasField("name")
            .extractFieldAsString("name").isEqualTo("John");
    }
}
```

This simple example checks that the JSON string represents an object, contains a `name` field, and the value of that field is `"John"`.

[Next: Explore Concepts →](./concepts)
