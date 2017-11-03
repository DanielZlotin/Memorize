package zlotindaniel.memorize.data;

import org.json.*;
import org.junit.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.cards.*;

import static org.assertj.core.api.Assertions.*;

public class CardsListParserTest extends BaseTest {
	private CardsListParser uut;

	@Override
	public void beforeEach() {
		super.beforeEach();
		uut = new CardsListParser();
	}

	@Test
	public void parseListInsertIds() throws Exception {
		JSONObject all = new JSONObject();
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		json1.put("question", "q 1");
		json1.put("answer", "a 1");
		json2.put("id", "id2");
		json2.put("question", "q 2");
		json2.put("answer", "a 2");
		all.put("id1", json1);
		all.put("id2", json2);
		assertThat(uut.parse(all)).containsOnly(new Card("id1", "q 1", "a 1"),new Card("id2", "q 2", "a 2"));
	}
}