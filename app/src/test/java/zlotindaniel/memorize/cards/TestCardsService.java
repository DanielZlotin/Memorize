package zlotindaniel.memorize.cards;

import java.util.*;

import zlotindaniel.memorize.data.*;

public class TestCardsService extends CardsService {
	public Queue<List<Card>> nextReadCards = new ArrayDeque<>();
	public Queue<Exception> nextError = new ArrayDeque<>();

	public int readCardsCalls = 0;

	public TestCardsService() {
		super(null);
	}

	@Override
	public void readTopicCards(final String topicId, final OnSuccess<List<Card>> onSuccess, final OnFailure onFailure) {
		readCardsCalls++;
		next(nextReadCards, onSuccess, onFailure);
	}

	private <T> void next(Queue<T> q, OnSuccess<T> onSuccess, OnFailure onFailure) {
		if (!q.isEmpty()) {
			onSuccess.success(q.poll());
		} else if (!nextError.isEmpty()) {
			onFailure.failure(nextError.poll());
		}
	}
}
