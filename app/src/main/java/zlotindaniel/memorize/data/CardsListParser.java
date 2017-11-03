package zlotindaniel.memorize.data;

import com.google.common.collect.*;

import org.json.*;

import java.util.*;

import zlotindaniel.memorize.cards.*;

public class CardsListParser implements JsonParser<List<Card>> {
	@Override
	public List<Card> parse(final JSONObject json) {
		List<Card> result = Lists.newArrayList();

		Iterator<String> keys = json.keys();
		while (keys.hasNext()) {
			String cardId = keys.next();
			JSONObject cardObj = json.optJSONObject(cardId);
			Utils.jsonPut(cardObj, "id", cardId);

			result.add(Card.parse(cardObj));
		}

		return result;

	}
}
