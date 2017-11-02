package zlotindaniel.memorize;

import com.google.common.collect.*;

import java.util.*;

import zlotindaniel.memorize.data.*;

public class TestNetwork implements Network {
	public List<Request<?>> loads = Lists.newArrayList();
	public List<Payload> payloads = Lists.newArrayList();
	public List<Request<Boolean>> deletions = Lists.newArrayList();

	private Stack<Object> nextSuccesses = new Stack<>();
	private Stack<Exception> nextErrors = new Stack<>();

	@Override
	public <T> void load(final Request<T> request) {
		loads.add(request);
		next(request);
	}

	@Override
	public void save(final Payload request) {
		payloads.add(request);
		next(request);
	}

	@Override
	public void delete(final DeleteRequest request) {
		deletions.add(request);
		next(request);
	}

	public void nextSuccess(Object o) {
		nextSuccesses.add(o);
	}

	public void nextError(Exception e) {
		nextErrors.add(e);
	}

	@SuppressWarnings("unchecked")
	private <T> void next(final Request<T> request) {
		if (!nextSuccesses.isEmpty()) {
			T t = (T) nextSuccesses.pop();
			request.onSuccess.success(t);
		} else if (!nextErrors.isEmpty()) {
			request.onFailure.failure(nextErrors.pop());
		}
	}
}
