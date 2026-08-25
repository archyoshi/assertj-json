/*
* Copyright (C)2026 the original author or authors.
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
package com.archyoshi.assertj.json.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.ThrowingConsumer;

/**
 * Assertions for Jackson {@link JsonNode} objects.
 *
 * <p>This class extends AssertJ's {@link AbstractAssert} to provide fluent assertions for JSON
 * structures parsed using Jackson.
 *
 * @since 0.1.0
 */
public class JsonNodeAssert extends AbstractAssert<JsonNodeAssert, JsonNode> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public JsonNodeAssert(final JsonNode actual) {
        super(actual, JsonNodeAssert.class);
    }

    /**
     * Creates a new assertion object for the given JSON string.
     *
     * @param json the JSON string to parse
     * @since 0.1.0
     */
    public JsonNodeAssert(final String json) {
        this(parseJson(json));
    }

    /**
     * Creates a new assertion object for the given JSON file.
     *
     * @param jsonFile the path to the JSON file
     * @since 0.1.0
     */
    public JsonNodeAssert(final Path jsonFile) {
        this(readJsonFile(jsonFile));
    }

    /**
     * Creates a new assertion object for the given JSON node.
     *
     * @param actual the JSON node
     * @since 0.1.0
     */
    /**
     * Verifies that the actual JSON object has a field with the given name.
     *
     * <p>Example:
     *
     * <pre>
     * <code class='java'> ObjectMapper mapper = new ObjectMapper();
     * JsonNode json = mapper.readTree("{\"name\": \"Vegeta\", \"age\": 30}");
     *
     * // this assertion succeeds
     * assertThat(json).hasField("name");
     *
     * // this assertion fails
     * assertThat(json).hasField("email"); </code>
     * </pre>
     *
     * @param fieldName the name of the field to verify
     * @return {@code this} assertion object
     * @throws AssertionError if the actual JSON object is null
     * @throws AssertionError if the actual JSON object does not have a field with the given name
     * @since 0.1.0
     */
    public JsonNodeAssert hasField(final String fieldName) {
        final JsonNode node = actualAsJsonNode();
        isNotNull();
        if (!node.has(fieldName)) {
            failWithActualExpectedAndMessage(
                    node, fieldName, "Expected JSON to contain field '%s'", fieldName);
        }
        return this;
    }

    /**
     * Verifies that the JSON object does not have a field with the given name.
     *
     * @param fieldName the field name to verify
     * @return {@code this} assertion object
     */
    public JsonNodeAssert doesNotHaveField(final String fieldName) {
        final JsonNode node = actualAsJsonNode();
        isNotNull();
        if (node.has(fieldName)) {
            failWithActualExpectedAndMessage(
                    node, fieldName, "Expected JSON not to contain field '%s'", fieldName);
        }
        return this;
    }

    /**
     * Verifies the Jackson node type of a field.
     *
     * @param fieldName the field name to verify
     * @param expectedType the expected node type
     * @return {@code this} assertion object
     */
    public JsonNodeAssert hasTypeForField(final String fieldName, final JsonNodeType expectedType) {
        final JsonNode node = actualAsJsonNode();
        hasField(fieldName);
        assertThat(node.get(fieldName).getNodeType()).isEqualTo(expectedType);
        return this;
    }

    /**
     * Verifies a field value after converting it to the requested Java type.
     *
     * @param expectedValue the expected value
     * @param fieldName the field name to verify
     * @param valueType the target Java type
     * @param <T> the target Java type
     * @return {@code this} assertion object
     */
    public <T> JsonNodeAssert hasTypedValueForField(
            final T expectedValue, final String fieldName, final Class<T> valueType) {
        final JsonNode node = actualAsJsonNode();
        hasField(fieldName);
        assertThat(MAPPER.convertValue(node.get(fieldName), valueType)).isEqualTo(expectedValue);
        return this;
    }

    /**
     * Verifies that the actual JSON object has a field with the given name and string value.
     *
     * <p>Example:
     *
     * <pre>
     * <code class='java'> ObjectMapper mapper = new ObjectMapper();
     * JsonNode json = mapper.readTree("{\"status\": \"active\"}");
     *
     * // this assertion succeeds
     * assertThat(json).hasValueForField("active", "status");
     *
     * // this assertion fails
     * assertThat(json).hasValueForField("inactive", "status"); </code>
     * </pre>
     *
     * @param expectedValue the expected string value
     * @param fieldName the name of the field to verify
     * @return {@code this} assertion object
     * @throws AssertionError if the actual JSON object is null
     * @throws AssertionError if the field does not exist or has a different value
     * @since 0.1.0
     */
    public JsonNodeAssert hasValueForField(final String expectedValue, final String fieldName) {
        final JsonNode node = actualAsJsonNode();
        hasField(fieldName);
        final JsonNode field = node.get(fieldName);
        assertThat(field.asText()).isEqualTo(expectedValue);
        return this;
    }

    /**
     * Verifies that the actual JSON object has a field with the given name and integer value.
     *
     * <p>Example:
     *
     * <pre>
     * <code class='java'> ObjectMapper mapper = new ObjectMapper();
     * JsonNode json = mapper.readTree("{\"age\": 30}");
     *
     * // this assertion succeeds
     * assertThat(json).hasValueForField(30, "age");
     *
     * // this assertion fails
     * assertThat(json).hasValueForField(25, "age"); </code>
     * </pre>
     *
     * @param expectedValue the expected integer value
     * @param fieldName the name of the field to verify
     * @return {@code this} assertion object
     * @throws AssertionError if the actual JSON object is null
     * @throws AssertionError if the field does not exist or has a different value
     * @since 0.1.0
     */
    public JsonNodeAssert hasValueForField(final int expectedValue, final String fieldName) {
        final JsonNode node = actualAsJsonNode();
        hasField(fieldName);
        final JsonNode field = node.get(fieldName);
        assertThat(field.asInt()).isEqualTo(expectedValue);
        return this;
    }

    /**
     * Verifies that a numeric field equals the expected value.
     *
     * @param expectedValue the expected value
     * @param fieldName the field name to verify
     * @return {@code this} assertion object
     */
    public JsonNodeAssert hasValueEqualForField(final int expectedValue, final String fieldName) {
        return hasNumericValueForField(
                expectedValue, fieldName, value -> value == expectedValue, "equal to");
    }

    /**
     * Verifies that a numeric field is greater than the expected value.
     *
     * @param expectedValue the exclusive lower bound
     * @param fieldName the field name to verify
     * @return {@code this} assertion object
     */
    public JsonNodeAssert hasValueMoreThanForField(
            final int expectedValue, final String fieldName) {
        return hasNumericValueForField(
                expectedValue, fieldName, value -> value > expectedValue, "greater than");
    }

    /**
     * Verifies that a numeric field is less than the expected value.
     *
     * @param expectedValue the exclusive upper bound
     * @param fieldName the field name to verify
     * @return {@code this} assertion object
     */
    public JsonNodeAssert hasValueLessThanForField(
            final int expectedValue, final String fieldName) {
        return hasNumericValueForField(
                expectedValue, fieldName, value -> value < expectedValue, "less than");
    }

    private JsonNodeAssert hasNumericValueForField(
            final int expectedValue,
            final String fieldName,
            final Predicate<Integer> condition,
            final String relation) {
        final JsonNode node = actualAsJsonNode();
        hasField(fieldName);
        final JsonNode field = node.get(fieldName);
        if (!field.isNumber()) {
            failWithMessage(
                    "Expected field '%s' to be numeric and %s '%s'",
                    fieldName, relation, expectedValue);
        }
        if (!condition.test(field.asInt())) {
            failWithMessage(
                    "Expected numeric field '%s' to be %s '%s' but was '%s'",
                    fieldName, relation, expectedValue, field.asInt());
        }
        return this;
    }

    /**
     * Verifies that the actual JSON content is equal to the expected JSON string.
     *
     * <p>Both strings are parsed as JSON and compared structurally.
     *
     * <p>Example:
     *
     * <pre>
     * <code class='java'> ObjectMapper mapper = new ObjectMapper();
     * JsonNode json = mapper.readTree("{\"name\": \"Vegeta\"}");
     *
     * // this assertion succeeds (same structure, different formatting)
     * assertThat(json).hasJsonContent("{\"name\":\"Vegeta\"}");
     *
     * // this assertion fails
     * assertThat(json).hasJsonContent("{\"name\": \"Goku\"}"); </code>
     * </pre>
     *
     * @param expectedJson the expected JSON string
     * @return {@code this} assertion object
     * @throws AssertionError if the actual JSON is not equal to the expected JSON
     * @since 0.1.0
     */
    public JsonNodeAssert hasJsonContent(final String expectedJson) {
        final JsonNode actualNode = actualAsJsonNode();
        final JsonNode expectedNode = parseJson(expectedJson);
        assertThat(actualNode).isEqualTo(expectedNode);
        return this;
    }

    /**
     * Verifies structural JSON equality while ignoring named fields recursively.
     *
     * @param expected the expected JSON node
     * @param ignoredFields field names to ignore at every object level
     * @return {@code this} assertion object
     */
    public JsonNodeAssert isEqualToIgnoringFields(
            final JsonNode expected, final List<String> ignoredFields) {
        final JsonNode actualNode = actualAsJsonNode();
        if (!equalsIgnoringFields(actualNode, expected, ignoredFields)) {
            failWithMessage(
                    "Expected JSON to match while ignoring fields %s, but was %s",
                    ignoredFields, actualNode);
        }
        return this;
    }

    private boolean equalsIgnoringFields(
            final JsonNode actualNode,
            final JsonNode expectedNode,
            final List<String> ignoredFields) {
        if (actualNode == null || expectedNode == null) {
            return actualNode == expectedNode;
        }
        if (actualNode.isObject() && expectedNode.isObject()) {
            final Iterator<Map.Entry<String, JsonNode>> expectedFields = expectedNode.fields();
            while (expectedFields.hasNext()) {
                final Map.Entry<String, JsonNode> entry = expectedFields.next();
                if (!ignoredFields.contains(entry.getKey())
                        && !equalsIgnoringFields(
                                actualNode.get(entry.getKey()), entry.getValue(), ignoredFields)) {
                    return false;
                }
            }
            final Iterator<String> actualFields = actualNode.fieldNames();
            while (actualFields.hasNext()) {
                final String fieldName = actualFields.next();
                if (!ignoredFields.contains(fieldName) && !expectedNode.has(fieldName)) {
                    return false;
                }
            }
            return true;
        }
        if (actualNode.isArray() && expectedNode.isArray()) {
            if (actualNode.size() != expectedNode.size()) {
                return false;
            }
            for (int index = 0; index < actualNode.size(); index++) {
                if (!equalsIgnoringFields(
                        actualNode.get(index), expectedNode.get(index), ignoredFields)) {
                    return false;
                }
            }
            return true;
        }
        return actualNode.equals(expectedNode);
    }

    /**
     * Verifies that the actual JSON node is a JSON object.
     *
     * <p>Example:
     *
     * <pre>
     * <code class='java'> ObjectMapper mapper = new ObjectMapper();
     * JsonNode json = mapper.readTree("{\"name\": \"Vegeta\"}");
     *
     * // this assertion succeeds
     * assertThat(json).isObject();
     *
     * // this assertion fails
     * assertThat(mapper.readTree("[]")).isObject(); </code>
     * </pre>
     *
     * @return {@code this} assertion object
     * @throws AssertionError if the actual JSON node is not an object
     * @since 0.1.0
     */
    public JsonNodeAssert isObject() {
        final JsonNode node = actualAsJsonNode();
        assertThat(node.isObject()).isTrue();
        return this;
    }

    /**
     * Verifies that the actual JSON node is a JSON array.
     *
     * <p>Example:
     *
     * <pre>
     * <code class='java'> ObjectMapper mapper = new ObjectMapper();
     * JsonNode json = mapper.readTree("[1, 2, 3]");
     *
     * // this assertion succeeds
     * assertThat(json).isArray();
     *
     * // this assertion fails
     * assertThat(mapper.readTree("{\"name\": \"Vegeta\"}")).isArray(); </code>
     * </pre>
     *
     * @return {@code this} assertion object
     * @throws AssertionError if the actual JSON node is not an array
     * @since 0.1.0
     */
    public JsonNodeAssert isArray() {
        final JsonNode node = actualAsJsonNode();
        assertThat(node.isArray()).isTrue();
        return this;
    }

    /**
     * Verifies that the actual JSON node is empty.
     *
     * <p>A JSON object or array is empty if it contains no elements.
     *
     * <p>Example:
     *
     * <pre>
     * <code class='java'> ObjectMapper mapper = new ObjectMapper();
     * JsonNode emptyObject = mapper.readTree("{}");
     * JsonNode emptyArray = mapper.readTree("[]");
     *
     * // these assertions succeed
     * assertThat(emptyObject).isEmpty();
     * assertThat(emptyArray).isEmpty();
     *
     * // this assertion fails
     * assertThat(mapper.readTree("{\"name\": \"Vegeta\"}")).isEmpty(); </code>
     * </pre>
     *
     * @return {@code this} assertion object
     * @throws AssertionError if the actual JSON node is not empty
     * @since 0.1.0
     */
    public JsonNodeAssert isEmpty() {
        final JsonNode node = actualAsJsonNode();
        assertThat(node.size()).isZero();
        return this;
    }

    /**
     * Verifies that the actual JSON node is not empty.
     *
     * <p>Example:
     *
     * <pre>
     * <code class='java'> ObjectMapper mapper = new ObjectMapper();
     * JsonNode nonEmpty = mapper.readTree("{\"name\": \"Vegeta\"}");
     *
     * // this assertion succeeds
     * assertThat(nonEmpty).isNotEmpty();
     *
     * // this assertion fails
     * assertThat(mapper.readTree("{}")).isNotEmpty(); </code>
     * </pre>
     *
     * @return {@code this} assertion object
     * @throws AssertionError if the actual JSON node is empty
     * @since 0.1.0
     */
    public JsonNodeAssert isNotEmpty() {
        final JsonNode node = actualAsJsonNode();
        assertThat(node.size()).isGreaterThan(0);
        return this;
    }

    /**
     * Verifies that the actual JSON array has the given size.
     *
     * <p>Example:
     *
     * <pre>
     * <code class='java'> ObjectMapper mapper = new ObjectMapper();
     * JsonNode array = mapper.readTree("[1, 2, 3]");
     *
     * // this assertion succeeds
     * assertThat(array).hasSize(3);
     *
     * // this assertion fails
     * assertThat(array).hasSize(5); </code>
     * </pre>
     *
     * @param expectedSize the expected size
     * @return {@code this} assertion object
     * @throws AssertionError if the actual JSON node size does not match
     * @since 0.1.0
     */
    public JsonNodeAssert hasSize(final int expectedSize) {
        final JsonNode node = actualAsJsonNode();
        assertThat(node.size()).isEqualTo(expectedSize);
        return this;
    }

    /**
     * Verifies the size of a named JSON array field.
     *
     * @param expectedSize the expected array size
     * @param fieldName the array field name
     * @return {@code this} assertion object
     */
    public JsonNodeAssert hasSizeForArrayField(final int expectedSize, final String fieldName) {
        extractingFieldAsArray(fieldName).hasSize(expectedSize);
        return this;
    }

    /**
     * Extracts a child field for further JSON assertions.
     *
     * @param fieldName the field name to extract
     * @return an assertion object for the child node
     */
    public JsonNodeAssert extractingFieldAsJsonNode(final String fieldName) {
        final JsonNode node = actualAsJsonNode();
        hasField(fieldName);
        return new JsonNodeAssert(node.get(fieldName));
    }

    /**
     * Extracts a named JSON array for standard and JSON-aware iterable assertions.
     *
     * @param fieldName the array field name
     * @return an assertion object for the array elements
     */
    public JsonIterableAssert extractingFieldAsArray(final String fieldName) {
        final JsonNode node = actualAsJsonNode();
        hasField(fieldName);
        return JsonIterableAssert.assertThat(node.get(fieldName));
    }

    /**
     * Applies one or more assertions to the current JSON node.
     *
     * @param assertions assertions to apply
     * @return {@code this} assertion object
     */
    @SafeVarargs
    public final JsonNodeAssert hasNodeThatSatisfies(
            final ThrowingConsumer<? super JsonNode>... assertions) {
        isNotNull();
        satisfiesForProxy(assertions);
        return this;
    }

    private JsonNode actualAsJsonNode() {
        final Object actualValue = actual;
        if (actualValue instanceof final JsonNode jsonNode) {
            return jsonNode;
        }
        if (actualValue instanceof final String str) {
            return parseJson(str);
        }
        if (actualValue instanceof final Path path) {
            return readJsonFile(path);
        }
        throw new AssertionError("JSON actual value must be JsonNode, String or Path");
    }

    private static JsonNode parseJson(final String json) {
        try {
            return MAPPER.readTree(json);
        } catch (final JsonProcessingException e) {
            throw new AssertionError("Invalid JSON content: " + e.getOriginalMessage(), e);
        }
    }

    private static JsonNode readJsonFile(final Path jsonFile) {
        try {
            final String contents = Files.readString(jsonFile);
            return parseJson(contents);
        } catch (final IOException e) {
            throw new AssertionError("Unable to read JSON file: " + jsonFile, e);
        }
    }
}
