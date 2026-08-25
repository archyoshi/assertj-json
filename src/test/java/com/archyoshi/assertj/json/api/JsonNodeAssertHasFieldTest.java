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

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static com.archyoshi.assertj.json.JsonAssertions.assertThat;

class JsonNodeAssertHasFieldTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void shouldPassIfJsonObjectHasField() {
        final JsonNode actual = mapper.convertValue(
                Map.of("name", "Vegeta", "age", 30), JsonNode.class);
        assertThat(actual).hasField("name");
    }

    @Test
    void shouldPassIfJsonObjectHasMultipleFields() {
        final JsonNode actual = mapper.convertValue(
                Map.of("name", "Vegeta", "age", 30), JsonNode.class);
        assertThat(actual).hasField("name").hasField("age");
    }

    @Test
    void shouldFailIfJsonObjectDoesNotHaveField() {
        final JsonNode actual = mapper.convertValue(
                Map.of("name", "Vegeta"), JsonNode.class);
        thenThrownBy(() -> assertThat(actual).hasField("email"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("email");
    }

    @Test
    void should_return_this_for_method_chaining() {
        final JsonNode actual = mapper.convertValue(
                Map.of("name", "Vegeta"), JsonNode.class);
        final JsonNodeAssert result = assertThat(actual).hasField("name");
        then(result).isInstanceOf(JsonNodeAssert.class);
    }
}
