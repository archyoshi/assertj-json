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
package com.archyoshi.assertj.json;

import com.archyoshi.assertj.json.api.JsonComparisonAssert;
import com.archyoshi.assertj.json.api.JsonIterableAssert;
import com.archyoshi.assertj.json.api.JsonNodeAssert;
import com.archyoshi.assertj.json.api.JsonNodeLoader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.file.Path;

/**
 * Entry point for JSON assertions.
 *
 * <p>Example:
 *
 * <pre>
 * <code class='java'> ObjectMapper mapper = new ObjectMapper();
 * JsonNode json = mapper.readTree("{\"name\": \"Vegeta\", \"age\": 30}");
 *
 * // Use static import for convenience
 * import static com.archyoshi.assertj.json.JsonAssertions.assertThat;
 *
 * assertThat(json).hasField("name").hasValueForField("Vegeta", "name"); </code>
 * </pre>
 *
 * @author archyoshi
 * @since 0.1.0
 */
public final class JsonAssertions {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonAssertions() {
        throw new UnsupportedOperationException("This class doesn't need to be instantiated !");
    }

    /**
     * Creates a new assertion object for the given JSON node.
     *
     * @param actual the JSON node to assert on
     * @return a new {@link JsonNodeAssert} instance
     * @throws AssertionError if the actual JSON node is null when an assertion is evaluated
     * @since 0.1.0
     */
    public static JsonNodeAssert assertThat(final JsonNode actual) {
        return new JsonNodeAssert(actual);
    }

    /**
     * Creates a new assertion object for the given JSON string.
     *
     * <p>The string is parsed as JSON content. The default {@code ObjectMapper} is used.
     *
     * @param json the JSON string to assert on
     * @return a new {@link JsonNodeAssert} instance
     * @throws AssertionError if the string is not valid JSON
     * @since 0.1.0
     */
    public static JsonNodeAssert assertThat(final String json) {
        return assertThat(json, MAPPER);
    }

    /**
     * Creates an assertion object for JSON parsed with the supplied mapper.
     *
     * @param json the JSON string to assert on
     * @param mapper the mapper used to parse the string
     * @return a new {@link JsonNodeAssert} instance
     */
    public static JsonNodeAssert assertThat(final String json, final ObjectMapper mapper) {
        return new JsonNodeAssert(JsonNodeLoader.toNode(json, mapper), mapper);
    }

    /**
     * Creates a new assertion object for the given JSON file.
     *
     * <p>The file is read and parsed as JSON content. The default {@code ObjectMapper} is used.
     *
     * @param jsonFile the path to the JSON file to assert on
     * @return a new {@link JsonComparisonAssert} instance
     * @throws AssertionError if the file cannot be read or contains invalid JSON
     * @since 0.1.0
     */
    public static JsonComparisonAssert assertThat(final Path jsonFile) {
        return assertThat(jsonFile, MAPPER);
    }

    /**
     * Creates an assertion object for a JSON file parsed with the supplied mapper.
     *
     * @param jsonFile the JSON file to assert on
     * @param mapper the mapper used to parse JSON
     * @return a new {@link JsonComparisonAssert} instance
     */
    public static JsonComparisonAssert assertThat(final Path jsonFile, final ObjectMapper mapper) {
        return new JsonComparisonAssert(jsonFile, mapper);
    }

    /**
     * Creates a new assertion object for the given JSON file.
     *
     * <p>The file is read and parsed as JSON content. The default {@code ObjectMapper} is used.
     *
     * @param jsonFile the JSON file to assert on
     * @return a new {@link JsonComparisonAssert} instance
     * @throws AssertionError if the file cannot be read or contains invalid JSON
     * @since 0.1.3
     */
    public static JsonComparisonAssert assertThat(final File jsonFile) {
        return assertThat(jsonFile, MAPPER);
    }

    /**
     * Creates an assertion object for a JSON file parsed with the supplied mapper.
     *
     * @param jsonFile the JSON file to assert on
     * @param mapper the mapper used to parse JSON
     * @return a new {@link JsonComparisonAssert} instance
     * @since 0.1.3
     */
    public static JsonComparisonAssert assertThat(final File jsonFile, final ObjectMapper mapper) {
        if (jsonFile == null) {
            throw new AssertionError("Expected JSON file not to be null");
        }
        return assertThat(jsonFile.toPath(), mapper);
    }

    /**
     * Creates iterable assertions for a JSON array node.
     *
     * @param actual the JSON array node
     * @return an assertion object for the array elements
     * @throws AssertionError if the node is null or is not an array
     * @since 0.1.0
     */
    public static JsonIterableAssert assertThatArray(final JsonNode actual) {
        return JsonIterableAssert.assertThat(actual);
    }

    /**
     * Creates iterable assertions for a JSON array string.
     *
     * @param jsonArray the JSON array string
     * @return an assertion object for the array elements
     * @throws AssertionError if the string cannot be parsed or is not a JSON array
     * @since 0.1.3
     */
    public static JsonIterableAssert assertThatArray(final String jsonArray) {
        return assertThatArray(jsonArray, MAPPER);
    }

    /**
     * Creates iterable assertions for a JSON array string parsed with the supplied mapper.
     *
     * @param jsonArray the JSON array string
     * @param mapper the mapper used to parse the string
     * @return an assertion object for the array elements
     * @throws AssertionError if the string cannot be parsed or is not a JSON array
     * @since 0.1.3
     */
    public static JsonIterableAssert assertThatArray(
            final String jsonArray, final ObjectMapper mapper) {
        return assertThatArray(JsonNodeLoader.toNode(jsonArray, mapper));
    }

    /**
     * Creates iterable assertions for a JSON array file at the given path.
     *
     * @param jsonArrayPath the path to the JSON array file
     * @return an assertion object for the array elements
     * @throws AssertionError if the file cannot be read or is not a JSON array
     * @since 0.1.3
     */
    public static JsonIterableAssert assertThatArray(final Path jsonArrayPath) {
        return assertThatArray(jsonArrayPath, MAPPER);
    }

    /**
     * Creates iterable assertions for a JSON array file at the given path using the supplied
     * mapper.
     *
     * @param jsonArrayPath the path to the JSON array file
     * @param mapper the mapper used to parse JSON
     * @return an assertion object for the array elements
     * @throws AssertionError if the file cannot be read or is not a JSON array
     * @since 0.1.3
     */
    public static JsonIterableAssert assertThatArray(
            final Path jsonArrayPath, final ObjectMapper mapper) {
        return assertThatArray(JsonNodeLoader.toNode(jsonArrayPath, mapper));
    }

    /**
     * Creates iterable assertions for a JSON array file.
     *
     * @param jsonArrayFile the JSON array file
     * @return an assertion object for the array elements
     * @throws AssertionError if the file cannot be read or is not a JSON array
     * @since 0.1.3
     */
    public static JsonIterableAssert assertThatArray(final File jsonArrayFile) {
        return assertThatArray(jsonArrayFile, MAPPER);
    }

    /**
     * Creates iterable assertions for a JSON array file using the supplied mapper.
     *
     * @param jsonArrayFile the JSON array file
     * @param mapper the mapper used to parse JSON
     * @return an assertion object for the array elements
     * @throws AssertionError if the file cannot be read or is not a JSON array
     * @since 0.1.3
     */
    public static JsonIterableAssert assertThatArray(
            final File jsonArrayFile, final ObjectMapper mapper) {
        return assertThatArray(JsonNodeLoader.toNode(jsonArrayFile, mapper));
    }
}
