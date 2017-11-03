package zlotindaniel.memorize.edit;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.data.request.*;
import zlotindaniel.memorize.topics.*;

public class EditTopicInteractor implements EditTopicDisplay.Listener {
	private Topic topic;
	private final EditTopicDisplay display;
	private final Network network;

	public EditTopicInteractor(final Topic topic, final EditTopicDisplay display, final Network network) {
		this.topic = topic;
		this.display = display;
		this.network = network;
	}

	public void start() {
		display.setListener(this);
		refresh();
	}

	@Override
	public void deleteTopic() {
		loading();
		network.delete(new DeleteRequest(
				"topics/index/" + topic.getId(),
				b -> display.navigateHome(),
				this::handleError));
	}

	@Override
	public void renameTopic(final String newName) {
		String verified = Utils.normalize(newName);

		if (verified.isEmpty() || topic.getName().equalsIgnoreCase(verified)) {
			return;
		}
		loading();

		new TopicService(network).updateTopic(topic.withName(verified),
				t -> {
					this.topic = t;
					refresh();
				},
				this::handleError);
	}

	private void loading() {
		display.bind(topic.getName(), true, "");
	}

	private void refresh() {
		display.bind(topic.getName(), false, "");
	}

	private void handleError(final Exception e) {
		display.bind(topic.getName(), false, e.getMessage());
	}
}
