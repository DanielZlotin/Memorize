package zlotindaniel.memorize.edit;

import java.util.*;

import zlotindaniel.memorize.cards.*;

public interface EditTopicDisplay {

	interface Listener {
		void deleteTopic();

		void renameTopic(String newName);
	}

	void setListener(Listener listener);

	void bind(String topicName, List<Card> cards, boolean loading, String error);

	void navigateHome();
}
