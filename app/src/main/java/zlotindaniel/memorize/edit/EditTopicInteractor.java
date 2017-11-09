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
	private final CardService cardService;
	private List<Card> cards = Lists.newArrayList();

	public EditTopicInteractor(Topic topic, EditTopicDisplay display, TopicService topicService, CardService cardService) {
		this.topic = topic;
		this.display = display;
		this.topicService = topicService;
		this.cardService = cardService;
	}

	public void start() {
		display.setListener(this);
		reload();
	}

	@Override
	public void deleteTopic() {
		displayLoading();
		topicService.deleteTopic(topic, b -> display.navigateHome(), this::displayError);
	}

	@Override
	public void renameTopic(final String newName) {
		String verified = Utils.normalize(newName);
		if (verified.isEmpty() || topic.getName().equalsIgnoreCase(verified)) {
			return;
		}

		displayLoading();
		topicService.updateTopic(topic.withName(verified),
				t -> {
					this.topic = t;
					displayRefresh();
				}, this::displayError);
	}

	@Override
	public void createCard(final String question, final String answer) {
		String verifiedQuestion = Utils.normalize(question);
		String verifiedAnswer = Utils.normalize(answer);
		if (verifiedQuestion.isEmpty() || verifiedAnswer.isEmpty()) {
			return;
		}

		displayLoading();
		cardService.createCard(topic.getId(), new Card("", verifiedQuestion, verifiedAnswer),
				c -> reload(),
				this::displayError);
	}

	@Override
	public void saveCard(final Card card, final String newQuestion, final String newAnswer) {
		String vQuestion = Utils.normalize(newQuestion);
		String vAnswer = Utils.normalize(newAnswer);
		if (vQuestion.isEmpty()
				|| vAnswer.isEmpty()
				|| (vQuestion.equals(card.getQuestion()) && vAnswer.equals(card.getAnswer()))) {
			return;
		}

		displayLoading();
		cardService.updateCard(topic.getId(), new Card(card.getId(), vQuestion, vAnswer), b -> reload(), this::displayError);
	}

	@Override
	public void deleteCard(final Card card) {
		displayLoading();
		cardService.deleteCard(topic.getId(), card, b -> reload(), this::displayError);
	}

	private void reload() {
		displayLoading();
		cardService.readTopicCards(topic.getId(),
				cards -> {
					this.cards = cards;
					Collections.sort(this.cards, (a, b) -> a.getQuestion().compareToIgnoreCase(b.getQuestion()));
					displayRefresh();
				}, this::displayError);
	}

	private void displayLoading() {
		display.bind(topic.getName(), this.cards, true, "");
	}

	private void displayRefresh() {
		display.bind(topic.getName(), this.cards, false, "");
	}

	private void displayError(final Exception e) {
		display.bind(topic.getName(), this.cards, false, e.getMessage());
	}
}
