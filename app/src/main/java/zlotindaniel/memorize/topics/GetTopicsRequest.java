package zlotindaniel.memorize.topics;

import com.google.common.collect.*;

import org.json.*;

import java.util.*;

import zlotindaniel.memorize.data.*;

public class GetTopicsRequest extends Request<List<Topic>> {
	public GetTopicsRequest(final OnSuccess<List<Topic>> onSuccess,
	                        final OnFailure onFailure) {
		super("topics/index", onSuccess, onFailure);
	}

	@Override
	public List<Topic> parseResponse(final JSONObject response) {
		List<Topic> result = Lists.newArrayList();
		Iterator<String> keys = response.keys();
		while (keys.hasNext()) {
			String topicId = keys.next();

			JSONObject topicObj = response.optJSONObject(topicId);
			String name = topicObj.optString("name");

			result.add(Topic.create(topicId, name));
		}
		return result;
	}
}
