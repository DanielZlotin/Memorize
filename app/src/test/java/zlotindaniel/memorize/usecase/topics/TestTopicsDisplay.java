package zlotindaniel.memorize.usecase.topics;

import java.util.List;

import zlotindaniel.memorize.data.*;

public class TestTopicsDisplay implements TopicsDisplay {

	public List<Topic> topics;
	public String error;
	public Listener listener;

	@Override
	public void bind(final List<Topic> topics) {
		this.topics = topics;
	}

	@Override
	public void bind(final String error) {
		this.error = error;
	}

	@Override
	public void setListener(final Listener listener) {
		this.listener = listener;
	}
}
