package zlotindaniel.memorize.data.request;

import zlotindaniel.memorize.data.*;

public class CreateRequest extends Request<String> {
	public final ValueType payload;

	public CreateRequest(final String path, final ValueType payload, final OnSuccess<String> onSuccess, final OnFailure onFailure) {
		super(path, onSuccess, onFailure);
		this.payload = payload;
	}
}