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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author archyoshi
 */
class JsonNodeAssertIsEmptyTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void shouldPassIfJsonObjectIsEmpty() {
        final JsonNode actual = mapper.convertValue(Map.of(), JsonNode.class);
        assertThat(actual).isEmpty();
    }

    @Test
    void shouldPassIfJsonArrayIsEmpty() {
        final JsonNode actual = mapper.createArrayNode();
        assertThat(actual).isEmpty();
    }

    @Test
    void shouldFailIfJsonObjectIsNotEmpty() {
        final JsonNode actual = mapper.convertValue(Map.of("name", "Vegeta"), JsonNode.class);
        thenThrownBy(() -> assertThat(actual).isEmpty())
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Expected JSON node to be empty but had <1> element(s)");
    }

    @Test
    void shouldFailIfJsonArrayIsNotEmpty() throws Exception {
        final JsonNode actual = mapper.readTree("[1, 2, 3]");
        thenThrownBy(() -> assertThat(actual).isEmpty())
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Expected JSON node to be empty but had <3> element(s)");
    }

    @Test
    void shouldPassIfJsonObjectIsNotEmpty() {
        final JsonNode actual = mapper.convertValue(Map.of("name", "Vegeta"), JsonNode.class);
        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldPassIfJsonArrayIsNotEmpty() throws Exception {
        final JsonNode actual = mapper.readTree("[1, 2, 3]");
        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldFailIfJsonObjectIsEmptyWhenAssertingNotEmpty() {
        final JsonNode actual = mapper.convertValue(Map.of(), JsonNode.class);
        thenThrownBy(() -> assertThat(actual).isNotEmpty())
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Expected JSON node not to be empty but it was");
    }

    @Test
    void shouldFailIfJsonArrayIsEmptyWhenAssertingNotEmpty() {
        final JsonNode actual = mapper.createArrayNode();
        thenThrownBy(() -> assertThat(actual).isNotEmpty())
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Expected JSON node not to be empty but it was");
    }

    @Test
    void shouldFailIfNodeIsNotObjectOrArray() throws Exception {
        final JsonNode actual = mapper.readTree("\"string value\"");
        thenThrownBy(() -> assertThat(actual).isEmpty())
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining(
                        "Expected JSON node to be an object or array to check emptiness");
        thenThrownBy(() -> assertThat(actual).isNotEmpty())
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining(
                        "Expected JSON node to be an object or array to check emptiness");
    }
}
