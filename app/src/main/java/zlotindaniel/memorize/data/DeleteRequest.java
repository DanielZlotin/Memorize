package zlotindaniel.memorize.data;

import android.support.annotation.*;

import org.json.*;

public abstract class DeleteRequest extends Request<Boolean> {
	public DeleteRequest(@NonNull final String path, @NonNull final OnSuccess<Boolean> onSuccess, @NonNull final OnFailure onFailure) {
		super(path, onSuccess, onFailure);
	}

	@Override
	public Boolean parseResponse(final JSONObject response) {
		return Boolean.TRUE;
	}
}
