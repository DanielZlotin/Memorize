package zlotindaniel.memorize.cards;

import com.google.common.base.*;

import org.json.*;

import zlotindaniel.memorize.data.*;

import static com.google.common.base.Preconditions.*;

public class Card implements ValueType {
	private static final String NO_ID = "NO_ID";

	public static Card parse(final JSONObject json) {
		String id = json.optString("id");
		String question = json.optString("question");
		String answer = json.optString("answer");
		return new Card(id, question, answer);
	}

	private final String id;
	private final String question;
	private final String answer;

	public Card(String id, String question, String answer) {
		this.id = MoreObjects.firstNonNull(Strings.emptyToNull(id), NO_ID);
		this.question = checkNotNull(Strings.emptyToNull(question));
		this.answer = checkNotNull(Strings.emptyToNull(answer));
	}

	public String getId() {
		return id;
	}

	public boolean hasId() {
		return !Objects.equal(id, NO_ID);
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Card card = (Card) o;

		return Objects.equal(id, card.id) &&
				Objects.equal(question, card.question) &&
				Objects.equal(answer, card.answer);

	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, question, answer);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).addValue(question).addValue(answer).toString();
	}

	@Override
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		if (hasId()) Utils.jsonPut(json, "id", id);
		Utils.jsonPut(json, "question", question);
		Utils.jsonPut(json, "answer", answer);
		return json;
	}
}
