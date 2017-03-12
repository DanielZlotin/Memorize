package zlotindaniel.memorize.mocks;

import java.util.List;

import zlotindaniel.memorize.data.Card;
import zlotindaniel.memorize.data.CardsDataLoader;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;

public class TestDataLoader implements CardsDataLoader {
	private List<Card> nextSuccess;
	private Exception nextError;

	public void setNextSuccess(List<Card> nextSuccess) {
		this.nextSuccess = nextSuccess;
	}

	public void setNextError(Exception nextError) {
		this.nextError = nextError;
	}

	@Override
	public void load(OnSuccess<List<Card>> onSuccess, OnFailure onFailure) {
		if (nextSuccess != null) {
			onSuccess.success(nextSuccess);
			nextSuccess = null;
		} else if (nextError != null) {
			onFailure.failure(nextError);
			nextError = null;
		}
	}
}
