package zlotindaniel.memorize.edit;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.topics.*;

public class EditTopicInteractor implements EditTopicDisplay.Listener {
	private Topic topic;
	private final EditTopicDisplay display;
	private final TopicService service;

	public EditTopicInteractor(Topic topic, EditTopicDisplay display, TopicService service) {
		this.topic = topic;
		this.display = display;
		this.service = service;
	}

	public void start() {
		display.setListener(this);
		refresh();
	}

	@Override
	public void deleteTopic() {
		loading();
		service.deleteTopic(topic, b -> display.navigateHome(), this::handleError);
	}

	@Override
	public void renameTopic(final String newName) {
		String verified = Utils.normalize(newName);

		if (verified.isEmpty() || topic.getName().equalsIgnoreCase(verified)) {
			return;
		}
		loading();

		service.updateTopic(topic.withName(verified),
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
