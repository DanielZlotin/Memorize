package zlotindaniel.memorize.topics;

import java.util.List;

import zlotindaniel.memorize.cards.Card;

public class Topic {
	public final String title;
	public final List<Card> cards;

	public Topic(final String title, final List<Card> cards) {
		this.title = title;
		this.cards = cards;
	}
}
