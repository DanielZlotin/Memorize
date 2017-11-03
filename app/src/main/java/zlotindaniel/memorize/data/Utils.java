package zlotindaniel.memorize.data;

import com.google.common.base.*;
import com.google.common.collect.*;

import org.json.*;

import java.util.*;

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
		                  .trimAndCollapseFrom(Strings.nullToEmpty(in), ' ');
	}

	public static String capitalizeFully(String in) {
		List<String> lowercase = Splitter.on(CharMatcher.whitespace()).omitEmptyStrings().splitToList(in);
		List<String> result = Lists.newArrayList();
		for (String word : lowercase) {
			result.add(capitalize(word));
		}
		return Joiner.on(' ').join(result);
	}

	private static String capitalize(final String word) {
		return Character.toUpperCase(word.charAt(0)) + word.toLowerCase().substring(1);
	}

	public static Map<String, Object> toMap(JSONObject o) {
		Map<String, Object> result = Maps.newHashMap();

		Iterator<String> keys = o.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			Object value = o.opt(key);
			if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			} else if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}
			result.put(key, value);
		}
		return result;
	}

	private static List<Object> toList(final JSONArray json) {
		List<Object> result = Lists.newArrayList();
		for (int i = 0; i < json.length(); i++) {
			Object value = json.opt(i);
			if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			} else if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}
			result.add(value);
		}
		return result;
	}

	Utils() {
	}
}
