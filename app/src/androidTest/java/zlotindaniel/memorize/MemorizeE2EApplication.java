package zlotindaniel.memorize;

import zlotindaniel.memorize.data.CardsStackNonShuffler;
import zlotindaniel.memorize.data.CardsStackShuffler;
import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.mocks.E2EFirebaseDataLoader;

public class MemorizeE2EApplication extends MemorizeApplication {

	@Override
	public DataLoader getDataLoader() {
		return new E2EFirebaseDataLoader();
	}

	@Override
	public CardsStackShuffler getCardsStackShuffler() {
		return new CardsStackNonShuffler();
	}
}
