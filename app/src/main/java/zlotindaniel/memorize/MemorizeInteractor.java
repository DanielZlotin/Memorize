package zlotindaniel.memorize;

import java.util.List;

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

	public MemorizeInteractor(Display display, CardsDataLoader dataLoader) {
		this.display = display;
		this.dataLoader = dataLoader;
	}

	public void start() {
		display.startLoading();
		dataLoader.load(this, this);
	}

	public void onClick() {
		display.showCard(cards.get(0).getDefinition());
	}

	@Override
	public void success(List<Card> cards) {
		display.endLoading();
		this.cards = cards;
		display.showCard(cards.get(0).getPhrase());
	}

	@Override
	public void failure(Exception e) {
		display.endLoading();
		display.showError(e.getMessage());
	}
}
