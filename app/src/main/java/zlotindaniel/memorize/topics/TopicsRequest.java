package zlotindaniel.memorize.topics;

import java.util.List;

import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;
import zlotindaniel.memorize.data.Request;

public class TopicsRequest extends Request<List<Topic>> {
	public TopicsRequest(final OnSuccess<List<Topic>> onSuccess, final OnFailure onFailure) {
		super("topics/index", new TopicsListParser(), onSuccess, onFailure);
	}
}
