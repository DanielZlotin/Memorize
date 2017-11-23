package zlotindaniel.memorize.android;

import com.google.common.base.*;
import com.google.firebase.database.*;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.data.parser.*;

public class FirebaseDatabaseAdapter implements Database {
	private static final String VERSION = "v1";
	private final String env;

	public FirebaseDatabaseAdapter(String env) {
		this.env = env;
	}

	@Override
	public void create(final String path, final ValueType payload, final OnSuccess<String> onSuccess, final OnFailure onFailure) {
		DatabaseReference ref = FirebaseDatabase.getInstance()
		                                        .getReference(fullpath(path))
		                                        .push();
		final String id = ref.getKey();

		ref.setValue(Utils.toMap(payload.toJson()))
		   .addOnSuccessListener(o -> onSuccess.success(id))
		   .addOnFailureListener(onFailure::failure);
	}

	@Override
	public <T> void read(final String path, final JsonParser<T> jsonParser, final OnSuccess<T> onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance()
		                .getReference(fullpath(path))
		                .addListenerForSingleValueEvent(
				                new FirebaseJsonResponseListener(
						                (jsonObject) -> onSuccess.success(jsonParser.parse(jsonObject)),
						                onFailure));
	}

	@Override
	public void update(final String path, final ValueType payload, final Runnable onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance()
		                .getReference(fullpath(path))
		                .setValue(Utils.toMap(payload.toJson()))
		                .addOnSuccessListener(o -> onSuccess.run())
		                .addOnFailureListener(onFailure::failure);
	}

	@Override
	public void delete(final String path, final Runnable onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance()
		                .getReference(fullpath(path))
		                .removeValue()
		                .addOnSuccessListener(o -> onSuccess.run())
		                .addOnFailureListener(onFailure::failure);
	}

	private String fullpath(String path) {
		return Joiner.on("/").join(VERSION, env, path);
	}
}
