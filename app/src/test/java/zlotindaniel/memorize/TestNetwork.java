package zlotindaniel.memorize;

import com.google.common.collect.*;

import java.util.*;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.data.request.*;

public class TestNetwork implements Network {
	public List<CreateRequest> creations = Lists.newArrayList();
	public List<ReadRequest<?>> reads = Lists.newArrayList();
	public List<UpdateRequest> updates = Lists.newArrayList();
	public List<DeleteRequest> deletions = Lists.newArrayList();
	public String userId;

	private Queue<Object> nextSuccesses = new ArrayDeque<>();
	private Queue<Exception> nextErrors = new ArrayDeque<>();

	public void nextSuccess(Object o) {
		nextSuccesses.offer(o);
	}

	public void nextError(Exception e) {
		nextErrors.offer(e);
	}

	@Override
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	@Override
	public void create(final CreateRequest request) {
		creations.add(request);
		next(request);
	}

	@Override
	public <T> void read(final ReadRequest<T> request) {
		reads.add(request);
		next(request);
	}

	@Override
	public void update(final UpdateRequest request) {
		updates.add(request);
		next(request);
	}

	@Override
	public void delete(final DeleteRequest request) {
		deletions.add(request);
		next(request);
	}

	@SuppressWarnings("unchecked")
	private <T> void next(Request<T> request) {
		if (!nextSuccesses.isEmpty()) {
			T t = (T) nextSuccesses.poll();
			request.onSuccess.success(t);
		} else if (!nextErrors.isEmpty()) {
			request.onFailure.failure(nextErrors.poll());
		}
	}
}
