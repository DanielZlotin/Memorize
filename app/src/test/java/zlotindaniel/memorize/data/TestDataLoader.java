package zlotindaniel.memorize.data;

import org.json.JSONException;
import org.json.JSONObject;

public class TestDataLoader implements DataLoader {
	private JSONObject nextSuccess;
	private Exception nextError;

	public void setNextSuccess(JSONObject nextSuccess) {
		this.nextSuccess = nextSuccess;
	}

	public void setNextSuccess(String... keysAndValues) {
		nextSuccess = new JSONObject();
		for (int i = 0; i < keysAndValues.length; i += 2) {
			try {
				nextSuccess.put(keysAndValues[i], keysAndValues[i + 1]);
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void setNextError(Exception nextError) {
		this.nextError = nextError;
	}

	@Override
	public void load(OnSuccess<JSONObject> onSuccess, OnFailure onFailure) {
		if (nextSuccess != null) {
			onSuccess.success(nextSuccess);
			nextSuccess = null;
		} else if (nextError != null) {
			onFailure.failure(nextError);
			nextError = null;
		}
	}
}
