package zlotindaniel.memorize.data.request;

import zlotindaniel.memorize.data.*;

public class DeleteRequest extends Request<Boolean> {
	public DeleteRequest(final String path, final OnSuccess<Boolean> onSuccess, final OnFailure onFailure) {
		super(path, onSuccess, onFailure);
	}
}
