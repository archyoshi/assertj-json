package com.archyoshi.assertj.json;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static com.archyoshi.assertj.json.JsonAssertions.assertThat;

class JsonAssertionsTest {

    @Test
    void should_parse_json_string_and_assert_field_value() {
        final String json = """
                { "name": "Vegeta", "age": 30, "active": true }""";
        assertThat(json).hasField("name")
                .hasValueForField("Vegeta", "name");
        assertThat(json).hasField("age")
                .hasValueForField(30, "age");
    }

    @Test
    void should_assert_on_json_node() {
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectNode node = mapper.createObjectNode();
        node.put("name", "Goku");
        assertThat(node).hasField("name")
                .hasValueForField("Goku", "name");
    }
}
