package zlotindaniel.memorize.edit;

public interface EditTopicDisplay {

	interface Listener {
		void deleteTopic();
	}

	void setListener(Listener listener);

	void bind(boolean loading, String error);

	void navigateHome();
}
