package zlotindaniel.memorize;

import java.util.List;

public class MemorizeInteractor implements OnSuccess<List<Card>>, OnFailure {
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

	@Override
	public void success(List<Card> cards) {
	}

	@Override
	public void failure(Exception e) {
		display.showError(e.getMessage());

	}

	public void start() {
		display.startLoading();
		dataLoader.load(this, this);
	}

	public void onClick() {
		display.showCard("Definition 1");
	}
}
