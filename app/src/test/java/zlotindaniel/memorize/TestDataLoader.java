package zlotindaniel.memorize;

import java.util.List;

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
