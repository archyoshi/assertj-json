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

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.assertj.core.api.AbstractIterableAssert;

/**
 * AssertJ iterable assertions for the elements of a JSON array.
 *
 * <p>In addition to the standard AssertJ iterable assertions, this class provides JSON-aware
 * assertions for finding object elements by field name and value.
 *
 * @since 0.1.0
 */
public class JsonIterableAssert
        extends AbstractIterableAssert<
                JsonIterableAssert, List<JsonNode>, JsonNode, JsonNodeAssert> {

    public JsonIterableAssert(final List<JsonNode> actual) {
        super(actual, JsonIterableAssert.class);
    }

    /**
     * Creates assertions for a JSON array node.
     *
     * @param actual the JSON array node
     * @return an assertion object for its elements
     */
    public static JsonIterableAssert assertThat(final JsonNode actual) {
        if (actual == null) {
            throw new AssertionError("Expected actual JSON array not to be null");
        }
        if (!actual.isArray()) {
            throw new AssertionError(
                    "Expected actual JSON node to be an array, but was " + actual.getNodeType());
        }
        final List<JsonNode> elements = new ArrayList<>();
        actual.forEach(elements::add);
        return new JsonIterableAssert(elements);
    }

    /**
     * Creates assertions for an existing collection of JSON nodes.
     *
     * @param actual the JSON nodes
     * @return an assertion object for the elements
     */
    public static JsonIterableAssert assertThat(final Collection<JsonNode> actual) {
        return new JsonIterableAssert(new ArrayList<>(actual));
    }

    /**
     * Verifies that at least one object element has the given field.
     *
     * @param fieldName the field name
     * @return {@code this} assertion object
     */
    public JsonIterableAssert containsElementWithFieldName(final String fieldName) {
        isNotNull();
        if (!containsElementWithField(fieldName)) {
            failWithMessage(
                    "Expected array to contain at least one element with field '%s' but was not"
                            + " found",
                    fieldName);
        }
        return this;
    }

    /**
     * Verifies that at least one object element has the given field and textual value.
     *
     * @param fieldName the field name
     * @param fieldValue the expected textual value
     * @return {@code this} assertion object
     */
    public JsonIterableAssert containsElementWithFieldAndValue(
            final String fieldName, final String fieldValue) {
        isNotNull();
        if (!containsElementWithFieldAndValueInternal(fieldName, fieldValue)) {
            failWithMessage(
                    "Expected array to contain at least one element with field '%s' and value '%s'"
                            + " but was not found",
                    fieldName, fieldValue);
        }
        return this;
    }

    /**
     * Verifies that no object element has the given field.
     *
     * @param fieldName the field name
     * @return {@code this} assertion object
     */
    public JsonIterableAssert doesNotContainElementWithField(final String fieldName) {
        isNotNull();
        if (containsElementWithField(fieldName)) {
            failWithMessage(
                    "Expected array not to contain an element with field '%s' but one was found",
                    fieldName);
        }
        return this;
    }

    /**
     * Verifies that no object element has the given field and textual value.
     *
     * @param fieldName the field name
     * @param fieldValue the value to reject
     * @return {@code this} assertion object
     */
    public JsonIterableAssert doesNotContainElementWithFieldAndValue(
            final String fieldName, final String fieldValue) {
        isNotNull();
        if (containsElementWithFieldAndValueInternal(fieldName, fieldValue)) {
            failWithMessage(
                    "Expected array not to contain an element with field '%s' and value '%s' but"
                            + " one was found",
                    fieldName, fieldValue);
        }
        return this;
    }

    /**
     * Extracts the first object element with the given field and textual value.
     *
     * @param fieldName the field name
     * @param fieldValue the expected textual value
     * @return an assertion object for the matching element
     */
    public JsonNodeAssert extractingElementWithFieldAndValue(
            final String fieldName, final String fieldValue) {
        isNotNull();
        for (final JsonNode element : actual) {
            if (hasFieldAndValue(element, fieldName, fieldValue)) {
                return new JsonNodeAssert(element);
            }
        }
        failWithMessage(
                "Expected to find an element with field '%s' and value '%s'",
                fieldName, fieldValue);
        return new JsonNodeAssert(null);
    }

    private boolean containsElementWithField(final String fieldName) {
        return actual.stream().anyMatch(element -> element != null && element.has(fieldName));
    }

    private boolean containsElementWithFieldAndValueInternal(
            final String fieldName, final String fieldValue) {
        return actual.stream()
                .anyMatch(element -> hasFieldAndValue(element, fieldName, fieldValue));
    }

    private boolean hasFieldAndValue(
            final JsonNode element, final String fieldName, final String fieldValue) {
        return element != null
                && element.has(fieldName)
                && Objects.equals(element.get(fieldName).asText(), fieldValue);
    }

    @Override
    protected JsonNodeAssert toAssert(final JsonNode value, final String description) {
        return new JsonNodeAssert(value).as(description);
    }

    @Override
    protected JsonIterableAssert newAbstractIterableAssert(
            final Iterable<? extends JsonNode> iterable) {
        final List<JsonNode> elements = new ArrayList<>();
        iterable.forEach(elements::add);
        return new JsonIterableAssert(elements);
    }
}
