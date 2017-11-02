package zlotindaniel.memorize.data;

import android.support.annotation.*;

import org.json.*;

import java.util.*;

public abstract class Payload extends Request<Boolean> {

	public Payload(@NonNull final String path,
	               @NonNull final OnSuccess<Boolean> onSuccess,
	               @NonNull final OnFailure onFailure) {
		super(path, onSuccess, onFailure);
	}

	@Override
	public Boolean parseResponse(final JSONObject response) {
		return Boolean.TRUE;
	}

	public abstract HashMap<String, Object> payload();
}
