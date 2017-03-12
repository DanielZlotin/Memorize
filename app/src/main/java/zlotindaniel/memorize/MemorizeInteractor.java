package zlotindaniel.memorize;

public class MemorizeInteractor {
	private final Display display;

	public interface Display {
		void show(String text);
	}

	public MemorizeInteractor(Display display) {
		this.display = display;
	}

	public void start() {
		display.show("Phrase 1");
	}

	public void onClick() {
		display.show("Definition 1");
	}
}
