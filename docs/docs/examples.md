---
title: Examples
sidebar_position: 5
---

# Examples

Here are some complete, copy-pasteable examples demonstrating how to use AssertJ-JSON in various scenarios, modeled after our own test suite.

## Example 1: Asserting on a JSON String

The most basic usage is parsing an inline JSON string and asserting on its properties.

```java
import static com.archyoshi.assertj.json.JsonAssertions.assertThat;
import org.junit.jupiter.api.Test;

class JsonStringTest {
    @Test
    void shouldParseJsonStringAndAssertFieldValue() {
        String json = "{ \"name\": \"Vegeta\", \"age\": 30, \"active\": true }";
        
        assertThat(json)
            .hasField("name")
            .hasValueForField("Vegeta", "name")
            .hasField("age")
            .hasValueForField(30, "age");
    }
}
```

## Example 2: Asserting with a Custom ObjectMapper

By default, AssertJ-JSON creates a standard Jackson `ObjectMapper` to parse strings and files. However, you can pass your own configured `ObjectMapper` if you need custom deserialization logic.

```java
import static com.archyoshi.assertj.json.JsonAssertions.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class CustomMapperTest {
    @Test
    void shouldUseProvidedObjectMapperForStringParsing() {
        ObjectMapper mapper = new ObjectMapper();
        // Configure mapper here...

        assertThat("{ \"name\": \"Goku\" }", mapper)
            .hasValueForField("Goku", "name");
    }
}
```

## Example 3: Asserting directly on `JsonNode`

If your code under test already returns a Jackson `JsonNode` or `ObjectNode`, you can pass it directly to `assertThat` without needing to serialize it to a string first.

```java
import static com.archyoshi.assertj.json.JsonAssertions.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

class JsonNodeTest {
    @Test
    void shouldAssertOnJsonNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("name", "Goku");
        
        assertThat(node)
            .hasField("name")
            .hasValueForField("Goku", "name");
    }
}
```

## Example 4: Asserting on Files and Paths

You can assert that a file's contents match an expected JSON structure. You can use either `java.io.File` or `java.nio.file.Path`.

```java
import static com.archyoshi.assertj.json.JsonAssertions.assertThat;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

class FileTest {
    @Test
    void shouldAssertOnFileDirectly() throws Exception {
        Path actualPath = Files.createTempFile("actual-", ".json");
        Files.writeString(actualPath, "{\"name\":\"Vegeta\"}");
        File actualFile = actualPath.toFile();

        // You can check that the fields match an expected JSON string
        assertThat(actualFile)
            .hasSameFieldsAs("{\"name\":\"Goku\"}");
    }
    
    @Test
    void shouldAssertOnPathAndCompareContent() throws Exception {
        Path actual = Files.createTempFile("actual-", ".json");
        Path expected = Files.createTempFile("expected-", ".json");
        Files.writeString(actual, "{ \"name\": \"Goku\" }");
        Files.writeString(expected, "{\n  \"name\": \"Goku\"\n}");

        // Compare two Paths ignoring formatting
        assertThat(actual).hasSameContentAs(expected);
    }
}
```
