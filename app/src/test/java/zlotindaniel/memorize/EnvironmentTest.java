package zlotindaniel.memorize;

import org.junit.Test;

import zlotindaniel.memorize.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;

public class EnvironmentTest extends BaseTest {
	@Test
	public void assertJ() throws Exception {
		assertThat(1 + 1).isEqualTo(2).isPositive().isNotZero().isNotNegative().isGreaterThan(1);
	}
}