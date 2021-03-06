package zlotindaniel.memorize.usecase.cards;

public interface CardsDisplay {
	interface Listener {
		void click();

		void clickEasyCard();
	}

	void setListener(Listener listener);

	void bind(CardsPresentation presentation, String text);
}
