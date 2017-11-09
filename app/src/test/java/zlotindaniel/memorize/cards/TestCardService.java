package zlotindaniel.memorize.cards;

import org.assertj.core.groups.*;
import org.assertj.core.util.*;

import java.util.*;

import zlotindaniel.memorize.data.*;

public class TestCardService extends CardService {
	public Queue<List<Card>> nextReadCards = new ArrayDeque<>();
	public Queue<String> nextCreateCard = new ArrayDeque<>();
	public Queue<Boolean> nextUpdateCard = new ArrayDeque<>();
	public Queue<Boolean> nextDeleteCard = new ArrayDeque<>();
	public Queue<Exception> nextError = new ArrayDeque<>();

	public List<Tuple> readCardsCalls = Lists.newArrayList();
	public List<Tuple> createCardCalls = Lists.newArrayList();
	public List<Tuple> updateCardCalls = Lists.newArrayList();
	public List<Tuple> deleteCardCalls = Lists.newArrayList();

	public TestCardService() {
		super(null);
	}

	@Override
	public void readTopicCards(final String topicId, final OnSuccess<List<Card>> onSuccess, final OnFailure onFailure) {
		readCardsCalls.add(Tuple.tuple(topicId));
		next(nextReadCards, onSuccess, onFailure);
	}

	@Override
	public void createCard(String topicId, final Card card, final OnSuccess<String> onSuccess, final OnFailure onFailure) {
		createCardCalls.add(Tuple.tuple(topicId, card));
		next(nextCreateCard, onSuccess, onFailure);
	}

	@Override
	public void updateCard(final String topicId, final Card card, final OnSuccess<Boolean> onSuccess, final OnFailure onFailure) {
		updateCardCalls.add(Tuple.tuple(topicId, card));
		next(nextUpdateCard, onSuccess, onFailure);
	}

	@Override
	public void deleteCard(final String topicId, final Card card, final OnSuccess<Boolean> onSuccess, final OnFailure onFailure) {
		deleteCardCalls.add(Tuple.tuple(topicId, card));
		next(nextDeleteCard, onSuccess, onFailure);
	}

	private <T> void next(Queue<T> q, OnSuccess<T> onSuccess, OnFailure onFailure) {
		if (!q.isEmpty()) {
			onSuccess.success(q.poll());
		} else if (!nextError.isEmpty()) {
			onFailure.failure(nextError.poll());
		}
	}
}
