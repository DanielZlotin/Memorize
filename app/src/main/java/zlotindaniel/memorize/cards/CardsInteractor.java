package zlotindaniel.memorize.cards;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;

public class CardsInteractor {

	private List<Card> loadedCards;

	public interface Display {
		void showPhrase(String phrase);

		void showDefinition(String definition);

		void showError(String text);

		void startLoading();

		void endLoading();
	}

	private final Display display;
	private final DataLoader dataLoader;
	private final CardsStackShuffler shuffler;
	private final Stack<Card> cardStack = new Stack<>();
	private final CardsParser cardsParser = new CardsParser();
	private Card currentCard;

	public CardsInteractor(Display display, DataLoader dataLoader, CardsStackShuffler shuffler) {
		this.display = display;
		this.dataLoader = dataLoader;
		this.shuffler = shuffler;
	}

	public void start() {
		display.startLoading();
		try {
			dataLoader.load(new OnSuccess<Map<String, Object>>() {
				@Override
				public void success(Map<String, Object> stringObjectMap) {
					loadingSuccess(stringObjectMap);
				}
			}, new OnFailure() {
				@Override
				public void failure(Exception e) {
					loadingFailure(e);
				}
			});
		} catch (Exception e) {
			loadingFailure(e);
		}
	}

	public void onClick() {
		showNext();
	}

	private void loadingSuccess(Map<String, Object> payload) {
		this.loadedCards = cardsParser.parse(payload);
		currentCard = null;
		cardStack.clear();
		display.endLoading();
		showNext();
	}

	private void loadingFailure(Exception e) {
		display.endLoading();
		display.showError(e.getMessage());
	}

	private void showNext() {
		if (loadedCards == null || loadedCards.isEmpty()) {
			return;
		}
		if (cardStack.isEmpty()) {
			cardStack.addAll(loadedCards);
			shuffler.shuffle(cardStack);
		}
		if (currentCard == null) {
			currentCard = cardStack.pop();
			display.showPhrase(currentCard.phrase);
		} else {
			display.showDefinition(currentCard.definition);
			currentCard = null;
		}
	}
}
