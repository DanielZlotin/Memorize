package zlotindaniel.memorize.cards;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Stack;

import zlotindaniel.memorize.data.Loader;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;
import zlotindaniel.memorize.shuffle.Shuffler;

public class CardsInteractor {

	private final String topicId;
	private final CardsDisplay display;
	private final Loader loader;
	private final Shuffler shuffler;

	private final Stack<Card> cardStack = new Stack<>();
	private List<Card> loadedCards = Lists.newArrayList();
	private CardsPresentation presentation;

	public CardsInteractor(String topicId, CardsDisplay display, Loader loader, Shuffler shuffler) {
		this.topicId = topicId;
		this.display = display;
		this.loader = loader;
		this.shuffler = shuffler;
	}

	public void start() {
		display(CardsPresentation.Loading, "");
		loader.load(new CardsRequest(topicId, new OnSuccess<List<Card>>() {
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
