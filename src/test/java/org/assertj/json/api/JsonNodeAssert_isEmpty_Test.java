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

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.assertj.json.JsonAssertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonNodeAssert_isEmpty_Test {

  private ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
  }

  @Test
  void should_pass_if_json_object_is_empty() {
    // GIVEN
    JsonNode actual = mapper.convertValue(java.util.Map.of(), JsonNode.class);
    // WHEN/THEN
    assertThat(actual).isEmpty();
  }

  @Test
  void should_pass_if_json_array_is_empty() {
    // GIVEN
    JsonNode actual = mapper.createArrayNode();
    // WHEN/THEN
    assertThat(actual).isEmpty();
  }

  @Test
  void should_fail_if_json_object_is_not_empty() {
    // GIVEN
    JsonNode actual = mapper.convertValue(java.util.Map.of("name", "Alice"), JsonNode.class);
    // WHEN/THEN
    thenThrownBy(() -> assertThat(actual).isEmpty())
        .isInstanceOf(AssertionError.class)
      .hasMessageContaining("expected: 0")
      .hasMessageContaining("but was: 1");
  }

  @Test
  void should_fail_if_json_array_is_not_empty() throws Exception {
    // GIVEN
    JsonNode actual = mapper.readTree("[1, 2, 3]");
    // WHEN/THEN
    thenThrownBy(() -> assertThat(actual).isEmpty())
        .isInstanceOf(AssertionError.class)
      .hasMessageContaining("expected: 0")
      .hasMessageContaining("but was: 3");
  }
}
