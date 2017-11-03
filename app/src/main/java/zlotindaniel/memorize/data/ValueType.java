package zlotindaniel.memorize.data;

import org.json.*;

public interface ValueType {
	JSONObject toJson();

	ValueType withId(String id);

	boolean hasId();

	String getId();
}
