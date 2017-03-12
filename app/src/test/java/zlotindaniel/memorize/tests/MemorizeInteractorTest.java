package zlotindaniel.memorize.tests;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import zlotindaniel.memorize.BaseTest;
import zlotindaniel.memorize.MemorizeInteractor;
import zlotindaniel.memorize.data.Card;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;
import zlotindaniel.memorize.mocks.TestDataLoader;
import zlotindaniel.memorize.mocks.TestDisplay;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

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
		testDataLoader.setNextSuccess(Collections.singletonList(new Card("the phrase", "the definition")));
		uut.start();
		assertThat(testDisplay.loading).isFalse();
		assertThat(testDisplay.text).isEqualTo("the phrase");
	}

	@Test
	public void loadDataSuccess_ShowPhrase_Click_ShowDefinition() throws Exception {
		testDataLoader.setNextSuccess(Collections.singletonList(new Card("the phrase", "the definition")));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("the phrase");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("the definition");
	}

	@Test
	public void onClickNotLoaded_DoesNothing() throws Exception {
		uut.onClick();
	}

	@Test
	public void successWithEmptyListHandled() throws Exception {
		testDataLoader.setNextSuccess(new ArrayList<Card>());
		uut.start();
		assertThat(testDisplay.loading).isFalse();
	}

	@Test
	public void displaysTheNextCardInTheList() throws Exception {
		Card card1 = new Card("P1", "D1");
		Card card2 = new Card("P2", "D2");
		Card card3 = new Card("P3", "D3");
		testDataLoader.setNextSuccess(Arrays.asList(card1, card2, card3));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("P3");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("D3");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("P2");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("D2");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("P1");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("D1");
	}

	@Test
	public void listIsDisplayedCircularilyEndlessly() throws Exception {
		Card card1 = new Card("P1", "D1");
		Card card2 = new Card("P2", "D2");
		testDataLoader.setNextSuccess(Arrays.asList(card1, card2));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("P2");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("D2");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("P1");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("D1");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("P2");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("D2");
	}

	@Test
	public void cleanStateWhenLoadAgain() throws Exception {
		Card card1 = new Card("P1", "D1");
		Card card2 = new Card("P2", "D2");
		testDataLoader.setNextSuccess(Arrays.asList(card1, card2));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("P2");
		testDataLoader.setNextSuccess(Arrays.asList(card1, card2));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("P2");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void errorOnLoad_ShowsAsError() throws Exception {
		TestDataLoader mock = mock(TestDataLoader.class);
		uut = new MemorizeInteractor(testDisplay, mock);
		doThrow(new RuntimeException("Error during load")).when(mock).load((OnSuccess<List<Card>>) any(), (OnFailure) any());
		uut.start();
		assertThat(testDisplay.text).isEqualTo("Error during load");
	}
}
