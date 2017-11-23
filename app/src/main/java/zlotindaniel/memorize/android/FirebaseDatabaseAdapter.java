package zlotindaniel.memorize.android;

import com.google.firebase.database.*;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.data.parser.*;

public class FirebaseDatabaseAdapter implements Database {
	@Override
	public void create(final String path, final ValueType payload, final OnSuccess<String> onSuccess, final OnFailure onFailure) {
		DatabaseReference ref = FirebaseDatabase.getInstance()
		                                        .getReference(path)
		                                        .push();
		final String id = ref.getKey();

		ref.setValue(Utils.toMap(payload.toJson()))
		   .addOnSuccessListener(o -> onSuccess.success(id))
		   .addOnFailureListener(onFailure::failure);
	}

	@Override
	public <T> void read(final String path, final JsonParser<T> jsonParser, final OnSuccess<T> onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance()
		                .getReference(path)
		                .addListenerForSingleValueEvent(
				                new FirebaseJsonResponseListener(
						                (jsonObject) -> onSuccess.success(jsonParser.parse(jsonObject)),
						                onFailure));
	}

	@Override
	public void update(final String path, final ValueType payload, final Runnable onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance()
		                .getReference(path)
		                .setValue(Utils.toMap(payload.toJson()))
		                .addOnSuccessListener(o -> onSuccess.run())
		                .addOnFailureListener(onFailure::failure);
	}

	@Override
	public void delete(final String path, final Runnable onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance()
		                .getReference(path)
		                .removeValue()
		                .addOnSuccessListener(o -> onSuccess.run())
		                .addOnFailureListener(onFailure::failure);
	}

}
