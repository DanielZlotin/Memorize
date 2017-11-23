package zlotindaniel.memorize.data;

import org.assertj.core.groups.*;
import org.assertj.core.util.*;

import java.util.*;

import zlotindaniel.memorize.data.parser.*;

public class MockDatabase implements Database {

	public List<Tuple> creations = Lists.newArrayList();
	public List<Tuple> reads = Lists.newArrayList();
	public List<Tuple> updates = Lists.newArrayList();
	public List<Tuple> deletions = Lists.newArrayList();

	private Queue<Object> nextSuccess = new ArrayDeque<>();
	private Queue<Exception> nextFailure = new ArrayDeque<>();

	@Override
	public void create(final String path, final ValueType payload, final OnSuccess<String> onSuccess, final OnFailure onFailure) {
		creations.add(Tuple.tuple(path, payload));
		next(onSuccess, onFailure);
	}

	@Override
	public <T> void read(final String path, final JsonParser<T> jsonParser, final OnSuccess<T> onSuccess, final OnFailure onFailure) {
		reads.add(Tuple.tuple(path, jsonParser));
		next(onSuccess, onFailure);
	}

	@Override
	public void update(final String path, final ValueType payload, final Runnable onSuccess, final OnFailure onFailure) {
		updates.add(Tuple.tuple(path, payload));
		next(onSuccess, onFailure);
	}

	@Override
	public void delete(final String path, final Runnable onSuccess, final OnFailure onFailure) {
		deletions.add(Tuple.tuple(path));
		next(onSuccess, onFailure);
	}

	public void nextSuccess(Object o) {
		nextSuccess.offer(o);
	}

	public void nextFailure(Exception e) {
		nextFailure.offer(e);
	}

	private void next(final Object onSuccess, final OnFailure onFailure) {
		if (!nextSuccess.isEmpty()) {
			Object o = nextSuccess.poll();
			if (onSuccess instanceof OnSuccess) {
				((OnSuccess) onSuccess).success(o);
			} else {
				((Runnable) onSuccess).run();
			}
		} else if (!nextFailure.isEmpty()) {
			onFailure.failure(nextFailure.poll());
		}
	}
}
