package zlotindaniel.memorize.usecase.cards;

import com.google.common.collect.*;

import org.junit.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.data.shuffle.*;

import static org.assertj.core.api.Assertions.*;

public class CardsInteractorTest extends BaseTest {
	private CardsInteractor uut;
	private TestCardsDisplay testDisplay;
	private Shuffler testShuffler;
	private MockDatabaseService service;

	@Override
	public void beforeEach() {
		super.beforeEach();
		testDisplay = new TestCardsDisplay();
		testShuffler = new TestShuffler();
		service = new MockDatabaseService();
		uut = new CardsInteractor("someTopicId", testDisplay, service, testShuffler);
	}

	@Test
	public void start_LoadsData() throws Exception {
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Loading);
		assertThat(testDisplay.listener).isEqualTo(uut);
	}

	@Test
	public void loadDataError_ShowError() throws Exception {
		service.nextFailure(new RuntimeException("some error"));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("some error");
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Error);
	}

	@Test
	public void loadDataSucess_ShowCardQuestion() throws Exception {
		service.nextSuccess(Lists.newArrayList(new Card("", "the question", "the answer")));
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("the question");
	}

	@Test
	public void loadDataSuccess_ShowQuestion_Click_ShowAnswer() throws Exception {
		service.nextSuccess(Lists.newArrayList(new Card("", "the question", "the answer")));
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
		service.nextSuccess(Lists.newArrayList());
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Error);
		assertThat(testDisplay.text).isEqualTo("empty cards");
	}

	@Test
	public void displaysTheNextCardInTheList() throws Exception {
		service.nextSuccess(Lists.newArrayList(
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
		service.nextSuccess(Lists.newArrayList(
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
		service.nextSuccess(Lists.newArrayList(
				new Card("", "Question1", "Answer1"),
				new Card("", "Question2", "Answer2")));
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question1");

		service.nextSuccess(Lists.newArrayList(
				new Card("", "Question1", "Answer1"),
				new Card("", "Question2", "Answer2")));

		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question1");
	}

	@Test
	public void easyButtonClickRemovesCardFromCurrentCards() throws Exception {
		service.nextSuccess(Lists.newArrayList(
				new Card("id1", "Question1", "Answer1"),
				new Card("id2", "Question2", "Answer2")));
		uut.start();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question1");
		uut.click();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Answer);
		assertThat(testDisplay.text).isEqualTo("Answer1");
		uut.clickEasyCard();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question2");
		uut.click();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Answer);
		assertThat(testDisplay.text).isEqualTo("Answer2");
		uut.click();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Question);
		assertThat(testDisplay.text).isEqualTo("Question2");
		uut.click();
		assertThat(testDisplay.presentation).isEqualTo(CardsPresentation.Answer);
		assertThat(testDisplay.text).isEqualTo("Answer2");
	}

	@Test
	public void easyButtonClickSavesStaticallyFiltersOutAllNextLoads() throws Exception {
		service.nextSuccess(Lists.newArrayList(
				new Card("myId1", "Question1", "Answer1"),
				new Card("myId2", "Question2", "Answer2")));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("Question1");
		uut.click();
		uut.clickEasyCard();
		service.nextSuccess(Lists.newArrayList(
				new Card("myId1", "Question1", "Answer1"),
				new Card("myId2", "Question2", "Answer2")));
		uut.start();
		assertThat(testDisplay.text).isEqualTo("Question2");
		uut.click();
		uut.click();
		assertThat(testDisplay.text).isEqualTo("Question2");
	}
}
