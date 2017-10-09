package zlotindaniel.memorize.cards;

public enum CardsPresentation {
	Loading, Error, Phrase, Definition;

	public boolean isProgressVisible() {
		return this == Loading;
	}

	public boolean isTitleVisbile() {
		return this == Phrase;
	}

	public boolean isTextVisible() {
		return !isProgressVisible();
	}
}
