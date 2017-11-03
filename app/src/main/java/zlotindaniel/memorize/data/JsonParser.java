package zlotindaniel.memorize.data;

import org.json.*;

public interface JsonParser<T> {
	T parse(JSONObject json);
}
