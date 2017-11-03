package zlotindaniel.memorize.topics;

import com.google.common.collect.*;

import org.junit.*;

import zlotindaniel.memorize.*;

import static org.assertj.core.api.Assertions.*;

public class TopicsInteractorTest extends BaseTest {

	private TopicsInteractor uut;
	private TestNetwork network;
	private TestTopicsDisplay testDisplay;

	@Override
	public void beforeEach() {
		super.beforeEach();
		testDisplay = new TestTopicsDisplay();
		network = new TestNetwork();
		uut = new TopicsInteractor(testDisplay, network);
	}

	@Test
	public void start_loadsTopics() throws Exception {
		Topic topic1 = new Topic("", "Topic 1");
		Topic topic2 = new Topic("", "Topic 2");
		Topic topic3 = new Topic("", "Topic 3");
		network.nextSuccess(Lists.newArrayList(topic1, topic2, topic3));

		uut.start();

		assertThat(network.reads).hasSize(1);
		assertThat(testDisplay.topics).containsExactly(topic1, topic2, topic3);
	}

	@Test
	public void start_failureDisplaysErrorMessage() throws Exception {
		network.nextError(new RuntimeException("The message"));
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
		assertThat(network.reads).hasSize(1);
		uut.refresh();
		assertThat(network.reads).hasSize(2);
	}

	@Test
	public void createTopic_EmptyDoesNothing() throws Exception {
		uut.createTopic("");
		assertThat(network.reads).hasSize(0);
	}

	@Test
	public void createTopic_SendsRequest_OnSuccessReload() throws Exception {
		network.nextSuccess(true);

		uut.createTopic("the new topic name");

		assertThat(network.creations).hasSize(1);
		assertThat(network.reads).hasSize(1);
	}

	@Test
	public void createTopicNormalizesInput() throws Exception {
		uut.createTopic("  \n\n a \t b     c  \r\n");
		assertThat(network.creations).hasSize(1);
		assertThat(network.creations.get(0).payload.toJson().get("name")).isEqualTo("A B C");
	}

	@Test
	public void createTopic_Failure() throws Exception {
		RuntimeException error = new RuntimeException("the error");
		network.nextError(error);
		uut.createTopic("the new topic name");

		assertThat(network.creations).hasSize(1);
		assertThat(testDisplay.error).isEqualTo("the error");
	}

	@Test
	public void createTopic_EnsureNoDuplicateByName() throws Exception {
		Topic topic1 = new Topic("", "Topic 1");
		Topic topic2 = new Topic("", "Topic 2");
		Topic topic3 = new Topic("", "Topic 3");
		network.nextSuccess(Lists.newArrayList(topic1, topic2, topic3));

		uut.start();

		uut.createTopic("   Topic  \n     \t    1 \t\n");

		assertThat(testDisplay.error).isEqualTo("Topic already exists");
	}

	@Test
	public void sortsTopicsByName() throws Exception {
		Topic topic1 = new Topic("", "Topic 1");
		Topic topic2 = new Topic("", "Topic 2");
		Topic topic3 = new Topic("", "Topic 3");
		network.nextSuccess(Lists.newArrayList(topic3, topic1, topic2));
		uut.start();
		assertThat(testDisplay.topics).containsExactly(topic1, topic2, topic3);
	}
}
