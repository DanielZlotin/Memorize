package zlotindaniel.memorize.topics;

import org.json.*;
import org.junit.*;

import zlotindaniel.memorize.*;

import static org.assertj.core.api.Assertions.*;

public class GetTopicsRequestTest extends BaseTest {
	private GetTopicsRequest uut = new GetTopicsRequest((o) -> { }, (e) -> { });

	@Test
	public void correctArgs() throws Exception {
		assertThat(uut.path).isEqualTo("topics/index");
	}

	@Test
	public void parseResponseEmpty() throws Exception {
		assertThat(uut.parseResponse(new JSONObject())).isEmpty();
	}

	@Test
	public void parsesResponse() throws Exception {
		JSONObject o = new JSONObject();
		JSONObject topicObj1 = new JSONObject();
		topicObj1.put("name", "the topic name 1");
		JSONObject topicObj2 = new JSONObject();
		topicObj2.put("name", "the topic name 2");
		o.put("the topicId1", topicObj1);
		o.put("the topicId2", topicObj2);

		assertThat(uut.parseResponse(o)).containsOnly(
				Topic.create("the topicId1", "the topic name 1"),
				Topic.create("the topicId2", "the topic name 2"));
	}
}
