package zlotindaniel.memorize.extern;

import com.google.common.base.Joiner;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import zlotindaniel.memorize.data.FirebaseJsonResponseListener;
import zlotindaniel.memorize.data.Loader;
import zlotindaniel.memorize.data.OnSuccess;
import zlotindaniel.memorize.data.Request;

public class FirebaseLoader implements Loader {
	private static final String VERSION = "v1";

	private final String env;

	public FirebaseLoader(String env) {
		this.env = env;
	}

	@Override
	public <T> void load(final Request<T> request) {
		FirebaseDatabase.getInstance()
				.getReference(Joiner.on("/").join(VERSION, env, request.path))
				.addListenerForSingleValueEvent(new FirebaseJsonResponseListener(new OnSuccess<JSONObject>() {
					@Override
					public void success(final JSONObject jsonObject) {
						request.onSuccess.success(request.parser.parse(jsonObject));
					}
				}, request.onFailure));
	}
}
