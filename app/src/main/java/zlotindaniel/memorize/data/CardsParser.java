package zlotindaniel.memorize.data;

import com.google.common.collect.Lists;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardsParser {
	public List<Card> parse(JSONObject source) {
		List<Card> result = new ArrayList<>();

		for (String id : Lists.newArrayList(source.keys())) {
			JSONObject cardObj = source.optJSONObject(id);
			String phrase = cardObj.optString("phrase");
			String definition = cardObj.optString("definition");

			Card card = Card.create(id, phrase, definition);
			result.add(card);
		}

		return result;
	}
}
