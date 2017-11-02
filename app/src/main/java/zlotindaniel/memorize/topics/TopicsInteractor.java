package zlotindaniel.memorize.topics;

import android.support.annotation.*;

import com.google.common.base.*;

import java.util.*;

import zlotindaniel.memorize.data.*;

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
		network.load(new GetTopicsRequest(this::handleSucess, this::handleFailure));
	}

	@Override
	public void refresh() {
		load();
	}

	@Override
	public void createTopic(@NonNull String name) {
		String normalized = Utils.normalize(name);
		if (!Strings.isNullOrEmpty(normalized)) {
			network.save(new CreateTopicPayload(normalized, b -> refresh(), this::handleFailure));
		}
	}

	private void handleSucess(final List<Topic> topics) {
		display.bind(topics);
	}

	private void handleFailure(final Exception e) {
		display.bind(e.getMessage());
	}
}
