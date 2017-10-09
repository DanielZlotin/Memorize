package zlotindaniel.memorize.data;

import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

public class FirebaseDataLoader implements DataLoader {
	private final String root;
	private final String topic;

	public FirebaseDataLoader(final String root, final String topic) {
		this.root = root;
		this.topic = topic;
	}

	@Override
	public void load(final OnSuccess<JSONObject> onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance()
				.getReference()
				.getRoot()
				.child(root)
				.child(topic)
				.addListenerForSingleValueEvent(new FirebaseDataHandler(onSuccess, onFailure));
	}
}
