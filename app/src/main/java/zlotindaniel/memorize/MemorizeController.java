package zlotindaniel.memorize;

public class MemorizeController {
	private final Controlled controlled;

	public interface Controlled {
		void show(String text);
	}

	public MemorizeController(Controlled controlled) {
		this.controlled = controlled;
	}

	public void onClick() {
		controlled.show("Definition 1");
	}
}
