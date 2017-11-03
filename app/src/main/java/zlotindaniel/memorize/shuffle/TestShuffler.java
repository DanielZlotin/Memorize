package zlotindaniel.memorize.shuffle;

import com.google.common.collect.*;

import java.util.*;

public class TestShuffler implements Shuffler {
	@Override
	public <T> void shuffle(final List<T> objs) {
		Collections.sort(objs, Ordering.usingToString().reverse());
	}
}
