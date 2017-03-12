package zlotindaniel.memorize;

import java.util.List;

public class TestDataLoader implements CardsDataLoader {
	private List<Card> nextSuccess;

	public void setNextSuccess(List<Card> nextSuccess) {
		this.nextSuccess = nextSuccess;
	}

	@Override
	public void load(OnSuccess<List<Card>> onSuccess, OnFailure onFailure) {
		if (nextSuccess != null) {
			onSuccess.success(nextSuccess);
			nextSuccess = null;
		}
	}
}
