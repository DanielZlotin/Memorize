package zlotindaniel.memorize.mocks;

import java.util.HashMap;
import java.util.Map;

import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;

public class TestDataLoader implements DataLoader {
	private Map<String, Object> nextSuccess;
	private Exception nextError;

	public void setNextSuccess(Map<String, Object> nextSuccess) {
		this.nextSuccess = nextSuccess;
	}

	public void setNextSuccess(String... keysAndValues) {
		nextSuccess = new HashMap<>();
		for (int i = 0; i < keysAndValues.length; i += 2) {
			nextSuccess.put(keysAndValues[i], keysAndValues[i + 1]);
		}
	}

	public void setNextError(Exception nextError) {
		this.nextError = nextError;
	}

	@Override
	public void load(OnSuccess<Map<String, Object>> onSuccess, OnFailure onFailure) {
		if (nextSuccess != null) {
			onSuccess.success(nextSuccess);
			nextSuccess = null;
		} else if (nextError != null) {
			onFailure.failure(nextError);
			nextError = null;
		}
	}
}
