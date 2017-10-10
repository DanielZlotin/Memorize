package zlotindaniel.memorize.extern;

import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {
	public static void put(JSONObject o, String name, Object value) {
		try {
			o.putOpt(name, value);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
