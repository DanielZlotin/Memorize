package zlotindaniel.memorize;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import zlotindaniel.memorize.data.CardsDataLoader;
import zlotindaniel.memorize.data.FirebaseDataLoader;

public class MemorizeApplication extends Application {
	private CardsDataLoader dataLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	public void init() {
		FirebaseDatabase.getInstance().setPersistenceEnabled(true);
		dataLoader = new FirebaseDataLoader();
	}

	public CardsDataLoader getDataLoader() {
		return dataLoader;
	}
}
