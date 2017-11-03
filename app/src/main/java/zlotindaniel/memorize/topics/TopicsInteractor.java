package zlotindaniel.memorize.topics;

import com.google.common.base.*;
import com.google.common.collect.*;

import java.util.*;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.data.request.*;

public class TopicsInteractor implements TopicsDisplay.Listener {

	private final TopicsDisplay display;
	private final Network network;
	private List<Topic> topics = Collections.emptyList();


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
		if (Strings.isNullOrEmpty(normalized)) return;

		if (hasTopic(normalized)) {
			handleFailure(new RuntimeException("Topic already exists"));
		} else {
			network.create(new CreateRequest(
					"topics/index",
					new Topic("", normalized),
					b -> this.refresh(),
					this::handleFailure));
		}
	}

	private boolean hasTopic(final String normalized) {
		return Iterables.any(topics, topic -> topic.getName().equalsIgnoreCase(normalized));
	}

	private void handleSucess(final List<Topic> topics) {
		this.topics = topics;
		display.bind(topics);
	}

	private void handleFailure(final Exception e) {
		display.bind(e.getMessage());
	}
}
