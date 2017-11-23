package zlotindaniel.memorize.data.parser;

import org.json.*;
import org.junit.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.data.parser.*;
import zlotindaniel.memorize.topics.*;

import static org.assertj.core.api.Assertions.*;

public class TopicsListParserTest extends BaseTest {
	private TopicsListParser uut;

	@Override
	public void beforeEach() {
		super.beforeEach();
		uut = new TopicsListParser();
	}

	@Test
	public void parseListInsertIds() throws Exception {
		JSONObject all = new JSONObject();
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		json1.put("name", "name 1");
		json2.put("id", "id2");
		json2.put("name", "name 2");
		all.put("id1", json1);
		all.put("id2", json2);
		assertThat(uut.parse(all)).containsOnly(new Topic("id1", "name 1"), new Topic("id2", "name 2"));
	}

}