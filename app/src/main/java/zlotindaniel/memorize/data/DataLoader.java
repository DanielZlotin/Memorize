package zlotindaniel.memorize.data;

import java.util.Map;

public interface DataLoader {
	void load(OnSuccess<Map<String, Object>> onSuccess, OnFailure onFailure);
}
