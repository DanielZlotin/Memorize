package zlotindaniel.memorize;

public class MemorizeE2EApplication extends MemorizeApplication {

	private E2EFirebaseDataLoader testDataLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		testDataLoader = new E2EFirebaseDataLoader();
	}

	@Override
	public CardsDataLoader getDataLoader() {
		return testDataLoader;
	}
}
