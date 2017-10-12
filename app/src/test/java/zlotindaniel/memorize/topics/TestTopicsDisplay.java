package zlotindaniel.memorize.topics;

import java.util.List;

public class TestTopicsDisplay implements TopicsDisplay {

	public List<Topic> topics;
	public String error;

	@Override
	public void bind(final List<Topic> topics) {
		this.topics = topics;
	}

	@Override
	public void bind(final String error) {
		this.error = error;
	}
}
