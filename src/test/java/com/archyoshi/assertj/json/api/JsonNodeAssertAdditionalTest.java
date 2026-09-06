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
import com.fasterxml.jackson.databind.node.JsonNodeType;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * @author archyoshi
 */
class JsonNodeAssertAdditionalTest {

    private JsonNode actual;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        actual =
                new ObjectMapper()
                        .readTree(
                                """
                                {"name":"Vegeta","age":30,"active":true,\
                                "profile":{"planet":"Vegeta"},"items":[{"id":1},{"id":2}]}\
                                """);
    }

    @Test
    void shouldAssertAbsentFieldAndFieldType() {
        assertThat(actual)
                .doesNotHaveField("missing")
                .hasTypeForField("active", JsonNodeType.BOOLEAN);
    }

    @Test
    void shouldAssertTypedFieldValues() {
        assertThat(actual)
                .hasTypedValueForField("Vegeta", "name", String.class)
                .hasTypedValueForField(true, "active", Boolean.class)
                .hasTypedValueForField(30, "age", Integer.class);
    }

    @Test
    void shouldAssertNumericRelationships() {
        assertThat(actual)
                .hasValueEqualForField(30, "age")
                .hasValueMoreThanForField(18, "age")
                .hasValueLessThanForField(65, "age");
    }

    @Test
    void shouldFailForInvalidFieldAssertions() {
        thenThrownBy(() -> assertThat(actual).doesNotHaveField("name"))
                .isInstanceOf(AssertionError.class);
        thenThrownBy(() -> assertThat(actual).hasTypeForField("age", JsonNodeType.STRING))
                .isInstanceOf(AssertionError.class);
        thenThrownBy(() -> assertThat(actual).hasTypedValueForField("thirty", "age", String.class))
                .isInstanceOf(AssertionError.class);
        thenThrownBy(() -> assertThat(actual).hasValueMoreThanForField(30, "age"))
                .isInstanceOf(AssertionError.class);
    }

    @TempDir Path tempDir;

    @Test
    void shouldCompareWhileIgnoringNestedFields() throws JsonProcessingException {
        final JsonNode expected =
                new ObjectMapper()
                        .readTree(
                                """
                                {"name":"Vegeta","age":30,"active":false,\
                                "profile":{"planet":"Earth"},"items":[{"id":1},{"id":2}]}\
                                """);

        assertThat(actual).isEqualToIgnoringFields(expected, List.of("active", "planet"));
        assertThat(actual).isEqualToIgnoringFields(expected, "active", "planet");
        thenThrownBy(() -> assertThat(actual).isEqualToIgnoringFields(expected, List.of("active")))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void shouldCompareWhileIgnoringNestedFieldsUsingString() {
        final String expectedJson =
                """
                {"name":"Vegeta","age":30,"active":false,\
                "profile":{"planet":"Earth"},"items":[{"id":1},{"id":2}]}\
                """;

        assertThat(actual).isEqualToIgnoringFields(expectedJson, List.of("active", "planet"));
        assertThat(actual).isEqualToIgnoringFields(expectedJson, "active", "planet");
        thenThrownBy(() -> assertThat(actual).isEqualToIgnoringFields(expectedJson, "active"))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void shouldCompareWhileIgnoringNestedFieldsUsingPathAndFile() throws Exception {
        final String expectedJson =
                """
                {"name":"Vegeta","age":30,"active":false,\
                "profile":{"planet":"Earth"},"items":[{"id":1},{"id":2}]}\
                """;
        final Path path =
                Files.writeString(
                        Files.createTempFile(tempDir, "expected-", ".json"), expectedJson);
        final File file = path.toFile();

        assertThat(actual).isEqualToIgnoringFields(path, List.of("active", "planet"));
        assertThat(actual).isEqualToIgnoringFields(path, "active", "planet");
        assertThat(actual).isEqualToIgnoringFields(file, List.of("active", "planet"));
        assertThat(actual).isEqualToIgnoringFields(file, "active", "planet");
    }

    @Test
    void shouldAssertHasJsonContentWithNodePathAndFile() throws Exception {
        final String json =
                "{\"name\":\"Vegeta\",\"age\":30,\"active\":true,\"profile\":{\"planet\":\"Vegeta\"},\"items\":[{\"id\":1},{\"id\":2}]}";
        final JsonNode expectedNode = new ObjectMapper().readTree(json);
        final Path expectedPath =
                Files.writeString(Files.createTempFile(tempDir, "content-", ".json"), json);
        final File expectedFile = expectedPath.toFile();

        assertThat(actual).hasJsonContent(expectedNode);
        assertThat(actual).hasJsonContent(json);
        assertThat(actual).hasJsonContent(expectedPath);
        assertThat(actual).hasJsonContent(expectedFile);
    }

    @Test
    void shouldExtractNestedNodesAndArrays() {
        assertThat(actual)
                .extractingFieldAsJsonNode("profile")
                .hasValueForField("Vegeta", "planet");
        assertThat(actual).extractingFieldAsArray("items").hasSize(2);
        assertThat(actual).hasSizeForArrayField(2, "items");
    }

    @Test
    void shouldApplyCustomNodeAssertions() {
        assertThat(actual)
                .hasNodeThatSatisfies(
                        node -> {
                            if (!node.has("name")) {
                                throw new AssertionError("name is missing");
                            }
                        });
    }
}
