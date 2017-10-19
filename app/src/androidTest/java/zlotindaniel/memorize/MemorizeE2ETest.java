package zlotindaniel.memorize;

import org.junit.Test;

import zlotindaniel.memorize.android.cards.CardsView;
import zlotindaniel.memorize.android.topics.TopicsView;

public class MemorizeE2ETest extends BaseE2ETest {

	@Test
	public void topicsCardsHappyPath() throws Exception {
		launchApp();
		waitForText(TopicsView.TITLE);

		waitForText("Topic Name 1");
		assertDisplayed("Topic Name 1");
		assertDisplayed("Topic Name 2");
		assertDisplayed("Topic Name 3");

		clickOn("Topic Name 1");

		waitForText(CardsView.TITLE);

		waitForText("Card Phrase 1");
		assertDisplayed("Card Phrase 1");
		clickOn("Card Phrase 1");
		assertDisplayed("Card Definition 1");
		clickOn("Card Definition 1");
		assertDisplayed("Card Phrase 2");
		clickOn("Card Phrase 2");
		assertDisplayed("Card Definition 2");
		clickOn("Card Definition 2");
		assertDisplayed("Card Phrase 3");
		clickOn("Card Phrase 3");
		assertDisplayed("Card Definition 3");
		clickOn("Card Definition 3");
		assertDisplayed("Card Phrase 1");
	}
}
