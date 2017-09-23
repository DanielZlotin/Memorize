package zlotindaniel.memorize.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class FirebaseDataLoader implements DataLoader {
	private final String root;

	public FirebaseDataLoader(final String root) {
		this.root = root;
	}

	@Override
	public void load(final OnSuccess<Map<String, Object>> onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance()
				.getReference()
				.getRoot()
				.child(root)
				.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						try {
							Map<String, Object> value = value(dataSnapshot);
							onSuccess.success(value);
						} catch (Exception e) {
							e.printStackTrace();
							onFailure.failure(e);
						}
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {
						onFailure.failure(databaseError.toException());
					}
				});
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> value(DataSnapshot dataSnapshot) {
		Map<String, Object> result = (Map<String, Object>) dataSnapshot.getValue();
		if (result == null)
			throw new RuntimeException("dataSnapshot.getValue is null!");
		return result;
	}
}
