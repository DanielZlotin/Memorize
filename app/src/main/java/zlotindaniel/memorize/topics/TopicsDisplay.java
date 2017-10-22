package zlotindaniel.memorize.topics;

import java.util.List;

public interface TopicsDisplay {

	interface Listener {
		void onTopicClicked(Topic topic);

		void onRefresh();

		void onTopicEditClicked(Topic topic);
	}

	void bind(List<Topic> topics);

	void bind(String error);

	void setListener(Listener listener);

	void navigateShowTopic(String topicId);

	void navigateEditTopic(String topicId);
}
