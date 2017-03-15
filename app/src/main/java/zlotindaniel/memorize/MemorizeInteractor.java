package zlotindaniel.memorize;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import zlotindaniel.memorize.data.Card;
import zlotindaniel.memorize.data.CardsDataLoader;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;

public class MemorizeInteractor implements OnSuccess<List<Card>>, OnFailure {

	private List<Card> cards;

	public interface Display {
		void showPhrase(String phrase);

		void showDefinition(String definition);

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
		try {
			dataLoader.load(this, this);
		} catch (Exception e) {
			failure(e);
		}
	}

	public void onClick() {
		showNext();
	}

	@Override
	public void success(List<Card> cards) {
		this.cards = new ArrayList<>(cards);
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
			display.showPhrase(currentCard.getPhrase());
		} else {
			display.showDefinition(currentCard.getDefinition());
			currentCard = null;
		}
	}
}
