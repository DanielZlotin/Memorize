package zlotindaniel.memorize.topics;

import org.assertj.core.util.*;
import org.junit.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.data.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TopicServiceTest extends BaseTest {
	private TopicService uut;
	private TestNetwork network;
	private OnSuccess<Topic> onSuccess;
	private OnFailure onFailure;

	@SuppressWarnings("unchecked")
	@Override
	public void beforeEach() {
		super.beforeEach();
		network = new TestNetwork();
		uut = new TopicService(network);
		onSuccess = mock(OnSuccess.class);
		onFailure = mock(OnFailure.class);
	}

	@Test
	public void createTopicCheckForDuplicateByName() throws Exception {
		network.nextSuccess(Lists.newArrayList(new Topic("", "  Some Name\n\n ")));
		uut.create("some name", onSuccess, onFailure);
		assertThat(network.reads).hasSize(1);
		assertThat(network.creations).hasSize(0);
		verify(onFailure, times(1)).failure(isA(TopicService.TopicExistsException.class));
		verifyZeroInteractions(onSuccess);
	}

	@Test
	public void createTopic() throws Exception {
		network.nextSuccess(Lists.newArrayList(new Topic("", "other")));
		network.nextSuccess("theNewId");
		uut.create("new name", onSuccess, onFailure);
		assertThat(network.creations).hasSize(1);
		verify(onSuccess, times(1)).success(eq(new Topic("theNewId", "New Name")));
		verifyZeroInteractions(onFailure);
	}

	@Test
	public void createError() throws Exception {
		RuntimeException error = new RuntimeException("error");
		network.nextError(error);
		uut.create("new name", onSuccess, onFailure);
		verify(onFailure, times(1)).failure(eq(error));
		verifyZeroInteractions(onSuccess);
	}

	@Test
	public void updateTopicChecksDuplicateName() throws Exception {
		network.nextSuccess(Lists.newArrayList(new Topic("", "  Some Name\n\n ")));
		uut.update(new Topic("theId", "some name"), onSuccess, onFailure);
		assertThat(network.reads).hasSize(1);
		assertThat(network.updates).hasSize(0);
		verify(onFailure, times(1)).failure(isA(TopicService.TopicExistsException.class));
		verifyZeroInteractions(onSuccess);
		assertThat(new TopicService.TopicExistsException()).hasMessage("Topic already exists");
	}

	@Test
	public void updateTopic() throws Exception {
		network.nextSuccess(Lists.newArrayList(new Topic("", "othe")));
		network.nextSuccess(true);
		uut.update(new Topic("theId", "some name"), onSuccess, onFailure);
		assertThat(network.reads).hasSize(1);
		assertThat(network.updates).hasSize(1);
		assertThat(network.updates.get(0).path).isEqualTo("topics/index/theId");
		verifyZeroInteractions(onFailure);
		verify(onSuccess, times(1)).success(eq(new Topic("theId", "some name")));
	}
}