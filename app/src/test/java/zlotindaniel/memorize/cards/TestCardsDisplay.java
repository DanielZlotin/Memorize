package zlotindaniel.memorize.cards;

public class TestCardsDisplay implements CardsDisplay {
	public CardsPresentation presentation;
	public String text;

	@Override
	public void bind(final CardsPresentation presentation, final String text) {
		this.presentation = presentation;
		this.text = text;
	}
}
