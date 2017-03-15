package zlotindaniel.memorize;

import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.mocks.TestDataLoader;

public class MemorizeUnitApplication extends MemorizeApplication {
	private TestDataLoader testDataLoader;

	@Override
	public void init() {
		testDataLoader = new TestDataLoader();
	}

	@Override
	public DataLoader getDataLoader() {
		return testDataLoader;
	}
}
