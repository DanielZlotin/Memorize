package zlotindaniel.memorize.cards;

public class Card {
	public final String phrase;
	public final String definition;

	public Card(String phrase, String definition) {
		this.phrase = phrase;
		this.definition = definition;
	}

	@Override
	public String toString() {
		return "Card{" +
				"phrase='" + phrase + '\'' +
				", definition='" + definition + '\'' +
				'}';
	}
}
