package zlotindaniel.memorize.data;

import com.google.common.base.*;
import com.google.common.collect.*;

import java.util.*;

import zlotindaniel.memorize.data.parser.*;

public class DatabaseService {
	private static final String VERSION = "v1";
	private static final String TEST = "test";
	private static final String PRODUCTION = "production";
	private static final String USERS = "users";
	private static final String TOPICS = "topics";
	private static final String INDEX = "index";
	private static final String CARDS = "cards";
	private static final String DETAILS = "details";

	private final String env;
	private final String userId;
	private final Database database;
	private final TopicsListParser topicsListParser;
	private final CardsListParser cardsListParser;

	public DatabaseService(boolean debug, String userId, Database database) {
		this.env = debug ? TEST : PRODUCTION;
		this.userId = userId;
		this.database = database;
		topicsListParser = new TopicsListParser();
		cardsListParser = new CardsListParser();
	}

	public String fullpath(String... parts) {
		return Joiner.on('/').join(VERSION, env, USERS, userId, Joiner.on('/').join(parts));
	}

	public void createTopic(Topic topic, OnSuccess<String> onSuccess, OnFailure onFailure) {
		String path = fullpath(TOPICS, INDEX);
		checkNoDuplicate(topic,
				() -> database.create(path, topic, onSuccess, onFailure),
				onFailure);
	}

	public void updateTopic(Topic topic, Runnable onSuccess, OnFailure onFailure) {
		String path = fullpath(TOPICS, INDEX, topic.getId());
		checkNoDuplicate(topic,
				() -> database.update(path, topic, onSuccess, onFailure),
				onFailure);
	}

	public void readAllTopics(OnSuccess<List<Topic>> onSuccess, OnFailure onFailure) {
		String path = fullpath(TOPICS, INDEX);
		database.read(path, topicsListParser, onSuccess, onFailure);
	}

	public void deleteTopic(final Topic topic, final Runnable onSuccess, final OnFailure onFailure) {
		String path = fullpath(TOPICS, INDEX, topic.getId());
		database.delete(path,
				() -> deleteAllTopicCards(topic.getId(), onSuccess, onFailure),
				onFailure);
	}

	public void readTopicCards(String topicId, OnSuccess<List<Card>> onSuccess, OnFailure onFailure) {
		String path = fullpath(TOPICS, CARDS, topicId);
		database.read(path, cardsListParser, onSuccess, onFailure);
	}

	public void createCard(String topicId, Card card, OnSuccess<String> onSuccess, OnFailure onFailure) {
		String path = fullpath(TOPICS, CARDS, topicId);
		database.create(path, card, onSuccess, onFailure);
	}

	public void updateCard(String topicId, Card card, Runnable onSuccess, OnFailure onFailure) {
		String path = fullpath(TOPICS, CARDS, topicId, card.getId());
		database.update(path, card, onSuccess, onFailure);
	}

	public void deleteCard(String topicId, Card card, Runnable onSuccess, OnFailure onFailure) {
		String path = fullpath(TOPICS, CARDS, topicId, card.getId());
		database.delete(path, onSuccess, onFailure);
	}

	public void updateUserDetails(final UserDetails userDetails, Runnable onSuccess, OnFailure onFailure) {
		database.update(fullpath(DETAILS), userDetails, onSuccess, onFailure);
	}

	private void deleteAllTopicCards(String topicId, Runnable onSuccess, OnFailure onFailure) {
		String path = fullpath(TOPICS, CARDS, topicId);
		database.delete(path, onSuccess, onFailure);
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
