package zlotindaniel.memorize.edit;

public interface EditTopicDisplay {

	interface Listener {
		void deleteTopic();

		void renameTopic(String newName);
	}

	void setListener(Listener listener);

	void bind(String topicName, boolean loading, String error);

	void navigateHome();
}
