package zlotindaniel.memorize.cards;

import com.google.common.collect.*;

import org.json.*;

import java.util.*;

import zlotindaniel.memorize.data.*;

public class GetCardsRequest extends Request<List<Card>> {
	public GetCardsRequest(String topicId, final OnSuccess<List<Card>> onSuccess, final OnFailure onFailure) {
		super("topics/cards/" + topicId, onSuccess, onFailure);
	}

	@Override
	public List<Card> parseResponse(final JSONObject response) {
		List<Card> result = Lists.newArrayList();

		Iterator<String> keys = response.keys();
		while (keys.hasNext()) {
			String cardId = keys.next();
			JSONObject cardObj = response.optJSONObject(cardId);
			String question = cardObj.optString("question");
			String answer = cardObj.optString("answer");

			Card card = Card.create(cardId, question, answer);
			result.add(card);
		}

		return result;
	}
}
