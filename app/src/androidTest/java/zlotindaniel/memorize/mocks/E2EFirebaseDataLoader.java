package zlotindaniel.memorize.mocks;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;

public class E2EFirebaseDataLoader implements DataLoader {
	@Override
	public void load(final OnSuccess<Map<String, Object>> onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance().getReference().getRoot().child("Test").addListenerForSingleValueEvent(new ValueEventListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				onSuccess.success((Map<String, Object>) dataSnapshot.getValue());
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				onFailure.failure(databaseError.toException());
			}
		});
	}
}
