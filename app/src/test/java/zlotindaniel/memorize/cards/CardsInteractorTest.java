package zlotindaniel.memorize.cards;

import com.google.common.collect.*;

import org.junit.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.data.request.*;
import zlotindaniel.memorize.shuffle.*;

import static org.assertj.core.api.Assertions.*;

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
			public <T> void read(final ReadRequest<T> request) {
				//
			}
		};
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Loading);
		assertThat(testDisplay.listener).isEqualTo(uut);
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
		loader.nextSuccess(Lists.newArrayList(new Card("", "the question", "the answer")));
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("the question");
	}

	@Test
	public void loadDataSuccess_ShowQuestion_Click_ShowAnswer() throws Exception {
		loader.nextSuccess(Lists.newArrayList(new Card("", "the question", "the answer")));
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("the question");
		uut.click();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Answer);
		assertThat(testDisplay.text).isEqualTo("the answer");
	}

	@Test
	public void clickNotLoaded_DoesNothing() throws Exception {
		uut.click();
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
				new Card("", "Question1", "Answer1"),
				new Card("", "Question2", "Answer2"),
				new Card("", "Question3", "Answer3")));

		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question1");
		uut.click();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Answer);
		assertThat(testDisplay.text).isEqualTo("Answer1");
		uut.click();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question2");
		uut.click();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Answer);
		assertThat(testDisplay.text).isEqualTo("Answer2");
		uut.click();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question3");
		uut.click();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Answer);
		assertThat(testDisplay.text).isEqualTo("Answer3");
	}

	@Test
	public void listIsDisplayedCircularilyEndlessly() throws Exception {
		loader.nextSuccess(Lists.newArrayList(
				new Card("", "Question1", "Answer1"),
				new Card("", "Question2", "Answer2")));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("Question1");
		uut.click();
		assertThat(testDisplay.text).isEqualTo("Answer1");
		uut.click();
		assertThat(testDisplay.text).isEqualTo("Question2");
		uut.click();
		assertThat(testDisplay.text).isEqualTo("Answer2");
		uut.click();
		assertThat(testDisplay.text).isEqualTo("Question1");
		uut.click();
		assertThat(testDisplay.text).isEqualTo("Answer1");
	}

	@Test
	public void cleanStateWhenLoadAgain() throws Exception {
		loader.nextSuccess(Lists.newArrayList(
				new Card("", "Question1", "Answer1"),
				new Card("", "Question2", "Answer2")));
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question1");

		loader.nextSuccess(Lists.newArrayList(
				new Card("", "Question1", "Answer1"),
				new Card("", "Question2", "Answer2")));

		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question1");
	}
}
