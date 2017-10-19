package zlotindaniel.memorize.mocks;

import com.google.common.collect.Lists;

import java.util.List;

import zlotindaniel.memorize.data.Loader;
import zlotindaniel.memorize.data.Request;

public class TestLoader implements Loader {
	public List<Request<?>> requests = Lists.newArrayList();
	private Object nextSuccess;
	private Exception nextError;

	@SuppressWarnings("unchecked")
	@Override
	public <T> void load(final Request<T> request) {
		requests.add(request);
		if (nextSuccess != null) {
			T t = (T) nextSuccess;
			request.onSuccess.success(t);
			nextSuccess = null;
		} else if (nextError != null) {
			request.onFailure.failure(nextError);
			nextError = null;
		}
	}

	public void nextSuccess(Object o) {
		this.nextSuccess = o;
	}

	public void nextError(Exception e) {
		this.nextError = e;
	}
}
