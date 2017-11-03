package zlotindaniel.memorize.data;

import com.google.common.collect.*;

import org.json.*;

import java.util.*;

import zlotindaniel.memorize.topics.*;

public class TopicsListParser implements JsonParser<List<Topic>> {
	@Override
	public List<Topic> parse(final JSONObject json) {
		List<Topic> result = Lists.newArrayList();
		Iterator<String> keys = json.keys();
		while (keys.hasNext()) {
			String topicId = keys.next();

			JSONObject topicObj = json.optJSONObject(topicId);
			Utils.jsonPut(topicObj, "id", topicId);

			result.add(Topic.parse(topicObj));
		}
		return result;

	}
}
