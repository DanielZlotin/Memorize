package zlotindaniel.memorize.topics;

import org.assertj.core.util.*;
import org.junit.*;

import zlotindaniel.memorize.*;

import static org.assertj.core.api.Assertions.*;

public class TopicsInteractorTest extends BaseTest {

	private TopicsInteractor uut;
	private TestTopicsDisplay testDisplay;
	private TestTopicService service;

	@Override
	public void beforeEach() {
		super.beforeEach();
		testDisplay = new TestTopicsDisplay();
		service = new TestTopicService();
		uut = new TopicsInteractor(testDisplay, service);
	}

	@Test
	public void start_loadsTopics() throws Exception {
		Topic topic1 = new Topic("", "Topic 1");
		Topic topic2 = new Topic("", "Topic 2");
		Topic topic3 = new Topic("", "Topic 3");
		service.nextReadTopics.offer(Lists.newArrayList(topic1, topic2, topic3));

		uut.start();

		assertThat(testDisplay.topics).containsExactly(topic1, topic2, topic3);
	}

	@Test
	public void start_failureDisplaysErrorMessage() throws Exception {
		service.nextError.offer(new RuntimeException("The message"));
		uut.start();
		assertThat(testDisplay.error).isEqualTo("The message");
	}

	@Test
	public void setListenersOnView() throws Exception {
		uut.start();
		assertThat(testDisplay.listener).isNotNull().isEqualTo(uut);
	}

	@Test
	public void onRefreshReloadsTheList() throws Exception {
		uut.start();
		assertThat(service.readAllTopicsCalls).isOne();
		uut.refresh();
		assertThat(service.readAllTopicsCalls).isEqualTo(2);
	}

	@Test
	public void createTopic_EmptyDoesNothing() throws Exception {
		uut.createTopic("");
		assertThat(service.readAllTopicsCalls).isZero();
	}

	@Test
	public void createTopic_SendsRequest_OnSuccessReload() throws Exception {
		assertThat(service.readAllTopicsCalls).isZero();
		service.nextCreateTopic.offer(new Topic("id", "name"));
		uut.createTopic("the new topic name");

		assertThat(service.readAllTopicsCalls).isOne();
	}

	@Test
	public void createTopicNormalizesInput() throws Exception {
		uut.createTopic("  \n\n a \t b     c  \r\n");
		assertThat(service.createTopicCalls).hasSize(1);
		assertThat(service.createTopicCalls.poll().getName()).isEqualTo("A B C");
	}

	@Test
	public void sortsTopicsByName() throws Exception {
		Topic topic1 = new Topic("", "Topic 1");
		Topic topic2 = new Topic("", "Topic 2");
		Topic topic3 = new Topic("", "Topic 3");
		service.nextReadTopics.offer(Lists.newArrayList(topic3, topic1, topic2));
		uut.start();
		assertThat(testDisplay.topics).containsExactly(topic1, topic2, topic3);
	}
}
