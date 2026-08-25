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
package com.archyoshi.assertj.json;

import com.fasterxml.jackson.databind.JsonNode;
import java.nio.file.Path;
import com.archyoshi.assertj.json.api.JsonNodeAssert;
import com.archyoshi.assertj.json.api.JsonPathAssert;

/**
 * Entry point for JSON assertions.
 * <p>
 * Example:
 *
 * <pre><code class='java'> ObjectMapper mapper = new ObjectMapper();
 * JsonNode json = mapper.readTree("{\"name\": \"Vegeta\", \"age\": 30}");
 *
 * // Use static import for convenience
 * import static com.archyoshi.assertj.json.JsonAssertions.assertThat;
 *
 * assertThat(json).hasField("name").hasValueForField("Vegeta", "name"); </code></pre>
 *
 * @since 0.1.0
 */
public final class JsonAssertions {

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
   * <p>
   * The string is parsed as JSON content.
   *
   * @param json the JSON string to assert on
   * @return a new {@link JsonNodeAssert} instance
   * @throws AssertionError if the string is not valid JSON
   * @since 0.1.0
   */
  public static JsonNodeAssert assertThat(final String json) {
    return new JsonNodeAssert(json);
  }

  /**
   * Creates a new assertion object for the given JSON file.
   * <p>
   * The file is read and parsed as JSON content.
   *
   * @param jsonFile the path to the JSON file to assert on
   * @return a new {@link JsonNodeAssert} instance
   * @throws AssertionError if the file cannot be read or contains invalid JSON
   * @since 0.1.0
   */
  public static JsonNodeAssert assertThat(final Path jsonFile) {
    return new JsonNodeAssert(jsonFile);
  }

  /**
   * Creates a new assertion object for the given JSON path expression.
   *
   * @param path the JSON path expression
   * @return a new {@link JsonPathAssert} instance
   * @since 0.1.0
   */
  public static JsonPathAssert assertThatPath(final String path) {
    return new JsonPathAssert(path);
  }
}
