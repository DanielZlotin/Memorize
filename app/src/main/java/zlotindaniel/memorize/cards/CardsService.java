package zlotindaniel.memorize.cards;

import java.util.*;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.data.request.*;

public class CardsService {
	private final Network network;

	public CardsService(Network network) {
		this.network = network;
	}

	public void readTopicCards(String topicId, OnSuccess<List<Card>> onSuccess, OnFailure onFailure) {
		network.read(new ReadRequest<>("topics/cards/" + topicId, new CardsListParser(), onSuccess, onFailure));
	}
}
