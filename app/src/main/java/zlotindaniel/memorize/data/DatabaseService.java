package zlotindaniel.memorize.data;

import com.google.common.collect.*;

import java.util.*;

import zlotindaniel.memorize.cards.*;
import zlotindaniel.memorize.data.parser.*;
import zlotindaniel.memorize.topics.*;

public class DatabaseService {

	private final String userId;
	private final Database database;
	private final TopicsListParser topicsListParser;
	private final CardsListParser cardsListParser;

	public DatabaseService(String userId, Database database) {
		this.userId = userId;
		this.database = database;
		topicsListParser = new TopicsListParser();
		cardsListParser = new CardsListParser();
	}

	public void createTopic(Topic topic, OnSuccess<String> onSuccess, OnFailure onFailure) {
		checkNoDuplicate(topic,
				() -> database.create("topics/index", topic, onSuccess, onFailure),
				onFailure);
	}

	public void updateTopic(Topic topic, Runnable onSuccess, OnFailure onFailure) {
		checkNoDuplicate(topic,
				() -> database.update("topics/index/" + topic.getId(), topic, onSuccess, onFailure),
				onFailure);
	}

	public void readAllTopics(OnSuccess<List<Topic>> onSuccess, OnFailure onFailure) {
		database.read("topics/index", topicsListParser, onSuccess, onFailure);
	}

	public void deleteTopic(final Topic topic, final Runnable onSuccess, final OnFailure onFailure) {
		database.delete("topics/index/" + topic.getId(),
				() -> deleteAllTopicCards(topic.getId(), onSuccess, onFailure),
				onFailure);
	}

	public void readTopicCards(String topicId, OnSuccess<List<Card>> onSuccess, OnFailure onFailure) {
		database.read("topics/cards/" + topicId, cardsListParser, onSuccess, onFailure);
	}

	public void createCard(String topicId, Card card, OnSuccess<String> onSuccess, OnFailure onFailure) {
		database.create("topics/cards/" + topicId, card, onSuccess, onFailure);
	}

	public void updateCard(String topicId, Card card, Runnable onSuccess, OnFailure onFailure) {
		database.update("topics/cards/" + topicId + "/" + card.getId(), card, onSuccess, onFailure);
	}

	public void deleteCard(String topicId, Card card, Runnable onSuccess, OnFailure onFailure) {
		database.delete("topics/cards/" + topicId + "/" + card.getId(), onSuccess, onFailure);
	}

	private void deleteAllTopicCards(String topicId, Runnable onSuccess, OnFailure onFailure) {
		database.delete("topics/cards/" + topicId, onSuccess, onFailure);
	}

	private void checkNoDuplicate(Topic topic, Runnable onSuccess, OnFailure onFailure) {
		readAllTopics(topics -> {
			if (hasTopicWithName(topics, topic.getName())) {
				onFailure.failure(new TopicExistsException());
			} else {
				onSuccess.run();
			}
		}, onFailure);
	}

	private boolean hasTopicWithName(final List<Topic> topics, final String topicName) {
		return Iterables.any(topics, t -> t.getName().equalsIgnoreCase(topicName));
	}

	public static class TopicExistsException extends RuntimeException {
		@Override
		public String getMessage() {
			return "Topic already exists";
		}
	}
}
