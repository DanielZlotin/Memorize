package zlotindaniel.memorize;

import zlotindaniel.memorize.data.CardsDataLoader;
import zlotindaniel.memorize.mocks.TestDataLoader;

public class MemorizeUnitApplication extends MemorizeApplication {
	private TestDataLoader testDataLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		testDataLoader = new TestDataLoader();
	}

	@Override
	public CardsDataLoader getDataLoader() {
		return testDataLoader;
	}
}
