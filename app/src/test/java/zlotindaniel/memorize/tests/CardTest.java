package zlotindaniel.memorize.tests;

import org.junit.Test;

import zlotindaniel.memorize.BaseTest;
import zlotindaniel.memorize.cards.Card;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CardTest extends BaseTest {
	@Test
	public void holdsPhraseAndDefinition() throws Exception {
		Card card = new Card("hi", "ho");
		assertThat(card).isNotNull();
		assertThat(card.phrase).isEqualTo("hi");
		assertThat(card.definition).isEqualTo("ho");
	}

	@Test
	public void stringRepresentation() throws Exception {
		assertThat(new Card("hi", "ho").toString()).isEqualTo("Card{phrase='hi', definition='ho'}");
	}
}
