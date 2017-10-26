package zlotindaniel.memorize.cards;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkNotNull;

public class Card {
	private static final String NO_ID = "NO_ID";

	public static Card create(String id, String question, String answer) {
		return new Card(
				MoreObjects.firstNonNull(Strings.emptyToNull(id), NO_ID),
				checkNotNull(Strings.emptyToNull(question)),
				checkNotNull(Strings.emptyToNull(answer)));
	}

	private final String id;
	private final String question;
	private final String answer;

	private Card(String id, String question, String answer) {
		this.id = id;
		this.question = question;
		this.answer = answer;
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
}
