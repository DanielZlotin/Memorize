package zlotindaniel.memorize.tests;

import org.json.JSONObject;
import org.junit.Test;

import zlotindaniel.memorize.BaseTest;
import zlotindaniel.memorize.data.Card;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CardTest extends BaseTest {
	@Test
	public void holdsPhraseAndDefinition() throws Exception {
		Card card = new Card("hi", "ho");
		assertThat(card).isNotNull();
		assertThat(card.getPhrase()).isEqualTo("hi");
		assertThat(card.getDefinition()).isEqualTo("ho");
	}

	@Test
	public void stringRepresentation() throws Exception {
		assertThat(new Card("hi", "ho").toString()).isEqualTo("Card{phrase='hi', definition='ho'}");
	}

	@Test
	public void dataStructure() throws Exception {
		assertThat(new Card("a", "b")).isEqualTo(new Card("a", "b"));
	}

	@Test
	public void fromJson() throws Exception {
		JSONObject o = new JSONObject();
		o.put("phrase", "The phrase");
		o.put("definition", "The definition");
		assertThat(new Card(o)).isEqualTo(new Card("The phrase", "The definition"));
	}

	@Test
	public void toJson() throws Exception {
		JSONObject o = new JSONObject();
		o.put("phrase", "The phrase");
		o.put("definition", "The definition");
		assertThat(new Card("The phrase", "The definition").toJson()).isEqualTo(o);
	}
}
