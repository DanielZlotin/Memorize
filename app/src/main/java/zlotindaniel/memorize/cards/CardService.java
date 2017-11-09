package zlotindaniel.memorize.cards;

import java.util.*;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.data.request.*;

public class CardService {
	private final Network network;

	public CardService(Network network) {
		this.network = network;
	}

	public void readTopicCards(String topicId, OnSuccess<List<Card>> onSuccess, OnFailure onFailure) {
		network.read(new ReadRequest<>("topics/cards/" + topicId, new CardsListParser(), onSuccess, onFailure));
	}

	public void createCard(String topicId, Card card, OnSuccess<String> onSuccess, OnFailure onFailure) {
		network.create(new CreateRequest("topics/cards/" + topicId, card, onSuccess, onFailure));
	}

	public void updateCard(String topicId, Card card, OnSuccess<Boolean> onSuccess, OnFailure onFailure) {
		network.update(new UpdateRequest("topics/cards/" + topicId + "/" + card.getId(), card, onSuccess, onFailure));
	}

	public void deleteCard(String topicId, Card card, OnSuccess<Boolean> onSuccess, OnFailure onFailure) {
		network.delete(new DeleteRequest("topics/cards/" + topicId + "/" + card.getId(), onSuccess, onFailure));
	}
}
