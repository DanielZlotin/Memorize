package zlotindaniel.memorize;

import android.app.Application;

import zlotindaniel.memorize.data.DefaultCardsStackShuffler;
import zlotindaniel.memorize.data.FirebaseDataLoader;

public class MemorizeApplication extends Application {

	private Config config;

	@Override
	public void onCreate() {
		super.onCreate();
		config = new Config(new FirebaseDataLoader(), new DefaultCardsStackShuffler());
	}

	public Config getConfig() {
		return config;
	}
}
