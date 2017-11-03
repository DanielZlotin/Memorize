package zlotindaniel.memorize.data.request;

import zlotindaniel.memorize.data.*;

public class ReadRequest<T> extends Request<T> {
	public final JsonParser<T> jsonParser;

	public ReadRequest(String path, JsonParser<T> jsonParser, OnSuccess<T> onSuccess, OnFailure onFailure) {
		super(path, onSuccess, onFailure);
		this.jsonParser = jsonParser;
	}
}
