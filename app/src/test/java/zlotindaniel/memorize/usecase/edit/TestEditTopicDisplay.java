package zlotindaniel.memorize.usecase.edit;

import java.util.*;

import zlotindaniel.memorize.data.*;

public class TestEditTopicDisplay implements EditTopicDisplay {
	public Listener listener;
	public String topicName;
	public List<Card> cards;
	public boolean loading;
	public String error;
	public boolean navigateHomeCalled;

	@Override
	public void setListener(final Listener listener) {
		this.listener = listener;
	}

	@Override
	public void bind(final String topicName, List<Card> cards, final boolean loading, final String error) {
		this.topicName = topicName;
		this.cards = cards;
		this.loading = loading;
		this.error = error;
	}

	@Override
	public void navigateHome() {
		navigateHomeCalled = true;
	}
}
