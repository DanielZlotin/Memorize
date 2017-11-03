package zlotindaniel.memorize.data.request;

import zlotindaniel.memorize.data.*;

public class UpdateRequest extends Request<Boolean> {
	public final ValueType payload;

	public UpdateRequest(final String path, final ValueType payload, final OnSuccess<Boolean> onSuccess, final OnFailure onFailure) {
		super(path, onSuccess, onFailure);
		this.payload = payload;
	}
}
