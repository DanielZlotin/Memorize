package zlotindaniel.memorize.cards;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Stack;

import zlotindaniel.memorize.data.Loader;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;
import zlotindaniel.memorize.shuffle.Shuffler;

public class CardsInteractor {

	private final CardsDisplay display;
	private final Loader loader;
	private final Shuffler shuffler;
	private final Stack<Card> cardStack = new Stack<>();
	private List<Card> loadedCards = Lists.newArrayList();
	private Card currentCard;

	public CardsInteractor(CardsDisplay display, Loader loader, Shuffler shuffler) {
		this.display = display;
		this.loader = loader;
		this.shuffler = shuffler;
	}

	public void start() {
		display.bind(CardsPresentation.Loading, "");
		loader.load(new CardsRequest(new OnSuccess<List<Card>>() {
			@Override
			public void success(final List<Card> cards) {
				handleSuccess(cards);
			}
		}, new OnFailure() {
			@Override
			public void failure(final Exception e) {
				handleFailure(e);
			}
		}));
	}

	public void onClick() {
		showNext();
	}

	private void handleSuccess(List<Card> result) {
		this.loadedCards = result;
		currentCard = null;
		cardStack.clear();
		showNext();
	}

	private void handleFailure(Exception e) {
		display.bind(CardsPresentation.Error, e.getMessage());
	}

	private void showNext() {
		if (Iterables.isEmpty(loadedCards)) {
			handleFailure(new RuntimeException("empty cards"));
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
