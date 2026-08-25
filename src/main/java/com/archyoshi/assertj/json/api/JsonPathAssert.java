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
package com.archyoshi.assertj.json.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;

/**
 * Assertions for JSON path expressions.
 * <p>
 * This class provides assertions for verifying JSON path expressions.
 *
 * @since 0.1.0
 */
public class JsonPathAssert extends AbstractAssert<JsonPathAssert, String> {

  /**
   * Creates a new assertion object for the given JSON path.
   *
   * @param actual the JSON path expression
   * @since 0.1.0
   */
  public JsonPathAssert(String actual) {
    super(actual, JsonPathAssert.class);
  }

  /**
   * Verifies that the JSON path is not empty or null.
   * <p>
   * Example:
   *
   * <pre><code class='java'> // this assertion succeeds
   * assertThatPath("$.name").exists();
   *
   * // this assertion fails
   * assertThatPath("").exists();
   * assertThatPath(null).exists(); </code></pre>
   *
   * @return {@code this} assertion object
   * @throws AssertionError if the path is empty or null
   * @since 0.1.0
   */
  public JsonPathAssert exists() {
    assertThat(actual).isNotBlank();
    return this;
  }
}

