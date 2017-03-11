package zlotindaniel.memorize;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class MemorizeActivityTest {

	@Test
	public void launchApp_() throws Exception {
		device().pressHome();
		device().findObject(By.desc("Apps")).click();
		new UiScrollable(new UiSelector()).scrollTextIntoView("Memorize");
		device().findObject(By.text("Memorize")).click();
		device().waitForIdle();
	}

	private UiDevice device() {
		return UiDevice.getInstance(getInstrumentation());
	}
}
