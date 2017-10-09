package zlotindaniel.memorize.shuffle;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import zlotindaniel.memorize.data.Card;

public class ReverseSortingCardShuffler implements Shuffler<Card> {

	@Override
	public <C extends List<Card>> void shuffle(final C objs) {
		Collections.sort(objs, new Comparator<Card>() {
			@Override
			public int compare(Card o1, Card o2) {
				return o2.getPhrase().compareToIgnoreCase(o1.getPhrase());
			}
		});
	}
}
