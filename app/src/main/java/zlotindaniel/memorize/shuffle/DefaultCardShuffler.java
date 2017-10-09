package zlotindaniel.memorize.shuffle;

import java.util.Collections;
import java.util.List;

import zlotindaniel.memorize.data.Card;

public class DefaultCardShuffler implements Shuffler<Card> {
	@Override
	public void shuffle(final List<Card> objs) {
		Collections.shuffle(objs);
	}
}
