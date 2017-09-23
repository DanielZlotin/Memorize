package zlotindaniel.memorize.tests;

import android.support.test.uiautomator.By;

import org.junit.Test;

import zlotindaniel.memorize.BaseE2ETest;

public class TopicActivityE2ETest extends BaseE2ETest {
	@Test
	public void uponLaunchShowsTopics() throws Exception {
		launchApp();
		assertExists(By.text("Select A Topic"));
		assertExists(By.text("Topic1"));
		assertExists(By.text("Topic2"));
	}

	@Test
	public void selectingAtopicShowsCardScreen() throws Exception {
		launchApp();
		assertExists(By.text("Select A Topic"));
		device().findObject(By.text("Topic2")).click();
		device().waitForIdle();

		assertExists(By.text("Memorize"));
	}
}
