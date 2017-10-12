package zlotindaniel.memorize.data;

public class Request<T> {
	public final String path;
	public final JsonParser<T> parser;
	public final OnSuccess<T> onSuccess;
	public final OnFailure onFailure;

	public Request(final String path, final JsonParser<T> parser, final OnSuccess<T> onSuccess, final OnFailure onFailure) {
		this.path = path;
		this.parser = parser;
		this.onSuccess = onSuccess;
		this.onFailure = onFailure;
	}
}
