package zlotindaniel.memorize.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.Map;

public class FirebaseDataHandler implements ValueEventListener {
	private final OnSuccess<JSONObject> onSuccess;
	private final OnFailure onFailure;

	public FirebaseDataHandler(final OnSuccess<JSONObject> onSuccess, final OnFailure onFailure) {
		this.onSuccess = onSuccess;
		this.onFailure = onFailure;
	}

	@Override
	public void onDataChange(final DataSnapshot dataSnapshot) {
		try {
			onSuccess.success(jsonValue(dataSnapshot));
		} catch (Exception e) {
			onFailure.failure(e);
		}
	}

	@Override
	public void onCancelled(final DatabaseError databaseError) {
		onFailure.failure(databaseError.toException());
	}

	private JSONObject jsonValue(final DataSnapshot dataSnapshot) {
		Map value = (Map) dataSnapshot.getValue();
		if (value == null) {
			throw new NullPointerException("value is null");
		}
		return new JSONObject(value);
	}
}