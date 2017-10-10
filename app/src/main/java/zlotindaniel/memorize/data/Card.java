package zlotindaniel.memorize.data;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import org.json.JSONObject;

import zlotindaniel.memorize.extern.JsonUtils;

import static com.google.common.base.Preconditions.checkNotNull;

public class Card {
	private static final String PHRASE = "phrase";
	private static final String DEFINITION = "definition";

	public static Card fromJson(final JSONObject o) {
		return Card.create(o.optString(PHRASE), o.optString(DEFINITION));
	}

	public static Card create(String phrase, String definition) {
		return new Card(
				checkNotNull(Strings.emptyToNull(phrase)),
				checkNotNull(Strings.emptyToNull(definition)));
	}

	private final String phrase;
	private final String definition;

	private Card(String phrase, String definition) {
		this.phrase = phrase;
		this.definition = definition;
	}

	public String getPhrase() {
		return phrase;
	}

	public String getDefinition() {
		return definition;
	}

	@Override
	public String toString() {
		return toJson().toString();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Card card = (Card) o;

		return Objects.equal(phrase, card.phrase) &&
				Objects.equal(definition, card.definition);

	}

	@Override
	public int hashCode() {
		return Objects.hashCode(phrase, definition);
	}

	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		JsonUtils.put(result, PHRASE, phrase);
		JsonUtils.put(result, DEFINITION, definition);
		return result;
	}
}
