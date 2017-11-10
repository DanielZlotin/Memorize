package zlotindaniel.memorize.android;

import com.google.common.base.*;
import com.google.firebase.database.*;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.data.request.*;

public class FirebaseNetwork implements Network {
	private static final String VERSION = "v1";
	private static final String USERS = "users";

	private final String env;
	private String userId = "";

	public FirebaseNetwork(String env) {
		this.env = env;
	}

	@Override
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	@Override
	public void create(CreateRequest request) {
		if (Strings.isNullOrEmpty(userId)) {
			request.onFailure.failure(new RuntimeException("no userId"));
			return;
		}

		DatabaseReference ref = FirebaseDatabase.getInstance()
		                                        .getReference(fullpath(request.path))
		                                        .push();

		String id = ref.getKey();

		ref.setValue(Utils.toMap(request.payload.toJson()))
		   .addOnSuccessListener(o -> request.onSuccess.success(id))
		   .addOnFailureListener(request.onFailure::failure);
	}

	@Override
	public <T> void read(ReadRequest<T> request) {
		if (Strings.isNullOrEmpty(userId)) {
			request.onFailure.failure(new RuntimeException("no userId"));
			return;
		}

		FirebaseDatabase.getInstance()
		                .getReference(fullpath(request.path))
		                .addListenerForSingleValueEvent(
				                new FirebaseJsonResponseListener(
						                (jsonObject) -> request.onSuccess.success(request.jsonParser.parse(jsonObject)),
						                request.onFailure));
	}

	@Override
	public void update(UpdateRequest request) {
		if (Strings.isNullOrEmpty(userId)) {
			request.onFailure.failure(new RuntimeException("no userId"));
			return;
		}

		FirebaseDatabase.getInstance()
		                .getReference(fullpath(request.path))
		                .setValue(Utils.toMap(request.payload.toJson()))
		                .addOnSuccessListener(o -> request.onSuccess.success(Boolean.TRUE))
		                .addOnFailureListener(request.onFailure::failure);
	}

	@Override
	public void delete(DeleteRequest request) {
		if (Strings.isNullOrEmpty(userId)) {
			request.onFailure.failure(new RuntimeException("no userId"));
			return;
		}

		FirebaseDatabase.getInstance()
		                .getReference(fullpath(request.path))
		                .removeValue()
		                .addOnSuccessListener(o -> request.onSuccess.success(Boolean.TRUE))
		                .addOnFailureListener(request.onFailure::failure);
	}

	private String fullpath(String path) {
		return Joiner.on("/").join(VERSION, USERS, userId, env, path);
	}
}
