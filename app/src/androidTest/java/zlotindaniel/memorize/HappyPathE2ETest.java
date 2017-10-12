package zlotindaniel.memorize;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Until;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import zlotindaniel.memorize.extern.cards.CardsView;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HappyPathE2ETest extends BaseE2ETest {

	@Test
	@Ignore
	public void showTopicsList() throws Exception {
		launchApp();

		device().wait(Until.hasObject(By.text(CardsView.TITLE)), TIMEOUT);
	}

	@Test
	public void goThroughCardsInTopic1() throws Exception {
		launchApp();

		device().wait(Until.hasObject(By.text(CardsView.TITLE)), TIMEOUT);

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
