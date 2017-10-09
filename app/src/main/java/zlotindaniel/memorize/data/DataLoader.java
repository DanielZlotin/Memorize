package zlotindaniel.memorize.data;

import org.json.JSONObject;

public interface DataLoader {
	void load(OnSuccess<JSONObject> onSuccess, OnFailure onFailure);
}
