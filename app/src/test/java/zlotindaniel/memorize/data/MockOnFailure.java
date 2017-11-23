package zlotindaniel.memorize.data;

import org.assertj.core.util.*;

import java.util.*;

import zlotindaniel.memorize.data.*;

public class MockOnFailure implements OnFailure {
	public List<Exception> calls = Lists.newArrayList();

	@Override
	public void failure(final Exception e) {
		calls.add(e);
	}
}
