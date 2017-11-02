package zlotindaniel.memorize.cards;

import org.json.*;
import org.junit.*;

import zlotindaniel.memorize.*;

import static org.assertj.core.api.Assertions.*;

public class GetCardsRequestTest extends BaseTest {

	private GetCardsRequest uut = new GetCardsRequest("the topic id", cards -> { }, e -> { });

	@Test
	public void correctArgs() throws Exception {
		assertThat(uut.path).isEqualTo("topics/cards/the topic id");
	}

	@Test
	public void parses() throws Exception {
		JSONObject o = new JSONObject();

		JSONObject carObj1 = new JSONObject();
		JSONObject carObj2 = new JSONObject();
		carObj1.put("question", "question1");
		carObj2.put("question", "question2");
		carObj1.put("answer", "answer1");
		carObj2.put("answer", "answer2");
		o.put("cardId1", carObj1);
		o.put("cardId2", carObj2);

		assertThat(uut.parseResponse(o))
				.containsOnly(Card.create("cardId1", "question1", "answer1"),
						Card.create("cardId2", "question2", "answer2")
				);
	}
}
