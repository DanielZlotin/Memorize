package zlotindaniel.memorize.shuffle;

import java.util.Collections;
import java.util.List;

public class DefaultShuffler<T> implements Shuffler<T> {
	@Override
	public void shuffle(final List<T> objs) {
		Collections.shuffle(objs);
	}
}
