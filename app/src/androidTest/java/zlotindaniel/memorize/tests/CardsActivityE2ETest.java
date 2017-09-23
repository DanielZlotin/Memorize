package zlotindaniel.memorize.tests;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import zlotindaniel.memorize.BaseE2ETest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class CardsActivityE2ETest extends BaseE2ETest {

	@Test
	public void _1_showPhrase1_Click_ShowDefinition1() throws Exception {
		launchApp();

		assertExists(By.text("Phrase1"));

		device().findObject(By.text("Phrase1")).click();
		assertExists(By.text("Definition1"));
	}

	@Test
	public void _2_showPhrase1_ClickTwice_ShowPhrase2() throws Exception {
		launchApp();

		assertExists(By.text("Phrase1"));

		UiObject2 parent = device().findObject(By.text("Phrase1")).getParent();
		parent.click();
		parent.click();

		assertExists(By.text("Phrase2"));
	}
}
