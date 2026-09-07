---
title: Concepts
sidebar_position: 2
---

# Concepts

AssertJ-JSON is built on top of AssertJ and Jackson to provide a fluent and intuitive way to test JSON payloads.

## Extension of AssertJ

AssertJ-JSON follows the same principles as [AssertJ Core](https://assertj.github.io/doc/). It provides a fluent API where you chain assertions on your JSON objects. This means that if you are familiar with AssertJ, you will feel right at home using AssertJ-JSON.

Instead of writing complex code to parse JSON and then asserting on the parsed objects, AssertJ-JSON handles the parsing under the hood and gives you direct methods like `hasField` and `hasValueForField`.

## Jackson Integration

Under the hood, AssertJ-JSON leverages [Jackson](https://github.com/FasterXML/jackson), a high-performance JSON processor for Java. It automatically converts your JSON inputs into Jackson's `JsonNode` or `ObjectNode` trees.

You can also pass a custom `ObjectMapper` if your JSON requires specific configuration (such as custom deserializers, date formats, or ignoring unknown properties) during the parsing phase.

## Versatile Inputs

AssertJ-JSON is designed to be flexible with the type of input it receives. You can assert directly on:

1.  **JSON Strings:** Perfect for small payloads or inline JSON strings in your tests.
2.  **`JsonNode` / `ObjectNode`:** If you have already parsed the JSON in your test, you can pass the node directly.
3.  **`java.nio.file.Path`:** Read JSON directly from a file path.
4.  **`java.io.File`:** Standard Java `File` objects are also fully supported.

This makes it easy to maintain external JSON fixture files and assert that your API responses match the expected contents exactly, without manual File I/O in every test.
