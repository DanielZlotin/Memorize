package zlotindaniel.memorize.usecase.topics;

import java.util.*;

import zlotindaniel.memorize.data.*;

public interface TopicsDisplay {

	interface Listener {
		void refresh();

		void createTopic(String name);
	}

	void bind(List<Topic> topics);

	void bind(String error);

	void setListener(Listener listener);
}
