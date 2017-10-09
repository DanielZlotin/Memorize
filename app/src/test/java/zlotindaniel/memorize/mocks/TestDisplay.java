package zlotindaniel.memorize.mocks;

import zlotindaniel.memorize.cards.CardsInteractor.Display;

public class TestDisplay implements Display {
	public String phrase;
	public String definition;
	public String error;
	public boolean loading;


	@Override
	public void showPhrase(String phrase) {
		this.phrase = phrase;
	}

	@Override
	public void showDefinition(String definition) {
		this.definition = definition;
	}

	@Override
	public void showError(String text) {
		this.error = text;
	}

	@Override
	public void startLoading() {
		loading = true;
	}

	@Override
	public void endLoading() {
		loading = false;
	}
}
