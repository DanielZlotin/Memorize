package zlotindaniel.memorize;

import android.app.Application;

import zlotindaniel.memorize.data.FirebaseDataLoader;
import zlotindaniel.memorize.shuffle.DefaultCardShuffler;

public class MemorizeApplication extends Application {

	private final Config config = createConfig();

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public Config createConfig() {
		return new Config(
				new FirebaseDataLoader("Production", "RT"),
				new DefaultCardShuffler()
		);
	}

	public final Config getConfig() {
		return config;
	}
}
