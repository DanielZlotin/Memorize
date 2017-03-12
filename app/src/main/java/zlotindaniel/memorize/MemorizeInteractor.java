package zlotindaniel.memorize;

import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;

public class MemorizeInteractor implements OnSuccess<List<Card>>, OnFailure {

	public interface Display {
		void showCard(String text);

		void showError(String text);

		void startLoading();

		void endLoading();
	}

	private final Display display;
	private final CardsDataLoader dataLoader;
	private final Stack<Card> cardStack = new Stack<>();
	private AtomicReference<Card> currentCard = new AtomicReference<>();

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
		cardStack.clear();
		cardStack.addAll(cards);
		display.endLoading();
		showNext();
	}

	@Override
	public void failure(Exception e) {
		display.endLoading();
		display.showError(e.getMessage());
	}

	private void showNext() {
		if (currentCard.get() == null) {
			if (cardStack.isEmpty()) {
				return;
			}
			currentCard.set(cardStack.pop());
			display.showCard(currentCard.get().getPhrase());
		} else {
			display.showCard(currentCard.get().getDefinition());
			currentCard.set(null);
		}
	}
}
