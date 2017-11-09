package zlotindaniel.memorize.cards;

import com.google.common.collect.*;

import java.util.*;

import zlotindaniel.memorize.shuffle.*;

public class CardsInteractor implements CardsDisplay.Listener {

	private final String topicId;
	private final CardsDisplay display;
	private final CardService cardService;
	private final Shuffler shuffler;

	private final Stack<Card> cardStack = new Stack<>();
	private List<Card> loadedCards = Lists.newArrayList();
	private CardsPresentation presentation;

	public CardsInteractor(String topicId, CardsDisplay display, CardService cardService, Shuffler shuffler) {
		this.topicId = topicId;
		this.display = display;
		this.cardService = cardService;
		this.shuffler = shuffler;
	}

	public void start() {
		display.setListener(this);
		display(CardsPresentation.Loading, "");
		cardService.readTopicCards(topicId, this::handleSuccess, this::handleFailure);
	}

	@Override
	public void click() {
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
