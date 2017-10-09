package zlotindaniel.memorize.shuffle;

import java.util.Collections;
import java.util.List;

public class DefaultShuffler implements Shuffler {
	@Override
	public <T> void shuffle(final List<T> objs) {
		Collections.shuffle(objs);
	}
}
