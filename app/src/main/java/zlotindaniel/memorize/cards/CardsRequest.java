package zlotindaniel.memorize.cards;

import java.util.List;

import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;
import zlotindaniel.memorize.data.Request;

public class CardsRequest extends Request<List<Card>> {
	public CardsRequest(String topicId, final OnSuccess<List<Card>> onSuccess, final OnFailure onFailure) {
		super("topics/cards/" + topicId, new CardsListParser(), onSuccess, onFailure);
	}
}
