package zlotindaniel.memorize;

import android.app.Application;

import zlotindaniel.memorize.cards.DefaultCardsStackShuffler;
import zlotindaniel.memorize.data.FirebaseDataLoader;

public class MemorizeApplication extends Application {
	private Config config;

	@Override
	public void onCreate() {
		super.onCreate();
		config = new Config(new FirebaseDataLoader("Production"), new DefaultCardsStackShuffler());
	}

	public Config getConfig() {
		return config;
	}
}
