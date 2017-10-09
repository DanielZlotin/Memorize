package zlotindaniel.memorize.shuffle;

import java.util.List;

public interface Shuffler<T> {
	<C extends List<T>> void shuffle(C objs);
}
