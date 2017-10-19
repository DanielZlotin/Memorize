package zlotindaniel.memorize.topics;

import com.google.common.collect.Lists;

import org.junit.Test;

import zlotindaniel.memorize.BaseTest;
import zlotindaniel.memorize.mocks.TestLoader;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TopicsInteractorTest extends BaseTest {

	private TopicsInteractor uut;
	private TestLoader loader;
	private TestTopicsDisplay testDisplay;

	@Override
	public void beforeEach() {
		super.beforeEach();
		testDisplay = new TestTopicsDisplay();
		loader = new TestLoader();
		uut = new TopicsInteractor(testDisplay, loader);
	}

	@Test
	public void start_loadsTopics() throws Exception {
		Topic topic1 = Topic.create("", "Topic 1");
		Topic topic2 = Topic.create("", "Topic 2");
		Topic topic3 = Topic.create("", "Topic 3");
		loader.nextSuccess(Lists.newArrayList(topic1, topic2, topic3));

		uut.start();

		assertThat(loader.requests).hasSize(1);
		assertThat(loader.requests.get(0)).isInstanceOf(TopicsRequest.class);
		assertThat(testDisplay.topics).containsExactly(topic1, topic2, topic3);
	}

	@Test
	public void start_failureDisplaysErrorMessage() throws Exception {
		loader.nextError(new RuntimeException("The message"));

		uut.start();

		assertThat(testDisplay.error).isEqualTo("The message");
	}

	@Test
	public void setListenersOnView() throws Exception {
		uut.start();
		assertThat(testDisplay.listener).isNotNull().isEqualTo(uut);
	}

	@Test
	public void navigateToSelectedTopic() throws Exception {
		uut.start();
		uut.onTopicClicked(Topic.create("the topic id", "the name"));

		assertThat(testDisplay.navigatedToTopics).containsExactly("the topic id");
	}

	@Test
	public void onRefreshReloadsTheList() throws Exception {
		uut.start();
		assertThat(loader.requests).hasSize(1);
		uut.onRefresh();
		assertThat(loader.requests).hasSize(2);
	}

	@Test
	public void avoidDoubleNavigate1PerSecond() throws Exception {
		uut.start();
		uut.onTopicClicked(Topic.create("theId1", "theName1"));
		uut.onTopicClicked(Topic.create("theId2", "theName2"));
		uut.onTopicClicked(Topic.create("theId3", "theName3"));
		Thread.sleep(1000);
		uut.onTopicClicked(Topic.create("theId4", "theName4"));
		assertThat(testDisplay.navigatedToTopics).containsExactly("theId1");
		Thread.sleep(1000);
		uut.onTopicClicked(Topic.create("theId5", "theName5"));
		assertThat(testDisplay.navigatedToTopics).containsExactly("theId1", "theId5");
		uut.onTopicClicked(Topic.create("theId6", "theName6"));
		assertThat(testDisplay.navigatedToTopics).containsExactly("theId1", "theId5");
	}
}
