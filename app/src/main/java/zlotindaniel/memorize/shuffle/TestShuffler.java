package zlotindaniel.memorize.shuffle;

import com.google.common.collect.Ordering;

import java.util.Collections;
import java.util.List;

public class TestShuffler implements Shuffler {
	@Override
	public <T> void shuffle(final List<T> objs) {
		Collections.sort(objs, Ordering.usingToString().reverse());
	}
}
