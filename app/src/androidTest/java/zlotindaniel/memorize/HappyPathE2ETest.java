package zlotindaniel.memorize;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Until;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HappyPathE2ETest extends BaseE2ETest {

	@Test
	public void _1_HappyPath() throws Exception {
		launchApp();

		device().wait(Until.hasObject(By.text("Card Phrase 1")), TIMEOUT);

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
