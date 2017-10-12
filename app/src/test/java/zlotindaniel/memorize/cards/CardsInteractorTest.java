package zlotindaniel.memorize.cards;

import com.google.common.collect.Lists;

import org.junit.Test;

import zlotindaniel.memorize.BaseTest;
import zlotindaniel.memorize.data.Request;
import zlotindaniel.memorize.mocks.TestLoader;
import zlotindaniel.memorize.shuffle.Shuffler;
import zlotindaniel.memorize.shuffle.TestShuffler;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CardsInteractorTest extends BaseTest {
	private CardsInteractor uut;
	private TestCardsDisplay testDisplay;
	private Shuffler testShuffler;
	private TestLoader loader;

	@Override
	public void beforeEach() {
		super.beforeEach();
		loader = new TestLoader();
		testDisplay = new TestCardsDisplay();
		testShuffler = new TestShuffler();
		uut = new CardsInteractor(testDisplay, loader, testShuffler);
	}

	@Test
	public void start_LoadsData() throws Exception {
		loader = new TestLoader() {
			@Override
			public <T> void load(final Request<T> request) {
				//
			}
		};
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Loading);

	}

	@Test
	public void loadDataError_ShowError() throws Exception {
		loader.nextError(new RuntimeException("some error"));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("some error");
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Error);
	}

	@Test
	public void loadDataSucess_ShowCardPhrase() throws Exception {
		loader.nextSuccess(Lists.newArrayList(Card.create("", "the phrase", "the definition")));
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Phrase);
		assertThat(testDisplay.text).isEqualTo("the phrase");
	}

	@Test
	public void loadDataSuccess_ShowPhrase_Click_ShowDefinition() throws Exception {
		loader.nextSuccess(Lists.newArrayList(Card.create("", "the phrase", "the definition")));
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Phrase);
		assertThat(testDisplay.text).isEqualTo("the phrase");
		uut.onClick();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Definition);
		assertThat(testDisplay.text).isEqualTo("the definition");
	}

	@Test
	public void onClickNotLoaded_DoesNothing() throws Exception {
		uut.onClick();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Error);
		assertThat(testDisplay.text).isEqualTo("empty cards");
	}

	@Test
	public void successWithEmptyListHandled() throws Exception {
		loader.nextSuccess(Lists.<Card>newArrayList());
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Error);
		assertThat(testDisplay.text).isEqualTo("empty cards");
	}

	@Test
	public void displaysTheNextCardInTheList() throws Exception {
		loader.nextSuccess(Lists.newArrayList(
				Card.create("", "Phrase1", "Definition1"),
				Card.create("", "Phrase2", "Definition2"),
				Card.create("", "Phrase3", "Definition3")));

		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Phrase);
		assertThat(testDisplay.text).isEqualTo("Phrase1");
		uut.onClick();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Definition);
		assertThat(testDisplay.text).isEqualTo("Definition1");
		uut.onClick();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Phrase);
		assertThat(testDisplay.text).isEqualTo("Phrase2");
		uut.onClick();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Definition);
		assertThat(testDisplay.text).isEqualTo("Definition2");
		uut.onClick();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Phrase);
		assertThat(testDisplay.text).isEqualTo("Phrase3");
		uut.onClick();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Definition);
		assertThat(testDisplay.text).isEqualTo("Definition3");
	}

	@Test
	public void listIsDisplayedCircularilyEndlessly() throws Exception {
		loader.nextSuccess(Lists.newArrayList(
				Card.create("", "Phrase1", "Definition1"),
				Card.create("", "Phrase2", "Definition2")));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("Phrase1");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("Definition1");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("Phrase2");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("Definition2");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("Phrase1");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("Definition1");
	}

	@Test
	public void cleanStateWhenLoadAgain() throws Exception {
		loader.nextSuccess(Lists.newArrayList(
				Card.create("", "Phrase1", "Definition1"),
				Card.create("", "Phrase2", "Definition2")));
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Phrase);
		assertThat(testDisplay.text).isEqualTo("Phrase1");

		loader.nextSuccess(Lists.newArrayList(
				Card.create("", "Phrase1", "Definition1"),
				Card.create("", "Phrase2", "Definition2")));

		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Phrase);
		assertThat(testDisplay.text).isEqualTo("Phrase1");
	}
}
