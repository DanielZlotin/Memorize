package zlotindaniel.memorize.data;

import org.assertj.core.util.*;

import java.util.*;

public class MockOnSuccess<T> implements OnSuccess<T> {
	public List<T> calls = Lists.newArrayList();
	@Override
	public void success(final T t) {
		calls.add(t);
	}
}
