package zlotindaniel.memorize.usecase.cards;

import org.json.*;
import org.junit.*;
import org.junit.experimental.theories.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.data.*;

import static org.assertj.core.api.Assertions.*;

public class CardTest extends BaseTest {
	@Test
	public void holdsQuestionAndAnswer() throws Exception {
		Card card = new Card("someId", "hi", "ho");
		assertThat(card).isNotNull();
		assertThat(card.getQuestion()).isEqualTo("hi");
		assertThat(card.getAnswer()).isEqualTo("ho");
	}

	@Test
	public void hasId() throws Exception {
		Card card = new Card("myId", "hi", "ho");
		assertThat(card.getId()).isEqualTo("myId");
	}

	@Test
	public void withId() throws Exception {
		Card card = new Card("theId", "theQuestion", "theAnswer");
		assertThat(card.withId("theId")).isSameAs(card);
	}

	@Test
	public void idIsOptional() throws Exception {
		assertThat(new Card("", "theQuestion", "theAnswer").hasId()).isFalse();
		assertThat(new Card("", "theQuestion", "theAnswer").getId()).isNotEmpty();

		assertThat(new Card((String) null, "theQuestion", "theAnswer").hasId()).isFalse();
		assertThat(new Card((String) null, "theQuestion", "theAnswer").getId()).isNotEmpty();

		assertThat(new Card("someId", "theQuestion", "theAnswer").hasId()).isTrue();
		assertThat(new Card("someId", "theQuestion", "theAnswer").getId()).isNotEmpty();
	}

	@Test
	public void valueType() throws Exception {
		Card first = new Card("id", "question", "answer");
		Card second = new Card("id", "question", "answer");
		assertThat(first).isEqualTo(second);
		assertThat(first).isNotEqualTo(new Card("x", "question", "answer"));
		assertThat(first).isNotEqualTo(new Card("id", "x", "answer"));
		assertThat(first).isNotEqualTo(new Card("id", "question", "x"));

		assertThat(new Card("", "q", "a").withId("id")).isEqualTo(new Card("id", "q", "a"));
		assertThrows(() -> first.withId("any"));
	}

	@Test(expected = NullPointerException.class)
	public void neverNullQuestion() throws Exception {
		new Card("asd", (String) null, (String) null);
	}

	@Test(expected = NullPointerException.class)
	public void neverNullAnswer() throws Exception {
		new Card("asd", "asd", (String) null);
	}

	@Test(expected = NullPointerException.class)
	public void neverEmptyValues() throws Exception {
		new Card("", "", "");
	}

	@Test
	public void normalizesQuestionAndAnswer() throws Exception {
		assertThat(new Card("", "  \n \t q  \n q   \n", "  \n\r\b a \n\r\b\t a   "))
				.isEqualTo(new Card("", "q q", "a a"));
	}

	@Test
	public void json() throws Exception {
		Card card = new Card("id", "question", "answer");
		JSONObject json = card.toJson();
		assertThat(json).isNotNull();
		assertThat(Card.parse(json)).isEqualTo(card);

		assertThat(new Card("", "q", "a").toJson().keys()).containsOnly("question", "answer");
	}

	public static class CardValueType extends EqualsHashCodeTheory {
		@DataPoints
		public static Object[] data = {
				new Card("theId", "theQuestion", "theAnswer"),
				new Card("a", "a", "a"),
				new Card("", "theQuestion", "theAnswer")
		};
	}
}
