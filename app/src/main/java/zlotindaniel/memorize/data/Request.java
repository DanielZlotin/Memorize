package zlotindaniel.memorize.data;

import android.support.annotation.*;

import com.google.common.base.*;

import org.json.*;

public abstract class Request<T> {
	@NonNull
	public final String path;
	@NonNull
	public final OnSuccess<T> onSuccess;
	@NonNull
	public final OnFailure onFailure;

	public Request(@NonNull final String path,
	               @NonNull final OnSuccess<T> onSuccess,
	               @NonNull final OnFailure onFailure) {
		this.path = Preconditions.checkNotNull(Strings.emptyToNull(path));
		this.onSuccess = Preconditions.checkNotNull(onSuccess);
		this.onFailure = Preconditions.checkNotNull(onFailure);
	}

	abstract public T parseResponse(JSONObject response);
}
