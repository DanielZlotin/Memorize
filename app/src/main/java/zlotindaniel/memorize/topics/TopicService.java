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

	public void create(String name, OnSuccess<Topic> onSuccess, OnFailure onFailure) {
		Topic topic = new Topic("", name);
		checkNoDuplicate(topic,
				b -> network.create(new CreateRequest("topics/index", topic, (id) -> onSuccess.success(topic.withId(id)), onFailure)),
				onFailure);
	}

	public void update(Topic topic, OnSuccess<Topic> onSuccess, OnFailure onFailure) {
		checkNoDuplicate(topic,
				b -> network.update(new UpdateRequest("topics/index/" + topic.getId(), topic, b2 -> onSuccess.success(topic), onFailure)),
				onFailure);
	}

	private void checkNoDuplicate(Topic topic, OnSuccess<Boolean> onSuccess, OnFailure onFailure) {
		network.read(new ReadRequest<>("topics/index", new TopicsListParser(), topics -> {
			if (hasTopic(topics, topic.getName())) {
				onFailure.failure(new TopicExistsException());
			} else {
				onSuccess.success(Boolean.TRUE);
			}
		}, onFailure));
	}

	private boolean hasTopic(final List<Topic> topics, final String topicName) {
		return Iterables.any(topics, t -> t.getName().equalsIgnoreCase(topicName));
	}
}
