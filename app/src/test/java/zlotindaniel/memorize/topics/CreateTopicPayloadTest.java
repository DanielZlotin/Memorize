package zlotindaniel.memorize.topics;

import org.assertj.core.util.*;
import org.json.*;
import org.junit.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.data.*;

import static org.assertj.core.api.Assertions.*;

public class CreateTopicPayloadTest extends BaseTest {
	private CreateTopicPayload uut = new CreateTopicPayload("the topic name", aBoolean -> {}, e -> { });

	@Test
	public void correctArgs() throws Exception {
		assertThat(uut).isInstanceOf(Payload.class);
		assertThat(uut.path).isEqualTo("topics/index");
		assertThat(uut.parseResponse(null)).isTrue();
	}

	@Test
	public void payload() throws Exception {
		JSONObject expected = new JSONObject();
		expected.put("name", "the topic name");
		assertThat(uut.payload()).isEqualTo(Maps.newHashMap("name", "the topic name"));
	}
}