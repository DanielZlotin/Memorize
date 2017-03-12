package zlotindaniel.memorize;

import android.app.Application;

import zlotindaniel.memorize.data.CardsDataLoader;
import zlotindaniel.memorize.data.FirebaseDataLoader;

public class MemorizeApplication extends Application {
	private CardsDataLoader dataLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		dataLoader = new FirebaseDataLoader();
	}

	public CardsDataLoader getDataLoader() {
		return dataLoader;
	}
}
