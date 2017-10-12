package zlotindaniel.memorize.cards;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;

import zlotindaniel.memorize.BaseTest;
import zlotindaniel.memorize.EqualsHashCodeTheory;
import zlotindaniel.memorize.cards.Card;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CardTest extends BaseTest {
	@Test
	public void holdsPhraseAndDefinition() throws Exception {
		Card card = Card.create("someId", "hi", "ho");
		assertThat(card).isNotNull();
		assertThat(card.getPhrase()).isEqualTo("hi");
		assertThat(card.getDefinition()).isEqualTo("ho");
	}

	@Test
	public void hasId() throws Exception {
		Card card = Card.create("myId", "hi", "ho");
		assertThat(card.getId()).isEqualTo("myId");
	}

	@Test
	public void idIsOptional() throws Exception {
		assertThat(Card.create("", "thePhrase", "theDefinition").hasId()).isFalse();
		assertThat(Card.create("", "thePhrase", "theDefinition").getId()).isNotEmpty();

		assertThat(Card.create(null, "thePhrase", "theDefinition").hasId()).isFalse();
		assertThat(Card.create(null, "thePhrase", "theDefinition").getId()).isNotEmpty();

		assertThat(Card.create("someId", "thePhrase", "theDefinition").hasId()).isTrue();
		assertThat(Card.create("someId", "thePhrase", "theDefinition").getId()).isNotEmpty();
	}

	@Test
	public void valueType() throws Exception {
		Card first = Card.create("id", "phrase", "definition");
		Card second = Card.create("id", "phrase", "definition");
		assertThat(first).isEqualTo(second);
		assertThat(first).isNotEqualTo(Card.create("x", "phrase", "definition"));
		assertThat(first).isNotEqualTo(Card.create("id", "x", "definition"));
		assertThat(first).isNotEqualTo(Card.create("id", "phrase", "x"));
	}

	@Test(expected = NullPointerException.class)
	public void neverNullPhrase() throws Exception {
		Card.create("asd", null, null);
	}

	@Test(expected = NullPointerException.class)
	public void neverNullDefinition() throws Exception {
		Card.create("asd", "asd", null);
	}

	@Test(expected = NullPointerException.class)
	public void neverEmptyValues() throws Exception {
		Card.create("", "", "");
	}

	public static class CardValueType extends EqualsHashCodeTheory {
		@DataPoints
		public static Object[] data = {
				Card.create("theId", "thePhrase", "theDefinition"),
				Card.create("a", "a", "a"),
				Card.create("", "thePhrase", "theDefinition")
		};
	}
}
