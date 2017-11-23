package zlotindaniel.memorize.topics;

import java.util.*;

import zlotindaniel.memorize.data.*;

public class TopicsInteractor implements TopicsDisplay.Listener {

	private final TopicsDisplay display;
	private final DatabaseService service;

	public TopicsInteractor(TopicsDisplay display, DatabaseService service) {
		this.display = display;
		this.service = service;
	}

	public void start() {
		display.setListener(this);
		load();
	}

	private void load() {
		service.readAllTopics(this::handleSucess, this::handleFailure);
	}

	@Override
	public void refresh() {
		load();
	}

	@Override
	public void createTopic(String name) {
		String normalized = Utils.normalize(name);
		if (normalized.isEmpty()) return;

		service.createTopic(new Topic("", normalized),
				t -> this.refresh(),
				this::handleFailure);
	}

	private void handleSucess(final List<Topic> topics) {
		Collections.sort(topics, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
		display.bind(topics);
	}

	private void handleFailure(final Exception e) {
		display.bind(e.getMessage());
	}
}
