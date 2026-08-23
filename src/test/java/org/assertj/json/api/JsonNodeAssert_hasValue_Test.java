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

class JsonNodeAssert_hasValue_Test {

  private ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
  }

  @Test
  void should_pass_if_json_field_has_expected_string_value() {
    // GIVEN
    JsonNode actual = mapper.convertValue(java.util.Map.of("status", "active"), JsonNode.class);
    // WHEN/THEN
    assertThat(actual).hasValue("status", "active");
  }

  @Test
  void should_pass_if_json_field_has_expected_integer_value() {
    // GIVEN
    JsonNode actual = mapper.convertValue(java.util.Map.of("age", 30), JsonNode.class);
    // WHEN/THEN
    assertThat(actual).hasValue("age", 30);
  }

  @Test
  void should_fail_if_json_field_has_different_string_value() {
    // GIVEN
    JsonNode actual = mapper.convertValue(java.util.Map.of("status", "active"), JsonNode.class);
    // WHEN/THEN
    thenThrownBy(() -> assertThat(actual).hasValue("status", "inactive"))
        .isInstanceOf(AssertionError.class)
        .hasMessageContaining("inactive");
  }

  @Test
  void should_fail_if_json_field_does_not_exist() {
    // GIVEN
    JsonNode actual = mapper.convertValue(java.util.Map.of("status", "active"), JsonNode.class);
    // WHEN/THEN
    thenThrownBy(() -> assertThat(actual).hasValue("email", "test@example.com"))
        .isInstanceOf(AssertionError.class)
        .hasMessageContaining("email");
  }
}
