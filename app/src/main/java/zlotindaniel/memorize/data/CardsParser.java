package zlotindaniel.memorize.data;

import com.google.common.collect.Lists;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardsParser {
	public List<Card> parse(JSONObject source) {
		List<Card> result = new ArrayList<>();

		for (String key : Lists.newArrayList(source.keys())) {
			JSONObject cardObj = source.optJSONObject(key);
			Card card = Card.fromJson(cardObj);
			result.add(card);
		}

		return result;
	}
}
