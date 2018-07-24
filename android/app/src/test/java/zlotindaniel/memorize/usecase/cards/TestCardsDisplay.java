package zlotindaniel.memorize.usecase.cards;

public class TestCardsDisplay implements CardsDisplay {
	public CardsPresentation presentation;
	public String text;
	public Listener listener;

	@Override
	public void setListener(final Listener listener) {
		this.listener = listener;
	}

	@Override
	public void bind(final CardsPresentation presentation, final String text) {
		this.presentation = presentation;
		this.text = text;
	}
}
