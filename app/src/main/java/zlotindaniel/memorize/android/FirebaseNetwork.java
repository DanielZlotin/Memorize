package zlotindaniel.memorize.android;

import com.google.common.base.*;
import com.google.firebase.database.*;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.data.request.*;

public class FirebaseNetwork implements Network {
	private static final String VERSION = "v1";

	private final String env;

	public FirebaseNetwork(String env) {
		this.env = env;
	}

	@Override
	public void create(CreateRequest request) {
		FirebaseDatabase.getInstance()
		                .getReference(fullpath(request.path))
		                .push()
		                .setValue(Utils.toMap(request.payload.toJson()))
		                .addOnSuccessListener(o -> request.onSuccess.success(Boolean.TRUE))
		                .addOnFailureListener(request.onFailure::failure);
	}

	@Override
	public <T> void read(ReadRequest<T> request) {
		FirebaseDatabase.getInstance()
		                .getReference(fullpath(request.path))
		                .addListenerForSingleValueEvent(
				                new FirebaseJsonResponseListener(
						                (jsonObject) -> request.onSuccess.success(request.jsonParser.parse(jsonObject)),
						                request.onFailure));
	}

	@Override
	public void update(UpdateRequest request) {
		FirebaseDatabase.getInstance()
		                .getReference(fullpath(request.path))
		                .setValue(Utils.toMap(request.payload.toJson()))
		                .addOnSuccessListener(o -> request.onSuccess.success(Boolean.TRUE))
		                .addOnFailureListener(request.onFailure::failure);
	}

	@Override
	public void delete(DeleteRequest request) {
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
