package zlotindaniel.memorize.cards;

import org.json.JSONObject;

import java.util.List;
import java.util.Stack;

import zlotindaniel.memorize.data.Card;
import zlotindaniel.memorize.data.CardsParser;
import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;
import zlotindaniel.memorize.shuffle.Shuffler;

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
	private final Shuffler shuffler;
	private final Stack<Card> cardStack = new Stack<>();
	private final CardsParser cardsParser = new CardsParser();
	private Card currentCard;

	public CardsInteractor(Display display, DataLoader dataLoader, Shuffler shuffler) {
		this.display = display;
		this.dataLoader = dataLoader;
		this.shuffler = shuffler;
	}

	public void start() {
		display.startLoading();
		try {
			dataLoader.load(new OnSuccess<JSONObject>() {
				@Override
				public void success(JSONObject json) {
					loadingSuccess(json);
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

	private void loadingSuccess(JSONObject payload) {
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
			display.showPhrase(currentCard.getPhrase());
		} else {
			display.showDefinition(currentCard.getDefinition());
			currentCard = null;
		}
	}
}
