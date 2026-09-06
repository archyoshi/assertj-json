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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Adapter utility for converting various JSON sources (strings, files, paths) into {@link JsonNode}
 * instances.
 *
 * @author archyoshi
 * @since 0.1.3
 */
public final class JsonNodeLoader {

    private JsonNodeLoader() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    /**
     * Returns the given {@link JsonNode} or throws an {@link AssertionError} if it is null.
     *
     * @param node the JSON node
     * @return the same JSON node
     */
    public static JsonNode toNode(final JsonNode node) {
        if (node == null) {
            throw new AssertionError("Expected JSON node not to be null");
        }
        return node;
    }

    /**
     * Parses a JSON string into a {@link JsonNode} using the given mapper.
     *
     * @param json the JSON string
     * @param mapper the mapper to use
     * @return the parsed JSON node
     */
    public static JsonNode toNode(final String json, final ObjectMapper mapper) {
        if (json == null) {
            throw new AssertionError("Expected JSON string not to be null");
        }
        try {
            return mapper.readTree(json);
        } catch (final JsonProcessingException e) {
            throw new AssertionError("Invalid JSON content: " + e.getOriginalMessage(), e);
        }
    }

    /**
     * Reads and parses a JSON file from a {@link Path} using the given mapper.
     *
     * @param path the path to the JSON file
     * @param mapper the mapper to use
     * @return the parsed JSON node
     */
    public static JsonNode toNode(final Path path, final ObjectMapper mapper) {
        if (path == null) {
            throw new AssertionError("Expected JSON path not to be null");
        }
        try {
            return toNode(Files.readString(path), mapper);
        } catch (final IOException e) {
            throw new AssertionError("Unable to read JSON file: " + path, e);
        }
    }

    /**
     * Reads and parses a JSON file from a {@link File} using the given mapper.
     *
     * @param file the JSON file
     * @param mapper the mapper to use
     * @return the parsed JSON node
     */
    public static JsonNode toNode(final File file, final ObjectMapper mapper) {
        if (file == null) {
            throw new AssertionError("Expected JSON file not to be null");
        }
        return toNode(file.toPath(), mapper);
    }
}
