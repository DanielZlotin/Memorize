package zlotindaniel.memorize.data;

import org.json.JSONObject;

public class Card {
	private String phrase;
	private String definition;

	public Card(String phrase, String definition) {
		this.phrase = phrase;
		this.definition = definition;
	}

	public Card(final JSONObject o) {
		this(o.optString("phrase"),
				o.optString("definition"));
	}

	public String getPhrase() {
		return phrase;
	}

	public String getDefinition() {
		return definition;
	}

	@Override
	public String toString() {
		return "Card{" +
				"phrase='" + phrase + '\'' +
				", definition='" + definition + '\'' +
				'}';
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Card card = (Card) o;

		if (!phrase.equals(card.phrase)) return false;
		return definition.equals(card.definition);

	}

	@Override
	public int hashCode() {
		int result = phrase.hashCode();
		result = 31 * result + definition.hashCode();
		return result;
	}

	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		try {
			result.putOpt("phrase", phrase);
			result.putOpt("defintion", definition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
