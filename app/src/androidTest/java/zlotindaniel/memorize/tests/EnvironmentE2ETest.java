package zlotindaniel.memorize.tests;

import android.content.Context;

import org.junit.Test;

import zlotindaniel.memorize.BaseE2ETest;
import zlotindaniel.memorize.MemorizeApplication;
import zlotindaniel.memorize.MemorizeE2EApplication;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class EnvironmentE2ETest extends BaseE2ETest {

	@Test
	public void useAppContext() throws Exception {
		Context appContext = getInstrumentation().getTargetContext();
		assertThat(appContext.getPackageName()).isEqualTo("zlotindaniel.memorize");
	}

	@Test
	public void applicationInjection() throws Exception {
		assertThat(getInstrumentation().getTargetContext().getApplicationContext()).isInstanceOf(MemorizeE2EApplication.class).isInstanceOf(MemorizeApplication.class);
	}
}
