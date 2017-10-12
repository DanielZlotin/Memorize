package zlotindaniel.memorize;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import zlotindaniel.memorize.extern.cards.CardsView;
import zlotindaniel.memorize.extern.topics.TopicsView;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HappyPathE2ETest extends BaseE2ETest {

	@Test
	public void showTopicsList() throws Exception {
		launchApp();
		waitForText(TopicsView.TITLE);

		assertDisplayed("Topic Name 1");
		assertDisplayed("Topic Name 2");
		assertDisplayed("Topic Name 3");
	}

	@Ignore
	@Test
	public void goThroughCardsInTopic1() throws Exception {
		launchApp();
		waitForText(CardsView.TITLE);

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
