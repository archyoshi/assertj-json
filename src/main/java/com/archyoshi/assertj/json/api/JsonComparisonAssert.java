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

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import org.assertj.core.api.AbstractAssert;

/**
 * Assertions for comparing JSON loaded from a file with another JSON document.
 *
 * <p>JSON is parsed before comparison, so whitespace and object field ordering do not affect
 * equality.
 *
 * @author archyoshi
 * @since 0.1.0
 */
public class JsonComparisonAssert extends AbstractAssert<JsonComparisonAssert, Path> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final ObjectMapper mapper;

    /**
     * Creates a comparison assertion using the default object mapper.
     *
     * @param actual the JSON file to compare
     */
    public JsonComparisonAssert(final Path actual) {
        this(actual, MAPPER);
    }

    /**
     * Creates a comparison assertion using the supplied object mapper.
     *
     * @param actual the JSON file to compare
     * @param mapper the mapper used to parse JSON
     */
    public JsonComparisonAssert(final Path actual, final ObjectMapper mapper) {
        super(actual, JsonComparisonAssert.class);
        this.mapper = mapper;
    }

    /**
     * Verifies that the actual JSON file and expected JSON file contain the same field names.
     * Values are ignored, while nested object structure and array positions are preserved.
     *
     * @param expectedFile the expected JSON file
     * @return this assertion object
     */
    public JsonComparisonAssert hasSameFieldsAs(final Path expectedFile) {
        return hasSameFieldsAs(readJson(expectedFile));
    }

    /**
     * Verifies that the actual JSON file has the same field names as a JSON string.
     *
     * @param expectedJson the expected JSON string
     * @return this assertion object
     */
    public JsonComparisonAssert hasSameFieldsAs(final String expectedJson) {
        return hasSameFieldsAs(parseJson(expectedJson));
    }

    private JsonComparisonAssert hasSameFieldsAs(final JsonNode expected) {
        JsonNode actualNode = actualJson();
        if (!sameFields(actualNode, expected)) {
            failWithActualExpectedAndMessage(
                    actualNode, expected, "Expected JSON files to have the same fields");
        }
        return this;
    }

    /**
     * Verifies that the actual JSON file and expected JSON file have the same parsed content.
     * Formatting and object field ordering are ignored.
     *
     * @param expectedFile the expected JSON file
     * @return this assertion object
     */
    public JsonComparisonAssert hasSameContentAs(final Path expectedFile) {
        return hasSameContentAs(readJson(expectedFile));
    }

    /**
     * Verifies that the actual JSON file has the same parsed content as a JSON string.
     *
     * @param expectedJson the expected JSON string
     * @return this assertion object
     */
    public JsonComparisonAssert hasSameContentAs(final String expectedJson) {
        return hasSameContentAs(parseJson(expectedJson));
    }

    private JsonComparisonAssert hasSameContentAs(final JsonNode expected) {
        JsonNode actualNode = actualJson();
        assertThat(actualNode).as("JSON content from %s", actual).isEqualTo(expected);
        return this;
    }

    /**
     * Verifies that the JSON document in the actual file contains the supplied JSON fragment.
     * Object fragments may omit fields; nested fragments are checked recursively. Arrays are
     * matched by position for the elements supplied in the fragment.
     *
     * @param expectedFragment the JSON fragment to find
     * @return this assertion object
     */
    public JsonComparisonAssert partiallyContains(final String expectedFragment) {
        JsonNode actualNode = actualJson();
        JsonNode expectedNode = parseJson(expectedFragment);
        if (!contains(actualNode, expectedNode)) {
            failWithActualExpectedAndMessage(
                    actualNode, expectedNode, "Expected JSON to partially contain");
        }
        return this;
    }

    private boolean sameFields(final JsonNode actualNode, final JsonNode expectedNode) {
        if (actualNode.isObject() && expectedNode.isObject()) {
            if (actualNode.size() != expectedNode.size()) {
                return false;
            }
            Iterator<Map.Entry<String, JsonNode>> fields = actualNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                JsonNode expectedField = expectedNode.get(field.getKey());
                if (expectedField == null || !sameFields(field.getValue(), expectedField)) {
                    return false;
                }
            }
            return true;
        }
        if (actualNode.isArray() && expectedNode.isArray()) {
            if (actualNode.size() != expectedNode.size()) {
                return false;
            }
            for (int index = 0; index < actualNode.size(); index++) {
                if (!sameFields(actualNode.get(index), expectedNode.get(index))) {
                    return false;
                }
            }
            return true;
        }
        return actualNode.isValueNode() && expectedNode.isValueNode();
    }

    private boolean contains(final JsonNode actualNode, final JsonNode expectedNode) {
        if (actualNode.isObject() && expectedNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = expectedNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                JsonNode actualField = actualNode.get(field.getKey());
                if (actualField == null || !contains(actualField, field.getValue())) {
                    return false;
                }
            }
            return true;
        }
        if (actualNode.isArray() && expectedNode.isArray()) {
            if (expectedNode.size() > actualNode.size()) {
                return false;
            }
            for (int index = 0; index < expectedNode.size(); index++) {
                if (!contains(actualNode.get(index), expectedNode.get(index))) {
                    return false;
                }
            }
            return true;
        }
        return actualNode.equals(expectedNode);
    }

    private JsonNode actualJson() {
        isNotNull();
        return readJson(actual);
    }

    private JsonNode readJson(final Path file) {
        try {
            return parseJson(Files.readString(file));
        } catch (IOException e) {
            throw new AssertionError("Unable to read JSON file: " + file, e);
        }
    }

    private JsonNode parseJson(final String json) {
        try {
            return mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new AssertionError("Invalid JSON content: " + e.getOriginalMessage(), e);
        }
    }
}
