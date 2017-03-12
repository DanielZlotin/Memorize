package zlotindaniel.memorize;

public class MemorizeInteractor {
	private final Display display;
	private boolean clicked;

	public interface Display {
		void show(String text);
	}

	public MemorizeInteractor(Display display) {
		this.display = display;
		this.display.show("Phrase 1");
	}

	public void onClick() {
		if (clicked) {
			display.show("Phrase 2");
		} else {
			clicked = true;
			display.show("Definition 1");
		}
	}
}
