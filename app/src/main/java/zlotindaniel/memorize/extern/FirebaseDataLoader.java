package zlotindaniel.memorize.extern;

import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.data.FirebaseToJson;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;

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
				.addListenerForSingleValueEvent(new FirebaseToJson(onSuccess, onFailure));
	}
}
