package zlotindaniel.memorize;

import java.util.List;
import java.util.Stack;

public class MemorizeInteractor implements OnSuccess<List<Card>>, OnFailure {

	private List<Card> cards;

	public interface Display {
		void showCard(String text);

		void showError(String text);

		void startLoading();

		void endLoading();
	}

	private final Display display;
	private final CardsDataLoader dataLoader;
	private final Stack<Card> cardStack = new Stack<>();
	private Card currentCard;

	public MemorizeInteractor(Display display, CardsDataLoader dataLoader) {
		this.display = display;
		this.dataLoader = dataLoader;
	}

	public void start() {
		display.startLoading();
		dataLoader.load(this, this);
	}

	public void onClick() {
		showNext();
	}

	@Override
	public void success(List<Card> cards) {
		this.cards = cards;
		currentCard = null;
		cardStack.clear();
		display.endLoading();
		showNext();
	}

	@Override
	public void failure(Exception e) {
		display.endLoading();
		display.showError(e.getMessage());
	}

	private void showNext() {
		if (cards == null || cards.isEmpty()) {
			return;
		}
		if (cardStack.isEmpty()) {
			cardStack.addAll(cards);
		}
		if (currentCard == null) {
			currentCard = cardStack.pop();
			display.showCard(currentCard.getPhrase());
		} else {
			display.showCard(currentCard.getDefinition());
			currentCard = null;
		}
	}
}
