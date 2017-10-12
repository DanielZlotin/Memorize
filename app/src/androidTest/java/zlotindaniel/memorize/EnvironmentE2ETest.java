package zlotindaniel.memorize;

import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class EnvironmentE2ETest extends BaseE2ETest {

	@Test
	public void useAppContext() throws Exception {
		assertThat(getInstrumentation().getTargetContext().getPackageName()).isEqualTo(PACKAGE_NAME).isEqualTo("zlotindaniel.memorize");
	}

	@Test
	public void applicationInjection() throws Exception {
		assertThat(getInstrumentation().getTargetContext().getApplicationContext()).isInstanceOf(MemorizeE2EApplication.class).isInstanceOf(MemorizeApplication.class);
	}
}
