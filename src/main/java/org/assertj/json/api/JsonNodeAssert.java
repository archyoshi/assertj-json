/*
 * Copyright 2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.assertj.json.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.assertj.core.api.AbstractAssert;

/**
 * Assertions for Jackson {@link JsonNode} objects.
 * <p>
 * This class extends AssertJ's {@link AbstractAssert} to provide fluent assertions for JSON
 * structures parsed using Jackson.
 *
 * @since 0.1.0
 */
public class JsonNodeAssert extends AbstractAssert<JsonNodeAssert, Object> {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private JsonNodeAssert(Object actual) {
    super(actual, JsonNodeAssert.class);
  }

  /**
   * Creates a new assertion object for the given JSON string.
   *
   * @param json the JSON string to parse
   * @since 0.1.0
   */
  public JsonNodeAssert(String json) {
    this(parseJson(json));
  }

  /**
   * Creates a new assertion object for the given JSON file.
   *
   * @param jsonFile the path to the JSON file
   * @since 0.1.0
   */
  public JsonNodeAssert(Path jsonFile) {
    this(readJsonFile(jsonFile));
  }

  /**
   * Creates a new assertion object for the given JSON node.
   *
   * @param actual the JSON node
   * @since 0.1.0
   */
  public JsonNodeAssert(JsonNode actual) {
    this((Object) actual);
  }

  /**
   * Verifies that the actual JSON object has a field with the given name.
   * <p>
   * Example:
   *
   * <pre><code class='java'> ObjectMapper mapper = new ObjectMapper();
   * JsonNode json = mapper.readTree("{\"name\": \"Alice\", \"age\": 30}");
   *
   * // this assertion succeeds
   * assertThat(json).hasField("name");
   *
   * // this assertion fails
   * assertThat(json).hasField("email"); </code></pre>
   *
   * @param fieldName the name of the field to verify
   * @return {@code this} assertion object
   * @throws AssertionError if the actual JSON object is null
   * @throws AssertionError if the actual JSON object does not have a field with the given name
   * @since 0.1.0
   */
  public JsonNodeAssert hasField(String fieldName) {
    JsonNode node = actualAsJsonNode();
    isNotNull();
    if (!node.has(fieldName)) {
      failWithActualExpectedAndMessage(node, fieldName, "Expected JSON to contain field '%s'", fieldName);
    }
    return this;
  }

  /**
   * Verifies that the actual JSON object has a field with the given name and string value.
   * <p>
   * Example:
   *
   * <pre><code class='java'> ObjectMapper mapper = new ObjectMapper();
   * JsonNode json = mapper.readTree("{\"status\": \"active\"}");
   *
   * // this assertion succeeds
   * assertThat(json).hasValue("status", "active");
   *
   * // this assertion fails
   * assertThat(json).hasValue("status", "inactive"); </code></pre>
   *
   * @param fieldName the name of the field to verify
   * @param expectedValue the expected string value
   * @return {@code this} assertion object
   * @throws AssertionError if the actual JSON object is null
   * @throws AssertionError if the field does not exist or has a different value
   * @since 0.1.0
   */
  public JsonNodeAssert hasValue(String fieldName, String expectedValue) {
    JsonNode node = actualAsJsonNode();
    hasField(fieldName);
    JsonNode field = node.get(fieldName);
    assertThat(field.asText()).isEqualTo(expectedValue);
    return this;
  }

  /**
   * Verifies that the actual JSON object has a field with the given name and integer value.
   * <p>
   * Example:
   *
   * <pre><code class='java'> ObjectMapper mapper = new ObjectMapper();
   * JsonNode json = mapper.readTree("{\"age\": 30}");
   *
   * // this assertion succeeds
   * assertThat(json).hasValue("age", 30);
   *
   * // this assertion fails
   * assertThat(json).hasValue("age", 25); </code></pre>
   *
   * @param fieldName the name of the field to verify
   * @param expectedValue the expected integer value
   * @return {@code this} assertion object
   * @throws AssertionError if the actual JSON object is null
   * @throws AssertionError if the field does not exist or has a different value
   * @since 0.1.0
   */
  public JsonNodeAssert hasValue(String fieldName, int expectedValue) {
    JsonNode node = actualAsJsonNode();
    hasField(fieldName);
    JsonNode field = node.get(fieldName);
    assertThat(field.asInt()).isEqualTo(expectedValue);
    return this;
  }

  /**
   * Verifies that the actual JSON content is equal to the expected JSON string.
   * <p>
   * Both strings are parsed as JSON and compared structurally.
   * <p>
   * Example:
   *
   * <pre><code class='java'> ObjectMapper mapper = new ObjectMapper();
   * JsonNode json = mapper.readTree("{\"name\": \"Alice\"}");
   *
   * // this assertion succeeds (same structure, different formatting)
   * assertThat(json).hasJsonContent("{\"name\":\"Alice\"}");
   *
   * // this assertion fails
   * assertThat(json).hasJsonContent("{\"name\": \"Bob\"}"); </code></pre>
   *
   * @param expectedJson the expected JSON string
   * @return {@code this} assertion object
   * @throws AssertionError if the actual JSON is not equal to the expected JSON
   * @since 0.1.0
   */
  public JsonNodeAssert hasJsonContent(String expectedJson) {
    JsonNode actualNode = actualAsJsonNode();
    JsonNode expectedNode = parseJson(expectedJson);
    assertThat(actualNode).isEqualTo(expectedNode);
    return this;
  }

  /**
   * Verifies that the actual JSON node is a JSON object.
   * <p>
   * Example:
   *
   * <pre><code class='java'> ObjectMapper mapper = new ObjectMapper();
   * JsonNode json = mapper.readTree("{\"name\": \"Alice\"}");
   *
   * // this assertion succeeds
   * assertThat(json).isObject();
   *
   * // this assertion fails
   * assertThat(mapper.readTree("[]")).isObject(); </code></pre>
   *
   * @return {@code this} assertion object
   * @throws AssertionError if the actual JSON node is not an object
   * @since 0.1.0
   */
  public JsonNodeAssert isObject() {
    JsonNode node = actualAsJsonNode();
    assertThat(node.isObject()).isTrue();
    return this;
  }

  /**
   * Verifies that the actual JSON node is a JSON array.
   * <p>
   * Example:
   *
   * <pre><code class='java'> ObjectMapper mapper = new ObjectMapper();
   * JsonNode json = mapper.readTree("[1, 2, 3]");
   *
   * // this assertion succeeds
   * assertThat(json).isArray();
   *
   * // this assertion fails
   * assertThat(mapper.readTree("{\"name\": \"Alice\"}")).isArray(); </code></pre>
   *
   * @return {@code this} assertion object
   * @throws AssertionError if the actual JSON node is not an array
   * @since 0.1.0
   */
  public JsonNodeAssert isArray() {
    JsonNode node = actualAsJsonNode();
    assertThat(node.isArray()).isTrue();
    return this;
  }

  /**
   * Verifies that the actual JSON node is empty.
   * <p>
   * A JSON object or array is empty if it contains no elements.
   * <p>
   * Example:
   *
   * <pre><code class='java'> ObjectMapper mapper = new ObjectMapper();
   * JsonNode emptyObject = mapper.readTree("{}");
   * JsonNode emptyArray = mapper.readTree("[]");
   *
   * // these assertions succeed
   * assertThat(emptyObject).isEmpty();
   * assertThat(emptyArray).isEmpty();
   *
   * // this assertion fails
   * assertThat(mapper.readTree("{\"name\": \"Alice\"}")).isEmpty(); </code></pre>
   *
   * @return {@code this} assertion object
   * @throws AssertionError if the actual JSON node is not empty
   * @since 0.1.0
   */
  public JsonNodeAssert isEmpty() {
    JsonNode node = actualAsJsonNode();
    assertThat(node.size()).isZero();
    return this;
  }

  /**
   * Verifies that the actual JSON node is not empty.
   * <p>
   * Example:
   *
   * <pre><code class='java'> ObjectMapper mapper = new ObjectMapper();
   * JsonNode nonEmpty = mapper.readTree("{\"name\": \"Alice\"}");
   *
   * // this assertion succeeds
   * assertThat(nonEmpty).isNotEmpty();
   *
   * // this assertion fails
   * assertThat(mapper.readTree("{}")).isNotEmpty(); </code></pre>
   *
   * @return {@code this} assertion object
   * @throws AssertionError if the actual JSON node is empty
   * @since 0.1.0
   */
  public JsonNodeAssert isNotEmpty() {
    JsonNode node = actualAsJsonNode();
    assertThat(node.size()).isGreaterThan(0);
    return this;
  }

  /**
   * Verifies that the actual JSON array has the given size.
   * <p>
   * Example:
   *
   * <pre><code class='java'> ObjectMapper mapper = new ObjectMapper();
   * JsonNode array = mapper.readTree("[1, 2, 3]");
   *
   * // this assertion succeeds
   * assertThat(array).hasSize(3);
   *
   * // this assertion fails
   * assertThat(array).hasSize(5); </code></pre>
   *
   * @param expectedSize the expected size
   * @return {@code this} assertion object
   * @throws AssertionError if the actual JSON node size does not match
   * @since 0.1.0
   */
  public JsonNodeAssert hasSize(int expectedSize) {
    JsonNode node = actualAsJsonNode();
    assertThat(node.size()).isEqualTo(expectedSize);
    return this;
  }

  private JsonNode actualAsJsonNode() {
    Object actualValue = actual;
    if (actualValue instanceof JsonNode jsonNode) {
      return jsonNode;
    }
    if (actualValue instanceof String str) {
      return parseJson(str);
    }
    if (actualValue instanceof Path path) {
      return readJsonFile(path);
    }
    throw new AssertionError("JSON actual value must be JsonNode, String or Path");
  }

  private static JsonNode parseJson(String json) {
    try {
      return MAPPER.readTree(json);
    } catch (JsonProcessingException e) {
      throw new AssertionError("Invalid JSON content: " + e.getOriginalMessage(), e);
    }
  }

  private static JsonNode readJsonFile(Path jsonFile) {
    try {
      String contents = Files.readString(jsonFile);
      return parseJson(contents);
    } catch (IOException e) {
      throw new AssertionError("Unable to read JSON file: " + jsonFile, e);
    }
  }
}
