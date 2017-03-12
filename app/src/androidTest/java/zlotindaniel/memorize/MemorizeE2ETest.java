package zlotindaniel.memorize;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class MemorizeE2ETest extends BaseE2ETest {

	private static final long TIMEOUT = 3000;

	@Test
	public void launchApp_ShowPhrase1() throws Exception {
		device().pressHome();
		device().findObject(By.desc("Apps")).click();
		new UiScrollable(new UiSelector()).scrollTextIntoView("Memorize");
		device().findObject(By.text("Memorize")).click();

		assertThat(device().wait(Until.hasObject(By.text("Phrase 1")), TIMEOUT)).isTrue();
	}

	@Test
	public void launchApp_ShowPhrase1_Click_ShowDefinition1() throws Exception {
		device().pressHome();
		device().findObject(By.desc("Apps")).click();
		new UiScrollable(new UiSelector()).scrollTextIntoView("Memorize");
		device().findObject(By.text("Memorize")).click();

		assertThat(device().wait(Until.hasObject(By.text("Phrase 1")), TIMEOUT)).isTrue();

		device().findObject(By.text("Phrase 1")).click();
		assertThat(device().wait(Until.hasObject(By.text("Definition 1")), TIMEOUT)).isTrue();
	}

	private UiDevice device() {
		return UiDevice.getInstance(getInstrumentation());
	}
}
