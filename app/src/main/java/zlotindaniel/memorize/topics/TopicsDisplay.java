package zlotindaniel.memorize.topics;

import java.util.*;

public interface TopicsDisplay {

	interface Listener {
		void refresh();

		void createTopic(String name);
	}

	void bind(List<Topic> topics);

	void bind(String error);

	void setListener(Listener listener);
}
