package zlotindaniel.memorize;

public class MemorizeUnitApplication extends MemorizeApplication {
	private TestDataLoader testDataLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		testDataLoader = new TestDataLoader();
	}

	@Override
	public DataLoader getDataLoader() {
		return testDataLoader;
	}
}
