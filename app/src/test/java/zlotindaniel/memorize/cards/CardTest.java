package zlotindaniel.memorize.cards;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;

import zlotindaniel.memorize.BaseTest;
import zlotindaniel.memorize.EqualsHashCodeTheory;

import static org.assertj.core.api.Assertions.assertThat;

public class CardTest extends BaseTest {
	@Test
	public void holdsQuestionAndAnswer() throws Exception {
		Card card = Card.create("someId", "hi", "ho");
		assertThat(card).isNotNull();
		assertThat(card.getQuestion()).isEqualTo("hi");
		assertThat(card.getAnswer()).isEqualTo("ho");
	}

	@Test
	public void hasId() throws Exception {
		Card card = Card.create("myId", "hi", "ho");
		assertThat(card.getId()).isEqualTo("myId");
	}

	@Test
	public void idIsOptional() throws Exception {
		assertThat(Card.create("", "theQuestion", "theAnswer").hasId()).isFalse();
		assertThat(Card.create("", "theQuestion", "theAnswer").getId()).isNotEmpty();

		assertThat(Card.create(null, "theQuestion", "theAnswer").hasId()).isFalse();
		assertThat(Card.create(null, "theQuestion", "theAnswer").getId()).isNotEmpty();

		assertThat(Card.create("someId", "theQuestion", "theAnswer").hasId()).isTrue();
		assertThat(Card.create("someId", "theQuestion", "theAnswer").getId()).isNotEmpty();
	}

	@Test
	public void valueType() throws Exception {
		Card first = Card.create("id", "question", "answer");
		Card second = Card.create("id", "question", "answer");
		assertThat(first).isEqualTo(second);
		assertThat(first).isNotEqualTo(Card.create("x", "question", "answer"));
		assertThat(first).isNotEqualTo(Card.create("id", "x", "answer"));
		assertThat(first).isNotEqualTo(Card.create("id", "question", "x"));
	}

	@Test(expected = NullPointerException.class)
	public void neverNullQuestion() throws Exception {
		Card.create("asd", null, null);
	}

	@Test(expected = NullPointerException.class)
	public void neverNullAnswer() throws Exception {
		Card.create("asd", "asd", null);
	}

	@Test(expected = NullPointerException.class)
	public void neverEmptyValues() throws Exception {
		Card.create("", "", "");
	}

	public static class CardValueType extends EqualsHashCodeTheory {
		@DataPoints
		public static Object[] data = {
				Card.create("theId", "theQuestion", "theAnswer"),
				Card.create("a", "a", "a"),
				Card.create("", "theQuestion", "theAnswer")
		};
	}
}
