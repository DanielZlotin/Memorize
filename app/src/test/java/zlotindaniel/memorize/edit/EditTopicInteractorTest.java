package zlotindaniel.memorize.edit;

import org.junit.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.topics.*;

import static org.assertj.core.api.Assertions.*;

public class EditTopicInteractorTest extends BaseTest {

	private EditTopicInteractor uut;
	private Topic topic;
	private TestEditTopicDisplay display;
	private TestNetwork network;

	@Override
	public void beforeEach() {
		super.beforeEach();
		topic = Topic.create("the id", "the name");
		display = new TestEditTopicDisplay();
		network = new TestNetwork();
		uut = new EditTopicInteractor(topic, display, network);
	}

	@Test
	public void start() throws Exception {
		uut.start();
		assertThat(display.listener).isEqualTo(uut);
	}

	@Test
	public void deleteTopic() throws Exception {
		uut.deleteTopic();
		assertThat(network.deletions).hasSize(1);
		assertThat(network.deletions.get(0)).isInstanceOf(DeleteTopicRequest.class);

		assertThat(display.loading).isTrue();
	}

	@Test
	public void deleteTopicSuccess() throws Exception {
		network.nextSuccess(true);
		uut.deleteTopic();
		assertThat(display.navigateHomeCalled).isTrue();
	}

	@Test
	public void deleteTopicError() throws Exception {
		network.nextError(new RuntimeException("the error"));
		uut.deleteTopic();
		assertThat(display.navigateHomeCalled).isFalse();
		assertThat(display.loading).isFalse();
		assertThat(display.error).isEqualTo("the error");
	}
}