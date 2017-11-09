package zlotindaniel.memorize;

import android.support.test.rule.*;
import android.support.test.runner.*;
import android.support.test.uiautomator.*;
import android.view.*;

import org.hamcrest.Description;
import org.hamcrest.*;
import org.junit.*;
import org.junit.runner.*;

import java.lang.reflect.*;

import static android.support.test.InstrumentationRegistry.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(AndroidJUnit4.class)
public abstract class BaseE2ETest {
	public static final long TIMEOUT = 30000;
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

	public void assertNotDisplayed(String txt) {
		assertThat(device().findObject(new UiSelector().text(txt)).exists()).isFalse();
	}

	public void waitForText(String text) {
		assertThat(device().wait(Until.hasObject(By.text(text)), TIMEOUT)).withFailMessage("%s is not visible", text).isTrue();
	}

	public static Matcher<View> withHint(final String hint) {
		return new TypeSafeMatcher<View>() {
			@Override
			protected boolean matchesSafely(final View item) {
				try {
					Method method = item.getClass().getMethod("getHint");
					return hint.equals(method.invoke(item));
				} catch (Exception e) {
				}
				return false;
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("withHint " + hint);
			}
		};
	}
}
