package zlotindaniel.memorize.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardsParser {
	public List<Card> parse(Map<String, Object> source) {
		List<Card> result = new ArrayList<>();
		for (String key : source.keySet()) {
			Card card = Card.create(key, (String) source.get(key));
			result.add(card);
		}
		return result;
	}
}
