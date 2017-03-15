package zlotindaniel.memorize.mocks;

import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

import zlotindaniel.memorize.data.Card;
import zlotindaniel.memorize.data.CardsStackShuffler;

public class CardsStackNonShuffler implements CardsStackShuffler {
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
