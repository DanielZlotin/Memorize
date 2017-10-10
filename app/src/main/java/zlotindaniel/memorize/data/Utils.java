package zlotindaniel.memorize.data;

import org.json.JSONObject;

public final class Utils {

	public static void jsonPut(JSONObject o, String name, Object value) {
		try {
			o.put(name, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	Utils() {
	}
}
