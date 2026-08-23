# AssertJ JSON

AssertJ JSON adds fluent assertions tailored for JSON payloads, JSON files, and JSON tree values.

## Goals

- assert JSON structure and values using the AssertJ style
- work with `JsonNode` from Jackson
- support JSON files as inputs
- remain easy to consume from Maven and Gradle

## Usage

```java
import static org.assertj.json.JsonAssertions.assertThat;

String json = "{ \"name\": \"Alice\", \"age\": 30, \"active\": true }";

assertThat(json)
  .hasField("name")
  .hasValue("name", "Alice")
  .hasField("age")
  .hasValue("age", 30);
```

```java
import static org.assertj.json.JsonAssertions.assertThat;

Path file = Path.of("src/test/resources/example.json");

assertThat(file)
  .exists()
  .hasJsonContent("{ \"name\": \"Alice\" }");
```

## Maven

```xml
<dependency>
  <groupId>org.assertj</groupId>
  <artifactId>assertj-json</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Building

```bash
mvn clean verify
```

## License

This project is licensed under the Apache License 2.0.
