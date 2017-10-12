package zlotindaniel.memorize.cards;

import org.json.JSONObject;
import org.junit.Test;

import zlotindaniel.memorize.BaseTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CardsListParserTest extends BaseTest {
	@Test
	public void parses() throws Exception {
		JSONObject o = new JSONObject();

		JSONObject carObj1 = new JSONObject();
		JSONObject carObj2 = new JSONObject();
		carObj1.put("phrase", "phrase1");
		carObj2.put("phrase", "phrase2");
		carObj1.put("definition", "definition1");
		carObj2.put("definition", "definition2");
		o.put("cardId1", carObj1);
		o.put("cardId2", carObj2);

		assertThat(new CardsListParser().parse(o))
				.containsOnly(Card.create("cardId1", "phrase1", "definition1"),
						Card.create("cardId2", "phrase2", "definition2")
				);
	}
}
