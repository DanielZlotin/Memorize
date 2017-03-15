package zlotindaniel.memorize;

import android.app.Application;

import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.data.FirebaseDataLoader;

public class MemorizeApplication extends Application {
	private DataLoader dataLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	public void init() {
		dataLoader = new FirebaseDataLoader();
	}

	public DataLoader getDataLoader() {
		return dataLoader;
	}
}
