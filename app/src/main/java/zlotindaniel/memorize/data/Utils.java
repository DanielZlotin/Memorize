package zlotindaniel.memorize.data;

import com.google.common.base.*;

import org.json.*;

public final class Utils {

	public static void jsonPut(JSONObject o, String name, Object value) {
		try {
			o.put(name, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String normalize(final String in) {
		return CharMatcher.whitespace()
		                  .or(CharMatcher.invisible())
		                  .collapseFrom(Strings.nullToEmpty(in), ' ')
		                  .trim();
	}

	Utils() {
	}
}
