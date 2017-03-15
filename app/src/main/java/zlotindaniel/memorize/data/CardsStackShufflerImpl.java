package zlotindaniel.memorize.data;

import java.util.Collections;
import java.util.Stack;

public class CardsStackShufflerImpl implements CardsStackShuffler {
	@Override
	public void shuffle(Stack<Card> cards) {
		Collections.shuffle(cards);
	}
}
