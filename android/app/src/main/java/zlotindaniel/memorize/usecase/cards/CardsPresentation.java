package zlotindaniel.memorize.usecase.cards;

public enum CardsPresentation {
	Loading, Error, Question, Answer;

	public boolean isProgressVisible() {
		return this == Loading;
	}

	public boolean isTitleVisbile() {
		return this == Question;
	}

	public boolean isTextVisible() {
		return !isProgressVisible();
	}
}
