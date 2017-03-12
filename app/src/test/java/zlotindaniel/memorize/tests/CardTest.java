package zlotindaniel.memorize.tests;

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
}
