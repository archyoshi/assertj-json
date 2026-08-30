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

import static com.archyoshi.assertj.json.JsonAssertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author archyoshi
 */
class JsonNodeAssertHasSizeTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void shouldPassIfJsonArrayHasExpectedSize() throws JsonProcessingException {
        final JsonNode actual = mapper.readTree("[1, 2, 3]");
        assertThat(actual).hasSize(3);
    }

    @Test
    void shouldPassIfJsonObjectHasExpectedSize() {
        final JsonNode actual = mapper.convertValue(Map.of("a", 1, "b", 2, "c", 3), JsonNode.class);
        assertThat(actual).hasSize(3);
    }

    @Test
    void shouldFailIfJsonArrayHasDifferentSize() throws JsonProcessingException {
        final JsonNode actual = mapper.readTree("[1, 2, 3]");
        thenThrownBy(() -> assertThat(actual).hasSize(5))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("expected: 5")
                .hasMessageContaining("but was: 3");
    }

    @Test
    void shouldFailIfJsonObjectHasDifferentSize() {
        final JsonNode actual = mapper.convertValue(Map.of("a", 1, "b", 2), JsonNode.class);
        thenThrownBy(() -> assertThat(actual).hasSize(5))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("expected: 5")
                .hasMessageContaining("but was: 2");
    }
}
