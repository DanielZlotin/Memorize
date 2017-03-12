package zlotindaniel.memorize;

public class MemorizeInteractor {
	private final Display display;
	private final CardsDataLoader dataLoader;

	public interface Display {
		void show(String text);

		void startLoading();

		void endLoading();
	}

	public MemorizeInteractor(Display display, CardsDataLoader dataLoader) {
		this.display = display;
		this.dataLoader = dataLoader;
	}

	public void start() {
		display.startLoading();
//		display.show("Phrase 1");
	}

	public void onClick() {
		display.show("Definition 1");
	}
}
