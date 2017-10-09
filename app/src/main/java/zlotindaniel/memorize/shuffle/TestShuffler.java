package zlotindaniel.memorize.shuffle;

import com.google.common.collect.Ordering;

import java.util.Collections;
import java.util.List;

public class TestShuffler<T> implements Shuffler<T> {
	@Override
	public void shuffle(final List<T> objs) {
		Collections.sort(objs, Ordering.usingToString().reverse());
	}
}
