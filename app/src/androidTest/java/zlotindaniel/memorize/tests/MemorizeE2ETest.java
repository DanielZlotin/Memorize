package zlotindaniel.memorize.tests;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import zlotindaniel.memorize.BaseE2ETest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MemorizeE2ETest extends BaseE2ETest {
	@Test
	public void _1_uponLaunchShowsTopics() throws Exception {
		launchApp();
		assertExists(By.text("Select A Topic"));
		assertExists(By.text("Topic1"));
		assertExists(By.text("Topic2"));

		device().findObject(By.text("Topic2")).click();
		assertExists(By.text("Topic2"));
		assertDoesNotExists(By.text("Select A Topic"));
	}

	@Test
	public void _2_selectTopic_ShowPhrase1_ShowDefinition1() throws Exception {
		launchApp();

		device().findObject(By.text("Topic1")).click();

		assertExists(By.text("Phrase1"));

		device().findObject(By.text("Phrase1")).click();
		assertExists(By.text("Definition1"));
	}

	@Test
	public void _3_showPhrase1_ClickTwice_ShowPhrase2() throws Exception {
		launchApp();

		device().findObject(By.text("Topic1")).click();

		assertExists(By.text("Phrase1"));

		UiObject2 parent = device().findObject(By.text("Phrase1")).getParent();
		parent.click();
		parent.click();

		assertExists(By.text("Phrase2"));
	}
}
