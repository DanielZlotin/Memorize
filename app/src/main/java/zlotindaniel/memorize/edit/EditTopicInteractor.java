package zlotindaniel.memorize.edit;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.topics.*;

public class EditTopicInteractor implements EditTopicDisplay.Listener {
	private final Topic topic;
	private final EditTopicDisplay display;
	private final Network network;

	public EditTopicInteractor(final Topic topic, final EditTopicDisplay display, final Network network) {
		this.topic = topic;
		this.display = display;
		this.network = network;
	}

	public void start() {
		display.setListener(this);
	}

	@Override
	public void deleteTopic() {
		display.bind(true, "");
		network.delete(new DeleteTopicRequest(topic.getId(), b -> display.navigateHome(), e -> display.bind(false, e.getMessage())));
	}
}
