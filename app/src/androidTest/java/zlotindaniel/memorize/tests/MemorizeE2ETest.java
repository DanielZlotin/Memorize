package zlotindaniel.memorize.tests;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Test;

import zlotindaniel.memorize.BaseE2ETest;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class MemorizeE2ETest extends BaseE2ETest {
	private static final long TIMEOUT = 10000;

	@Test
	public void showPhrase1() throws Exception {
		launchApp();
		assertExists(By.text("Phrase1"));
	}

	@Test
	public void showPhrase1_Click_ShowDefinition1() throws Exception {
		launchApp();

		assertExists(By.text("What is:"));
		assertExists(By.text("Phrase1"));

		device().findObject(By.text("Phrase1")).click();
		assertExists(By.text("Definition1"));
		assertThat(device().findObject(new UiSelector().text("What is:")).exists()).isFalse();
	}

	@Test
	public void showPhrase1_ClickTwice_ShowPhrase2() throws Exception {
		launchApp();

		assertExists(By.text("Phrase1"));

		UiObject2 parent = device().findObject(By.text("Phrase1")).getParent();
		parent.click();
		parent.click();

		assertExists(By.text("Phrase2"));
	}

	private void launchApp() throws UiObjectNotFoundException {
		if (device().findObject(new UiSelector().text("Memorize")).exists()) {
			device().findObject(By.text("Memorize")).click();
		} else {
			device().pressHome();
			device().findObject(By.desc("Apps")).click();
			new UiScrollable(new UiSelector()).scrollTextIntoView("Memorize");
			device().findObject(By.text("Memorize")).click();
		}
	}

	private UiDevice device() {
		return UiDevice.getInstance(getInstrumentation());
	}

	private void assertExists(BySelector by) {
		assertThat(device().wait(Until.hasObject(by), TIMEOUT)).isTrue();
	}
}
