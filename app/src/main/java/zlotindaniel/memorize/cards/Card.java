package zlotindaniel.memorize.cards;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkNotNull;

public class Card {
	private static final String NO_ID = "NO_ID";

	public static Card create(String id, String phrase, String definition) {
		return new Card(
				MoreObjects.firstNonNull(Strings.emptyToNull(id), NO_ID),
				checkNotNull(Strings.emptyToNull(phrase)),
				checkNotNull(Strings.emptyToNull(definition)));
	}

	private final String id;
	private final String phrase;
	private final String definition;

	private Card(String id, String phrase, String definition) {
		this.id = id;
		this.phrase = phrase;
		this.definition = definition;
	}

	public String getId() {
		return id;
	}

	public boolean hasId() {
		return !Objects.equal(id, NO_ID);
	}

	public String getPhrase() {
		return phrase;
	}

	public String getDefinition() {
		return definition;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Card card = (Card) o;

		return Objects.equal(id, card.id) &&
				Objects.equal(phrase, card.phrase) &&
				Objects.equal(definition, card.definition);

	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, phrase, definition);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).addValue(phrase).addValue(definition).toString();
	}
}
