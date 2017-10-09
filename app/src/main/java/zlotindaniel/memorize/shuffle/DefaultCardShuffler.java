package zlotindaniel.memorize.shuffle;

import java.util.Collections;
import java.util.List;

import zlotindaniel.memorize.data.Card;

public class DefaultCardShuffler implements Shuffler<Card> {
	@Override
	public <C extends List<Card>> void shuffle(final C objs) {
		Collections.shuffle(objs);
	}
}
