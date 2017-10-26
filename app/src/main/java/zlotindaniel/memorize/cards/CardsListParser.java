package zlotindaniel.memorize.cards;

import com.google.common.collect.Lists;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import zlotindaniel.memorize.data.JsonParser;

public class CardsListParser implements JsonParser<List<Card>> {

	public List<Card> parse(JSONObject source) {
		List<Card> result = Lists.newArrayList();

		Iterator<String> keys = source.keys();
		while (keys.hasNext()) {
			String cardId = keys.next();
			JSONObject cardObj = source.optJSONObject(cardId);
			String question = cardObj.optString("question");
			String answer = cardObj.optString("answer");

			Card card = Card.create(cardId, question, answer);
			result.add(card);
		}

		return result;
	}
}
