package org.assertj.json;

import static org.assertj.json.JsonAssertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

class JsonAssertionsTest {

  @Test
  void should_parse_json_string_and_assert_field_value() {
    String json = "{ \"name\": \"Alice\", \"age\": 30, \"active\": true }";

    assertThat(json).hasField("name").hasValue("name", "Alice");
    assertThat(json).hasField("age").hasValue("age", 30);
  }

  @Test
  void should_assert_on_json_node() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode node = mapper.createObjectNode();
    node.put("name", "Bob");

    assertThat(node).hasField("name").hasValue("name", "Bob");
  }
}
