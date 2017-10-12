package zlotindaniel.memorize.topics;

import java.util.List;

import zlotindaniel.memorize.data.Topic;

public class TestTopicsDisplay implements TopicsDisplay {

	public List<Topic> topics;

	@Override
	public void bind(final List<Topic> topics) {
		this.topics = topics;
	}
}
