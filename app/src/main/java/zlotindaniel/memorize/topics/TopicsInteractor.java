package zlotindaniel.memorize.topics;

import java.util.*;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.data.request.*;

public class TopicsInteractor implements TopicsDisplay.Listener {

	private final TopicsDisplay display;
	private final Network network;

	public TopicsInteractor(TopicsDisplay display, Network network) {
		this.display = display;
		this.network = network;
	}

	public void start() {
		display.setListener(this);
		load();
	}

	private void load() {
		network.read(new ReadRequest<>("topics/index", new TopicsListParser(), this::handleSucess, this::handleFailure));
	}

	@Override
	public void refresh() {
		load();
	}

	@Override
	public void createTopic(String name) {
		String normalized = Utils.normalize(name);
		if (normalized.isEmpty()) return;

		new TopicService(network).create(normalized,
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
