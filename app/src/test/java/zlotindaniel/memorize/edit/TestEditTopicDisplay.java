package zlotindaniel.memorize.edit;

public class TestEditTopicDisplay implements EditTopicDisplay {
	public Listener listener;
	public boolean loading;
	public String error;
	public boolean navigateHomeCalled;

	@Override
	public void setListener(final Listener listener) {
		this.listener = listener;
	}

	@Override
	public void bind(final boolean loading, String error) {
		this.loading = loading;
		this.error = error;
	}

	@Override
	public void navigateHome() {
		navigateHomeCalled = true;
	}
}
