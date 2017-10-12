package zlotindaniel.memorize.cards;

import java.util.List;

import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;
import zlotindaniel.memorize.data.Request;

public class CardsRequest extends Request<List<Card>> {
	public CardsRequest(final OnSuccess<List<Card>> onSuccess, final OnFailure onFailure) {
		super("topics/cards", new CardsListParser(), onSuccess, onFailure);
	}
}
