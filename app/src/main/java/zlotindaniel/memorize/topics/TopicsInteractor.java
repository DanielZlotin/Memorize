package zlotindaniel.memorize.topics;

import java.util.List;

import zlotindaniel.memorize.data.Loader;

public class TopicsInteractor implements TopicsDisplay.Listener {

	private final TopicsDisplay display;
	private final Loader loader;


	public TopicsInteractor(TopicsDisplay display, Loader loader) {
		this.display = display;
		this.loader = loader;
	}

	public void start() {
		display.setListener(this);
		load();
	}

	private void load() {
		loader.load(new TopicsRequest(this::handleSucess, this::handleFailure));
	}

	@Override
	public void refresh() {
		load();
	}

	@Override
	public void createTopic(String name) {

	}

	private void handleSucess(final List<Topic> topics) {
		display.bind(topics);
	}

	private void handleFailure(final Exception e) {
		display.bind(e.getMessage());
	}
}
