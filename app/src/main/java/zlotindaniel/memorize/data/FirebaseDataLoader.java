package zlotindaniel.memorize.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class FirebaseDataLoader implements DataLoader {
	private final String root;
	private final String topic;

	public FirebaseDataLoader(final String root, final String topic) {
		this.root = root;
		this.topic = topic;
	}

	@Override
	public void load(final OnSuccess<Map<String, Object>> onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance().getReference().getRoot().child(root).child(topic).addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				parseResult(dataSnapshot, onSuccess, onFailure);
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				onFailure.failure(databaseError.toException());
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void parseResult(DataSnapshot dataSnapshot, OnSuccess<Map<String, Object>> onSuccess, OnFailure onFailure) {
		try {
			Map<String, Object> result = (Map<String, Object>) dataSnapshot.getValue();
			if (result == null) {
				onFailure.failure(new RuntimeException("dataSnapshot.getValue is null!"));
				return;
			}
			onSuccess.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			onFailure.failure(e);
		}
	}
}
