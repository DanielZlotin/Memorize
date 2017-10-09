package zlotindaniel.memorize.cards;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

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

	private List<Card> loadedCards = Lists.newArrayList();

	private final CardsDisplay display;
	private final DataLoader dataLoader;
	private final Shuffler shuffler;
	private final Stack<Card> cardStack = new Stack<>();
	private final CardsParser cardsParser = new CardsParser();
	private Card currentCard;

	public CardsInteractor(CardsDisplay display, DataLoader dataLoader, Shuffler shuffler) {
		this.display = display;
		this.dataLoader = dataLoader;
		this.shuffler = shuffler;
	}

	public void start() {
		display.bind(CardsPresentation.Loading, "");
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
		showNext();
	}

	private void loadingFailure(Exception e) {
		display.bind(CardsPresentation.Error, e.getMessage());
	}

	private void showNext() {
		if (Iterables.isEmpty(loadedCards)) {
			loadingFailure(new RuntimeException("empty cards"));
			return;
		}
		if (cardStack.isEmpty()) {
			cardStack.addAll(loadedCards);
			shuffler.shuffle(cardStack);
		}
		if (currentCard == null) {
			currentCard = cardStack.pop();
			display.bind(CardsPresentation.Phrase, currentCard.getPhrase());
		} else {
			display.bind(CardsPresentation.Definition, currentCard.getDefinition());
			currentCard = null;
		}
	}
}
