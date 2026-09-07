---
title: Features Highlight
sidebar_position: 3
---

# Features Highlight

AssertJ-JSON provides a comprehensive set of methods to validate JSON structures, values, and arrays. Below are the key features highlighted with examples.

## Object Field Assertions

You can easily assert the presence and value of fields within a JSON object.

```java
import static com.archyoshi.assertj.json.JsonAssertions.assertThat;

String json = "{ \"name\": \"Vegeta\", \"age\": 30, \"active\": true }";

assertThat(json)
    .hasField("name")
    .hasValueForField("Vegeta", "name")
    .hasField("age")
    .hasValueForField(30, "age");
```

## Content and Structure Comparison

AssertJ-JSON allows you to compare entire JSON payloads against expected strings or files. This is extremely useful for verifying large API responses against a known good snapshot.

### Strict Equality

Use `hasSameContentAs` to verify that the JSON precisely matches the expected structure and values, ignoring formatting (like whitespace and newlines).

```java
Path actualFile = Path.of("src/test/resources/example.json");

assertThat(actualFile).hasSameContentAs("""
    {
      "name": "Vegeta"
    }
    """);
```

### Key Equality

Use `hasSameFieldsAs` when you only care that two JSON payloads contain the exact same keys (fields), regardless of their actual values. This is great for schema validation.

```java
assertThat(actualFile).hasSameFieldsAs(Path.of("src/test/resources/expected.json"));
```

### Partial Matches

If your API response contains dynamic data (like IDs or timestamps) and you only want to verify a subset of the JSON, use `partiallyContains`.

```java
assertThat(actualFile).partiallyContains("""
    {
      "active": true
    }
    """);
```

## Array Assertions

AssertJ-JSON provides the `assertThatArray` entry point for dealing specifically with JSON arrays.

```java
import static com.archyoshi.assertj.json.JsonAssertions.assertThatArray;

String jsonArray = "[{\"name\":\"Vegeta\"}, {\"name\":\"Goku\"}]";

assertThatArray(jsonArray)
    .containsElementWithFieldName("name")
    .containsElementWithFieldAndValue("name", "Vegeta");
```
