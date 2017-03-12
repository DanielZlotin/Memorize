package zlotindaniel.memorize;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import edu.emory.mathcs.backport.java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MemorizeInteractorTest extends BaseTest {
	private MemorizeInteractor uut;
	private TestDataLoader testDataLoader;
	private TestDisplay testDisplay;

	@Before
	public void beforeEach() {
		testDataLoader = new TestDataLoader();
		testDisplay = new TestDisplay();
		uut = new MemorizeInteractor(testDisplay, testDataLoader);
	}

	@Test
	public void start_LoadsData() throws Exception {
		assertThat(testDisplay.loading).isFalse();
		uut.start();
		assertThat(testDisplay.loading).isTrue();
	}

	@Test
	public void loadDataError_ShowError() throws Exception {
		testDataLoader.setNextError(new RuntimeException("some error"));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("some error");
		assertThat(testDisplay.loading).isEqualTo(false);
	}

	@Test
	public void loadDataSucess_ShowCardPhrase() throws Exception {
		testDataLoader.setNextSuccess(Arrays.asList(new Card("the phrase", "the definition")));
		uut.start();
		assertThat(testDisplay.loading).isFalse();
		assertThat(testDisplay.text).isEqualTo("the phrase");
	}

	@Test
	public void loadDataSuccess_ShowPhrase_Click_ShowDefinition() throws Exception {
		testDataLoader.setNextSuccess(Arrays.asList(new Card("the phrase", "the definition")));
		uut.start();
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("the definition");
	}

	@Test
	public void onClickNotLoaded_DoesNothing() throws Exception {
		uut.onClick();
	}

	@Test
	public void successWithEmptyListHandled() throws Exception {
		testDataLoader.setNextSuccess(Collections.emptyList());
		uut.start();
		assertThat(testDisplay.loading).isFalse();
	}
}
