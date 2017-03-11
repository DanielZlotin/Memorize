package zlotindaniel.memorize;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class EnvironmentE2ETest {
	@Test
	public void useAppContext() throws Exception {
		Context appContext = getInstrumentation().getTargetContext();
		assertThat(appContext.getPackageName()).isEqualTo("zlotindaniel.memorize");
	}
}
