package zlotindaniel.memorize;

import android.app.Application;

public class MemorizeApplication extends Application {
	private DataLoader dataLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		dataLoader = new FirebaseDataLoader();
	}

	public DataLoader getDataLoader() {
		return dataLoader;
	}
}
