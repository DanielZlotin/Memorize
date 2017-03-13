package zlotindaniel.memorize;

import zlotindaniel.memorize.data.CardsDataLoader;
import zlotindaniel.memorize.mocks.E2EFirebaseDataLoader;

public class MemorizeE2EApplication extends MemorizeApplication {

	private E2EFirebaseDataLoader testDataLoader;

	@Override
	public void init() {
		testDataLoader = new E2EFirebaseDataLoader();
	}

	@Override
	public CardsDataLoader getDataLoader() {
		return testDataLoader;
	}
}
