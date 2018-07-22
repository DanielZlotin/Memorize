package zlotindaniel.memorize.usecase.topics;

import org.assertj.core.groups.*;
import org.assertj.core.util.*;
import org.junit.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.data.*;

import static org.assertj.core.api.Assertions.*;

public class TopicsInteractorTest extends BaseTest {

	private TopicsInteractor uut;
	private TestTopicsDisplay testDisplay;
	private MockDatabaseService service;
	private UserDetails userDetails;

	@Override
	public void beforeEach() {
		super.beforeEach();
		testDisplay = new TestTopicsDisplay();
		service = new MockDatabaseService();
		userDetails = new UserDetails("theUserId", "theEmail", "theDisplayName", "thePhotoUrl");
		uut = new TopicsInteractor(userDetails, testDisplay, service);
	}

	@Test
	public void start_loadsTopics() throws Exception {
		service.nextSuccess(true);

		Topic topic1 = new Topic("", "Topic 1");
		Topic topic2 = new Topic("", "Topic 2");
		Topic topic3 = new Topic("", "Topic 3");
		service.nextSuccess(Lists.newArrayList(topic1, topic2, topic3));

		uut.start();

		assertThat(testDisplay.topics).containsExactly(topic1, topic2, topic3);
	}

	@Test
	public void start_failureDisplaysErrorMessage() throws Exception {
		service.nextSuccess(true);
		service.nextFailure(new RuntimeException("The message"));
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
		assertThat(service.readAllTopicsCalls).hasSize(1);
		uut.refresh();
		assertThat(service.readAllTopicsCalls).hasSize(2);
	}

	@Test
	public void createTopic_EmptyDoesNothing() throws Exception {
		uut.createTopic("");
		assertThat(service.readAllTopicsCalls).isEmpty();
	}

	@Test
	public void createTopic_SendsRequest_OnSuccessReload() throws Exception {
		assertThat(service.readAllTopicsCalls).isEmpty();
		service.nextSuccess("id");
		uut.createTopic("the new topic name");

		assertThat(service.readAllTopicsCalls).hasSize(1);
	}

	@Test
	public void createTopicNormalizesInput() throws Exception {
		uut.createTopic("  \n\n a \t b     c  \r\n");
		assertThat(service.createTopicCalls).hasSize(1).containsOnly(Tuple.tuple(new Topic("", "A B C")));
	}

	@Test
	public void sortsTopicsByName() throws Exception {
		service.nextSuccess(true);

		Topic topic1 = new Topic("", "Topic 1");
		Topic topic2 = new Topic("", "Topic 2");
		Topic topic3 = new Topic("", "Topic 3");
		service.nextSuccess(Lists.newArrayList(topic3, topic1, topic2));
		uut.start();
		assertThat(testDisplay.topics).containsExactly(topic1, topic2, topic3);
	}

	@Test
	public void startUpdatesUserDetails() throws Exception {
		service.nextFailure(new RuntimeException("should not be displayed"));
		uut.start();
		assertThat(service.updateUserDetailsCalls).hasSize(1).containsExactly(Tuple.tuple(userDetails));
		assertThat(testDisplay.error).isNull();
	}
}
