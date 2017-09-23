package zlotindaniel.memorize.cards;

import java.util.Collections;
import java.util.Stack;

public class DefaultCardsStackShuffler implements CardsStackShuffler {
	@Override
	public void shuffle(Stack<Card> cards) {
		Collections.shuffle(cards);
	}
}
