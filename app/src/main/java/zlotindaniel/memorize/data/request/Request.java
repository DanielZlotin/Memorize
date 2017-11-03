package zlotindaniel.memorize.data.request;

import zlotindaniel.memorize.data.*;

public abstract class Request<T> {
	public final String path;
	public final OnSuccess<T> onSuccess;
	public final OnFailure onFailure;

	public Request(final String path, final OnSuccess<T> onSuccess, final OnFailure onFailure) {
		this.path = path;
		this.onSuccess = onSuccess;
		this.onFailure = onFailure;
	}
}
