package zlotindaniel.memorize.data;

import com.google.common.collect.Lists;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardsParser {
	public List<Card> parse(JSONObject source) {
		List<Card> result = new ArrayList<>();
		for (String key : Lists.newArrayList(source.keys())) {
			Card card = Card.create(key, source.optString(key));
			result.add(card);
		}
		return result;
	}
}
