package zlotindaniel.memorize.cards;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Stack;

import zlotindaniel.memorize.data.Network;
import zlotindaniel.memorize.shuffle.Shuffler;

public class CardsInteractor {

	private final String topicId;
	private final CardsDisplay display;
	private final Network network;
	private final Shuffler shuffler;

	private final Stack<Card> cardStack = new Stack<>();
	private List<Card> loadedCards = Lists.newArrayList();
	private CardsPresentation presentation;

	public CardsInteractor(String topicId, CardsDisplay display, Network network, Shuffler shuffler) {
		this.topicId = topicId;
		this.display = display;
		this.network = network;
		this.shuffler = shuffler;
	}

	public void start() {
		display(CardsPresentation.Loading, "");
		network.load(new GetCardsRequest(topicId, this::handleSuccess, this::handleFailure));
	}

	public void onClick() {
		next();
	}

	private void handleSuccess(List<Card> result) {
		this.loadedCards = result;
		cardStack.clear();
		next();
	}

	private void handleFailure(Exception e) {
		display(CardsPresentation.Error, e.getMessage());
	}

	private void next() {
		if (loadedCards.isEmpty()) {
			handleFailure(new RuntimeException("empty cards"));
			return;
		}

		if (cardStack.isEmpty()) {
			cardStack.addAll(loadedCards);
			shuffler.shuffle(cardStack);
		}

		if (presentation != CardsPresentation.Question) {
			display(CardsPresentation.Question, cardStack.peek().getQuestion());
		} else {
			display(CardsPresentation.Answer, cardStack.pop().getAnswer());
		}
	}

	private void display(final CardsPresentation presentation, final String text) {
		this.presentation = presentation;
		display.bind(presentation, text);
	}
}
