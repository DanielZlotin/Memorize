package zlotindaniel.memorize.topics;

import java.util.List;

public interface TopicsDisplay {
	void bind(List<Topic> topics);

	void bind(String error);
}
