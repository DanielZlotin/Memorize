package zlotindaniel.memorize.topics;

import org.json.JSONObject;
import org.junit.Test;

import zlotindaniel.memorize.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicsListParserTest extends BaseTest {
	private TopicsListParser uut;

	@Override
	public void beforeEach() {
		super.beforeEach();
		uut = new TopicsListParser();
	}

	@Test
	public void emptyList() throws Exception {
		assertThat(uut.parse(new JSONObject())).isEmpty();
	}

	@Test
	public void parsesTopicsList() throws Exception {
		JSONObject o = new JSONObject();
		JSONObject topicObj1 = new JSONObject();
		topicObj1.put("name", "the topic name 1");
		JSONObject topicObj2 = new JSONObject();
		topicObj2.put("name", "the topic name 2");
		o.put("the topicId1", topicObj1);
		o.put("the topicId2", topicObj2);

		assertThat(uut.parse(o)).containsOnly(
				Topic.create("the topicId1", "the topic name 1"),
				Topic.create("the topicId2", "the topic name 2"));
	}
}
