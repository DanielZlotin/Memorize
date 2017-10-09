package zlotindaniel.memorize.tests;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zlotindaniel.memorize.BaseTest;
import zlotindaniel.memorize.cards.CardsInteractor;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;
import zlotindaniel.memorize.mocks.TestDataLoader;
import zlotindaniel.memorize.mocks.TestDisplay;
import zlotindaniel.memorize.shuffle.DefaultCardShuffler;
import zlotindaniel.memorize.shuffle.ReverseSortingCardShuffler;
import zlotindaniel.memorize.shuffle.Shuffler;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class CardsInteractorTest extends BaseTest {
	private CardsInteractor uut;
	private TestDataLoader testDataLoader;
	private TestDisplay testDisplay;
	private Shuffler testShuffler;

	@Before
	public void beforeEach() {
		testDataLoader = new TestDataLoader();
		testDisplay = new TestDisplay();
		testShuffler = new ReverseSortingCardShuffler();
		uut = new CardsInteractor(testDisplay, testDataLoader, testShuffler);
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
		assertThat(testDisplay.error).isEqualTo("some error");
		assertThat(testDisplay.loading).isEqualTo(false);
	}

	@Test
	public void loadDataSucess_ShowCardPhrase() throws Exception {
		testDataLoader.setNextSuccess("the phrase", "the definition");
		uut.start();
		assertThat(testDisplay.loading).isFalse();
		assertThat(testDisplay.phrase).isEqualTo("the phrase");
	}

	@Test
	public void loadDataSuccess_ShowPhrase_Click_ShowDefinition() throws Exception {
		testDataLoader.setNextSuccess("the phrase", "the definition");
		uut.start();
		assertThat(testDisplay.phrase).isEqualTo("the phrase");
		uut.onClick();
		assertThat(testDisplay.definition).isEqualTo("the definition");
	}

	@Test
	public void onClickNotLoaded_DoesNothing() throws Exception {
		uut.onClick();
	}

	@Test
	public void successWithEmptyListHandled() throws Exception {
		testDataLoader.setNextSuccess();
		uut.start();
		assertThat(testDisplay.loading).isFalse();
	}

	@Test
	public void displaysTheNextCardInTheList() throws Exception {
		testDataLoader.setNextSuccess("Phrase1", "Definition1",
				"Phrase2", "Definition2",
				"Phrase3", "Definition3");
		uut.start();
		assertThat(testDisplay.phrase).isEqualTo("Phrase1");
		uut.onClick();
		assertThat(testDisplay.definition).isEqualTo("Definition1");
		uut.onClick();
		assertThat(testDisplay.phrase).isEqualTo("Phrase2");
		uut.onClick();
		assertThat(testDisplay.definition).isEqualTo("Definition2");
		uut.onClick();
		assertThat(testDisplay.phrase).isEqualTo("Phrase3");
		uut.onClick();
		assertThat(testDisplay.definition).isEqualTo("Definition3");
	}

	@Test
	public void listIsDisplayedCircularilyEndlessly() throws Exception {
		testDataLoader.setNextSuccess("Phrase1", "Definition1",
				"Phrase2", "Definition2");
		uut.start();
		assertThat(testDisplay.phrase).isEqualTo("Phrase1");
		uut.onClick();
		assertThat(testDisplay.definition).isEqualTo("Definition1");
		uut.onClick();
		assertThat(testDisplay.phrase).isEqualTo("Phrase2");
		uut.onClick();
		assertThat(testDisplay.definition).isEqualTo("Definition2");
		uut.onClick();
		assertThat(testDisplay.phrase).isEqualTo("Phrase1");
		uut.onClick();
		assertThat(testDisplay.definition).isEqualTo("Definition1");
	}

	@Test
	public void cleanStateWhenLoadAgain() throws Exception {
		testDataLoader.setNextSuccess("Phrase1", "Definition1",
				"Phrase2", "Definition2");
		uut.start();
		assertThat(testDisplay.phrase).isEqualTo("Phrase1");
		testDataLoader.setNextSuccess("Phrase1", "Definition1",
				"Phrase2", "Definition2");
		uut.start();
		assertThat(testDisplay.phrase).isEqualTo("Phrase1");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void errorOnLoad_ShowsAsError() throws Exception {
		TestDataLoader mock = mock(TestDataLoader.class);
		uut = new CardsInteractor(testDisplay, mock, testShuffler);
		doThrow(new RuntimeException("Error during load")).when(mock).load(any(OnSuccess.class), (OnFailure) any());
		uut.start();
		assertThat(testDisplay.error).isEqualTo("Error during load");
	}

	@Test
	public void shufflesTheCardsRandomly() throws Exception {
		List<String> phrasesDisplayed = new ArrayList<>();
		List<String> allPhrases = Arrays.asList("Phrase1", "Phrase2", "Phrase3");
		for (int i = 0; i < 1e4; i++) {
			uut = new CardsInteractor(testDisplay, testDataLoader, new DefaultCardShuffler());
			testDataLoader.setNextSuccess("Phrase1", "Definition1",
					"Phrase2", "Definition2",
					"Phrase3", "Definition3");
			uut.start();
			phrasesDisplayed.add(testDisplay.phrase);
			if (phrasesDisplayed.containsAll(allPhrases)) {
				return;
			}
		}
		fail("cant find all phrases in 10,000 retries");
	}
}
