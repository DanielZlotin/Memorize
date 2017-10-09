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

		device().wait(Until.hasObject(By.text("Phrase1")), TIMEOUT);

		assertDisplayed("Phrase1");
		clickOn("Phrase1");
		assertDisplayed("Definition1");
		clickOn("Definition1");
		assertDisplayed("Phrase2");
		clickOn("Phrase2");
		assertDisplayed("Definition2");
		clickOn("Definition2");
		assertDisplayed("Phrase3");
		clickOn("Phrase3");
		assertDisplayed("Definition3");
		clickOn("Definition3");
		assertDisplayed("Phrase1");
	}
}
