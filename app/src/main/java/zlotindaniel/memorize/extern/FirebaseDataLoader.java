package zlotindaniel.memorize.extern;

import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.data.FirebaseToJson;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;

public class FirebaseDataLoader implements DataLoader {
	private static final String VERSION = "v1";
	private static final String CATEGORY = "topics";

	private final String env;
	private final String topic;

	public FirebaseDataLoader(final String env, final String topic) {
		this.env = env;
		this.topic = topic;
	}

	@Override
	public void load(final OnSuccess<JSONObject> onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance()
				.getReference()
				.getRoot()
				.child(VERSION)
				.child(env)
				.child(CATEGORY)
				.child(topic)
				.addListenerForSingleValueEvent(new FirebaseToJson(onSuccess, onFailure));
	}
}
