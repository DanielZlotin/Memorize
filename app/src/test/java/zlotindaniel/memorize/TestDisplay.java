package zlotindaniel.memorize;

import zlotindaniel.memorize.MemorizeInteractor.Display;

public class TestDisplay implements Display {
	public String text;
	public boolean loading;

	@Override
	public void showCard(String text) {
		this.text = text;
	}

	@Override
	public void showError(String text) {
		this.text = text;
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
