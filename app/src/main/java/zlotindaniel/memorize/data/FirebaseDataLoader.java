package zlotindaniel.memorize.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseDataLoader implements CardsDataLoader {
	@Override
	public void load(final OnSuccess<List<Card>> onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance().getReference().getRoot().child("Production").child("RT").addListenerForSingleValueEvent(new ValueEventListener() {

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
	private void parseResult(DataSnapshot dataSnapshot, OnSuccess<List<Card>> onSuccess, OnFailure onFailure) {
		try {
			Map<String, String> allValues = (Map<String, String>) dataSnapshot.getValue();
			if (allValues == null) {
				onFailure.failure(new RuntimeException("dataSnapshot.getValue is null!"));
				return;
			}

			List<Card> result = new ArrayList<>();
			for (String key : allValues.keySet()) {
				result.add(new Card(key, allValues.get(key)));
			}

			onSuccess.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			onFailure.failure(e);
		}
	}
}
