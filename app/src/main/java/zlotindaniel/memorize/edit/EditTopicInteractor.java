package zlotindaniel.memorize.edit;

import com.google.common.collect.*;

import java.util.*;

import zlotindaniel.memorize.cards.*;
import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.topics.*;

public class EditTopicInteractor implements EditTopicDisplay.Listener {
	private Topic topic;
	private final EditTopicDisplay display;
	private final TopicService topicService;
	private final CardsService cardsService;
	private List<Card> cards = Lists.newArrayList();

	public EditTopicInteractor(Topic topic, EditTopicDisplay display, TopicService topicService, CardsService cardsService) {
		this.topic = topic;
		this.display = display;
		this.topicService = topicService;
		this.cardsService = cardsService;
	}

	public void start() {
		display.setListener(this);
		loading();
		cardsService.readTopicCards(topic.getId(),
				cards -> {
					Collections.sort(cards, (a, b) -> a.getQuestion().compareToIgnoreCase(b.getQuestion()));
					this.cards = cards;
					refresh();
				}, this::handleError);
	}

	@Override
	public void deleteTopic() {
		loading();
		topicService.deleteTopic(topic, b -> display.navigateHome(), this::handleError);
	}

	@Override
	public void renameTopic(final String newName) {
		String verified = Utils.normalize(newName);

		if (verified.isEmpty() || topic.getName().equalsIgnoreCase(verified)) {
			return;
		}
		loading();

		topicService.updateTopic(topic.withName(verified),
				t -> {
					this.topic = t;
					refresh();
				}, this::handleError);
	}

	private void loading() {
		display.bind(topic.getName(), this.cards, true, "");
	}

	private void refresh() {
		display.bind(topic.getName(), this.cards, false, "");
	}

	private void handleError(final Exception e) {
		display.bind(topic.getName(), this.cards, false, e.getMessage());
	}
}
