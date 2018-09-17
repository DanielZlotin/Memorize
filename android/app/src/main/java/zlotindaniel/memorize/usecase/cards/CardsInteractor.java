package zlotindaniel.memorize.usecase.cards;

import com.google.common.collect.*;

import java.util.*;

import zlotindaniel.memorize.data.*;

public class CardsInteractor implements CardsDisplay.Listener {

	private static Set<String> filterOutIds = Sets.newHashSet();

	private final String topicId;
	private final CardsDisplay display;
	private final DatabaseService service;
	private final Shuffler shuffler;

	private final Stack<Card> cardStack = new Stack<>();
	private List<Card> loadedCards = Lists.newArrayList();
	private CardsPresentation presentation;
	private Card currentCard;

	public CardsInteractor(String topicId, CardsDisplay display, DatabaseService service, Shuffler shuffler) {
		this.topicId = topicId;
		this.display = display;
		this.service = service;
		this.shuffler = shuffler;
	}

	public void start() {
		display.setListener(this);
		display(CardsPresentation.Loading, "");
		service.readTopicCards(topicId, this::handleSuccess, this::handleFailure);
	}

	@Override
	public void click() {
		next();
	}

	@Override
	public void clickEasyCard() {
		filterOutIds.add(currentCard.getId());
		loadedCards.remove(currentCard);
		next();
	}

	private void handleSuccess(List<Card> result) {
		Iterables.removeIf(result, (c) -> filterOutIds.contains(c.getId()));
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
			currentCard = cardStack.peek();
			display(CardsPresentation.Question, currentCard.getQuestion());
		} else {
			cardStack.pop();
			display(CardsPresentation.Answer, currentCard.getAnswer());
		}
	}

	private void display(final CardsPresentation presentation, final String text) {
		this.presentation = presentation;
		display.bind(presentation, text);
	}
}
