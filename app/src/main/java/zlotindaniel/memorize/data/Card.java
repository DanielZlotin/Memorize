package zlotindaniel.memorize.data;

public class Card {
	private String phrase;
	private String definition;

	public Card(String phrase, String definition) {
		this.phrase = phrase;
		this.definition = definition;
	}

	public String getPhrase() {
		return phrase;
	}

	public String getDefinition() {
		return definition;
	}
}
