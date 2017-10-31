package zlotindaniel.memorize;

import org.junit.After;
import org.junit.Before;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseTest {

	@Before
	public void beforeEach() {

	}

	@After
	public void afterEach() {

	}

	public void assertThrows(Runnable r) {
		Exception err = null;
		try {
			r.run();
		} catch (Exception e) {
			err = e;
		}
		assertThat(err).isNotNull();
	}
}

