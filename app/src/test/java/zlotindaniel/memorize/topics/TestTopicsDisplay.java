package zlotindaniel.memorize.topics;

import com.google.common.collect.Lists;

import java.util.List;

public class TestTopicsDisplay implements TopicsDisplay {

	public List<Topic> topics;
	public String error;
	public Listener listener;
	public List<String> navigatedToTopics = Lists.newArrayList();

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

	@Override
	public void navigateShowTopic(final String topicId) {
		navigatedToTopics.add(topicId);
	}
}
