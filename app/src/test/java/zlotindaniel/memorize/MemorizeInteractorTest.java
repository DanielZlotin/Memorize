package zlotindaniel.memorize;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MemorizeInteractorTest extends BaseTest {
	@Test
	public void start_LoadsData() throws Exception {
		TestDataLoader testDataLoader = new TestDataLoader();
		TestDisplay testDisplay = new TestDisplay();
		MemorizeInteractor uut = new MemorizeInteractor(testDisplay, testDataLoader);

		assertThat(testDisplay.loading).isFalse();
		uut.start();
		assertThat(testDisplay.loading).isTrue();
	}

	@Test
	public void loadDataError_ShowError() throws Exception {
		TestDataLoader testDataLoader = new TestDataLoader();
		TestDisplay testDisplay = new TestDisplay();
		MemorizeInteractor uut = new MemorizeInteractor(testDisplay, testDataLoader);

		testDataLoader.setNextError(new RuntimeException("some error"));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("some error");
	}
}
