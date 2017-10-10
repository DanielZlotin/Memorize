package zlotindaniel.memorize.data;

import org.json.JSONObject;
import org.junit.Test;

import zlotindaniel.memorize.BaseTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CardTest extends BaseTest {
	@Test
	public void holdsPhraseAndDefinition() throws Exception {
		Card card = Card.create("hi", "ho");
		assertThat(card).isNotNull();
		assertThat(card.getPhrase()).isEqualTo("hi");
		assertThat(card.getDefinition()).isEqualTo("ho");
	}

	@Test
	public void stringRepresentation() throws Exception {
		assertThat(Card.create("hi", "ho").toString()).isEqualTo("{\"phrase\":\"hi\",\"definition\":\"ho\"}");
	}

	@Test
	public void valueType() throws Exception {
		Card first = Card.create("a", "b");
		Card second = Card.create("a", "b");
		assertThat(first).isEqualTo(first);
		assertThat(first).isEqualTo(second);
		assertThat(first).isNotEqualTo(null);
		assertThat(first).isNotEqualTo(new Object());
		assertThat(first.hashCode()).isEqualTo(second.hashCode());
		assertThat(first.toString()).isEqualTo(second.toString());
		assertThat(first).isNotEqualTo(Card.create("a", "y"));
		assertThat(first).isNotEqualTo(Card.create("x", "b"));
	}

	@Test
	public void fromJson() throws Exception {
		JSONObject o = new JSONObject();
		o.put("phrase", "The phrase");
		o.put("definition", "The definition");
		assertThat(Card.fromJson(o)).isEqualTo(Card.create("The phrase", "The definition"));
	}

	@Test
	public void toJson() throws Exception {
		JSONObject o = new JSONObject();
		o.put("phrase", "The phrase");
		o.put("definition", "The definition");
		Card result = Card.create("The phrase", "The definition");
		assertThat(result.toJson().toString()).isEqualTo(o.toString());
	}

	@Test(expected = NullPointerException.class)
	public void neverNullValues() throws Exception {
		Card.create(null, null);
	}

	@Test(expected = NullPointerException.class)
	public void neverEmptyValues() throws Exception {
		Card.create("", "");
	}
}
