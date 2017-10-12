package zlotindaniel.memorize.topics;

import com.google.common.collect.Lists;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import zlotindaniel.memorize.data.JsonParser;
import zlotindaniel.memorize.data.Topic;

public class TopicsParser implements JsonParser<List<Topic>> {
	@Override
	public List<Topic> parse(final JSONObject o) {
		List<Topic> result = Lists.newArrayList();
		Iterator<String> keys = o.keys();
		while (keys.hasNext()) {
			String topicId = keys.next();
			JSONObject topicObj = o.optJSONObject(topicId);
			String name = topicObj.optString("name");

			result.add(Topic.create(topicId, name));
		}
		return result;
	}
}
