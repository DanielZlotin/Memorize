package zlotindaniel.memorize.topics;

import java.util.*;

import zlotindaniel.memorize.data.*;

public class TestTopicService extends TopicService {

	public Queue<List<Topic>> nextReadTopics = new ArrayDeque<>();
	public Queue<Topic> nextCreateTopic = new ArrayDeque<>();
	public Queue<Topic> nextUpdateTopic = new ArrayDeque<>();
	public Queue<Boolean> nextDeleteTopic = new ArrayDeque<>();
	public Queue<Exception> nextError = new ArrayDeque<>();

	public Queue<Topic> createTopicCalls = new ArrayDeque<>();
	public Queue<Topic> updateTopicCalls = new ArrayDeque<>();
	public Queue<Topic> deleteTopicCalls = new ArrayDeque<>();
	public int readAllTopicsCalls = 0;

	public TestTopicService() {
		super(null);
	}

	@Override
	public void createTopic(final Topic topic, final OnSuccess<Topic> onSuccess, final OnFailure onFailure) {
		createTopicCalls.offer(topic);
		next(nextCreateTopic, onSuccess, onFailure);
	}

	@Override
	public void updateTopic(final Topic topic, final OnSuccess<Topic> onSuccess, final OnFailure onFailure) {
		updateTopicCalls.offer(topic);
		next(nextUpdateTopic, onSuccess, onFailure);
	}

	@Override
	public void readAllTopics(final OnSuccess<List<Topic>> onSuccess, final OnFailure onFailure) {
		readAllTopicsCalls++;
		next(nextReadTopics, onSuccess, onFailure);
	}

	@Override
	public void deleteTopic(final Topic topic, final OnSuccess<Boolean> onSuccess, final OnFailure onFailure) {
		deleteTopicCalls.offer(topic);
		next(nextDeleteTopic, onSuccess, onFailure);
	}

	private <T> void next(Queue<T> q, OnSuccess<T> onSuccess, OnFailure onFailure) {
		if (!q.isEmpty()) {
			onSuccess.success(q.poll());
		} else if (!nextError.isEmpty()) {
			onFailure.failure(nextError.poll());
		}
	}
}
