package zlotindaniel.memorize.mocks;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zlotindaniel.memorize.data.Card;
import zlotindaniel.memorize.data.CardsDataLoader;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;

public class E2EFirebaseDataLoader implements CardsDataLoader {
	@Override
	public void load(final OnSuccess<List<Card>> onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance().getReference().getRoot().child("Test").addListenerForSingleValueEvent(new ValueEventListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				Map<String, String> allValues = (Map<String, String>) dataSnapshot.getValue();

				List<Card> result = new ArrayList<>();
				for (String key : allValues.keySet()) {
					result.add(new Card(key, allValues.get(key)));
				}

				onSuccess.success(result);
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				onFailure.failure(databaseError.toException());
			}
		});
	}
}
