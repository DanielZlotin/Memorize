package zlotindaniel.memorize.extern;

import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.data.FirebaseJsonResponseListener;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;

public class FirebaseDataLoader implements DataLoader {
	private static final String VERSION = "v1";
	private static final String CATEGORY = "topics";
	private static final String DIV = "cards";

	private final String env;
	private final String topicId;

	public FirebaseDataLoader(final String env, String topicId) {
		this.env = env;
		this.topicId = topicId;
	}

	@Override
	public void load(final OnSuccess<JSONObject> onSuccess, final OnFailure onFailure) {
		FirebaseDatabase.getInstance()
				.getReference()
				.getRoot()
				.child(VERSION)
				.child(env)
				.child(CATEGORY)
				.child(DIV)
				.child(topicId)
				.addListenerForSingleValueEvent(new FirebaseJsonResponseListener(onSuccess, onFailure));
	}
}
