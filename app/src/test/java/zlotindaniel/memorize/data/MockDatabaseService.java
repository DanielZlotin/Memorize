package zlotindaniel.memorize.data;

import org.assertj.core.groups.*;
import org.assertj.core.util.*;

import java.util.*;


public class MockDatabaseService extends DatabaseService {

	public List<Tuple> createTopicCalls = Lists.newArrayList();
	public List<Tuple> updateTopicCalls = Lists.newArrayList();
	public List<Tuple> readAllTopicsCalls = Lists.newArrayList();
	public List<Tuple> deleteTopicCalls = Lists.newArrayList();
	public List<Tuple> readTopicCardsCalls = Lists.newArrayList();
	public List<Tuple> createCardCalls = Lists.newArrayList();
	public List<Tuple> updateCardCalls = Lists.newArrayList();
	public List<Tuple> deleteCardCalls = Lists.newArrayList();
	public List<Tuple> updateUserDetailsCalls = Lists.newArrayList();

	private Queue<Object> nextSuccesses = new ArrayDeque<>();
	private Queue<Exception> nextFailures = new ArrayDeque<>();

	public MockDatabaseService() {
		super(true, "", new MockDatabase());
	}

	@Override
	public void createTopic(final Topic topic, final OnSuccess<String> onSuccess, final OnFailure onFailure) {
		createTopicCalls.add(Tuple.tuple(topic));
		next(onSuccess, onFailure);
	}

	@Override
	public void updateTopic(final Topic topic, final Runnable onSuccess, final OnFailure onFailure) {
		updateTopicCalls.add(Tuple.tuple(topic));
		next(onSuccess, onFailure);
	}

	@Override
	public void readAllTopics(final OnSuccess<List<Topic>> onSuccess, final OnFailure onFailure) {
		readAllTopicsCalls.add(Tuple.tuple());
		next(onSuccess, onFailure);
	}

	@Override
	public void deleteTopic(final Topic topic, final Runnable onSuccess, final OnFailure onFailure) {
		deleteTopicCalls.add(Tuple.tuple(topic));
		next(onSuccess, onFailure);
	}

	@Override
	public void readTopicCards(final String topicId, final OnSuccess<List<Card>> onSuccess, final OnFailure onFailure) {
		readTopicCardsCalls.add(Tuple.tuple(topicId));
		next(onSuccess, onFailure);
	}

	@Override
	public void createCard(final String topicId, final Card card, final OnSuccess<String> onSuccess, final OnFailure onFailure) {
		createCardCalls.add(Tuple.tuple(topicId, card));
		next(onSuccess, onFailure);
	}

	@Override
	public void updateCard(final String topicId, final Card card, final Runnable onSuccess, final OnFailure onFailure) {
		updateCardCalls.add(Tuple.tuple(topicId, card));
		next(onSuccess, onFailure);
	}

	@Override
	public void deleteCard(final String topicId, final Card card, final Runnable onSuccess, final OnFailure onFailure) {
		deleteCardCalls.add(Tuple.tuple(topicId, card));
		next(onSuccess, onFailure);
	}

	@Override
	public void updateUserDetails(final UserDetails userDetails, final Runnable onSuccess, final OnFailure onFailure) {
		updateUserDetailsCalls.add(Tuple.tuple(userDetails));
		next(onSuccess, onFailure);
	}

	public void nextSuccess(Object o) {
		nextSuccesses.offer(o);
	}

	public void nextFailure(Exception e) {
		nextFailures.offer(e);
	}

	private void next(Object onSuccess, OnFailure onFailure) {
		if (!nextSuccesses.isEmpty()) {
			Object o = nextSuccesses.poll();
			if (onSuccess instanceof OnSuccess) {
				((OnSuccess) onSuccess).success(o);
			} else {
				((Runnable) onSuccess).run();
			}
		} else if (!nextFailures.isEmpty()) {
			onFailure.failure(nextFailures.poll());
		}
	}
}
