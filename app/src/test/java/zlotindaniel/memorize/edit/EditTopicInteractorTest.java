package zlotindaniel.memorize.edit;

import org.assertj.core.groups.*;
import org.assertj.core.util.*;
import org.junit.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.cards.*;
import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.topics.*;

import static org.assertj.core.api.Assertions.*;

public class EditTopicInteractorTest extends BaseTest {

	private EditTopicInteractor uut;
	private Topic topic;
	private TestEditTopicDisplay display;
	private MockDatabaseService service;

	@Override
	public void beforeEach() {
		super.beforeEach();
		topic = new Topic("theTopicId", "the name");
		display = new TestEditTopicDisplay();
		service = new MockDatabaseService();
		uut = new EditTopicInteractor(topic, display, service);
	}

	@Test
	public void start() throws Exception {
		uut.start();

		assertThat(display.listener).isEqualTo(uut);
		assertThat(display.topicName).isEqualTo("The Name");
		assertThat(display.loading).isTrue();

		assertThat(service.readTopicCardsCalls).hasSize(1).containsOnly(Tuple.tuple("theTopicId"));
	}

	@Test
	public void deleteTopic() throws Exception {
		uut.deleteTopic();
		assertThat(service.deleteTopicCalls).hasSize(1).containsOnly(Tuple.tuple(topic));
		assertThat(display.loading).isTrue();
	}

	@Test
	public void deleteTopicSuccess() throws Exception {
		service.nextSuccess(true);
		uut.deleteTopic();
		assertThat(display.navigateHomeCalled).isTrue();
	}

	@Test
	public void deleteTopicError() throws Exception {
		service.nextFailures(new RuntimeException("the error"));
		uut.deleteTopic();
		assertThat(display.navigateHomeCalled).isFalse();
		assertThat(display.loading).isFalse();
		assertThat(display.error).isEqualTo("the error");
	}

	@Test
	public void renameTopicStartsLoading() throws Exception {
		uut.renameTopic("the new name");
		assertThat(display.loading).isTrue();
	}

	@Test
	public void renameTopic() throws Exception {
		service.nextSuccess(true);
		uut.renameTopic("the new name");
		assertThat(service.updateTopicCalls).hasSize(1);
		assertThat(display.topicName).isEqualTo("The New Name");
	}

	@Test
	public void renameTopic_MustBeDifferentAfterNormalization() throws Exception {
		uut.renameTopic("");
		assertThat(service.updateTopicCalls).isEmpty();
		uut.renameTopic("   \t\n the   \t \n NAME  \r\n\b");
		assertThat(service.updateTopicCalls).isEmpty();
	}

	@Test
	public void startReadTopicsError() throws Exception {
		service.nextFailures(new RuntimeException("the error"));
		uut.start();
		assertThat(display.loading).isFalse();
		assertThat(display.error).isEqualTo("the error");
	}

	@Test
	public void startLoadCardsAndSortsByQuestion() throws Exception {
		Card card1 = new Card("id1", "q1", "a1");
		Card card2 = new Card("id2", "q2", "a2");
		service.nextSuccess(Lists.newArrayList(card2, card1));
		uut.start();
		assertThat(display.loading).isFalse();
		assertThat(display.cards).containsExactly(card1, card2);
	}

	@Test
	public void createCardStartsLoading() throws Exception {
		uut.createCard("q", "a");
		assertThat(display.loading).isTrue();
	}

	@Test
	public void createCardValidatesNonEmptyInput() throws Exception {
		uut.createCard(null, "");
		assertThat(display.loading).isFalse();
		uut.createCard("q", "");
		assertThat(display.loading).isFalse();
	}

	@Test
	public void createCardReloads() throws Exception {
		Card card1 = new Card("the id", "q1", "a1");
		Card card2 = new Card("id2", "q2", "a2");
		service.nextSuccess("the id");
		service.nextSuccess(Lists.newArrayList(card2, card1));

		uut.createCard("q1", "a1");

		assertThat(service.createCardCalls).containsOnly(Tuple.tuple("theTopicId", new Card("", "q1", "a1")));
		assertThat(service.readTopicCardsCalls).hasSize(1);

		assertThat(display.loading).isFalse();
		assertThat(display.cards).containsExactly(
				new Card("the id", "q1", "a1"),
				new Card("id2", "q2", "a2"));
	}

	@Test
	public void cardDetailsSave() throws Exception {
		Card card = new Card("cardId", "q", "a");
		uut.saveCard(card, " q2  \n", "a2");
		assertThat(service.updateCardCalls).hasSize(1).containsExactly(
				Tuple.tuple("theTopicId", new Card("cardId", "q2", "a2")));
		assertThat(display.loading).isTrue();
	}

	@Test
	public void cardDetailsSaveAndReload() throws Exception {
		Card card = new Card("cardId", "q", "a");

		service.nextSuccess(true);
		service.nextSuccess(Lists.newArrayList());
		uut.saveCard(card, "q", "a2");

		assertThat(service.readTopicCardsCalls).hasSize(1);
		assertThat(display.loading).isFalse();
	}

	@Test
	public void cardDetailsSaveSameDetailsDoesNothing() throws Exception {
		Card card = new Card("cardId", "q", "a");
		uut.saveCard(card, null, " a  ");
		assertThat(service.updateCardCalls).isEmpty();
		uut.saveCard(card, "123", "");
		assertThat(service.updateCardCalls).isEmpty();
		uut.saveCard(card, "  q  ", "  \n a \t");
		assertThat(service.updateCardCalls).isEmpty();
	}

	@Test
	public void cardDetailsDeleteCard() throws Exception {
		Card card = new Card("cardId", "q", "a");
		uut.deleteCard(card);
		assertThat(service.deleteCardCalls).hasSize(1).containsExactly(Tuple.tuple("theTopicId", card));
		assertThat(display.loading).isTrue();
	}

	@Test
	public void cardDetailsDeleteCardThenReload() throws Exception {
		Card card = new Card("cardId", "q", "a");
		service.nextSuccess(true);
		service.nextSuccess(Lists.newArrayList());
		uut.deleteCard(card);
		assertThat(display.loading).isFalse();
	}
}