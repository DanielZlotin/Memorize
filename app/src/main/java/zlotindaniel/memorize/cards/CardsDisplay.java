package zlotindaniel.memorize.cards;

public interface CardsDisplay {
	interface Listener {
		void click();
	}

	void setListener(Listener listener);

	void bind(CardsPresentation presentation, String text);
}
