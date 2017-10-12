package zlotindaniel.memorize.data;

import org.json.JSONObject;

public interface JsonParser<T> {
	T parse(JSONObject o);
}
