package zlotindaniel.memorize.data.parser;

import org.json.*;

public interface JsonParser<T> {
	T parse(JSONObject json);
}
