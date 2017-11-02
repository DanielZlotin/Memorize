package zlotindaniel.memorize.android;

import com.google.common.base.*;
import com.google.firebase.database.*;

import org.json.*;

import zlotindaniel.memorize.data.*;

public class FirebaseNetwork implements Network {
	private static final String VERSION = "v1";

	private final String env;

	public FirebaseNetwork(String env) {
		this.env = env;
	}

	@Override
	public <T> void load(final Request<T> request) {

		OnSuccess<JSONObject> onSuccess = (jsonObject) -> request.onSuccess.success(request.parseResponse(jsonObject));
		OnFailure onFailure = request.onFailure;

		FirebaseDatabase.getInstance()
		                .getReference(fullpath(request.path))
		                .addListenerForSingleValueEvent(new FirebaseJsonResponseListener(onSuccess, onFailure));
	}

	@Override
	public void save(final Payload request) {
		FirebaseDatabase.getInstance()
		                .getReference(fullpath(request.path))
		                .push()
		                .setValue(request.payload())
		                .addOnSuccessListener(o -> request.onSuccess.success(Boolean.TRUE))
		                .addOnFailureListener(request.onFailure::failure);
	}

	@Override
	public void delete(final DeleteRequest request) {
		FirebaseDatabase.getInstance()
		                .getReference(fullpath(request.path))
		                .removeValue()
		                .addOnSuccessListener(o -> request.onSuccess.success(Boolean.TRUE))
		                .addOnFailureListener(request.onFailure::failure);
	}

	private String fullpath(String path) {
		return Joiner.on("/").join(VERSION, env, path);
	}
}
