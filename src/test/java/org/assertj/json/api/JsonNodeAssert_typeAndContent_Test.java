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

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.assertj.json.JsonAssertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonNodeAssert_typeAndContent_Test {

  private ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
  }

  @Test
  void should_pass_if_json_is_an_object() throws Exception {
    // GIVEN
    JsonNode actual = mapper.readTree("{\"name\":\"Alice\"}");
    // WHEN/THEN
    assertThat(actual).isObject();
  }

  @Test
  void should_fail_if_json_is_not_an_object() throws Exception {
    // GIVEN
    JsonNode actual = mapper.readTree("[]");
    // WHEN/THEN
    thenThrownBy(() -> assertThat(actual).isObject()).isInstanceOf(AssertionError.class);
  }

  @Test
  void should_pass_if_json_is_an_array() throws Exception {
    // GIVEN
    JsonNode actual = mapper.readTree("[1, 2]");
    // WHEN/THEN
    assertThat(actual).isArray();
  }

  @Test
  void should_fail_if_json_is_not_an_array() throws Exception {
    // GIVEN
    JsonNode actual = mapper.readTree("{\"name\":\"Alice\"}");
    // WHEN/THEN
    thenThrownBy(() -> assertThat(actual).isArray()).isInstanceOf(AssertionError.class);
  }

  @Test
  void should_pass_if_json_is_not_empty() throws Exception {
    // GIVEN
    JsonNode actual = mapper.readTree("[1]");
    // WHEN/THEN
    assertThat(actual).isNotEmpty();
  }

  @Test
  void should_fail_if_json_is_empty() throws Exception {
    // GIVEN
    JsonNode actual = mapper.readTree("{}");
    // WHEN/THEN
    thenThrownBy(() -> assertThat(actual).isNotEmpty()).isInstanceOf(AssertionError.class);
  }

  @Test
  void should_pass_if_json_content_matches_structurally() throws Exception {
    // GIVEN
    JsonNode actual = mapper.readTree("{\"name\":\"Alice\",\"age\":30}");
    // WHEN/THEN
    assertThat(actual).hasJsonContent("{ \"age\": 30, \"name\": \"Alice\" }");
  }

  @Test
  void should_fail_if_json_content_does_not_match() throws Exception {
    // GIVEN
    JsonNode actual = mapper.readTree("{\"name\":\"Alice\"}");
    // WHEN/THEN
    thenThrownBy(() -> assertThat(actual).hasJsonContent("{\"name\":\"Bob\"}"))
        .isInstanceOf(AssertionError.class);
  }
}
