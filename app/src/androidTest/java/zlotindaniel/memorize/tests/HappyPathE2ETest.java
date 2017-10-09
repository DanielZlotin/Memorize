package zlotindaniel.memorize.tests;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import zlotindaniel.memorize.BaseE2ETest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HappyPathE2ETest extends BaseE2ETest {

	@Test
	public void _1_HappyPath() throws Exception {
		launchApp();

		assertExists(By.text("Phrase1"));

		UiObject2 parent = device().findObject(By.text("Phrase1")).getParent();

		parent.click();
		assertExists(By.text("Definition1"));

		parent.click();
		assertExists(By.text("Phrase2"));

		parent.click();
		assertExists(By.text("Definition2"));

		parent.click();
		assertExists(By.text("Phrase3"));

		parent.click();
		assertExists(By.text("Definition3"));

		parent.click();
		assertExists(By.text("Phrase1"));
	}
}
