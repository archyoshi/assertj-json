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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.archyoshi.assertj.json.JsonAssertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class JsonNodeAssertTypeAndContentTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void shouldPassIfJsonIsAnObject() throws JsonProcessingException {
        final JsonNode actual = mapper.readTree("""
                {"name":"Vegeta"}""");
        assertThat(actual).isObject();
    }

    @Test
    void shouldFailIfJsonIsNotAnObject() throws JsonProcessingException {
        final JsonNode actual = mapper.readTree("[]");
        thenThrownBy(() -> assertThat(actual).isObject())
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void shouldPassIfJsonIsAnArray() throws JsonProcessingException {
        final JsonNode actual = mapper.readTree("[1, 2]");
        assertThat(actual).isArray();
    }

    @Test
    void shouldFailIfJsonIsNotAnArray() throws JsonProcessingException {
        final JsonNode actual = mapper.readTree("""
                {"name":"Vegeta"}""");
        thenThrownBy(() -> assertThat(actual).isArray())
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void shouldPassIfJsonIsNotEmpty() throws JsonProcessingException {
        final JsonNode actual = mapper.readTree("[1]");
        assertThat(actual).isNotEmpty();
    }

    @Test
    void shouldFailIfJsonIsEmpty() throws JsonProcessingException {
        final JsonNode actual = mapper.readTree("{}");
        thenThrownBy(() -> assertThat(actual).isNotEmpty())
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void shouldPassIfJsonContentMatchesStructurally() throws JsonProcessingException {
        final JsonNode actual = mapper.readTree("""
                {"name":"Vegeta","age":30}""");
        assertThat(actual).hasJsonContent("""
                { "age": 30, "name": "Vegeta" }""");
    }

    @Test
    void shouldFailIfJsonContentDoesNotMatch() throws JsonProcessingException {
        final JsonNode actual = mapper.readTree("""
                {"name":"Vegeta"}""");
        thenThrownBy(() -> assertThat(actual).hasJsonContent("""
                {"name":"Goku"}"""))
                .isInstanceOf(AssertionError.class);
    }
}
