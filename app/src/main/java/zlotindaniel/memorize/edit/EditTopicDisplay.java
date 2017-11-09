package zlotindaniel.memorize.edit;

import java.util.*;

import zlotindaniel.memorize.cards.*;

public interface EditTopicDisplay {

	interface Listener {
		void deleteTopic();

		void renameTopic(String newName);

		void createCard(String question, String answer);

		void saveCard(Card card, String newQuestion, String newAnswer);

		void deleteCard(Card card);
	}

	void setListener(Listener listener);

	void bind(String topicName, List<Card> cards, boolean loading, String error);

	void navigateHome();
}
