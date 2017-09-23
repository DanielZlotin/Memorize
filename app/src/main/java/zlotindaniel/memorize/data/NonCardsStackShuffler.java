package zlotindaniel.memorize.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

public class NonCardsStackShuffler implements CardsStackShuffler {
	@Override
	public void shuffle(Stack<Card> cards) {
		Collections.sort(cards, new Comparator<Card>() {
			@Override
			public int compare(Card o1, Card o2) {
				return o2.getPhrase().compareToIgnoreCase(o1.getPhrase());
			}
		});
	}
}
