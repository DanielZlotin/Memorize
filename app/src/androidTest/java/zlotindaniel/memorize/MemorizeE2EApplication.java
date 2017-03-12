package zlotindaniel.memorize;

import com.google.firebase.database.FirebaseDatabase;

import zlotindaniel.memorize.data.CardsDataLoader;
import zlotindaniel.memorize.mocks.E2EFirebaseDataLoader;

public class MemorizeE2EApplication extends MemorizeApplication {

	private E2EFirebaseDataLoader testDataLoader;

	@Override
	public void init() {
		FirebaseDatabase.getInstance().setPersistenceEnabled(true);
		testDataLoader = new E2EFirebaseDataLoader();
	}

	@Override
	public CardsDataLoader getDataLoader() {
		return testDataLoader;
	}
}
