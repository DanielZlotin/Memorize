package zlotindaniel.memorize;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public abstract class BaseE2ETest {
	public static final long TIMEOUT = 10000;
	public static final String PACKAGE_NAME = getInstrumentation().getTargetContext().getPackageName();

	@Rule
	public final ActivityTestRule<LaunchActivity> rule = new ActivityTestRule<>(LaunchActivity.class, false, false);

	@Before
	public void beforeEach() throws Exception {
		device().wakeUp();
		device().setOrientationNatural();
	}

	@After
	public void afterEach() throws Exception {
	}

	public void launchApp() throws Exception {
		device().pressHome();
		rule.launchActivity(null);
		device().waitForIdle();
	}

	public UiDevice device() {
		return UiDevice.getInstance(getInstrumentation());
	}

	public void assertExists(BySelector by) {
		assertThat(device().wait(Until.hasObject(by), TIMEOUT)).isTrue();
	}
}
