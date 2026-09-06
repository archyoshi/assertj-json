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

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * @author archyoshi
 */
class JsonComparisonAssertTest {

    @TempDir Path tempDir;

    @Test
    void shouldCompareFieldNamesIndependentlyOfOrderAndValues() throws Exception {
        Path actual = jsonFile("{\"name\":\"Vegeta\",\"profile\":{\"planet\":\"Vegeta\"}}");
        Path expected = jsonFile("{\"profile\":{\"planet\":\"Earth\"},\"name\":\"Goku\"}");

        assertThat(actual).hasSameFieldsAs(expected);
    }

    @Test
    void shouldFailWhenFieldNamesDiffer() throws Exception {
        Path actual = jsonFile("{\"name\":\"Vegeta\"}");

        thenThrownBy(() -> assertThat(actual).hasSameFieldsAs("{\"age\":30}"))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void shouldCompareContentIndependentlyOfFormattingAndObjectOrder() throws Exception {
        Path actual = jsonFile("{\"name\": \"Vegeta\", \"age\": 30}");

        assertThat(actual).hasSameContentAs("{\n  \"age\": 30,\n  \"name\": \"Vegeta\"\n}");
    }

    @Test
    void shouldFailWhenContentDiffers() throws Exception {
        Path actual = jsonFile("{\"name\":\"Vegeta\"}");

        thenThrownBy(() -> assertThat(actual).hasSameContentAs("{\"name\":\"Goku\"}"))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void shouldMatchPartialObjectAndArrayContent() throws Exception {
        Path actual =
                jsonFile(
                        "{\"name\":\"Vegeta\",\"profile\":{\"planet\":\"Vegeta\",\"rank\":1},"
                                + "\"items\":[{\"id\":1,\"name\":\"sword\"},{\"id\":2}]}");

        assertThat(actual)
                .partiallyContains("{\"profile\":{\"planet\":\"Vegeta\"},\"items\":[{\"id\":1}]}");
    }

    @Test
    void shouldFailWhenPartialContentIsMissing() throws Exception {
        Path actual = jsonFile("{\"name\":\"Vegeta\"}");

        thenThrownBy(() -> assertThat(actual).partiallyContains("{\"name\":\"Goku\"}"))
                .isInstanceOf(AssertionError.class);
    }

    private Path jsonFile(final String content) throws Exception {
        final Path file = Files.createTempFile(tempDir, "assertj-json-", ".json");
        return Files.writeString(file, content);
    }
}
