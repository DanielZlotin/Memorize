package zlotindaniel.memorize.tests;

import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import zlotindaniel.memorize.BaseTest;
import zlotindaniel.memorize.MemorizeActivity;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.robolectric.Robolectric.setupActivity;

public class EnvironmentTest extends BaseTest {
	@Test
	public void assertJ() throws Exception {
		assertThat(1 + 1).isEqualTo(2).isPositive().isNotZero().isNotNegative().isGreaterThan(1);
	}

	@Test
	public void robolectric() throws Exception {
		assertThat(RuntimeEnvironment.application).isNotNull();
		assertThat(setupActivity(MemorizeActivity.class)).isNotNull();
	}
}