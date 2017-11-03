package zlotindaniel.memorize.edit;

import org.junit.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.topics.*;

import static org.assertj.core.api.Assertions.*;

public class EditTopicInteractorTest extends BaseTest {

	private EditTopicInteractor uut;
	private Topic topic;
	private TestEditTopicDisplay display;
	private TestTopicService service;

	@Override
	public void beforeEach() {
		super.beforeEach();
		topic = new Topic("the id", "the name");
		display = new TestEditTopicDisplay();
		service = new TestTopicService();
		uut = new EditTopicInteractor(topic, display, service);
	}

	@Test
	public void start() throws Exception {
		uut.start();
		assertThat(display.listener).isEqualTo(uut);
		assertThat(display.topicName).isEqualTo("The Name");
	}

	@Test
	public void deleteTopic() throws Exception {
		uut.deleteTopic();
		assertThat(service.deleteTopicCalls).hasSize(1);

		assertThat(display.loading).isTrue();
	}

	@Test
	public void deleteTopicSuccess() throws Exception {
		service.nextDeleteTopic.offer(true);
		uut.deleteTopic();
		assertThat(display.navigateHomeCalled).isTrue();
	}

	@Test
	public void deleteTopicError() throws Exception {
		service.nextError.offer(new RuntimeException("the error"));
		uut.deleteTopic();
		assertThat(display.navigateHomeCalled).isFalse();
		assertThat(display.loading).isFalse();
		assertThat(display.error).isEqualTo("the error");
	}

	@Test
	public void renameTopic() throws Exception {
		service.nextUpdateTopic.offer(new Topic("id", "name"));
		uut.renameTopic("the new name");
		assertThat(service.updateTopicCalls).hasSize(1);
		assertThat(display.topicName).isEqualTo("Name");
	}

	@Test
	public void renameTopic_MustBeDifferentAfterNormalization() throws Exception {
		uut.renameTopic("");
		assertThat(service.updateTopicCalls).isEmpty();
		uut.renameTopic("   \t\n the   \t \n NAME  \r\n\b");
		assertThat(service.updateTopicCalls).isEmpty();
	}
}