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

import static com.archyoshi.assertj.json.JsonAssertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/**
 * @author archyoshi
 */
class JsonAssertionsTest {

    @Test
    void shouldParseJsonStringAndAssertFieldValue() {
        final String json =
                """
                { "name": "Vegeta", "age": 30, "active": true }\
                """;
        assertThat(json).hasField("name").hasValueForField("Vegeta", "name");
        assertThat(json).hasField("age").hasValueForField(30, "age");
    }

    @Test
    void shouldAssertOnJsonNode() {
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectNode node = mapper.createObjectNode();
        node.put("name", "Goku");
        assertThat(node).hasField("name").hasValueForField("Goku", "name");
    }

    @Test
    void shouldUseProvidedObjectMapperForStringParsing() {
        final ObjectMapper mapper = new ObjectMapper();

        assertThat(
                        """
                        { "name": "Goku" }
                        """,
                        mapper)
                .hasValueForField("Goku", "name");
    }

    @Test
    void shouldUseProvidedObjectMapperForFileParsing() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        final Path actual = Files.createTempFile("assertj-json-", ".json");
        final Path expected = Files.createTempFile("assertj-json-", ".json");
        Files.writeString(
                actual,
                """
                { "name": "Goku" }
                """);
        Files.writeString(
                expected,
                """
                {
                  "name": "Goku"
                }
                """);

        assertThat(actual, mapper).hasSameContentAs(expected);
    }
}
