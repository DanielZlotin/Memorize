package zlotindaniel.memorize.topics;

import com.google.common.collect.*;

import java.util.*;

import zlotindaniel.memorize.data.*;

public class CreateTopicPayload extends Payload {
	private final String topicName;

	public CreateTopicPayload(String topicName, final OnSuccess<Boolean> onSuccess, OnFailure onFailure) {
		super("topics/index", onSuccess, onFailure);
		this.topicName = topicName;
	}


	@Override
	public HashMap<String, Object> payload() {
		HashMap<String, Object> o = Maps.newHashMap();
		o.put("name", topicName);
		return o;
	}
}
