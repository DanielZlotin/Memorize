package zlotindaniel.memorize.cards;

import com.google.common.collect.Lists;

import org.junit.Test;

import zlotindaniel.memorize.BaseTest;
import zlotindaniel.memorize.data.Request;
import zlotindaniel.memorize.TestNetwork;
import zlotindaniel.memorize.shuffle.Shuffler;
import zlotindaniel.memorize.shuffle.TestShuffler;

import static org.assertj.core.api.Assertions.assertThat;

public class CardsInteractorTest extends BaseTest {
	private CardsInteractor uut;
	private TestCardsDisplay testDisplay;
	private Shuffler testShuffler;
	private TestNetwork loader;

	@Override
	public void beforeEach() {
		super.beforeEach();
		loader = new TestNetwork();
		testDisplay = new TestCardsDisplay();
		testShuffler = new TestShuffler();
		uut = new CardsInteractor("someTopicId", testDisplay, loader, testShuffler);
	}

	@Test
	public void start_LoadsData() throws Exception {
		loader = new TestNetwork() {
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
	public void loadDataSucess_ShowCardQuestion() throws Exception {
		loader.nextSuccess(Lists.newArrayList(Card.create("", "the question", "the answer")));
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("the question");
	}

	@Test
	public void loadDataSuccess_ShowQuestion_Click_ShowAnswer() throws Exception {
		loader.nextSuccess(Lists.newArrayList(Card.create("", "the question", "the answer")));
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("the question");
		uut.onClick();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Answer);
		assertThat(testDisplay.text).isEqualTo("the answer");
	}

	@Test
	public void onClickNotLoaded_DoesNothing() throws Exception {
		uut.onClick();
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
				Card.create("", "Question1", "Answer1"),
				Card.create("", "Question2", "Answer2"),
				Card.create("", "Question3", "Answer3")));

		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question1");
		uut.onClick();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Answer);
		assertThat(testDisplay.text).isEqualTo("Answer1");
		uut.onClick();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question2");
		uut.onClick();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Answer);
		assertThat(testDisplay.text).isEqualTo("Answer2");
		uut.onClick();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question3");
		uut.onClick();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Answer);
		assertThat(testDisplay.text).isEqualTo("Answer3");
	}

	@Test
	public void listIsDisplayedCircularilyEndlessly() throws Exception {
		loader.nextSuccess(Lists.newArrayList(
				Card.create("", "Question1", "Answer1"),
				Card.create("", "Question2", "Answer2")));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("Question1");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("Answer1");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("Question2");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("Answer2");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("Question1");
		uut.onClick();
		assertThat(testDisplay.text).isEqualTo("Answer1");
	}

	@Test
	public void cleanStateWhenLoadAgain() throws Exception {
		loader.nextSuccess(Lists.newArrayList(
				Card.create("", "Question1", "Answer1"),
				Card.create("", "Question2", "Answer2")));
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question1");

		loader.nextSuccess(Lists.newArrayList(
				Card.create("", "Question1", "Answer1"),
				Card.create("", "Question2", "Answer2")));

		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question1");
	}
}
