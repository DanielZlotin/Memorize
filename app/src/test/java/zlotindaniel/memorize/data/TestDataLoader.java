package zlotindaniel.memorize.data;

import org.json.JSONObject;

public class TestDataLoader implements DataLoader {
	private JSONObject nextSuccess;
	private Exception nextError;
	private int rollingId = 1;

	public void setNextSuccess(String... keysAndValues) {
		nextSuccess = new JSONObject();
		for (int i = 0; i < keysAndValues.length; i += 2) {
			String phrase = keysAndValues[i];
			String definition = keysAndValues[i + 1];


			JSONObject cardObj = new JSONObject();
			Utils.jsonPut(cardObj, "phrase", phrase);
			Utils.jsonPut(cardObj, "definition", definition);

			String cardId = String.valueOf(rollingId++);

			Utils.jsonPut(nextSuccess, cardId, cardObj);

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
