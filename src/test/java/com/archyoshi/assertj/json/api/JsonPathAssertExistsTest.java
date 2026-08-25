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

import org.junit.jupiter.api.Test;

import static com.archyoshi.assertj.json.JsonAssertions.assertThatPath;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class JsonPathAssertExistsTest {

    @Test
    void shouldPassIfPathIsNotBlank() {
        assertThatPath("$.name").exists();
    }

    @Test
    void shouldFailIfPathIsBlank() {
        thenThrownBy(() -> assertThatPath(" ").exists())
                .isInstanceOf(AssertionError.class).hasMessageContaining("blank");
    }

    @Test
    void shouldFailIfPathIsNull() {
        thenThrownBy(() -> assertThatPath(null).exists())
                .isInstanceOf(AssertionError.class).hasMessageContaining("blank");
    }
}
