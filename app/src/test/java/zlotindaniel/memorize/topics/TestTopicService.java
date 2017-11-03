package zlotindaniel.memorize.topics;

import org.assertj.core.util.*;

import java.util.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.data.*;

public class TestTopicService extends TopicService {

	private Queue<List<Topic>> nextReadTopics = new ArrayDeque<>();
	private Queue<Topic> nextCreateTopic = new ArrayDeque<>();
	private Queue<Topic> nextUpdateTopic = new ArrayDeque<>();
	private Queue<Exception> nextError = new ArrayDeque<>();

	public Queue<Topic> createTopicCalls = new ArrayDeque<>();
	public Queue<Topic> updateTopicCalls = new ArrayDeque<>();
	public int readAllTopicsCalls = 0;

	public TestTopicService() {
		super(new TestNetwork());
	}

	@Override
	public void createTopic(final Topic topic, final OnSuccess<Topic> onSuccess, final OnFailure onFailure) {
		createTopicCalls.offer(topic);

		if (!nextCreateTopic.isEmpty()) {
			onSuccess.success(nextCreateTopic.poll());
		} else if (!nextError.isEmpty()) {
			onFailure.failure(nextError.poll());
		}
	}

	@Override
	public void updateTopic(final Topic topic, final OnSuccess<Topic> onSuccess, final OnFailure onFailure) {
		updateTopicCalls.offer(topic);

		if (!nextUpdateTopic.isEmpty()) {
			onSuccess.success(nextUpdateTopic.poll());
		} else if (!nextError.isEmpty()) {
			onFailure.failure(nextError.poll());
		}
	}

	@Override
	public void readAllTopics(final OnSuccess<List<Topic>> onSuccess, final OnFailure onFailure) {
		readAllTopicsCalls++;

		if (!nextReadTopics.isEmpty()) {
			onSuccess.success(nextReadTopics.poll());
		} else if (!nextError.isEmpty()) {
			onFailure.failure(nextError.poll());
		}
	}

	public void nextReadAllTopics(Topic... topics) {
		nextReadTopics.offer(Lists.newArrayList(topics));
	}

	public void nextCreateTopic(Topic topic) {
		nextCreateTopic.offer(topic);
	}

	public void nextUpdateTopic(Topic topic) {
		nextUpdateTopic.offer(topic);
	}

	public void nextError(Exception e) {
		nextError.offer(e);
	}
}
