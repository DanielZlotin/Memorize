package zlotindaniel.memorize.edit;

import org.assertj.core.util.*;
import org.junit.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.cards.*;
import zlotindaniel.memorize.topics.*;

import static org.assertj.core.api.Assertions.*;

public class EditTopicInteractorTest extends BaseTest {

	private EditTopicInteractor uut;
	private Topic topic;
	private TestEditTopicDisplay display;
	private TestTopicService topicService;
	private TestCardsService cardsService;

	@Override
	public void beforeEach() {
		super.beforeEach();
		topic = new Topic("the id", "the name");
		display = new TestEditTopicDisplay();
		topicService = new TestTopicService();
		cardsService = new TestCardsService();
		uut = new EditTopicInteractor(topic, display, topicService, cardsService);
	}

	@Test
	public void start() throws Exception {
		uut.start();
		assertThat(display.listener).isEqualTo(uut);
		assertThat(display.topicName).isEqualTo("The Name");
		assertThat(display.loading).isTrue();
	}

	@Test
	public void deleteTopic() throws Exception {
		uut.deleteTopic();
		assertThat(topicService.deleteTopicCalls).hasSize(1);

		assertThat(display.loading).isTrue();
	}

	@Test
	public void deleteTopicSuccess() throws Exception {
		topicService.nextDeleteTopic.offer(true);
		uut.deleteTopic();
		assertThat(display.navigateHomeCalled).isTrue();
	}

	@Test
	public void deleteTopicError() throws Exception {
		topicService.nextError.offer(new RuntimeException("the error"));
		uut.deleteTopic();
		assertThat(display.navigateHomeCalled).isFalse();
		assertThat(display.loading).isFalse();
		assertThat(display.error).isEqualTo("the error");
	}

	@Test
	public void renameTopic() throws Exception {
		topicService.nextUpdateTopic.offer(new Topic("id", "name"));
		uut.renameTopic("the new name");
		assertThat(topicService.updateTopicCalls).hasSize(1);
		assertThat(display.topicName).isEqualTo("Name");
	}

	@Test
	public void renameTopic_MustBeDifferentAfterNormalization() throws Exception {
		uut.renameTopic("");
		assertThat(topicService.updateTopicCalls).isEmpty();
		uut.renameTopic("   \t\n the   \t \n NAME  \r\n\b");
		assertThat(topicService.updateTopicCalls).isEmpty();
	}

	@Test
	public void startReadTopicsError() throws Exception {
		cardsService.nextError.offer(new RuntimeException("the error"));
		uut.start();
		assertThat(display.loading).isFalse();
		assertThat(display.error).isEqualTo("the error");
	}

	@Test
	public void startLoadCardsAndSortsByQuestion() throws Exception {
		Card card1 = new Card("id1", "q1", "a1");
		Card card2 = new Card("id2", "q2", "a2");
		cardsService.nextReadCards.offer(Lists.newArrayList(card2, card1));
		uut.start();
		assertThat(display.loading).isFalse();
		assertThat(display.cards).containsExactly(card1, card2);
	}
}