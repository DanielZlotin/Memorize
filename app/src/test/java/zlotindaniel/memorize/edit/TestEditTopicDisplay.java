package zlotindaniel.memorize.edit;

public class TestEditTopicDisplay implements EditTopicDisplay {
	public Listener listener;
	public String topicName;
	public boolean loading;
	public String error;
	public boolean navigateHomeCalled;

	@Override
	public void setListener(final Listener listener) {
		this.listener = listener;
	}

	@Override
	public void bind(final String topicName, final boolean loading, final String error) {
		this.topicName = topicName;
		this.loading = loading;
		this.error = error;
	}

	@Override
	public void navigateHome() {
		navigateHomeCalled = true;
	}
}
