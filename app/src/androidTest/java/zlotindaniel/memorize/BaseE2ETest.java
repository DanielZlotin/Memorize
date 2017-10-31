package zlotindaniel.memorize;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.assertj.core.api.Assertions.assertThat;

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

	public void assertDisplayed(String txt) {
		Espresso.onView(withText(txt)).check(matches(isDisplayed()));
	}

	public void clickOn(String txt) {
		Espresso.onView(withText(txt)).perform(click());
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
