package zlotindaniel.memorize.topics;

import com.google.common.collect.*;

import java.util.*;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.data.request.*;

public class TopicService {

	public static class TopicExistsException extends RuntimeException {
		@Override
		public String getMessage() {
			return "Topic already exists";
		}
	}

	private final Network network;

	public TopicService(Network network) {
		this.network = network;
	}

	public void createTopic(Topic topic, OnSuccess<Topic> onSuccess, OnFailure onFailure) {
		checkNoDuplicate(topic,
				b -> network.create(new CreateRequest("topics/index", topic, (id) -> onSuccess.success(topic.withId(id)), onFailure)),
				onFailure);
	}

	public void updateTopic(Topic topic, OnSuccess<Topic> onSuccess, OnFailure onFailure) {
		checkNoDuplicate(topic,
				b -> network.update(new UpdateRequest("topics/index/" + topic.getId(), topic, b2 -> onSuccess.success(topic), onFailure)),
				onFailure);
	}

	public void readAllTopics(OnSuccess<List<Topic>> onSuccess, OnFailure onFailure) {
		network.read(new ReadRequest<>("topics/index", new TopicsListParser(), onSuccess, onFailure));
	}

	public void deleteTopic(final Topic topic, final OnSuccess<Boolean> onSuccess, final OnFailure onFailure) {
		network.delete(new DeleteRequest("topics/index/" + topic.getId(), onSuccess, onFailure));
	}

	private void checkNoDuplicate(Topic topic, OnSuccess<Boolean> onSuccess, OnFailure onFailure) {
		readAllTopics(topics -> {
			if (hasTopic(topics, topic.getName())) {
				onFailure.failure(new TopicExistsException());
			} else {
				onSuccess.success(Boolean.TRUE);
			}
		}, onFailure);
	}

	private boolean hasTopic(final List<Topic> topics, final String topicName) {
		return Iterables.any(topics, t -> t.getName().equalsIgnoreCase(topicName));
	}
}
