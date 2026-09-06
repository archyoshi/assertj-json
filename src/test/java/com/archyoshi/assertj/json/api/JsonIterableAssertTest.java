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

import static com.archyoshi.assertj.json.JsonAssertions.assertThatArray;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author archyoshi
 */
class JsonIterableAssertTest {

    private ObjectMapper mapper;
    private JsonNode array;

    @BeforeEach
    void setUp() throws Exception {
        mapper = new ObjectMapper();
        array =
                mapper.readTree(
                        "[{\"id\":\"1\",\"name\":\"Vegeta\",\"active\":true},"
                                + "{\"id\":\"2\",\"name\":\"Goku\",\"active\":false}]");
    }

    @Test
    void shouldSupportStandardIterableAssertions() {
        assertThatArray(array)
                .hasSize(2)
                .containsExactly(array.get(0), array.get(1))
                .doesNotHaveDuplicates();
    }

    @Test
    void shouldFindElementsByField() {
        assertThatArray(array).containsElementWithFieldName("name");
        assertThatArray(array).containsElementWithFieldAndValue("name", "Vegeta");
        assertThatArray(array).doesNotContainElementWithField("missing");
        assertThatArray(array).doesNotContainElementWithFieldAndValue("name", "Trunks");
    }

    @Test
    void shouldFailWhenElementFieldAssertionDoesNotMatch() {
        thenThrownBy(() -> assertThatArray(array).containsElementWithFieldName("missing"))
                .isInstanceOf(AssertionError.class);
        thenThrownBy(
                        () ->
                                assertThatArray(array)
                                        .containsElementWithFieldAndValue("name", "Trunks"))
                .isInstanceOf(AssertionError.class);
        thenThrownBy(() -> assertThatArray(array).doesNotContainElementWithField("name"))
                .isInstanceOf(AssertionError.class);
        thenThrownBy(
                        () ->
                                assertThatArray(array)
                                        .doesNotContainElementWithFieldAndValue("name", "Vegeta"))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void shouldExtractMatchingElementAsJsonNodeAssert() {
        assertThatArray(array)
                .extractingElementWithFieldAndValue("id", "1")
                .hasValueForField("Vegeta", "name")
                .hasTypeForField(
                        "active", com.fasterxml.jackson.databind.node.JsonNodeType.BOOLEAN);
    }

    @Test
    void shouldFailWhenExtractingUnknownElement() {
        thenThrownBy(() -> assertThatArray(array).extractingElementWithFieldAndValue("id", "3"))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void shouldRejectNullAndNonArrayNodes() throws Exception {
        thenThrownBy(() -> assertThatArray(null)).isInstanceOf(AssertionError.class);
        thenThrownBy(() -> assertThatArray(mapper.readTree("{}")))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void shouldReturnFluentAssertionType() {
        JsonIterableAssert result = assertThatArray(array);
        then(result).isInstanceOf(JsonIterableAssert.class);
    }
}
